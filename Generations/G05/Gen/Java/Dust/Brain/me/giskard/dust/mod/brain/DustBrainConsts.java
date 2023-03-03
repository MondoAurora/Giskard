package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {
	
	final class BrainHandle implements MindHandle, Comparable<BrainHandle> {
		static Object idlock = new Object();
		static long nextId = 0;
		
		final Long id;

		public BrainHandle() {
			synchronized (idlock) {
				this.id = nextId ++;
			}
		}

		@Override
		public Object getId() {
			return id;
		}

		@Override
		public int compareTo(BrainHandle o) {
			return id.compareTo(o.id);
		}
		
		@Override
		public String toString() {
			return "BH(" + id + ")";
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
		MindHandle createItem(MindHandle hTag);

		MindColl getMemberColl(MindHandle hMember);
		MindHandle getTagParent(MindHandle hTag);
	}

	interface LogicMediator {
		MindHandle getHandleByToken(Object token, KnowledgeItem kMediator, KnowledgeItem kContext);
	}

	interface LogicDialog {

	}
}
