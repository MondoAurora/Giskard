package me.giskard.dust.io.json;

import java.io.IOException;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

import me.giskard.Mind;

public class DustIOJsonReader implements DustIOJsonConsts {
	public static class JsonContentDispatcher implements ContentHandler {
		JsonContext ctx;
		MiNDAgent processor;

		public JsonContentDispatcher(MiNDAgent processor) {
			ctx = new JsonContext();
			this.processor = processor;
		}

		boolean processJsonEvent(MiNDAgentAction action, JsonBlock block, Object param) {
			ctx.block = block;
			ctx.param = param;

			try {
				MiNDResultType ret = processor.process(action, ctx);

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
			return processJsonEvent(MiNDAgentAction.BEGIN, JsonBlock.Entry, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, JsonBlock.Entry, null);
		}

		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.PROCESS, null, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, JsonBlock.Object, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, JsonBlock.Object, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.BEGIN, JsonBlock.Array, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.END, JsonBlock.Array, null);
		}
	}
}
