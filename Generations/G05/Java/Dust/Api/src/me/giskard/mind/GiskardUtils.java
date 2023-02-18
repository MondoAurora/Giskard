package me.giskard.mind;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

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
	
	public static void dump(Object sep, boolean strict, Object... objects) {
		StringBuilder sb = sbAppend(null, sep, strict, objects);
		
		if ( null != sb ) {
			System.out.println(sb);
		}
	}


	public static ClassLoader getClassLoader(String brainModuleName, String modulePath) throws Exception {
		ArrayList<URL> urls = new ArrayList<>();
	
		File f = new File(modulePath, brainModuleName + EXT_JAR);
		urls.add(f.toURI().toURL());
	
		File dir = new File(modulePath, brainModuleName);
		if ( dir.isDirectory() ) {
			for (File fLib : dir.listFiles()) {
				if ( fLib.isFile() && fLib.getName().endsWith(EXT_JAR) ) {
					urls.add(fLib.toURI().toURL());
				}
			}
		}
	
		URL[] uu = new URL[urls.size()];
		uu = urls.toArray(uu);
	
		return new URLClassLoader(uu, ClassLoader.getSystemClassLoader());
	}

}
