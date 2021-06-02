package me.giskard.dust.db.jdbc;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustJdbcAgentDBConn implements DustJdbcConsts, GiskardConsts.MiNDAgentBlock {
	DustJdbcSerializer serializer;

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		Object ser = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_TEMP_TEMP_SERIALIZER);

		if ( null == ser ) {
				serializer = new DustJdbcSerializer();
				Giskard.access(MiNDAccessCommand.Set, serializer, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_TEMP_TEMP_SERIALIZER);
				Giskard.access(MiNDAccessCommand.Set, MTTAG_TEMP_SERIALIZEMODE_LOAD, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_MODEL_ENTITY_TAGS);
		} else {
			serializer = (DustJdbcSerializer) ser;
			Giskard.access(MiNDAccessCommand.Set, MTTAG_TEMP_SERIALIZEMODE_SAVE, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_MODEL_ENTITY_TAGS);
		}

		serializer.step();

		return ret;
	}

	@Override
	public void mindAgentBegin() throws Exception {
	}

	@Override
	public void mindAgentEnd() throws Exception {
	}

}
