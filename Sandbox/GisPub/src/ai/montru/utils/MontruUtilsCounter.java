package ai.montru.utils;

import java.util.HashMap;
import java.util.Map;

public class MontruUtilsCounter {
	private Map<Object, Integer> data = new HashMap<>();
	
	public int add(Object key) {
		int count = data.getOrDefault(key, 0) + 1;
		data.put(key, count);
		return count;
	}
	
	public int del(Object key) {
		int count = data.getOrDefault(key, 0) - 1;
		
		switch ( count ) {
		case -1:
			count = 0;
			break;
		case 0:
			data.remove(key);
			break;
		default:
			data.put(key, count);
			break;
		}
		
		return count;
	}
	
	public int get(Object key) {
		return data.getOrDefault(key, 0);
	}
	
	public Iterable<Object> getKeys() {
		return data.keySet();
	}
}
