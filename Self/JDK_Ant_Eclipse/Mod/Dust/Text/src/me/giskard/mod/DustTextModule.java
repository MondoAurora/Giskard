package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.text.DustTextFormat;
import me.giskard.dust.text.DustTextMatcherConst;
import me.giskard.dust.text.DustTextMatcherRange;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensText;

public class DustTextModule implements GiskardConsts.MiNDAgent, DustTokensText {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Text module initializing");

		DustTokens.addModuleImpInfo(MTAGENT_TEXT_MATCHCONST, DustTextMatcherConst.class);
		DustTokens.addModuleImpInfo(MTAGENT_TEXT_MATCHRANGE, DustTextMatcherRange.class);
		DustTokens.addModuleImpInfo(MTAGENT_TEXT_FORMAT, DustTextFormat.class);

		return MiNDResultType.Accept;
	}
}
