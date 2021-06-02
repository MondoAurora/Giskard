package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.text.DustTextFormat;
import me.giskard.dust.text.DustTextMatcherConst;
import me.giskard.dust.text.DustTextMatcherRange;
import me.giskard.tokens.DustTokensText;
import me.giskard.tokens.DustTokens;

public class DustTextModule implements GiskardConsts.MiNDAgent, DustTokensText {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Text module initializing");

		DustTokens.addModuleImpInfo(MTAGN_TEXT_MATCHCONST, DustTextMatcherConst.class);
		DustTokens.addModuleImpInfo(MTAGN_TEXT_MATCHRANGE, DustTextMatcherRange.class);
		DustTokens.addModuleImpInfo(MTAGN_TEXT_FORMAT, DustTextFormat.class);

		return MiNDResultType.Accept;
	}
}
