package me.giskard.dust.db.jdbc;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustJdbcAgentDBConn implements DustJdbcConsts, GiskardConsts.MiNDAgentBlock {
	DustJdbcSerializer serializer;

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		Object ser = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_TEMP_SERIALIZER);

		if ( null == ser ) {
				serializer = new DustJdbcSerializer();
				Giskard.access(MiNDAccessCommand.Set, serializer, MTMEMBER_ACTION_DIALOG, MTMEMBER_TEMP_SERIALIZER);
				Giskard.access(MiNDAccessCommand.Set, MTTAG_SERIALIZEMODE_LOAD, MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
		} else {
			serializer = (DustJdbcSerializer) ser;
			Giskard.access(MiNDAccessCommand.Set, MTTAG_SERIALIZEMODE_SAVE, MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
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
