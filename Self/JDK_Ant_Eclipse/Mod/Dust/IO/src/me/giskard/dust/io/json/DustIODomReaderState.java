package me.giskard.dust.io.json;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

import me.giskard.Mind;
import me.giskard.coll.MindCollStack;
import me.giskard.dust.io.DustIOSerializeStep;
import me.giskard.tokens.DustTokensMind;

@SuppressWarnings("rawtypes")
public class DustIODomReaderState implements DustIOJsonConsts, DustTokensMind {
	static final MiNDToken TC_ACTION = MT_NARRATIVE_AGENTACTION;

	DustIOSerializeStep step;
	MindCollStack<DustIOSerializeStep> procStack = new MindCollStack<DustIOSerializeStep>(DustIOSerializeStep.BUILDER);
	StringBuilder indent = new StringBuilder();

	public DustIODomReaderState(Reader r) throws Exception {
		setCurrOb(JSONValue.parseWithException(r));
	}

	public DustIODomReaderState(String str) throws Exception {
		setCurrOb(JSONValue.parseWithException(str));
	}

	void setCurrOb(Object newOb) {
		MiNDAgentAction action = MiNDAgentAction.Begin;
		Object ob = newOb;
		MiNDToken item;

		if ( newOb instanceof List ) {
			item = MT_IDEA_COLLTYPE_ARR;
			ob = ((List) newOb).iterator();
		} else if ( newOb instanceof Map ) {
			item = MT_IDEA_COLLTYPE_MAP;
			ob = ((Map) newOb).entrySet().iterator();
		} else if ( newOb instanceof Map.Entry ) {
			item = MT_IDEA_VALTYPE_REF;
		} else {
			item = MT_IDEA_VALTYPE_RAW;
			action = MiNDAgentAction.Process;
		}

		step = procStack.step(1);
		step.setAll(action, item, ob);
	}

	public boolean step() {
		boolean ret = true;

		Object val = step.publish(MT_IO_SERIALIZEEVENT);
		
		if ( step.action == MiNDAgentAction.End) {
			indent.delete(0, 2);
		}
		Mind.log(MiNDEventLevel.TRACE, "SerEvent ", indent, step.action, step.item, val);
		if ( step.action == MiNDAgentAction.Begin) {
			indent.append("  ");
		}

		if ( (step.item == MT_IDEA_VALTYPE_RAW) || (step.action == MiNDAgentAction.End) ) {
			step = procStack.step(-1);
		}
		
		if ( null == step ) {
			ret = false;
		} else {
			if (step.item == MT_IDEA_VALTYPE_REF) {
				if ( step.action == MiNDAgentAction.Begin ) {
					step.action = MiNDAgentAction.End;
					setCurrOb(((Map.Entry<?, ?>) step.data).getValue());
				}
			} else {
				Iterator<?> it = (Iterator) step.data;
				if ( it.hasNext() ) {
					setCurrOb(it.next());
				} else {
					step.action = MiNDAgentAction.End;
				}
			}
		}
		return ret;
	}

	public void reset() {

	}
}
