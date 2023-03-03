package me.giskard.dust.mod.brain;

public interface DustBrainBootstrap {

	String CN_KNOWLEDGE_ITEM = "me.giskard.dust.mod.brain.logic.DustBrainKnowledge";

	String CN_KNOWLEDGE = "me.giskard.dust.mod.brain.logic.DustBrainLogicKnowledge";
	String CN_CONTEXT = "me.giskard.dust.mod.brain.logic.DustBrainLogicContext";
	String CN_DIALOG = "me.giskard.dust.mod.brain.logic.DustBrainLogicDialog";
	
	enum BootToken {
		typContext, memContextLocalKnowledge,
		typKnowledge, memKnowledgeHandle,
		logMediator, memMediatorRemote, memMediatorLocalToRemote, memMediatorRemoteToLocal,
		typText, memTextString, typVocabulary, memVocabularyWords
	}

}
