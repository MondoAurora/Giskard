package me.giskard.dust.io.json;

import java.io.IOException;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

import me.giskard.Mind;
import me.giskard.utils.MindDevCounter;
import me.giskard.utils.MindUtils;

public class DustIOJsonReader implements DustIOJsonConsts {
	public static class JsonContentDispatcher implements ContentHandler {
		MiNDAgent processor;
		MindDevCounter counter;

		public JsonContentDispatcher(MiNDAgent processor) {
			this.processor = processor;
			counter = new MindDevCounter("JSON reader", false, 5000);
			counter.setShowMem(true);
		}

		boolean processJsonEvent(MiNDAgentAction action, MiNDToken block, Object param) {
			Mind.access(MiNDAccessCommand.Set, param, MT_IO_SERIALIZEEVENT, MT_VARIANT_VALUE);
			Mind.access(MiNDAccessCommand.Set, block, MT_IO_SERIALIZEEVENT, MT_IO_SERIALIZEEVENT_TYPE);
			
			if ( (MiNDAgentAction.Begin == action) || (MT_IDEA_COLLTYPE_ONE == block) ) {
				counter.add(block);
			}
			
			try {
				MiNDResultType ret = MiNDResultType.ACCEPT_READ; // processor.process(action);

				switch ( ret ) {
				case NOTIMPLEMENTED:
					return MindUtils.wrapException(null);
				case REJECT:
					return false;
				default:
					return true;
				}
			} catch (Exception e) {
				return MindUtils.wrapException(e);
			}
		}
		
		@Override
		public String toString() {
			return counter.toString();
		}

		@Override
		public void startJSON() throws ParseException, IOException {
			processJsonEvent(MiNDAgentAction.Init, null, null);
		}

		@Override
		public void endJSON() throws ParseException, IOException {
			processJsonEvent(MiNDAgentAction.Release, null, null);
		}

		@Override
		public boolean startObjectEntry(String arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Begin, MT_IDEA_VALTYPE_REF, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MT_IDEA_VALTYPE_REF, null);
		}

		
		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Process, MT_IDEA_COLLTYPE_ONE, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Begin, MT_IDEA_COLLTYPE_MAP, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MT_IDEA_COLLTYPE_MAP, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Begin, MT_IDEA_COLLTYPE_ARR, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MT_IDEA_COLLTYPE_ARR, null);
		}
	}
}
