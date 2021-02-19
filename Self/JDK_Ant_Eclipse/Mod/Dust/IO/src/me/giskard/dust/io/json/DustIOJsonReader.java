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
			Mind.access(MiNDAccessCommand.SET, param, MT_IO_SERIALIZEEVENT, MT_VARIANT_VALUE);
			Mind.access(MiNDAccessCommand.SET, block, MT_IO_SERIALIZEEVENT, MT_IO_SERIALIZEITEM);
			
			if ( (MiNDAgentAction.BEGIN == action) || (MT_IO_SERIALIZEITEM_VALUE == block) ) {
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
			processJsonEvent(MiNDAgentAction.INIT, null, null);
		}

		@Override
		public void endJSON() throws ParseException, IOException {
			processJsonEvent(MiNDAgentAction.RELEASE, null, null);
		}

		@Override
		public boolean startObjectEntry(String arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_IO_SERIALIZEITEM_KEY, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_IO_SERIALIZEITEM_KEY, null);
		}

		
		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.PROCESS, MT_IO_SERIALIZEITEM_VALUE, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_IO_SERIALIZEITEM_MAP, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_IO_SERIALIZEITEM_MAP, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, MT_IO_SERIALIZEITEM_ARRAY, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, MT_IO_SERIALIZEITEM_ARRAY, null);
		}
	}
}
