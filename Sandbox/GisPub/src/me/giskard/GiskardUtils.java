package me.giskard;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class GiskardUtils implements GiskardConsts {

	public static String toString(Object o) {
		return (null == o) ? null : o.toString();
	}

	public static boolean isEmpty(String str) {
		return (null == str) ? true : (0 == str.trim().length());
	}

	public static String enumToKey(Enum<?> key) {
		return key.getClass().getSimpleName() + "::" + key.name();
	}

	public static StringBuilder sbAppend(StringBuilder sb, String sep, Object... objects) {
		for (Object o : objects) {
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

	@SuppressWarnings({ "unchecked" })
	public static <RetType> RetType instantiate(String className, Object... params) {
		try {
			return (RetType) instantiate(Class.forName(className), params);
		} catch (Exception e) {
			return Giskard.wrapException(e, null, params);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <RetType> RetType instantiate(Class<RetType> rc, Object... params) {
		try {
			Constructor<RetType> cc = null;
			
			int plen = (null == params) ? 0 : params.length;
			if ( 0 == plen ) {
				cc = rc.getConstructor();
			} else {
				for (Constructor ctor : rc.getConstructors()) {
					Parameter[] cp = ctor.getParameters();
					if ( plen == cp.length ) {
						cc = ctor;
						for (int i = 0; i < plen; ++i) {
							if ( !cp[i].getType().isInstance(params[i]) ) {
								cc = null;
								break;
							}
						}
					}
					if ( null != cc ) {
						break;
					}
				}
			}
			
			return ( null == cc) ? null : cc.newInstance(params);
			
		} catch (Exception e) {
			return Giskard.wrapException(e, null, params);
		}
	}

}
