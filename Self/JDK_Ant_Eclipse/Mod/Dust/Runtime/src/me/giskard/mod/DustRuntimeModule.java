package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.DustRuntimeBootConsts;

public class DustRuntimeModule implements GiskardConsts.MiNDAgentResource, DustRuntimeBootConsts {
	
	@Override
	public MiNDResultType mindAgentInit() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Runtime initializing");
		
		DustGiskard runtime = (DustGiskard) Class.forName("me.giskard.dust.runtime.DustRuntimeGiskard").newInstance();
		runtime.init(this);
		
		Class.forName("me.giskard.mod.DustRuntimeBoot").getMethod("boot").invoke(null);
		return MiNDResultType.Accept;
	}

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		return MiNDResultType.Accept;
	}
	
	@Override
	public MiNDResultType mindAgentRelease() throws Exception {
		return null;
	}
}
