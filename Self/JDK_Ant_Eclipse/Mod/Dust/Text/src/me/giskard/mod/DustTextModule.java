package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.text.DustTextMatcherConst;
import me.giskard.dust.text.DustTextMatcherRange;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensText;

public class DustTextModule implements GiskardConsts.MiNDAgent, DustTokensText {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "Text module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_MATCHCONST, DustTextMatcherConst.class);
		DustTokens.addModuleImpInfo(MTAGENT_MATCHRANGE, DustTextMatcherRange.class);

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
