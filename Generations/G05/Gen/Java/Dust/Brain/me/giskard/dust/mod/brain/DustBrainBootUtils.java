package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public class DustBrainBootUtils extends GiskardUtils implements DustBrainConsts, DustBrainBootstrap {
	
	public static DustBrainHandle createKnowledgeItem(KnowledgeItem ctx) {
		KnowledgeItem ki = createInstance(CN_KNOWLEDGE_ITEM);

		DustBrainHandle handle = new DustBrainHandle();

		initKnowledgeItem(ctx, ki, handle);

		return handle;
	}

	public static KnowledgeItem createKnowledgeItem(KnowledgeItem ctx, MindHandle handle) throws Exception {
		KnowledgeItem ki = createInstance(CN_KNOWLEDGE_ITEM);

		if ( null == handle ) {
			handle = new DustBrainHandle();
		}

		initKnowledgeItem(ctx, ki, handle);

		return ki;
	}

	public static void initKnowledgeItem(KnowledgeItem ctx, KnowledgeItem ki, MindHandle handle) {
		ki.access(MindAccess.Insert, getHandle(BootToken.memKnowledgeHandle), MindColl.One, null, handle, null);
		ctx.access(MindAccess.Insert, getHandle(BootToken.memContextLocalKnowledge), MindColl.Map, handle, ki, null);
	}

	public static MindHandle getHandle(KnowledgeItem item) {
		return item.access(MindAccess.Peek, getHandle(BootToken.memKnowledgeHandle), MindColl.One, null, null, null);
	}

	public static KnowledgeItem resolveHandle(KnowledgeItem ctx, MindHandle handle, KnowledgeConnector conn) {
		return ctx.access((null == conn) ? MindAccess.Peek : MindAccess.Get, getHandle(BootToken.memContextLocalKnowledge), MindColl.Map, handle, null, conn);
	}

}