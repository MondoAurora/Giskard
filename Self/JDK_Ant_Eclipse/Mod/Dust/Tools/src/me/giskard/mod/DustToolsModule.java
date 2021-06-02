package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.tools.DustToolsDump;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokens;

public class DustToolsModule implements GiskardConsts.MiNDAgent, DustTokensGeneric {

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Tools module initializing");

		DustTokens.addModuleImpInfo(MTAGN_GENERIC_DUMP, DustToolsDump.class);
		return MiNDResultType.Accept;
	}
	
}
