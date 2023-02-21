package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {

	class BrainHandle implements MindHandle {
		final Object id;
		final String alias;
		
		public BrainHandle(Object id, String alias) {
			this.id = id;
			this.alias = alias;
		}

		@Override
		public Object getId() {
			return id;
		}

		@Override
		public String getAlias() {
			return alias;
		}
		
	}
}
