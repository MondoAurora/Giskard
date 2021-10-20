package me.giskard;

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
	
}
