package me.giskard;

import java.util.HashMap;

public abstract class GiskardUtils implements GiskardConsts {
	
	public static interface Creator<KeyType, ValType> {
		ValType create(KeyType key, Object... params);
		default void init(ValType val, KeyType key, Object... params) {};
	}
	
	public static class ClassCreator<KeyType, ValType> implements Creator<KeyType, ValType> {
		Class<? extends ValType> c;
		
		public ClassCreator(Class<? extends ValType> c) {
			this.c = c;
		}
		
		public ValType create(KeyType key, Object... params) {
			try {
				return c.newInstance();
			} catch (Throwable e) {
				return Giskard.wrapException(e, key, params);
			}
		}
	}
	
	public static class HashFactory<KeyType, ValType> extends HashMap<KeyType, ValType>{
		private static final long serialVersionUID = 1L;
		
		Creator<KeyType, ValType> creator;
		
		public HashFactory(Creator<KeyType, ValType> creator) {
			this.creator = creator;
		}

		public HashFactory(Class<? extends ValType> c) {
			this.creator = new ClassCreator<KeyType, ValType>(c);
		}

		public ValType peek(Object key) {
			return super.get(key);
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
}
