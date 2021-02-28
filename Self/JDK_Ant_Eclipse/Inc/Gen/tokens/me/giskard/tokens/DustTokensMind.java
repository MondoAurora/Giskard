package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMind extends MindConsts {
	MiNDToken MT_IDEA_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Idea");
	
	MiNDToken MT_IDEA_VALTYPE = Mind.defineToken(MiNDTokenType.TAG, "ValType", MT_IDEA_UNIT, MiNDValType.class);
	MiNDToken MT_IDEA_VALTYPE_INT = Mind.defineToken(MiNDTokenType.TAG, "Int", MT_IDEA_VALTYPE, MiNDValType.Int);
	MiNDToken MT_IDEA_VALTYPE_REAL = Mind.defineToken(MiNDTokenType.TAG, "Real", MT_IDEA_VALTYPE, MiNDValType.Real);
	MiNDToken MT_IDEA_VALTYPE_REF = Mind.defineToken(MiNDTokenType.TAG, "Ref", MT_IDEA_VALTYPE, MiNDValType.Ref);
	MiNDToken MT_IDEA_VALTYPE_RAW = Mind.defineToken(MiNDTokenType.TAG, "Raw", MT_IDEA_VALTYPE, MiNDValType.Raw);

	MiNDToken MT_IDEA_COLLTYPE = Mind.defineToken(MiNDTokenType.TAG, "CollType", MT_IDEA_UNIT, MiNDCollType.class);
	MiNDToken MT_IDEA_COLLTYPE_ONE = Mind.defineToken(MiNDTokenType.TAG, "One", MT_IDEA_COLLTYPE, MiNDCollType.One);
	MiNDToken MT_IDEA_COLLTYPE_ARR = Mind.defineToken(MiNDTokenType.TAG, "Arr", MT_IDEA_COLLTYPE, MiNDCollType.Arr);
	MiNDToken MT_IDEA_COLLTYPE_SET = Mind.defineToken(MiNDTokenType.TAG, "Set", MT_IDEA_COLLTYPE, MiNDCollType.Set);
	MiNDToken MT_IDEA_COLLTYPE_MAP = Mind.defineToken(MiNDTokenType.TAG, "Map", MT_IDEA_COLLTYPE, MiNDCollType.Map);

	
	MiNDToken MT_DIALOG_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Dialog");
	
	MiNDToken MT_DIALOG_ACCESSCOMMAND = Mind.defineToken(MiNDTokenType.TAG, "AccessCommand", MT_DIALOG_UNIT, MiNDAccessCommand.class);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_CHK = Mind.defineToken(MiNDTokenType.TAG, "Chk", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Chk);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_GET = Mind.defineToken(MiNDTokenType.TAG, "Get", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Get);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_SET = Mind.defineToken(MiNDTokenType.TAG, "Set", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Set);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_ADD = Mind.defineToken(MiNDTokenType.TAG, "Add", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Add);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_DEL = Mind.defineToken(MiNDTokenType.TAG, "Del", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Del);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_USE = Mind.defineToken(MiNDTokenType.TAG, "Use", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.Use);
	
	MiNDToken MT_NARRATIVE_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Narrative");
	
	MiNDToken MT_NARRATIVE_AGENT = Mind.defineToken(MiNDTokenType.TYPE, "Agent", MT_NARRATIVE_UNIT);
	MiNDToken MT_NARRATIVE_AGENT_INSTANCE = Mind.defineToken(MiNDTokenType.MEMBER, "Instance", MT_NARRATIVE_AGENT, MiNDValType.Raw, MiNDCollType.One);
	
	MiNDToken MT_NARRATIVE_AGENTACTION = Mind.defineToken(MiNDTokenType.TAG, "AgentAction", MT_NARRATIVE_UNIT, MiNDAgentAction.class);
	MiNDToken MT_NARRATIVE_AGENTACTION_INIT = Mind.defineToken(MiNDTokenType.TAG, "Init", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.Init);
	MiNDToken MT_NARRATIVE_AGENTACTION_BEGIN = Mind.defineToken(MiNDTokenType.TAG, "Begin", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.Begin);
	MiNDToken MT_NARRATIVE_AGENTACTION_PROCESS = Mind.defineToken(MiNDTokenType.TAG, "Process", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.Process);
	MiNDToken MT_NARRATIVE_AGENTACTION_END = Mind.defineToken(MiNDTokenType.TAG, "End", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.End);
	MiNDToken MT_NARRATIVE_AGENTACTION_RELEASE = Mind.defineToken(MiNDTokenType.TAG, "Release", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.Release);

}
