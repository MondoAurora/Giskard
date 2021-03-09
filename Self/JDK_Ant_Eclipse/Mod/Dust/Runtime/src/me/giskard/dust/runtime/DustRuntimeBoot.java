package me.giskard.dust.runtime;

import me.giskard.Giskard;
import me.giskard.coll.MindCollConsts;
import me.giskard.dust.runtime.model.DustModelConsts;
import me.giskard.dust.runtime.model.DustModelContext;
import me.giskard.tokens.DustTokens;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustRuntimeBoot implements DustModelConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts {
	public static void boot() {
		Giskard.log(MiNDEventLevel.TRACE, "DustRuntimeBoot.boot started...");
		
		GisToolsTokenTranslator.setTokenMember(MTMEMBER_TAGGED_TAGS);
		
		// Machine
		Giskard.selectByPath(MTSHARED_MACHINE);
		
		// Application
		Giskard.selectByPath(MTMEMBER_ACTION_PARAM);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP);
		
		// Runtime module
		DustTokens.registerNewModule();

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_MIND, DustRuntime.class);
		DustTokens.addModuleImpInfo(MTAGENT_CONTEXT, DustModelContext.class);
		
	}

}
