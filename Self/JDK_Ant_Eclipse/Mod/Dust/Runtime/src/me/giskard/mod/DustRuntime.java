package me.giskard.mod;

import me.giskard.Mind;
import me.giskard.MindConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;

public class DustRuntime implements MindConsts.MiNDAgent, DustRuntimeConsts {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			Mind.log(MiNDEventLevel.TRACE, "Runtime initializing");

			Mind mind = (Mind) Class.forName(CLASSNAME_MIND).newInstance();
			Mind.setMind(mind);
			mind.initContext();
			break;
		case Begin:
			Mind.log(MiNDEventLevel.INFO, "DustRuntime launch...");
			
			Mind.log(MiNDEventLevel.INFO, Mind.getMind());
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
