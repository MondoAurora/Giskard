package me.giskard.dust.brain;

import java.util.Collections;
import java.util.Iterator;

import me.giskard.dust.DustTokens;

public interface DustBrainConsts extends DustTokens {
	
	final MiNDSpecKey KEY_ITERATOR = new MiNDSpecKey("Iterator");
	final MiNDSpecKey KEY_COLLTYPE = new MiNDSpecKey("CollType");

	public static final Iterator<?> NO_VAL = Collections.emptyIterator();

	enum CollType {
		One, Arr(true), Map(true), Set, //SStack, Queue, Pool,
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
		Int, Real, Link, Raw, Any
	}
	
}
