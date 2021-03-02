package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMind extends MindConsts {
	MiNDToken MTUNIT_IDEA = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Idea");
	
	MiNDToken MTTAG_VALTYPE = Mind.defineToken(MiNDTokenType.TAG, "ValType", MTUNIT_IDEA, MiNDValType.class);
	MiNDToken MTTAG_VALTYPE_INT = Mind.defineToken(MiNDTokenType.TAG, "Int", MTTAG_VALTYPE, MiNDValType.Int);
	MiNDToken MTTAG_VALTYPE_REAL = Mind.defineToken(MiNDTokenType.TAG, "Real", MTTAG_VALTYPE, MiNDValType.Real);
	MiNDToken MTTAG_VALTYPE_REF = Mind.defineToken(MiNDTokenType.TAG, "Ref", MTTAG_VALTYPE, MiNDValType.Ref);
	MiNDToken MTTAG_VALTYPE_RAW = Mind.defineToken(MiNDTokenType.TAG, "Raw", MTTAG_VALTYPE, MiNDValType.Raw);

	MiNDToken MTTAG_COLLTYPE = Mind.defineToken(MiNDTokenType.TAG, "CollType", MTUNIT_IDEA, MiNDCollType.class);
	MiNDToken MTTAG_COLLTYPE_ONE = Mind.defineToken(MiNDTokenType.TAG, "One", MTTAG_COLLTYPE, MiNDCollType.One);
	MiNDToken MTTAG_COLLTYPE_ARR = Mind.defineToken(MiNDTokenType.TAG, "Arr", MTTAG_COLLTYPE, MiNDCollType.Arr);
	MiNDToken MTTAG_COLLTYPE_SET = Mind.defineToken(MiNDTokenType.TAG, "Set", MTTAG_COLLTYPE, MiNDCollType.Set);
	MiNDToken MTTAG_COLLTYPE_MAP = Mind.defineToken(MiNDTokenType.TAG, "Map", MTTAG_COLLTYPE, MiNDCollType.Map);

	
	MiNDToken MTUNIT_DIALOG = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Dialog");
	
	MiNDToken MTTAG_ACCESSCOMMAND = Mind.defineToken(MiNDTokenType.TAG, "AccessCommand", MTUNIT_DIALOG, MiNDAccessCommand.class);
	MiNDToken MTTAG_ACCESSCOMMAND_CHK = Mind.defineToken(MiNDTokenType.TAG, "Chk", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Chk);
	MiNDToken MTTAG_ACCESSCOMMAND_GET = Mind.defineToken(MiNDTokenType.TAG, "Get", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Get);
	MiNDToken MTTAG_ACCESSCOMMAND_SET = Mind.defineToken(MiNDTokenType.TAG, "Set", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Set);
	MiNDToken MTTAG_ACCESSCOMMAND_ADD = Mind.defineToken(MiNDTokenType.TAG, "Add", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Add);
	MiNDToken MTTAG_ACCESSCOMMAND_DEL = Mind.defineToken(MiNDTokenType.TAG, "Del", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Del);
	MiNDToken MTTAG_ACCESSCOMMAND_USE = Mind.defineToken(MiNDTokenType.TAG, "Use", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Use);
	
	MiNDToken MTUNIT_NARRATIVE = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Narrative");

	MiNDToken MTAGENT_MIND = Mind.defineToken(MiNDTokenType.AGENT, "Mind", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CONTEXT = Mind.defineToken(MiNDTokenType.AGENT, "Context", MTUNIT_NARRATIVE);

	MiNDToken MTTYPE_AGENT = Mind.defineToken(MiNDTokenType.TYPE, "Agent", MTUNIT_NARRATIVE);
	MiNDToken MTMEMBER_AGENT_INSTANCE = Mind.defineToken(MiNDTokenType.MEMBER, "Instance", MTTYPE_AGENT, MiNDValType.Raw, MiNDCollType.One);
	
	MiNDToken MTTAG_AGENTACTION = Mind.defineToken(MiNDTokenType.TAG, "AgentAction", MTUNIT_NARRATIVE, MiNDAgentAction.class);
	MiNDToken MTTAG_AGENTACTION_INIT = Mind.defineToken(MiNDTokenType.TAG, "Init", MTTAG_AGENTACTION, MiNDAgentAction.Init);
	MiNDToken MTTAG_AGENTACTION_BEGIN = Mind.defineToken(MiNDTokenType.TAG, "Begin", MTTAG_AGENTACTION, MiNDAgentAction.Begin);
	MiNDToken MTTAG_AGENTACTION_PROCESS = Mind.defineToken(MiNDTokenType.TAG, "Process", MTTAG_AGENTACTION, MiNDAgentAction.Process);
	MiNDToken MTTAG_AGENTACTION_END = Mind.defineToken(MiNDTokenType.TAG, "End", MTTAG_AGENTACTION, MiNDAgentAction.End);
	MiNDToken MTTAG_AGENTACTION_RELEASE = Mind.defineToken(MiNDTokenType.TAG, "Release", MTTAG_AGENTACTION, MiNDAgentAction.Release);

	MiNDToken MTTYPE_ACTION = Mind.defineToken(MiNDTokenType.TYPE, "Action", MTUNIT_NARRATIVE);
	MiNDToken MTMEMBER_ACTION_THIS = Mind.defineToken(MiNDTokenType.MEMBER, "This", MTTYPE_ACTION, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_PARAM = Mind.defineToken(MiNDTokenType.MEMBER, "Param", MTTYPE_ACTION, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_LOCAL = Mind.defineToken(MiNDTokenType.MEMBER, "Local", MTTYPE_ACTION, MiNDValType.Ref, MiNDCollType.One);
	
}
