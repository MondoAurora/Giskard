package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.devjava.DustDevJavaAgentGenTokens;
import me.giskard.tokens.DustTokensDev;
import me.giskard.tokens.DustTokensDevJava;
import me.giskard.tokens.DustTokens;

public class DustDevJavaModule implements GiskardConsts.MiNDAgent, DustTokensDev, DustTokensDevJava {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DevJava module initializing");

		DustTokens.addModuleImpInfo(MTAGN_DEV_GENTOKENS, DustDevJavaAgentGenTokens.class);

		return MiNDResultType.Accept;
	}
}
