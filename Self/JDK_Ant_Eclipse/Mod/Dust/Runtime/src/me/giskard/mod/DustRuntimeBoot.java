package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.dust.runtime.DustRuntimeAgentControl;
import me.giskard.dust.runtime.DustRuntimeAgentData;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.tokens.DustTokens;

public class DustRuntimeBoot implements DustRuntimeConsts {
	public static void boot() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DustRuntimeBoot.boot started...");

		// Machine
		Object machine = Giskard.access(MiNDAccessCommand.Get, MTTYPE_MACHINE);
		Giskard.access(MiNDAccessCommand.Set, machine, MTSHARED_MACHINE);

		// Runtime module
		DustTokens.addModule(MODULE_NAME, MODULE_VER);

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_ITERATION, DustRuntimeAgentControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SEQUENCE, DustRuntimeAgentControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SELECTION, DustRuntimeAgentControl.Selection.class);

		DustTokens.addModuleImpInfo(MTAGENT_DATA_LISTALL, DustRuntimeAgentData.ListAll.class);
		DustTokens.addModuleImpInfo(MTAGENT_DATA_VISIT, DustRuntimeAgentData.Visit.class);
		DustTokens.addModuleImpInfo(MTAGENT_DATA_READ, DustRuntimeAgentData.Read.class);
//		DustTokens.addModuleImpInfo(MTAGENT_DATA_COLLECT, DustRuntimeAgentData.Collect.class);

	}

}
