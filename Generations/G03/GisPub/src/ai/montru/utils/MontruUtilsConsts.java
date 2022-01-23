package ai.montru.utils;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruUtilsConsts extends GiskardConsts {

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
				return Giskard.wrapException(e, null, key, params);
			}
		}
	}
}
