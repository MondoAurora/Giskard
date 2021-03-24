package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.dust.runtime.DustRuntimeMachineAgent;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.tokens.DustTokens;

public class DustRuntimeBoot implements DustRuntimeConsts {
	public static void boot() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "DustRuntimeBoot.boot started...");
		
		// Machine
		Giskard.access(MiNDAccessCommand.Get, MTSHARED_MACHINE);
		
		// Application
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_PARAM);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP);
		
		// Runtime module
		DustTokens.registerNewModule();

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_ITERATION, DustRuntimeMachineAgent.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SEQUENCE, DustRuntimeMachineAgent.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SELECTION, DustRuntimeMachineAgent.Selection.class);
		
	}

}
