package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.db.jdbc.DustJdbcAgent;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensDB;

public class DustDBModule implements GiskardConsts.MiNDAgent, DustTokensDB {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "DB module initializing");
		
		DustTokens.registerNewModule();
		
		DustTokens.addModuleImpInfo(MTAGENT_DBTEST01, DustJdbcAgent.class);
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			initModule();
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}

}
