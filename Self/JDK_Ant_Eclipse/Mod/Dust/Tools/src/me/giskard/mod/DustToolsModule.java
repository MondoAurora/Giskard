package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.tools.DustToolsDump;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensGeneric;

public class DustToolsModule implements GiskardConsts.MiNDAgent, DustTokensGeneric {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "Tools module initializing");
		
		DustTokens.registerNewModule();
		
		DustTokens.addModuleImpInfo(MTAGENT_DUMP, DustToolsDump.class);

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
