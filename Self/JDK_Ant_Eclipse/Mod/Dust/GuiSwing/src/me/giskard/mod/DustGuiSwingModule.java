package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensGui;

public class DustGuiSwingModule implements GiskardConsts.MiNDAgent, DustTokensGui {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "GUI Swing module initializing");
		
		DustTokens.registerNewModule();
		
//		DustTokens.addModuleImpInfo(MTAGENT_MATCHCONST, DustTextMatcherConst.class);
//		DustTokens.addModuleImpInfo(MTAGENT_MATCHRANGE, DustTextMatcherRange.class);

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
