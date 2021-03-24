package me.giskard.coll;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.coll.GisCollConsts.MiNDCollMap;

public class GisCollMap<Key, Val> implements MiNDCollMap<Key, Val> {
	Map<Key, Val> map;
	
	public GisCollMap(boolean sorted) {
		this.map = sorted ? new TreeMap<>() : new HashMap<>();
	}
	
	public GisCollMap(GisCollMap<Key, Val> source) {
		this.map = (source.map instanceof TreeMap) ? new TreeMap<>(source.map) : new HashMap<>(source.map);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
	@Override
	public Val get(Key key) {
		return map.get(key);
	}
	
	@Override
	public Val put(Key key, Val val) {
		return map.put(key, val);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public int getCount() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();
	}
}