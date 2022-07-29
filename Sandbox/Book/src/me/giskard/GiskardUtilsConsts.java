package me.giskard;

import java.util.HashMap;

public interface GiskardUtilsConsts {
	public static interface GisUtilsCommandCenter {
		<RetType> RetType execCmd(Enum<?> cmd, Object... params) throws Exception;
	}

	public static interface GisUtilsCreator<KeyType, ValType> {
		ValType create(KeyType key, Object... params);

		default void init(ValType val, KeyType key, Object... params) {
		};
	}

	public static class GisUtilsCreatorClass<KeyType, ValType> implements GisUtilsCreator<KeyType, ValType> {
		Class<? extends ValType> c;

		public GisUtilsCreatorClass(Class<? extends ValType> c) {
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

	public static class GisUtilsFactory<KeyType, ValType> extends HashMap<KeyType, ValType> {
		private static final long serialVersionUID = 1L;

		GisUtilsCreator<KeyType, ValType> creator;

		public GisUtilsFactory(GisUtilsCreator<KeyType, ValType> creator) {
			this.creator = creator;
		}

		public GisUtilsFactory(Class<? extends ValType> c) {
			this.creator = new GisUtilsCreatorClass<KeyType, ValType>(c);
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
