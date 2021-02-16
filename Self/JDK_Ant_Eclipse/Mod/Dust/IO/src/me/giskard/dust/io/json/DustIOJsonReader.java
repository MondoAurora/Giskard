package me.giskard.dust.io.json;

import java.io.IOException;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

import me.giskard.Mind;

public class DustIOJsonReader implements DustIOJsonConsts {
	public static class JsonContentDispatcher implements ContentHandler {
		MiNDAgent processor;

		public JsonContentDispatcher(MiNDAgent processor) {
			this.processor = processor;
		}

		boolean processJsonEvent(MiNDAgentAction action, MiNDToken block, Object param) {
			Mind.access(MiNDAccessCommand.SET, param, MT_JSON_EVENT, MT_VARIANT_VALUE);
			Mind.access(MiNDAccessCommand.SET, block, MT_JSON_EVENT, MT_JSONEVENT_BLOCK);
			
			try {
				MiNDResultType ret = MiNDResultType.ACCEPT_READ; // processor.process(action);

				switch ( ret ) {
				case NOTIMPLEMENTED:
					return Mind.wrapException(null);
				case REJECT:
					return false;
				default:
					return true;
				}
			} catch (Exception e) {
				return Mind.wrapException(e);
			}
		}

		@Override
		public void startJSON() throws ParseException, IOException {
			processJsonEvent(MiNDAgentAction.INIT, null, null);
		}

		@Override
		public void endJSON() throws ParseException, IOException {
			processJsonEvent(MiNDAgentAction.RELEASE, null, null);
		}

		@Override
		public boolean startObjectEntry(String arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_JSON_BLOCKTYPE_ENTRY, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_JSON_BLOCKTYPE_ENTRY, null);
		}

		
		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.PROCESS, MT_JSON_BLOCKTYPE_PRIMITIVE, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_JSON_BLOCKTYPE_OBJECT, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_JSON_BLOCKTYPE_OBJECT, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_JSON_BLOCKTYPE_ARRAY, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_JSON_BLOCKTYPE_ARRAY, null);
		}
	}
}
