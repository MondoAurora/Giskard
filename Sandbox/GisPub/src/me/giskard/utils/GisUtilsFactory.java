package me.giskard.utils;

import java.util.HashMap;

import me.giskard.utils.GisUtilsConsts.ClassCreator;
import me.giskard.utils.GisUtilsConsts.Creator;

public class GisUtilsFactory<KeyType, ValType> extends HashMap<KeyType, ValType> {
	private static final long serialVersionUID = 1L;

	Creator<KeyType, ValType> creator;

	public GisUtilsFactory(Creator<KeyType, ValType> creator) {
		this.creator = creator;
	}

	public GisUtilsFactory(Class<? extends ValType> c) {
		this.creator = new ClassCreator<KeyType, ValType>(c);
	}

	public ValType peek(Object key) {
		return super.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValType get(Object key) {
		ValType v = super.get(key);

		if ( null == v ) {
			KeyType k = (KeyType) key;
			v = creator.create(k);
			put(k, v);
			creator.init(v, k);
		}

		return v;
	}

	public synchronized ValType get(KeyType key, Object... params) {
		ValType v = super.get(key);

		if ( null == v ) {
			v = creator.create(key, params);
			put(key, v);
			creator.init(v, key, params);
		}

		return v;
	}
}