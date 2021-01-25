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
		
		@Override
		public Val get(Key key) {
			return map.get(key);
		}
	}	
	
	public class DustFactory<Key, Val> extends DustMap<Key, Val> {
		DustCreator<Key, ? extends Val> creator;
		
		public DustFactory(boolean sorted) {
			super(sorted);
		}
		
		public DustFactory(boolean sorted, DustCreator<Key, ? extends Val> creator) {
			this(sorted);
			setCreator(creator);
		}
		
		public void setCreator(DustCreator<Key, ? extends Val> creator) {
			this.creator = creator;
		}
		
		public synchronized Val get(Key key) {
			Val ret = super.get(key);
			if ( null == ret ) {
				ret = creator.create(key);
				map.put(key, ret);
			}
			return ret;
		}
	}
}
