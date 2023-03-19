package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public class DustBrainBootUtils extends GiskardUtils implements DustBrainConsts, DustBrainBootstrap {

	public static DustBrainHandle createKnowledgeItem(KnowledgeItem unit) {
		KnowledgeItem ki = createInstance(CN_KNOWLEDGE_ITEM);

		DustBrainHandle handle = new DustBrainHandle();

		initKnowledgeItem(unit, ki, handle);

		return handle;
	}

	public static KnowledgeItem createKnowledgeItem(KnowledgeItem unit, MindHandle handle) throws Exception {
		KnowledgeItem ki = createInstance(CN_KNOWLEDGE_ITEM);

		if ( null == handle ) {
			handle = new DustBrainHandle();
		}

		initKnowledgeItem(unit, ki, handle);

		return ki;
	}

	public static void initKnowledgeItem(KnowledgeItem unit, KnowledgeItem ki, MindHandle handle) {
		ki.access(MindAccess.Insert, getHandle(BootToken.memKnowledgeHandle), MindColl.One, null, handle, null);
		unit.access(MindAccess.Insert, getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, handle, ki, null);
	}

	public static MindHandle getHandle(KnowledgeItem item) {
		return item.access(MindAccess.Peek, getHandle(BootToken.memKnowledgeHandle), MindColl.One, null, null, null);
	}

	public static KnowledgeItem resolveHandle(KnowledgeItem unit, MindHandle handle, KnowledgeConnector conn) {
		return unit.access((null == conn) ? MindAccess.Peek : MindAccess.Get, getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, handle, null, conn);
	}

	public static KnowledgeItem resolveBrainHandle(KnowledgeItem brain, MindHandle handle) {
		KnowledgeItem unit;

		Object unitToken = ((DustBrainHandle) handle).getUnitToken();
		if ( null == unitToken ) {
			unit = brain;
		} else {
			DustBrainHandle hUnit = brain.access(MindAccess.Peek, getHandle(BootToken.memBrainUnits), MindColl.Map, unitToken, null, null);
			unit = resolveHandle(brain, hUnit, null);
		}
		
		return (null == unit) ? null : resolveHandle(unit, handle, null);
	}

}