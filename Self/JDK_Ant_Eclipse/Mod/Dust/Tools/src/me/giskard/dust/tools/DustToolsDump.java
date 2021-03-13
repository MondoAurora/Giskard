package me.giskard.dust.tools;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustToolsDump implements DustToolsConsts, GiskardConsts.MiNDAgent {
	
	Object val = "Not initialized.";
	
	public DustToolsDump() {
		
	}
	
	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT;
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			val = Giskard.access(MiNDAccessCommand.Get, "Not configured.", MTMEMBER_ACTION_THIS, MTMEMBER_VARIANT_VALUE);
			break;
		case Process:
			MiNDToken resp = Giskard.access(MiNDAccessCommand.Get, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_THIS);
			if ( null != resp ) {
				ret = (MiNDResultType) GisToolsTokenTranslator.toEnum(resp);
			}
			if ( GiskardUtils.isAgentAccept(ret)) {
				Giskard.log(MiNDEventLevel.INFO, "Dump called", val);				
			} else {
				Giskard.log(MiNDEventLevel.TRACE, "Dump SKIPPED!", val);				
			}
			break;
		case Release:
			break;
		}
		return ret;
	}

}
