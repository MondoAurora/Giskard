package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensIO;

public class DustIOModule implements GiskardConsts.MiNDAgent, DustTokensIO {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "IO module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_TEST01, DustIOTest01.class);
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
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
