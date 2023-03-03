package me.giskard.dust.mod.brain;

import java.util.EnumMap;
import java.util.Map;

import me.giskard.mind.GiskardConsts;
import me.giskard.mind.GiskardMind;
import me.giskard.mind.GiskardUtils;

public abstract class DustBrainBase implements DustBrainConsts, DustBrainBootstrap, GiskardConsts.MindBrain {

	protected KnowledgeItem brainRoot;

	protected LogicKnowledge logKnowledge;
	protected LogicContext logContext;
	protected LogicDialog logDialog;

	protected EnumMap<BootToken, BrainHandle> bootHandles = new EnumMap<>(BootToken.class);

	protected abstract void loadBootTokens();

	public KnowledgeItem createKnowledgeItem() throws Exception {
		KnowledgeItem ki = GiskardUtils.createInstance(CN_KNOWLEDGE_ITEM);

		return ki;
	}

	public KnowledgeItem createKnowledgeItem(KnowledgeItem ctx, BrainHandle handle) throws Exception {
		KnowledgeItem ki = createKnowledgeItem();
		
		if ( null == handle ) {
			handle = new BrainHandle();
		}

		ki.access(MindAccess.Insert, bootHandles.get(BootToken.memKnowledgeHandle), MindColl.One, null, handle, null);

		ctx.access(MindAccess.Insert, bootHandles.get(BootToken.memContextLocalKnowledge), MindColl.Map, handle, ki, null);

		return ki;
	}

	protected void initBrain() throws Exception {
		brainRoot = createKnowledgeItem();

		logKnowledge = GiskardUtils.createInstance(CN_KNOWLEDGE);
		logContext = GiskardUtils.createInstance(CN_CONTEXT);
		logDialog = GiskardUtils.createInstance(CN_DIALOG);

		loadBootTokens();

		KnowledgeItem bootCtx = createKnowledgeItem();
		
		KnowledgeItem langCtx = createKnowledgeItem(bootCtx, null);
		KnowledgeItem bootVoc = createKnowledgeItem(langCtx, null);
		KnowledgeItem bootMed = createKnowledgeItem(bootCtx, null);
		
		BrainHandle lh = langCtx.access(MindAccess.Peek, bootHandles.get(BootToken.memKnowledgeHandle), MindColl.One, null, null, null);
		bootMed.access(MindAccess.Set, bootHandles.get(BootToken.memMediatorRemote), MindColl.One, null, lh, null);

		for (Map.Entry<BootToken, BrainHandle> e : bootHandles.entrySet()) {
			BrainHandle bh = e.getValue();
			createKnowledgeItem(bootCtx, bh);
			
			String tokenStr = e.getKey().name();
			KnowledgeItem token = createKnowledgeItem(langCtx, null);
			BrainHandle th = token.access(MindAccess.Peek, bootHandles.get(BootToken.memKnowledgeHandle), MindColl.One, null, null, null);
			
			token.access(MindAccess.Set, bootHandles.get(BootToken.memTextString), MindColl.One, null, tokenStr, null);
			bootVoc.access(MindAccess.Insert, bootHandles.get(BootToken.memVocabularyWords), MindColl.Map, tokenStr, token, null);			
			bootMed.access(MindAccess.Insert, bootHandles.get(BootToken.memMediatorLocalToRemote), MindColl.Map, bh, th, null);
			bootMed.access(MindAccess.Insert, bootHandles.get(BootToken.memMediatorRemoteToLocal), MindColl.Map, th, bh, null);
		}

		GiskardMind.dump(bootVoc);
	}

}
