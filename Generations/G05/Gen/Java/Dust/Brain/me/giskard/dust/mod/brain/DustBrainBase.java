package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public abstract class DustBrainBase implements DustBrainConsts, DustBrainBootstrap, GiskardConsts.MindBrain {
	
	KnowledgeItem brainRoot;
	
	LogicKnowledge logKnowledge;
	LogicContext logContext;
	LogicDialog logDialog;
	
	protected void initBrain() throws Exception {
		brainRoot = (KnowledgeItem) Class.forName(CN_KNOWLEDGE_ITEM).getConstructor().newInstance();
		
		logKnowledge = (LogicKnowledge) Class.forName(CN_KNOWLEDGE).getConstructor().newInstance();
		logContext = (LogicContext) Class.forName(CN_CONTEXT).getConstructor().newInstance();
		logDialog = (LogicDialog) Class.forName(CN_DIALOG).getConstructor().newInstance();
	}

}
