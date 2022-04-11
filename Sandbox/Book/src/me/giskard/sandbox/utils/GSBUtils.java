package me.giskard.sandbox.utils;

public class GSBUtils {
	public static boolean isEmpty(String str) {
		return (null == str) || str.trim().isEmpty();
	}

	public static boolean endsWithNoCase(String str, String end) {
		return str.toLowerCase().endsWith(end.toLowerCase());
	}

	public static String optCutEnd(String str, String end) {
		return endsWithNoCase(str, end) ? str.substring(0, str.length() - end.length()) : str;
	}
	
	public static String toString(Object ob, String sep) {
		if ( null == ob ) {
			return "";
		} else if ( ob.getClass().isArray() ) {
			StringBuilder sb = null;
			for ( Object oo : (Object[]) ob) {
				sb = sbAppend(sb, sep, false, oo);
			}
			return ( null == sb ) ? "" : sb.toString();
		} else { 
			return ob.toString();
		}
	}
	
	public static String toString(Object ob) {
		return toString(ob, ", ");
	}

	public static StringBuilder sbAppend(StringBuilder sb, Object sep, boolean strict, Object... objects) {
		for (Object ob : objects) {
			String str = toString(ob);

			if ( strict || (0 < str.length()) ) {
				if ( null == sb ) {
					sb = new StringBuilder(str);
				} else {
					if ( 0 < sb.length() ) {
						sb.append(sep);
					}
					sb.append(str);
				}
			}
		}
		return sb;
	}
}
