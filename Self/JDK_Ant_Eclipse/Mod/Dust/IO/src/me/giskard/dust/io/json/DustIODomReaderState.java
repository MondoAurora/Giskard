package me.giskard.dust.io.json;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

import me.giskard.Mind;
import me.giskard.coll.MindCollStack;
import me.giskard.tokens.DustTokensMind;
import me.giskard.utils.MindTokenTranslator;
import me.giskard.utils.MindUtils;

@SuppressWarnings("rawtypes")
public class DustIODomReaderState implements DustIOJsonConsts, DustTokensMind {
	static final MiNDToken TC_ACTION = MT_NARRATIVE_AGENTACTION;
	
	static StringBuilder DEBUG_INDENT = new StringBuilder();


	static class SerializeStep {
		public MiNDAgentAction action;
		public SerializeItem item;
		public Object data;

		public void reset() {
			data = null;
		}

		public void setAll(MiNDAgentAction action, SerializeItem item, Object data) {
			this.action = action;
			this.item = item;
			this.data = data;
		}

		public void publish(MiNDToken target) {
			MindTokenTranslator.setEnumToken(MT_IO_SERIALIZEEVENT, item);
			Object val = (item == SerializeItem.Key) ? ((Map.Entry)data).getKey() : (item == SerializeItem.Value) ? data : null;
			Mind.access(MiNDAccessCommand.SET, val, MT_IO_SERIALIZEEVENT, MT_VARIANT_VALUE);
			MindTokenTranslator.setEnumToken(MT_IO_SERIALIZEEVENT, action);
			
			if ( action == MiNDAgentAction.END) {
				DEBUG_INDENT.delete(0, 2);
			}
			Mind.log(MiNDEventLevel.TRACE, "SerEvent ", DEBUG_INDENT, action, item, val);
			if ( action == MiNDAgentAction.BEGIN) {
				DEBUG_INDENT.append("  ");
			}
		}
		
		@Override
		public String toString() {
			return action + " " + item + " " + data;
		}
	}

	MiNDBuilder<Integer, SerializeStep> serStepBuilder = new MiNDBuilder<Integer, SerializeStep>() {
		@Override
		public SerializeStep create(Integer key) {
			return new SerializeStep();
		}

		@Override
		public void release(Integer key, SerializeStep val) {
			val.reset();
		}
	};

	SerializeStep step;
	MindCollStack<SerializeStep> procStack = new MindCollStack<SerializeStep>(serStepBuilder);

	public DustIODomReaderState(Reader r) throws Exception {
		setCurrOb(JSONValue.parseWithException(r));
	}

	public DustIODomReaderState(String str) throws Exception {
		setCurrOb(JSONValue.parseWithException(str));
	}

	void setCurrOb(Object newOb) {
		MiNDAgentAction action = MiNDAgentAction.BEGIN;
		Object ob = newOb;
		SerializeItem item;

		if ( newOb instanceof List ) {
			item = SerializeItem.Array;
			ob = ((List) newOb).iterator();
		} else if ( newOb instanceof Map ) {
			item = SerializeItem.Map;
			ob = ((Map) newOb).entrySet().iterator();
		} else if ( newOb instanceof Map.Entry ) {
			item = SerializeItem.Key;
		} else {
			item = SerializeItem.Value;
			action = MiNDAgentAction.PROCESS;
		}

		step = procStack.step(1);
		step.setAll(action, item, ob);
	}

	public boolean step() {
		boolean ret = true;

		step.publish(MT_IO_SERIALIZEEVENT);

		if ( (step.item == SerializeItem.Value) || (step.action == MiNDAgentAction.END) ) {
			step = procStack.step(-1);
		}
		
		if ( null == step ) {
			ret = false;
		} else {
			switch ( step.item ) {
			case Value:
				MindUtils.wrapException(null, "Should not get here");
				break;
			case Key:
				if ( step.action == MiNDAgentAction.BEGIN ) {
					step.action = MiNDAgentAction.END;
					setCurrOb(((Map.Entry<?, ?>) step.data).getValue());
				}

				break;
			case Map:
			case Array:
				Iterator<?> it = (Iterator) step.data;
				if ( it.hasNext() ) {
					setCurrOb(it.next());
				} else {
					step.action = MiNDAgentAction.END;
				}
				break;
			}
		}
		return ret;
	}

	public void reset() {

	}
}
