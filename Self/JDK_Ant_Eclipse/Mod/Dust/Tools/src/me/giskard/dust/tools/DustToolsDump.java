package me.giskard.dust.tools;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustToolsDump implements DustToolsConsts, GiskardConsts.MiNDAgent {

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Object val = Giskard.access(MiNDAccessCommand.Get, "Not configured.", MTMEMBER_ACTION_THIS, MTMEMBER_VALUE_RAW);
		
		MiNDToken resp = Giskard.access(MiNDAccessCommand.Get, GisToolsTokenTranslator.toToken(MiNDResultType.Accept),
				MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_TAGS);
		MiNDResultType ret = (MiNDResultType) GisToolsTokenTranslator.toEnum(resp);
		
		if ( GiskardUtils.isAgentAccept(ret) ) {
			Giskard.log(MiNDEventLevel.Info, "Dump called", val);
		} else {
			Giskard.log(MiNDEventLevel.Trace, "Dump SKIPPED!", val);
		}
		
		return ret;
	}

}
