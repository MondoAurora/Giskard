package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensDB extends DustTokensMind {
	MiNDToken MTUNIT_DB = Giskard.defineToken(MiNDTokenType.Unit, "DB");
	
	MiNDToken MTTYPE_CONNECTION = Giskard.defineToken(MiNDTokenType.Type, "Connection", MTUNIT_DB);
	MiNDToken MTMEMBER_DRIVER = Giskard.defineToken(MiNDTokenType.Member, "Driver", MTTYPE_CONNECTION, MiNDValType.Raw,
			MiNDCollType.One);

	
	MiNDToken MTAGENT_DBTEST01 = Giskard.defineToken(MiNDTokenType.Agent, "DBTest01", MTUNIT_DB);

}
