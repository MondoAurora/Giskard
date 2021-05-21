package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.db.jdbc.DustJdbcAgent;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensDB;

public class DustDBModule implements GiskardConsts.MiNDAgent, DustTokensDB {
	
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DB module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_DBTEST01, DustJdbcAgent.class);
		
		return MiNDResultType.Accept;
	}

}
