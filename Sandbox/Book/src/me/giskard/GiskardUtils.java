package me.giskard;

import java.util.HashMap;

public class GiskardUtils implements GiskardConsts {

	public static String toString(Object o) {
		return (null == o) ? null : o.toString();
	}

	public static boolean isEmpty(String str) {
		return (null == str) ? true : (0 == str.trim().length());
	}

	public static StringBuilder sbAppend(StringBuilder sb, String sep, Object... objects) {
		for ( Object o : objects ) {
			String str = toString(o);
			if ( !isEmpty(str) ) {
				if ( null == sb ) {
					sb = new StringBuilder(str);
				} else {
					sb.append(sep).append(str);
				}
			}
		}
		
		return sb;
	}

	public static interface Creator<KeyType, ValType> {
		ValType create(KeyType key, Object... params);

		default void init(ValType val, KeyType key, Object... params) {
		};
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

	public static class HashFactory<KeyType, ValType> extends HashMap<KeyType, ValType> {
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
}
