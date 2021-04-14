package me.giskard.dust.tools;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustToolsDump implements DustToolsConsts, GiskardConsts.MiNDAgent {
	
	Object val = "Not initialized.";
	MiNDResultType result;
	
	public DustToolsDump() {
		
	}
	
	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;
		switch ( action ) {
		case Begin:
			break;
		case End:
			ret = result;
			break;
		case Init:
			val = Giskard.access(MiNDAccessCommand.Get, "Not configured.", MTMEMBER_ACTION_THIS, MTMEMBER_VARIANT_VALUE);
			MiNDToken resp = Giskard.access(MiNDAccessCommand.Get, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_TAGS);
			result = ( null == resp ) ? ret : (MiNDResultType) GisToolsTokenTranslator.toEnum(resp);
			break;
		case Process:
			ret = result;
			if ( GiskardUtils.isAgentAccept(ret)) {
				Giskard.log(MiNDEventLevel.Info, "Dump called", val);				
			} else {
				Giskard.log(MiNDEventLevel.Trace, "Dump SKIPPED!", val);				
			}
			break;
		case Release:
			break;
		}
		return ret;
	}

}
