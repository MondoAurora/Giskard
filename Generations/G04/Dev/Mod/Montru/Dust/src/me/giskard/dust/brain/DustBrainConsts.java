package me.giskard.dust.brain;

import me.giskard.dust.DustConsts;

public interface DustBrainConsts extends DustConsts {

	enum CollType {
		One, Arr(true), Map(true), Set, Stack, Queue, Pool,
		;
		
		public final boolean hasKey;

		private CollType(boolean hasKey) {
			this.hasKey = hasKey;
		}
		
		private CollType() {
			this(false);
		}
	}
	
	enum ValType {
		Int, Real, Link, Raw
	}
	
}
