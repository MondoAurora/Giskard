package me.giskard.dust.io.json;

import java.io.IOException;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.tools.GisToolsCounter;

public class DustIOJsonReader implements DustIOJsonConsts {
	public static class JsonContentDispatcher implements ContentHandler {
		MiNDAgent processor;
		GisToolsCounter counter;

		public JsonContentDispatcher(MiNDAgent processor) {
			this.processor = processor;
			counter = new GisToolsCounter("JSON reader", false, 5000);
			counter.setShowMem(true);
		}

		boolean processJsonEvent(MiNDAgentAction action, MiNDToken block, Object param) {
			Giskard.access(MiNDAccessCommand.Set, param, MTTYPE_SERIALIZEEVENT, MTMEMBER_VARIANT_VALUE);
			Giskard.access(MiNDAccessCommand.Set, block, MTTYPE_SERIALIZEEVENT, MTMEMBER_SERIALIZEEVENT_TYPE);
			
			if ( (MiNDAgentAction.Begin == action) || (MTTAG_COLLTYPE_ONE == block) ) {
				counter.add(block);
			}
			
			try {
				MiNDResultType ret = MiNDResultType.ACCEPT_READ; // processor.process(action);

				switch ( ret ) {
				case NOTIMPLEMENTED:
					return GiskardException.wrap(null);
				case REJECT:
					return false;
				default:
					return true;
				}
			} catch (Exception e) {
				return GiskardException.wrap(e);
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
			return processJsonEvent(MiNDAgentAction.Begin, MTTAG_VALTYPE_REF, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MTTAG_VALTYPE_REF, null);
		}

		
		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Process, MTTAG_COLLTYPE_ONE, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Begin, MTTAG_COLLTYPE_MAP, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MTTAG_COLLTYPE_MAP, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.Begin, MTTAG_COLLTYPE_ARR, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MiNDAgentAction.End, MTTAG_COLLTYPE_ARR, null);
		}
	}
}
