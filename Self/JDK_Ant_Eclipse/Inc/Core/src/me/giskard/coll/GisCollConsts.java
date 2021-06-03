package me.giskard.coll;

import me.giskard.GiskardConsts;
import me.giskard.GiskardException;

public interface GisCollConsts extends GiskardConsts {
	
	interface MiNDCreator<Key, Val> {
		Val create(Key key);
	}
	
	public static class CreatorSimple<Key, Val> implements MiNDCreator<Key, Val> {
		Class<Val> cVal;

		public CreatorSimple(Class<Val> cVal) {
			this.cVal = cVal;
		}

		@Override
		public Val create(Key key) {
			try {
				return cVal.newInstance();
			} catch (Throwable e) {
				return GiskardException.wrap(e);
			}
		}
	}

	interface MiNDBuilder<Key, Val> extends MiNDCreator<Key, Val>	{
		void release(Key key, Val v);
	}

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
