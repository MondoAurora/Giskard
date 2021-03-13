package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.coll.MindCollConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeMeta;
import me.giskard.dust.runtime.knowledge.DustKnowledgeConsts;
import me.giskard.dust.runtime.machine.DustMachineControl;
import me.giskard.tokens.DustTokens;

public class DustRuntimeBoot implements DustKnowledgeConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts {
	public static void boot() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "DustRuntimeBoot.boot started...");
		
//		GisToolsTokenTranslator.setTokenMember(MTMEMBER_TAGGED_TAGS);
		
		// Machine
		Giskard.selectByPath(MTSHARED_MACHINE);
		
		// Application
		Giskard.selectByPath(MTMEMBER_ACTION_PARAM);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP);
		
		// Runtime module
		DustTokens.registerNewModule();

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_ITERATION, DustMachineControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SEQUENCE, DustMachineControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SELECTION, DustMachineControl.Selection.class);
		
//		DustTokens.addModuleImpInfo(MTAGENT_MIND, DustRuntime.class);
//		DustTokens.addModuleImpInfo(MTAGENT_CONTEXT, DustKnowledgeContext.class);
		
	}

}
