package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;

public class DustRuntimeModule implements GiskardConsts.MiNDAgent, DustRuntimeConsts {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			Giskard.log(MiNDEventLevel.TRACE, "Runtime initializing");
			
			DustRuntime runtime = (DustRuntime) Class.forName(CLASSNAME_RUNTIME).newInstance();
			DustMachine machine = (DustMachine) Class.forName(CLASSNAME_MACHINE).newInstance();
			machine.init("DustRuntime", this);
			runtime.setMachine(machine);
			
			Class.forName(CLASSNAME_BOOT).getMethod("boot").invoke(null);

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
