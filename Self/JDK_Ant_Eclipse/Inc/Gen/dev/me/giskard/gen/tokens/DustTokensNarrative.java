package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensNarrative extends GiskardConsts { 
	MiNDToken MTUNIT_NARRATIVE = Giskard.defineToken(MiNDTokenType.Unit, "Narrative"); 
	MiNDToken MTTYPE_AGENT = Giskard.defineToken(MiNDTokenType.Type, "Agent", MTUNIT_NARRATIVE);
	MiNDToken MTTAG_AGENTACTION = Giskard.defineToken(MiNDTokenType.Tag, "AgentAction", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_FINDALL = Giskard.defineToken(MiNDTokenType.Agent, "FindAll", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_ITERATION = Giskard.defineToken(MiNDTokenType.Agent, "Iteration", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_READ = Giskard.defineToken(MiNDTokenType.Agent, "Read", MTUNIT_NARRATIVE);
	MiNDToken MTTAG_RESULTTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ResultType", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_SELECTION = Giskard.defineToken(MiNDTokenType.Agent, "Selection", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_SEQUENCE = Giskard.defineToken(MiNDTokenType.Agent, "Sequence", MTUNIT_NARRATIVE);
	MiNDToken MTTYPE_SERVICE = Giskard.defineToken(MiNDTokenType.Type, "Service", MTUNIT_NARRATIVE);
	MiNDToken MTAGENT_VISIT = Giskard.defineToken(MiNDTokenType.Agent, "Visit", MTUNIT_NARRATIVE);
	MiNDToken MTTAG_RESULTTYPE_ACCEPT = Giskard.defineToken(MiNDTokenType.Tag, "Accept", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_RESULTTYPE_ACCEPTREAD = Giskard.defineToken(MiNDTokenType.Tag, "AcceptRead", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_RESULTTYPE_NOTIMPLEMENTED = Giskard.defineToken(MiNDTokenType.Tag, "Notimplemented", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_RESULTTYPE_READ = Giskard.defineToken(MiNDTokenType.Tag, "Read", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_RESULTTYPE_REJECT = Giskard.defineToken(MiNDTokenType.Tag, "Reject", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_RESULTTYPE_WAIT = Giskard.defineToken(MiNDTokenType.Tag, "Wait", MTTAG_RESULTTYPE);
	MiNDToken MTTAG_AGENTACTION_BEGIN = Giskard.defineToken(MiNDTokenType.Tag, "Begin", MTTAG_AGENTACTION);
	MiNDToken MTTAG_AGENTACTION_END = Giskard.defineToken(MiNDTokenType.Tag, "End", MTTAG_AGENTACTION);
	MiNDToken MTTAG_AGENTACTION_INIT = Giskard.defineToken(MiNDTokenType.Tag, "Init", MTTAG_AGENTACTION);
	MiNDToken MTTAG_AGENTACTION_PROCESS = Giskard.defineToken(MiNDTokenType.Tag, "Process", MTTAG_AGENTACTION);
	MiNDToken MTTAG_AGENTACTION_RELEASE = Giskard.defineToken(MiNDTokenType.Tag, "Release", MTTAG_AGENTACTION);
}
