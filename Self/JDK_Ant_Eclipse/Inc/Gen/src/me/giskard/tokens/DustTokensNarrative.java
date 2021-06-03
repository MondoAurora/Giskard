package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensNarrative extends GiskardConsts { 
	MiNDToken MTUNI_NARRATIVE = Giskard.defineToken(MiNDTokenType.Unit, "Narrative"); 
	MiNDToken MTTYP_NARRATIVE_SERVICE = Giskard.defineToken(MiNDTokenType.Type, "Service", MTUNI_NARRATIVE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ResultType", MTUNI_NARRATIVE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_WAIT = Giskard.defineToken(MiNDTokenType.Tag, "Wait", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_REJECT = Giskard.defineToken(MiNDTokenType.Tag, "Reject", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_READ = Giskard.defineToken(MiNDTokenType.Tag, "Read", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_NOTIMPLEMENTED = Giskard.defineToken(MiNDTokenType.Tag, "Notimplemented", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_ACCEPTREAD = Giskard.defineToken(MiNDTokenType.Tag, "AcceptRead", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTTAG_NARRATIVE_RESULTTYPE_ACCEPT = Giskard.defineToken(MiNDTokenType.Tag, "Accept", MTTAG_NARRATIVE_RESULTTYPE);
	MiNDToken MTAGN_NARRATIVE_DATAVISIT = Giskard.defineToken(MiNDTokenType.Agent, "DataVisit", MTUNI_NARRATIVE);
	MiNDToken MTAGN_NARRATIVE_DATAREAD = Giskard.defineToken(MiNDTokenType.Agent, "DataRead", MTUNI_NARRATIVE);
	MiNDToken MTAGN_NARRATIVE_DATALISTALL = Giskard.defineToken(MiNDTokenType.Agent, "DataListAll", MTUNI_NARRATIVE);
	MiNDToken MTAGN_NARRATIVE_CTRLSEQUENCE = Giskard.defineToken(MiNDTokenType.Agent, "CtrlSequence", MTUNI_NARRATIVE);
	MiNDToken MTAGN_NARRATIVE_CTRLSELECTION = Giskard.defineToken(MiNDTokenType.Agent, "CtrlSelection", MTUNI_NARRATIVE);
	MiNDToken MTAGN_NARRATIVE_CTRLITERATION = Giskard.defineToken(MiNDTokenType.Agent, "CtrlIteration", MTUNI_NARRATIVE);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION = Giskard.defineToken(MiNDTokenType.Tag, "AgentAction", MTUNI_NARRATIVE);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION_RELEASE = Giskard.defineToken(MiNDTokenType.Tag, "Release", MTTAG_NARRATIVE_AGENTACTION);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION_PROCESS = Giskard.defineToken(MiNDTokenType.Tag, "Process", MTTAG_NARRATIVE_AGENTACTION);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION_INIT = Giskard.defineToken(MiNDTokenType.Tag, "Init", MTTAG_NARRATIVE_AGENTACTION);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION_END = Giskard.defineToken(MiNDTokenType.Tag, "End", MTTAG_NARRATIVE_AGENTACTION);
	MiNDToken MTTAG_NARRATIVE_AGENTACTION_BEGIN = Giskard.defineToken(MiNDTokenType.Tag, "Begin", MTTAG_NARRATIVE_AGENTACTION);
	MiNDToken MTTYP_NARRATIVE_AGENT = Giskard.defineToken(MiNDTokenType.Type, "Agent", MTUNI_NARRATIVE);
}
