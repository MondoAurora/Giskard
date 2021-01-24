package me.giskard.java.dust;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public interface DustCollections extends DustConsts {
	public class DustMap<Key, Val> implements DustCollMap<Key, Val> {
		Map<Key, Val> map;
		
		public DustMap(boolean sorted) {
			this.map = sorted ? new TreeMap<>() : new HashMap<>();
		}
		
		public DustMap() {
			this(false);
		}
		
		@Override
		public Val get(Key key) {
			return map.get(key);
		}
	}	
	
	public class DustFactory<Key, Val> extends DustMap<Key, Val> {
		DustCreator<Key, Val> creator;
		
		public DustFactory(DustCreator<Key, Val> creator, boolean sorted) {
			this.creator = creator;
		}
		
		public synchronized Val get(Key key, Object... hints) {
			Val ret = super.get(key);
			if ( null == ret ) {
				ret = creator.create(key, hints);
				map.put(key, ret);
			}
			return ret;
		}
	}
}
