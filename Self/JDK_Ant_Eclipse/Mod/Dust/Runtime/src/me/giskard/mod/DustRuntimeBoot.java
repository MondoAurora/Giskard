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
		Object machine = Giskard.access(MiNDAccessCommand.Get, MTTYP_MACHINE_MACHINE);
		Giskard.access(MiNDAccessCommand.Set, machine, MTLOC_MACHINE_THEMACHINE);

		// Runtime module
		DustTokens.addModule(MODULE_NAME, MODULE_VER);

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_CTRLITERATION, DustRuntimeAgentControl.Iteration.class);
		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_CTRLSEQUENCE, DustRuntimeAgentControl.Sequence.class);
		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_CTRLSELECTION, DustRuntimeAgentControl.Selection.class);

		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_DATALISTALL, DustRuntimeAgentData.ListAll.class);
		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_DATAVISIT, DustRuntimeAgentData.Visit.class);
		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_DATAREAD, DustRuntimeAgentData.Read.class);
//		DustTokens.addModuleImpInfo(MTAGN_NARRATIVE_DATA_COLLECT, DustRuntimeAgentData.Collect.class);

		//	enum MiNDTokenType { Unit, Type, Member, Tag, Service, Agent, Local	}
		GisToolsTokenTranslator.register(MTTYP_MODEL_UNIT, MiNDTokenType.Unit);
		GisToolsTokenTranslator.register(MTTYP_IDEA_TYPE, MiNDTokenType.Type);
		GisToolsTokenTranslator.register(MTTYP_IDEA_MEMBER, MiNDTokenType.Member);
		GisToolsTokenTranslator.register(MTTYP_GENERIC_TAG, MiNDTokenType.Tag);
		GisToolsTokenTranslator.register(MTTYP_NARRATIVE_SERVICE, MiNDTokenType.Service);
		GisToolsTokenTranslator.register(MTTYP_NARRATIVE_AGENT, MiNDTokenType.Agent);
		
		
		
		

		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE, MiNDTokenType.class);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_UNIT, MiNDTokenType.Unit);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_TYPE, MiNDTokenType.Type);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_MEMBER, MiNDTokenType.Member);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_TAG, MiNDTokenType.Tag);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_SERVICE, MiNDTokenType.Service);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_AGENT, MiNDTokenType.Agent);
		GisToolsTokenTranslator.register(MTTAG_IDEA_TOKENTYPE_LOCAL, MiNDTokenType.Local);
		
		GisToolsTokenTranslator.register(MTTAG_IDEA_VALTYPE, MiNDValType.class);
		GisToolsTokenTranslator.register(MTTAG_IDEA_VALTYPE_INT, MiNDValType.Int);
		GisToolsTokenTranslator.register(MTTAG_IDEA_VALTYPE_REAL, MiNDValType.Real);
		GisToolsTokenTranslator.register(MTTAG_IDEA_VALTYPE_LINK, MiNDValType.Link);
		GisToolsTokenTranslator.register(MTTAG_IDEA_VALTYPE_RAW, MiNDValType.Raw);

		GisToolsTokenTranslator.register(MTTAG_IDEA_COLLTYPE, MiNDCollType.class);
		GisToolsTokenTranslator.register(MTTAG_IDEA_COLLTYPE_ONE, MiNDCollType.One);
		GisToolsTokenTranslator.register(MTTAG_IDEA_COLLTYPE_ARR, MiNDCollType.Arr);
		GisToolsTokenTranslator.register(MTTAG_IDEA_COLLTYPE_SET, MiNDCollType.Set);
		GisToolsTokenTranslator.register(MTTAG_IDEA_COLLTYPE_MAP, MiNDCollType.Map);
		

		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.class);
		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND_CHK, MiNDAccessCommand.Chk);
		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND_GET, MiNDAccessCommand.Get);
		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND_SET, MiNDAccessCommand.Set);
		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND_ADD, MiNDAccessCommand.Add);
		GisToolsTokenTranslator.register(MTTAG_DIALOG_ACCESSCOMMAND_DEL, MiNDAccessCommand.Del);


		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE, MiNDResultType.class);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_NOTIMPLEMENTED, MiNDResultType.Notimplemented);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_REJECT, MiNDResultType.Reject);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_ACCEPT, MiNDResultType.Accept);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_ACCEPTREAD, MiNDResultType.AcceptRead);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_READ, MiNDResultType.Read);
		GisToolsTokenTranslator.register(MTTAG_NARRATIVE_RESULTTYPE_WAIT, MiNDResultType.Wait);
	}

}
