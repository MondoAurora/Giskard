package me.giskard.dust.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DustUtilsFactory<KeyType, ValType> implements DustUtilsConsts {
	DustCreator<ValType> creator;
	
	String name;
	protected final Map<KeyType, ValType> content;

	public DustUtilsFactory(DustCreator<ValType> creator) {
		this(creator, false);
	}

	public DustUtilsFactory(DustCreator<ValType> creator, boolean sorted) {
		this.content = sorted ? new TreeMap<>() : new HashMap<>();
		this.creator = creator;
	}

//	protected abstract ValType create(KeyType key, Object... hints);

//	protected void initNew(ValType item, KeyType key, Object... hints) {
//
//	}

	public synchronized ValType peek(KeyType key) {
		return content.get(key);
	}

	public synchronized ValType get(KeyType key, Object... hints) {
		ValType v = content.get(key);

		if (null == v) {
			v = creator.create(key, hints);
			content.put(key, v);
//			initNew(v, key, hints);
		}

		return v;
	}

	public synchronized void clear() {
		content.clear();
	}

	public Iterable<KeyType> keys() {
		return content.keySet();
	}

	public Iterable<ValType> values() {
		return content.values();
	}

	public void put(KeyType key, ValType value) {
		content.put(key, value);
	}

	public boolean drop(ValType value) {
		return content.values().remove(value);
	}

	public static class Simple<KeyType, ValType> extends DustUtilsFactory<KeyType, ValType> {

		public Simple(boolean sorted, Class<? extends ValType> clVal) {
			super(new DustCreatorSimple<ValType>(clVal), sorted);
		}

//		@Override
//		protected ValType create(KeyType key, Object... hints) {
//			try {
//				return constructor.newInstance();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}
	}

	public int size() {
		return content.size();
	}
	
	@Override
	public String toString() {
		return content.toString();
	}
}
