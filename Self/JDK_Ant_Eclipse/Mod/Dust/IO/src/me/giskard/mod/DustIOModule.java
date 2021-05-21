package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensIO;

public class DustIOModule implements GiskardConsts.MiNDAgent, DustTokensIO {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "IO module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_TEST01, DustIOTest01.class);

		return MiNDResultType.Accept;
	}

}
