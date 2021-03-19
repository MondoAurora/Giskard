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
		Giskard.access(MiNDAccessCommand.Get, MTSHARED_MACHINE);
		
		// Application
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_TEMP01);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_TEMP01, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP);
		
		// Runtime module
		DustTokens.registerNewModule();

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_ITERATION, DustMachineControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SEQUENCE, DustMachineControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRL_SELECTION, DustMachineControl.Selection.class);
		
		MiNDToken[] bootTokens = { 
				MTMEMBER_PLAIN_STRING, MTMEMBER_CONN_OWNER, MTMEMBER_ENTITY_PRIMARYTYPE, MTMEMBER_ENTITY_STOREID, MTMEMBER_ENTITY_STOREUNIT,
				MTTYPE_AGENT, MTTYPE_MEMBER, MTTYPE_TAG, MTTYPE_TYPE, MTTYPE_UNIT};
		
		Giskard.log(MiNDEventLevel.TRACE, "Boot tokens registered", bootTokens);

//		DustTokens.addModuleImpInfo(MTAGENT_MIND, DustRuntime.class);
//		DustTokens.addModuleImpInfo(MTAGENT_CONTEXT, DustKnowledgeContext.class);
		
	}

}
