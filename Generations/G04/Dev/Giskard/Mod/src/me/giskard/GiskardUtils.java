package me.giskard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GiskardUtils implements GiskardConsts {
	
	public static boolean isEqual(Object o1, Object o2) {
		return (null == o1) ? (null == o2) : (null != o2) && o1.equals(o2);
	}

	public static String toString(Object ob) {
		return toString(ob, ", ");
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
	
	public static boolean isEmpty(String str) {
		return (null == str) || str.isEmpty();
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

	
	public static String strTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date());
	}

	public static String strDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_DATE);
		return sdf.format(new Date());
	}

	public static String strTimestamp(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date(time));
	}
	
	public static boolean isAccessCreator(MiNDAccessCommand cmd) {
		return (cmd == MiNDAccessCommand.Set) || (cmd == MiNDAccessCommand.Add);
	}

	public static boolean isAgentReject(MiNDResultType res ) {
		return (res == MiNDResultType.Reject) || (res == MiNDResultType.Notimplemented);
	}

	public static boolean isAgentRead(MiNDResultType res ) {
		return (res == MiNDResultType.Read) || (res == MiNDResultType.AcceptRead);
	}

	public static boolean isAgentAccept(MiNDResultType res ) {
		return (res == MiNDResultType.Accept) || (res == MiNDResultType.AcceptRead);
	}

}
