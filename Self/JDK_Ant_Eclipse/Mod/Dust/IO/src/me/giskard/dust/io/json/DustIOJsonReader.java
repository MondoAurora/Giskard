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

		boolean processJsonEvent(MiNDToken action, MiNDToken block, Object param) {
			Giskard.access(MiNDAccessCommand.Set, param, MTTYP_IO_SERIALIZEEVENT, MTMEM_GENERIC_VALUE_RAW);
			if ( null != block ) {
				Giskard.access(MiNDAccessCommand.Set, block, MTTYP_IO_SERIALIZEEVENT, MTMEM_MODEL_ENTITY_TAGS);
			}
			
			if ( (MTTAG_NARRATIVE_AGENTACTION_BEGIN == action) || (MTTAG_IDEA_COLLTYPE_ONE == block) ) {
				counter.add(block);
			}
			
			try {
				MiNDResultType ret = MiNDResultType.AcceptRead; // processor.process(action);

				switch ( ret ) {
				case Notimplemented:
					return GiskardException.wrap(null);
				case Reject:
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
			processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_INIT, null, null);
		}

		@Override
		public void endJSON() throws ParseException, IOException {
			processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_RELEASE, null, null);
		}

		@Override
		public boolean startObjectEntry(String arg0) throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_BEGIN, MTTAG_IDEA_VALTYPE_LINK, arg0);
		}

		@Override
		public boolean endObjectEntry() throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_END, MTTAG_IDEA_VALTYPE_LINK, null);
		}

		
		@Override
		public boolean primitive(Object arg0) throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_PROCESS, MTTAG_IDEA_COLLTYPE_ONE, arg0);
		}

		@Override
		public boolean startObject() throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_BEGIN, MTTAG_IDEA_COLLTYPE_MAP, null);
		}

		@Override
		public boolean endObject() throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_END, MTTAG_IDEA_COLLTYPE_MAP, null);
		}

		@Override
		public boolean startArray() throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_BEGIN, MTTAG_IDEA_COLLTYPE_ARR, null);
		}

		@Override
		public boolean endArray() throws ParseException, IOException {
			return processJsonEvent(MTTAG_NARRATIVE_AGENTACTION_END, MTTAG_IDEA_COLLTYPE_ARR, null);
		}
	}
}
