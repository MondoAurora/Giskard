package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {
	
	class BrainHandle implements MindHandle, Comparable<BrainHandle> {
		final Long id;

		public BrainHandle(Long id) {
			this.id = id;
		}

		@Override
		public Object getId() {
			return id;
		}

		@Override
		public int compareTo(BrainHandle o) {
			return id.compareTo(o.id);
		}
	}

	interface KnowledgeConnector {
		Object create(BrainHandle hMember, Object key);
		void notifyChange(MindAccess cmd, BrainHandle hMember, Object key, Object old, Object curr);
	}

	interface KnowledgeItem {
		public <RetType> RetType access(MindAccess cmd, BrainHandle hMember, MindColl coll, Object key, Object val, KnowledgeConnector kc);
	}

	interface LogicKnowledge {
		<RetType> RetType access(LogicContext ctx, MindAccess cmd, Object val, Object root, Object... path);
	}

	interface LogicContext {
		MindColl getMemberColl(MindHandle hMember);

		MindHandle getTagParent(MindHandle hTag);
	}

	interface LogicDialog {

	}
}
