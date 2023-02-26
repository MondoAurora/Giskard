package me.giskard.dust.mod.brain;

import java.util.HashMap;

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
