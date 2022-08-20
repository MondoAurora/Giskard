package me.giskard.dust.brain;

import me.giskard.dust.DustConsts;

public interface DustBrainConsts extends DustConsts {

	enum CollType {
		One(false), Arr(true), Map(true), Set(false), // Stack, Queue, Pool?
		
		;
		
		public final boolean indexed;

		private CollType(boolean indexed) {
			this.indexed = indexed;
		}
		
		
	}
	
	enum ValType {
		Int, Real, Link, Raw
	}
	
}
