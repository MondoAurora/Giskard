package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.dust.runtime.DustRuntimeAgentControl;
import me.giskard.dust.runtime.DustRuntimeAgentData;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustRuntimeBoot implements DustRuntimeConsts {
	public static void boot() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "DustRuntimeBoot.boot started...");

		// Machine
		Object machine = Giskard.access(MiNDAccessCommand.Get, MTTYPE_MACHINE);
		Giskard.access(MiNDAccessCommand.Set, machine, MTSHARED_MACHINE);

		// Runtime module
		DustTokens.addModule(MODULE_NAME, MODULE_VER);

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_CTRLITERATION, DustRuntimeAgentControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRLSEQUENCE, DustRuntimeAgentControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGENT_CTRLSELECTION, DustRuntimeAgentControl.Selection.class);

		DustTokens.addModuleImpInfo(MTAGENT_DATALISTALL, DustRuntimeAgentData.ListAll.class);
		DustTokens.addModuleImpInfo(MTAGENT_DATAVISIT, DustRuntimeAgentData.Visit.class);
		DustTokens.addModuleImpInfo(MTAGENT_DATAREAD, DustRuntimeAgentData.Read.class);
//		DustTokens.addModuleImpInfo(MTAGENT_DATA_COLLECT, DustRuntimeAgentData.Collect.class);

		//	enum MiNDTokenType { Unit, Type, Member, Tag, Service, Agent, Local	}
		GisToolsTokenTranslator.register(MTTYPE_UNIT, MiNDTokenType.Unit);
		GisToolsTokenTranslator.register(MTTYPE_TYPE, MiNDTokenType.Type);
		GisToolsTokenTranslator.register(MTTYPE_MEMBER, MiNDTokenType.Member);
		GisToolsTokenTranslator.register(MTTYPE_TAG, MiNDTokenType.Tag);
		GisToolsTokenTranslator.register(MTTYPE_SERVICE, MiNDTokenType.Service);
		GisToolsTokenTranslator.register(MTTYPE_AGENT, MiNDTokenType.Agent);
	}

}
