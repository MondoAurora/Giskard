package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeUtils;

public class DustRuntimeModule implements GiskardConsts.MiNDAgent, DustRuntimeConsts {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			Giskard.log(MiNDEventLevel.TRACE, "Runtime initializing");
			
			DustRuntime runtime = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_RUNTIME);
			runtime.init("DustRuntime", this);
			
			DustRuntimeUtils.getRuntimeClass(CLASSPATH_BOOT).getMethod("boot").invoke(null);

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
