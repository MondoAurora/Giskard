package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMind extends GiskardConsts {
	MiNDToken MTUNIT_MODEL = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Model");

	MiNDToken MTTYPE_UNIT = Giskard.defineToken(MiNDTokenType.TYPE, "Unit", MTUNIT_MODEL);

	MiNDToken MTTYPE_ENTITY = Giskard.defineToken(MiNDTokenType.TYPE, "Entity", MTUNIT_MODEL);
	MiNDToken MTMEMBER_ENTITY_HANDLE = Giskard.defineToken(MiNDTokenType.MEMBER, "Handle", MTTYPE_ENTITY, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_PRIMARYTYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "PrimaryType", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_TYPES = Giskard.defineToken(MiNDTokenType.MEMBER, "Types", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEMBER_ENTITY_TAGS = Giskard.defineToken(MiNDTokenType.MEMBER, "Tags", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEMBER_ENTITY_STOREUNIT = Giskard.defineToken(MiNDTokenType.MEMBER, "StoreUnit", MTTYPE_ENTITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_STOREID = Giskard.defineToken(MiNDTokenType.MEMBER, "StoreId", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);

	
	
	MiNDToken MTUNIT_IDEA = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Idea");
	
	MiNDToken MTTYPE_TYPE = Giskard.defineToken(MiNDTokenType.TYPE, "Type", MTUNIT_IDEA);
	MiNDToken MTTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.TYPE, "Member", MTUNIT_IDEA);

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
	
	MiNDToken MTTYPE_CONTEXT = Giskard.defineToken(MiNDTokenType.TYPE, "Context", MTUNIT_DIALOG);
	MiNDToken MTMEMBER_CONTEXT_TOKENS = Giskard.defineToken(MiNDTokenType.MEMBER, "Tokens", MTTYPE_CONTEXT, MiNDValType.Link, MiNDCollType.Map);

	MiNDToken MTTAG_ACCESSCOMMAND = Giskard.defineToken(MiNDTokenType.TAG, "AccessCommand", MTUNIT_DIALOG, MiNDAccessCommand.class);
	MiNDToken MTTAG_ACCESSCOMMAND_CHK = Giskard.defineToken(MiNDTokenType.TAG, "Chk", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Chk);
	MiNDToken MTTAG_ACCESSCOMMAND_GET = Giskard.defineToken(MiNDTokenType.TAG, "Get", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Get);
	MiNDToken MTTAG_ACCESSCOMMAND_SET = Giskard.defineToken(MiNDTokenType.TAG, "Set", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Set);
	MiNDToken MTTAG_ACCESSCOMMAND_ADD = Giskard.defineToken(MiNDTokenType.TAG, "Add", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Add);
	MiNDToken MTTAG_ACCESSCOMMAND_DEL = Giskard.defineToken(MiNDTokenType.TAG, "Del", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Del);
	MiNDToken MTTAG_ACCESSCOMMAND_USE = Giskard.defineToken(MiNDTokenType.TAG, "Use", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Use);

	MiNDToken MTTAG_VISITSTATE = Giskard.defineToken(MiNDTokenType.TAG, "VisitState", MTUNIT_DIALOG, MiNDVisitState.class);
	MiNDToken MTTAG_VISITSTATE_ENTITY = Giskard.defineToken(MiNDTokenType.TAG, "Entity", MTTAG_VISITSTATE, MiNDVisitState.Entity);
	MiNDToken MTTAG_VISITSTATE_TOKEN = Giskard.defineToken(MiNDTokenType.TAG, "Token", MTTAG_VISITSTATE, MiNDVisitState.Token);
	MiNDToken MTTAG_VISITSTATE_VALUE = Giskard.defineToken(MiNDTokenType.TAG, "Value", MTTAG_VISITSTATE, MiNDVisitState.Value);
	
	MiNDToken MTTYPE_VISITINFO = Giskard.defineToken(MiNDTokenType.TYPE, "VisitInfo", MTUNIT_DIALOG);
	MiNDToken MTMEMBER_VISITINFO_KEY = Giskard.defineToken(MiNDTokenType.MEMBER, "Key", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_TOKEN = Giskard.defineToken(MiNDTokenType.MEMBER, "Token", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALOLD = Giskard.defineToken(MiNDTokenType.MEMBER, "ValOld", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALNEW = Giskard.defineToken(MiNDTokenType.MEMBER, "ValNew", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKOLD = Giskard.defineToken(MiNDTokenType.MEMBER, "LinkOld", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKNEW = Giskard.defineToken(MiNDTokenType.MEMBER, "LinkNew", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);


	MiNDToken MTUNIT_NARRATIVE = Giskard.defineToken(MiNDTokenType.UNIT, "MiND_Narrative");
	
	MiNDToken MTTYPE_AGENT = Giskard.defineToken(MiNDTokenType.TYPE, "Agent", MTUNIT_NARRATIVE);
	
	MiNDToken MTTAG_RESULTTYPE = Giskard.defineToken(MiNDTokenType.TAG, "ResultType", MTUNIT_NARRATIVE, MiNDResultType.class);
	MiNDToken MTTAG_RESULTTYPE_NOTIMPLEMENTED = Giskard.defineToken(MiNDTokenType.TAG, "NOTIMPLEMENTED", MTTAG_RESULTTYPE, MiNDResultType.NOTIMPLEMENTED);
	MiNDToken MTTAG_RESULTTYPE_REJECT = Giskard.defineToken(MiNDTokenType.TAG, "REJECT", MTTAG_RESULTTYPE, MiNDResultType.REJECT);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT_PASS = Giskard.defineToken(MiNDTokenType.TAG, "ACCEPT_PASS", MTTAG_RESULTTYPE, MiNDResultType.ACCEPT_PASS);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT = Giskard.defineToken(MiNDTokenType.TAG, "ACCEPT", MTTAG_RESULTTYPE, MiNDResultType.ACCEPT);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT_READ = Giskard.defineToken(MiNDTokenType.TAG, "ACCEPT_READ", MTTAG_RESULTTYPE, MiNDResultType.ACCEPT_READ);
	MiNDToken MTTAG_RESULTTYPE_READ = Giskard.defineToken(MiNDTokenType.TAG, "READ", MTTAG_RESULTTYPE, MiNDResultType.READ);
	MiNDToken MTTAG_RESULTTYPE_WAIT = Giskard.defineToken(MiNDTokenType.TAG, "WAIT", MTTAG_RESULTTYPE, MiNDResultType.WAIT);
	
	MiNDToken MTAGENT_CTRL_ITERATION = Giskard.defineToken(MiNDTokenType.AGENT, "Iteration", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SEQUENCE = Giskard.defineToken(MiNDTokenType.AGENT, "Sequence", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SELECTION = Giskard.defineToken(MiNDTokenType.AGENT, "Selection", MTUNIT_NARRATIVE);

	MiNDToken MTAGENT_DATA_FINDALL = Giskard.defineToken(MiNDTokenType.AGENT, "FindAll", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_DATA_COLLECT = Giskard.defineToken(MiNDTokenType.AGENT, "Collect", MTUNIT_NARRATIVE);

	MiNDToken MTTAG_AGENTACTION = Giskard.defineToken(MiNDTokenType.TAG, "AgentAction", MTUNIT_NARRATIVE, MiNDAgentAction.class);
	MiNDToken MTTAG_AGENTACTION_INIT = Giskard.defineToken(MiNDTokenType.TAG, "Init", MTTAG_AGENTACTION, MiNDAgentAction.Init);
	MiNDToken MTTAG_AGENTACTION_BEGIN = Giskard.defineToken(MiNDTokenType.TAG, "Begin", MTTAG_AGENTACTION, MiNDAgentAction.Begin);
	MiNDToken MTTAG_AGENTACTION_PROCESS = Giskard.defineToken(MiNDTokenType.TAG, "Process", MTTAG_AGENTACTION, MiNDAgentAction.Process);
	MiNDToken MTTAG_AGENTACTION_END = Giskard.defineToken(MiNDTokenType.TAG, "End", MTTAG_AGENTACTION, MiNDAgentAction.End);
	MiNDToken MTTAG_AGENTACTION_RELEASE = Giskard.defineToken(MiNDTokenType.TAG, "Release", MTTAG_AGENTACTION, MiNDAgentAction.Release);
	
}
