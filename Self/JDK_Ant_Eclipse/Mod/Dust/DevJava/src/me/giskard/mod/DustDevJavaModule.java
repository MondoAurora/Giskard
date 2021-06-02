package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.devjava.DustDevJavaAgentGenTokens;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensDev;
import me.giskard.tokens.DustTokensDevJava;

public class DustDevJavaModule implements GiskardConsts.MiNDAgent, DustTokensDev, DustTokensDevJava {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DevJava module initializing");

		DustTokens.addModuleImpInfo(MTAGENT_DEV_GENTOKENS, DustDevJavaAgentGenTokens.class);

		return MiNDResultType.Accept;
	}
}
