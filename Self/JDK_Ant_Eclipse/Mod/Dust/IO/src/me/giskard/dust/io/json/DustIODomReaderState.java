package me.giskard.dust.io.json;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

import me.giskard.Giskard;
import me.giskard.coll.GisCollStack;
import me.giskard.dust.io.DustIOSerializeStep;
import me.giskard.tokens.DustTokensMind;

@SuppressWarnings("rawtypes")
public class DustIODomReaderState implements DustIOJsonConsts, DustTokensMind {
	static final MiNDToken TC_ACTION = MTTAG_NARRATIVE_AGENTACTION;

	DustIOSerializeStep step;
	GisCollStack<DustIOSerializeStep> procStack = new GisCollStack<DustIOSerializeStep>(DustIOSerializeStep.BUILDER);
	StringBuilder indent = new StringBuilder();

	public DustIODomReaderState(Reader r) throws Exception {
		setCurrOb(JSONValue.parseWithException(r));
	}

	public DustIODomReaderState(String str) throws Exception {
		setCurrOb(JSONValue.parseWithException(str));
	}

	void setCurrOb(Object newOb) {
		MiNDToken action = MTTAG_NARRATIVE_AGENTACTION_BEGIN;
		Object ob = newOb;
		MiNDToken item;

		if ( newOb instanceof List ) {
			item = MTTAG_IDEA_COLLTYPE_ARR;
			ob = ((List) newOb).iterator();
		} else if ( newOb instanceof Map ) {
			item = MTTAG_IDEA_COLLTYPE_MAP;
			ob = ((Map) newOb).entrySet().iterator();
		} else if ( newOb instanceof Map.Entry ) {
			item = MTTAG_IDEA_VALTYPE_LINK;
		} else {
			item = MTTAG_IDEA_VALTYPE_RAW;
			action = MTTAG_NARRATIVE_AGENTACTION_PROCESS;
		}

		step = procStack.step(1);
		step.setAll(action, item, ob);
	}

	public boolean step() {
		boolean ret = true;

		Object val = step.publish(MTTYP_IO_SERIALIZEEVENT);
		
		if ( step.action == MTTAG_NARRATIVE_AGENTACTION_END) {
			indent.delete(0, 2);
		}
		Giskard.log(MiNDEventLevel.Trace, "SerEvent ", indent, step.action, step.item, val);
		if ( step.action == MTTAG_NARRATIVE_AGENTACTION_BEGIN) {
			indent.append("  ");
		}

		if ( (step.item == MTTAG_IDEA_VALTYPE_RAW) || (step.action == MTTAG_NARRATIVE_AGENTACTION_END) ) {
			step = procStack.step(-1);
		}
		
		if ( null == step ) {
			ret = false;
		} else {
			if (step.item == MTTAG_IDEA_VALTYPE_LINK) {
				if ( step.action == MTTAG_NARRATIVE_AGENTACTION_BEGIN ) {
					step.action = MTTAG_NARRATIVE_AGENTACTION_END;
					setCurrOb(((Map.Entry<?, ?>) step.data).getValue());
				}
			} else {
				Iterator<?> it = (Iterator) step.data;
				if ( it.hasNext() ) {
					setCurrOb(it.next());
				} else {
					step.action = MTTAG_NARRATIVE_AGENTACTION_END;
				}
			}
		}
		return ret;
	}

	public void reset() {

	}
}
