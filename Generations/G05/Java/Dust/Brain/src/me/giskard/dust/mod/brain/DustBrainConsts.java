package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {

	class BrainHandle implements MindHandle {
		final Object id;
		
		public BrainHandle(Object id) {
			this.id = id;
		}

		@Override
		public Object getId() {
			return id;
		}
	}
	
	interface BrainKnowledge {
		<RetType> RetType access(BrainContext ctx, MindAccess cmd, Object val, Object root, Object... path);
	}
	
	interface BrainContext {
		MindColl getMemberColl(MindHandle hMember);
		MindHandle getTagParent(MindHandle hTag);
	}
	
	interface BrainDialog {
		
	}
}
