package me.giskard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GiskardUtils implements GiskardConsts {
	public static boolean isEmpty(String str) {
		return (null == str) ? true : (0 == str.trim().length());
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

	public static File optBackup(File ret) throws IOException {
		return optBackup(ret, System.currentTimeMillis());
	}

	public static File optBackup(File ret, Object ts) throws IOException {
		if ( ret.isFile() ) {
			String backupName = ret.getName();
			int dot = backupName.lastIndexOf(".");

			backupName = (-1 == dot) ? backupName + "." + ts : backupName.substring(0, dot) + "." + ts + backupName.substring(dot);
			Files.move(ret.toPath(), new File(ret.getParentFile(), backupName).toPath());
		}

		return ret;
	}
}
