package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMind extends GiskardConsts {
	MiNDToken MTUNIT_MODEL = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Model");

	MiNDToken MTTYPE_ENTITY = Giskard.defineToken(MiNDTokenType.TYPE, "Entity", MTUNIT_MODEL);
	MiNDToken MTMEMBER_ENTITY_PRIMARYTYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "PrimaryType", MTTYPE_ENTITY, MiNDValType.Link, MiNDCollType.One);
	
	
	MiNDToken MTUNIT_IDEA = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Idea");
	
	MiNDToken MTTAG_VALTYPE = Giskard.defineToken(MiNDTokenType.TAG, "ValType", MTUNIT_IDEA, MiNDValType.class);
	MiNDToken MTTAG_VALTYPE_INT = Giskard.defineToken(MiNDTokenType.TAG, "Int", MTTAG_VALTYPE, MiNDValType.Int);
	MiNDToken MTTAG_VALTYPE_REAL = Giskard.defineToken(MiNDTokenType.TAG, "Real", MTTAG_VALTYPE, MiNDValType.Real);
	MiNDToken MTTAG_VALTYPE_LINK = Giskard.defineToken(MiNDTokenType.TAG, "Link", MTTAG_VALTYPE, MiNDValType.Link);
	MiNDToken MTTAG_VALTYPE_RAW = Giskard.defineToken(MiNDTokenType.TAG, "Raw", MTTAG_VALTYPE, MiNDValType.Raw);

	MiNDToken MTTAG_COLLTYPE = Giskard.defineToken(MiNDTokenType.TAG, "CollType", MTUNIT_IDEA, MiNDCollType.class);
	MiNDToken MTTAG_COLLTYPE_ONE = Giskard.defineToken(MiNDTokenType.TAG, "One", MTTAG_COLLTYPE, MiNDCollType.One);
	MiNDToken MTTAG_COLLTYPE_ARR = Giskard.defineToken(MiNDTokenType.TAG, "Arr", MTTAG_COLLTYPE, MiNDCollType.Arr);
	MiNDToken MTTAG_COLLTYPE_SET = Giskard.defineToken(MiNDTokenType.TAG, "Set", MTTAG_COLLTYPE, MiNDCollType.Set);
	MiNDToken MTTAG_COLLTYPE_MAP = Giskard.defineToken(MiNDTokenType.TAG, "Map", MTTAG_COLLTYPE, MiNDCollType.Map);

	
	MiNDToken MTUNIT_DIALOG = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Dialog");
	
	MiNDToken MTTAG_ACCESSCOMMAND = Giskard.defineToken(MiNDTokenType.TAG, "AccessCommand", MTUNIT_DIALOG, MiNDAccessCommand.class);
	MiNDToken MTTAG_ACCESSCOMMAND_CHK = Giskard.defineToken(MiNDTokenType.TAG, "Chk", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Chk);
	MiNDToken MTTAG_ACCESSCOMMAND_GET = Giskard.defineToken(MiNDTokenType.TAG, "Get", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Get);
	MiNDToken MTTAG_ACCESSCOMMAND_SET = Giskard.defineToken(MiNDTokenType.TAG, "Set", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Set);
	MiNDToken MTTAG_ACCESSCOMMAND_ADD = Giskard.defineToken(MiNDTokenType.TAG, "Add", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Add);
	MiNDToken MTTAG_ACCESSCOMMAND_DEL = Giskard.defineToken(MiNDTokenType.TAG, "Del", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Del);
	MiNDToken MTTAG_ACCESSCOMMAND_USE = Giskard.defineToken(MiNDTokenType.TAG, "Use", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Use);
	
	MiNDToken MTUNIT_NARRATIVE = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Narrative");

	MiNDToken MTAGENT_CTRL_ITERATION = Giskard.defineToken(MiNDTokenType.AGENT, "Iteration", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SEQUENCE = Giskard.defineToken(MiNDTokenType.AGENT, "Sequence", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SELECTION = Giskard.defineToken(MiNDTokenType.AGENT, "Selection", MTUNIT_NARRATIVE);

	MiNDToken MTTAG_AGENTACTION = Giskard.defineToken(MiNDTokenType.TAG, "AgentAction", MTUNIT_NARRATIVE, MiNDAgentAction.class);
	MiNDToken MTTAG_AGENTACTION_INIT = Giskard.defineToken(MiNDTokenType.TAG, "Init", MTTAG_AGENTACTION, MiNDAgentAction.Init);
	MiNDToken MTTAG_AGENTACTION_BEGIN = Giskard.defineToken(MiNDTokenType.TAG, "Begin", MTTAG_AGENTACTION, MiNDAgentAction.Begin);
	MiNDToken MTTAG_AGENTACTION_PROCESS = Giskard.defineToken(MiNDTokenType.TAG, "Process", MTTAG_AGENTACTION, MiNDAgentAction.Process);
	MiNDToken MTTAG_AGENTACTION_END = Giskard.defineToken(MiNDTokenType.TAG, "End", MTTAG_AGENTACTION, MiNDAgentAction.End);
	MiNDToken MTTAG_AGENTACTION_RELEASE = Giskard.defineToken(MiNDTokenType.TAG, "Release", MTTAG_AGENTACTION, MiNDAgentAction.Release);
	
}
