package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.db.jdbc.DustJdbcAgentDBConn;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokens;

public class DustDBModule implements GiskardConsts.MiNDAgent, DustTokensDB {
	
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DB module initializing");
		
		DustTokens.addModuleImpInfo(MTAGN_DB_TEST01, DustJdbcAgentDBConn.class);
		
		return MiNDResultType.Accept;
	}

}
