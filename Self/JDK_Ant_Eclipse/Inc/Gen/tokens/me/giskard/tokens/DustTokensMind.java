package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMind extends GiskardConsts, DustTokensGeneric {
	MiNDToken MTUNIT_MODEL = Giskard.defineToken(MiNDTokenType.Unit, "MiND_Model");

	MiNDToken MTTYPE_UNIT = Giskard.defineToken(MiNDTokenType.Type, "Unit", MTUNIT_MODEL);
	MiNDToken MTMEMBER_UNIT_SERVICES = Giskard.defineToken(MiNDTokenType.Member, "Services", MTTYPE_UNIT, MiNDValType.Link, MiNDCollType.Map);

	MiNDToken MTTYPE_ENTITY = Giskard.defineToken(MiNDTokenType.Type, "Entity", MTUNIT_MODEL);
	MiNDToken MTMEMBER_ENTITY_HANDLE = Giskard.defineToken(MiNDTokenType.Member, "Handle", MTTYPE_ENTITY, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_PRIMARYTYPE = Giskard.defineToken(MiNDTokenType.Member, "PrimaryType", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_TYPES = Giskard.defineToken(MiNDTokenType.Member, "Types", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEMBER_ENTITY_TAGS = Giskard.defineToken(MiNDTokenType.Member, "Tags", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEMBER_ENTITY_STOREUNIT = Giskard.defineToken(MiNDTokenType.Member, "StoreUnit", MTTYPE_ENTITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_STOREID = Giskard.defineToken(MiNDTokenType.Member, "StoreId", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);

	
	
	MiNDToken MTUNIT_IDEA = Giskard.defineToken(MiNDTokenType.Unit, "MiND_Idea");
	
	MiNDToken MTTYPE_TYPE = Giskard.defineToken(MiNDTokenType.Type, "Type", MTUNIT_IDEA);
	MiNDToken MTTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.Type, "Member", MTUNIT_IDEA);

	MiNDToken MTTAG_TOKENTYPE = Giskard.defineToken(MiNDTokenType.Tag, "TokenType", MTUNIT_IDEA, MiNDTokenType.class);
	MiNDToken MTTAG_TOKENTYPE_UNIT = Giskard.defineToken(MiNDTokenType.Tag, "Unit", MTTAG_TOKENTYPE, MiNDTokenType.Unit);
	MiNDToken MTTAG_TOKENTYPE_TYPE = Giskard.defineToken(MiNDTokenType.Tag, "Type", MTTAG_TOKENTYPE, MiNDTokenType.Type);
	MiNDToken MTTAG_TOKENTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.Tag, "Member", MTTAG_TOKENTYPE, MiNDTokenType.Member);
	MiNDToken MTTAG_TOKENTYPE_TAG = Giskard.defineToken(MiNDTokenType.Tag, "Tag", MTTAG_TOKENTYPE, MiNDTokenType.Tag);
	MiNDToken MTTAG_TOKENTYPE_SERVICE = Giskard.defineToken(MiNDTokenType.Tag, "Service", MTTAG_TOKENTYPE, MiNDTokenType.Service);
	MiNDToken MTTAG_TOKENTYPE_AGENT = Giskard.defineToken(MiNDTokenType.Tag, "Agent", MTTAG_TOKENTYPE, MiNDTokenType.Agent);
	MiNDToken MTTAG_TOKENTYPE_LOCAL = Giskard.defineToken(MiNDTokenType.Tag, "Local", MTTAG_TOKENTYPE, MiNDTokenType.Local);
	
	MiNDToken MTTAG_VALTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ValType", MTUNIT_IDEA, MiNDValType.class);
	MiNDToken MTTAG_VALTYPE_INT = Giskard.defineToken(MiNDTokenType.Tag, "Int", MTTAG_VALTYPE, MiNDValType.Int);
	MiNDToken MTTAG_VALTYPE_REAL = Giskard.defineToken(MiNDTokenType.Tag, "Real", MTTAG_VALTYPE, MiNDValType.Real);
	MiNDToken MTTAG_VALTYPE_LINK = Giskard.defineToken(MiNDTokenType.Tag, "Link", MTTAG_VALTYPE, MiNDValType.Link);
	MiNDToken MTTAG_VALTYPE_RAW = Giskard.defineToken(MiNDTokenType.Tag, "Raw", MTTAG_VALTYPE, MiNDValType.Raw);

	MiNDToken MTTAG_COLLTYPE = Giskard.defineToken(MiNDTokenType.Tag, "CollType", MTUNIT_IDEA, MiNDCollType.class);
	MiNDToken MTTAG_COLLTYPE_ONE = Giskard.defineToken(MiNDTokenType.Tag, "One", MTTAG_COLLTYPE, MiNDCollType.One);
	MiNDToken MTTAG_COLLTYPE_ARR = Giskard.defineToken(MiNDTokenType.Tag, "Arr", MTTAG_COLLTYPE, MiNDCollType.Arr);
	MiNDToken MTTAG_COLLTYPE_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_COLLTYPE, MiNDCollType.Set);
	MiNDToken MTTAG_COLLTYPE_MAP = Giskard.defineToken(MiNDTokenType.Tag, "Map", MTTAG_COLLTYPE, MiNDCollType.Map);

	
	MiNDToken MTUNIT_DIALOG = Giskard.defineToken(MiNDTokenType.Unit, "MiND_Dialog");
	
	MiNDToken MTTYPE_CONTEXT = Giskard.defineToken(MiNDTokenType.Type, "Context", MTUNIT_DIALOG);
	MiNDToken MTMEMBER_CONTEXT_TOKENS = Giskard.defineToken(MiNDTokenType.Member, "Tokens", MTTYPE_CONTEXT, MiNDValType.Link, MiNDCollType.Map);

	MiNDToken MTTAG_ACCESSCOMMAND = Giskard.defineToken(MiNDTokenType.Tag, "AccessCommand", MTUNIT_DIALOG, MiNDAccessCommand.class);
	MiNDToken MTTAG_ACCESSCOMMAND_CHK = Giskard.defineToken(MiNDTokenType.Tag, "Chk", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Chk);
	MiNDToken MTTAG_ACCESSCOMMAND_GET = Giskard.defineToken(MiNDTokenType.Tag, "Get", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Get);
	MiNDToken MTTAG_ACCESSCOMMAND_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Set);
	MiNDToken MTTAG_ACCESSCOMMAND_ADD = Giskard.defineToken(MiNDTokenType.Tag, "Add", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Add);
	MiNDToken MTTAG_ACCESSCOMMAND_DEL = Giskard.defineToken(MiNDTokenType.Tag, "Del", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Del);
	MiNDToken MTTAG_ACCESSCOMMAND_USE = Giskard.defineToken(MiNDTokenType.Tag, "Use", MTTAG_ACCESSCOMMAND, MiNDAccessCommand.Use);

	MiNDToken MTTAG_VISITSTATE = Giskard.defineToken(MiNDTokenType.Tag, "VisitState", MTUNIT_DIALOG, MiNDVisitState.class);
	MiNDToken MTTAG_VISITSTATE_ENTITY = Giskard.defineToken(MiNDTokenType.Tag, "Entity", MTTAG_VISITSTATE, MiNDVisitState.Entity);
	MiNDToken MTTAG_VISITSTATE_TOKEN = Giskard.defineToken(MiNDTokenType.Tag, "Token", MTTAG_VISITSTATE, MiNDVisitState.Token);
	MiNDToken MTTAG_VISITSTATE_VALUE = Giskard.defineToken(MiNDTokenType.Tag, "Value", MTTAG_VISITSTATE, MiNDVisitState.Value);
	
	MiNDToken MTTYPE_VISITINFO = Giskard.defineToken(MiNDTokenType.Type, "VisitInfo", MTUNIT_DIALOG);
	MiNDToken MTMEMBER_VISITINFO_KEYARR = Giskard.defineToken(MiNDTokenType.Member, "KeyArr", MTTYPE_VISITINFO, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_KEYMAP = Giskard.defineToken(MiNDTokenType.Member, "KeyMap", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_TOKEN = Giskard.defineToken(MiNDTokenType.Member, "Token", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALOLD = Giskard.defineToken(MiNDTokenType.Member, "ValOld", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALNEW = Giskard.defineToken(MiNDTokenType.Member, "ValNew", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKOLD = Giskard.defineToken(MiNDTokenType.Member, "LinkOld", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKNEW = Giskard.defineToken(MiNDTokenType.Member, "LinkNew", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);


	MiNDToken MTUNIT_NARRATIVE = Giskard.defineToken(MiNDTokenType.Unit, "MiND_Narrative");
	
	MiNDToken MTTYPE_AGENT = Giskard.defineToken(MiNDTokenType.Type, "Agent", MTUNIT_NARRATIVE);
	MiNDToken MTTYPE_SERVICE = Giskard.defineToken(MiNDTokenType.Type, "Service", MTUNIT_NARRATIVE);

	MiNDToken MTTAG_RESULTTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ResultType", MTUNIT_NARRATIVE, MiNDResultType.class);
	MiNDToken MTTAG_RESULTTYPE_NOTIMPLEMENTED = Giskard.defineToken(MiNDTokenType.Tag, "Notimplemented", MTTAG_RESULTTYPE, MiNDResultType.Notimplemented);
	MiNDToken MTTAG_RESULTTYPE_REJECT = Giskard.defineToken(MiNDTokenType.Tag, "Reject", MTTAG_RESULTTYPE, MiNDResultType.Reject);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT_PASS = Giskard.defineToken(MiNDTokenType.Tag, "AcceptPass", MTTAG_RESULTTYPE, MiNDResultType.AcceptPass);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT = Giskard.defineToken(MiNDTokenType.Tag, "Accept", MTTAG_RESULTTYPE, MiNDResultType.Accept);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT_READ = Giskard.defineToken(MiNDTokenType.Tag, "AcceptRead", MTTAG_RESULTTYPE, MiNDResultType.AcceptRead);
	MiNDToken MTTAG_RESULTTYPE_READ = Giskard.defineToken(MiNDTokenType.Tag, "Read", MTTAG_RESULTTYPE, MiNDResultType.Read);
	MiNDToken MTTAG_RESULTTYPE_WAIT = Giskard.defineToken(MiNDTokenType.Tag, "Wait", MTTAG_RESULTTYPE, MiNDResultType.Wait);
	
	MiNDToken MTAGENT_CTRL_ITERATION = Giskard.defineToken(MiNDTokenType.Agent, "Iteration", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SEQUENCE = Giskard.defineToken(MiNDTokenType.Agent, "Sequence", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_CTRL_SELECTION = Giskard.defineToken(MiNDTokenType.Agent, "Selection", MTUNIT_NARRATIVE);

	MiNDToken MTAGENT_DATA_FINDALL = Giskard.defineToken(MiNDTokenType.Agent, "FindAll", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_DATA_COLLECT = Giskard.defineToken(MiNDTokenType.Agent, "Collect", MTUNIT_NARRATIVE);

	MiNDToken MTTAG_AGENTACTION = Giskard.defineToken(MiNDTokenType.Tag, "AgentAction", MTUNIT_NARRATIVE, MiNDAgentAction.class);
	MiNDToken MTTAG_AGENTACTION_INIT = Giskard.defineToken(MiNDTokenType.Tag, "Init", MTTAG_AGENTACTION, MiNDAgentAction.Init);
	MiNDToken MTTAG_AGENTACTION_BEGIN = Giskard.defineToken(MiNDTokenType.Tag, "Begin", MTTAG_AGENTACTION, MiNDAgentAction.Begin);
	MiNDToken MTTAG_AGENTACTION_PROCESS = Giskard.defineToken(MiNDTokenType.Tag, "Process", MTTAG_AGENTACTION, MiNDAgentAction.Process);
	MiNDToken MTTAG_AGENTACTION_END = Giskard.defineToken(MiNDTokenType.Tag, "End", MTTAG_AGENTACTION, MiNDAgentAction.End);
	MiNDToken MTTAG_AGENTACTION_RELEASE = Giskard.defineToken(MiNDTokenType.Tag, "Release", MTTAG_AGENTACTION, MiNDAgentAction.Release);
	
}
