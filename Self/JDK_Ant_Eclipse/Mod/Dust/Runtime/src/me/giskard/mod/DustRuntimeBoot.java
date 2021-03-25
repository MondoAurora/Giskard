package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.dust.runtime.DustRuntimeAgentControl;
import me.giskard.dust.runtime.DustRuntimeAgentData;
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
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_ITERATION, DustRuntimeAgentControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SEQUENCE, DustRuntimeAgentControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SELECTION, DustRuntimeAgentControl.Selection.class);

		DustTokens.addModuleImpInfo(MTAGENT_DATA_FINDALL, DustRuntimeAgentData.FindAll.class);
		DustTokens.addModuleImpInfo(MTAGENT_DATA_COLLECT, DustRuntimeAgentData.Collect.class);

	}

}
