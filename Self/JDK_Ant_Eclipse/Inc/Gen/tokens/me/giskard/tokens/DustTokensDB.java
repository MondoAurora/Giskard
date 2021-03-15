package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensDB extends DustTokensMind {
	MiNDToken MTUNIT_DB = Giskard.defineToken(MiNDTokenType.UNIT, "DB");
	
	MiNDToken MTTYPE_CONNECTION = Giskard.defineToken(MiNDTokenType.TYPE, "Connection", MTUNIT_DB);
	MiNDToken MTMEMBER_DRIVER = Giskard.defineToken(MiNDTokenType.MEMBER, "Driver", MTTYPE_CONNECTION, MiNDValType.Raw,
			MiNDCollType.One);

	
	MiNDToken MTAGENT_DBTEST01 = Giskard.defineToken(MiNDTokenType.AGENT, "DBTest01", MTUNIT_DB);

}
