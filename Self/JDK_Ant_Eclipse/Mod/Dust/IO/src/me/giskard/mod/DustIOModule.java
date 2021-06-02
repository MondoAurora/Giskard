package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokens;

public class DustIOModule implements GiskardConsts.MiNDAgent, DustTokensIO {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "IO module initializing");
		
		DustTokens.addModuleImpInfo(MTAGN_IO_TEST01, DustIOTest01.class);

		return MiNDResultType.Accept;
	}

}
