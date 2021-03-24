package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;

public class DustRuntimeModule implements GiskardConsts.MiNDAgent, DustRuntimeConsts {

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		switch ( action ) {
		case Init:
			Giskard.log(MiNDEventLevel.TRACE, "Runtime initializing");
			
			DustGiskard runtime = (DustGiskard) Class.forName("me.giskard.dust.runtime.DustRuntimeGiskard").newInstance();
			runtime.init(this);
			
			Class.forName("me.giskard.mod.DustRuntimeBoot").getMethod("boot").invoke(null);
			runtime.afterBoot();

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
