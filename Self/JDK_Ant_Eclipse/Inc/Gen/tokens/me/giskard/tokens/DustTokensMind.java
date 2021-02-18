package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMind extends MindConsts {
	MiNDToken MT_DIALOG_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Dialog");
	
	MiNDToken MT_DIALOG_ACCESSCOMMAND = Mind.defineToken(MiNDTokenType.TAG, "AccessCommand", MT_DIALOG_UNIT, MiNDAccessCommand.class);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_CHK = Mind.defineToken(MiNDTokenType.TAG, "Chk", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.CHK);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_GET = Mind.defineToken(MiNDTokenType.TAG, "Get", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.GET);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_SET = Mind.defineToken(MiNDTokenType.TAG, "Set", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.SET);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_ADD = Mind.defineToken(MiNDTokenType.TAG, "Add", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.ADD);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_DEL = Mind.defineToken(MiNDTokenType.TAG, "Del", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.DEL);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_USE = Mind.defineToken(MiNDTokenType.TAG, "Use", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.USE);
	
	MiNDToken MT_NARRATIVE_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_Narrative");
	
	MiNDToken MT_NARRATIVE_AGENTACTION = Mind.defineToken(MiNDTokenType.TAG, "AgentAction", MT_NARRATIVE_UNIT, MiNDAgentAction.class);
	MiNDToken MT_NARRATIVE_AGENTACTION_INIT = Mind.defineToken(MiNDTokenType.TAG, "Init", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.INIT);
	MiNDToken MT_NARRATIVE_AGENTACTION_BEGIN = Mind.defineToken(MiNDTokenType.TAG, "Begin", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.BEGIN);
	MiNDToken MT_NARRATIVE_AGENTACTION_PROCESS = Mind.defineToken(MiNDTokenType.TAG, "Process", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.PROCESS);
	MiNDToken MT_NARRATIVE_AGENTACTION_END = Mind.defineToken(MiNDTokenType.TAG, "End", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.END);
	MiNDToken MT_NARRATIVE_AGENTACTION_RELEASE = Mind.defineToken(MiNDTokenType.TAG, "Release", MT_NARRATIVE_AGENTACTION, MiNDAgentAction.RELEASE);

}
