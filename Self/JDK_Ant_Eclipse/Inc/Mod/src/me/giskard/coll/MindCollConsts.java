package me.giskard.coll;

import me.giskard.MindConsts;

public interface MindCollConsts extends MindConsts {
	
	interface MiNDColl<Key, Val> {
		boolean isEmpty();

		int getCount();

		void clear();
	}

	interface MiNDCollMap<Key, Val> extends MiNDColl<Key, Val> {
		Val get(Key key);
		Val put(Key key, Val val);
	}
}
