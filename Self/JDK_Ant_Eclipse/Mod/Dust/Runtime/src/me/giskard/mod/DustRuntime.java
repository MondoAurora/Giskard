package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;

public class DustRuntime implements GiskardConsts.MiNDAgent, DustRuntimeConsts {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			Giskard.log(MiNDEventLevel.TRACE, "Runtime initializing");

			Giskard runtime = (Giskard) Class.forName(CLASSNAME_RUNTIME).newInstance();
			Giskard.setImplementation(runtime);
			runtime.initContext();
			break;
		case Begin:
			Giskard.log(MiNDEventLevel.INFO, "DustRuntime launch...");

			Giskard.log(MiNDEventLevel.TRACE);			
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
