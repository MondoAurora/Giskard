package me.giskard;

import java.io.File;
import java.net.URL;

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

	public static final MiNDAgent LOGGER = new MiNDAgent() {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			Giskard.log(MiNDEventLevel.INFO, action, sbAppend(null, ",", true, params));
			return MiNDResultType.ACCEPT_READ;
		}
	};
	
	public static String getRoot() {
		String root = System.getenv("GISKARD");
		return isEmpty(root) ? System.getenv("GISKARD_ECLIPSE") : root;
	}
	
	public static File getBrainFolder(String segment) {
		String root = GiskardUtils.getRoot();

		if ( !GiskardUtils.isEmpty(root) ) {
			return new File(root + GISKARD_PATH_BRAIN + segment);
		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}
	}
	
	public static URL optGetFileUrl(File root, String name) {
		File f = GiskardUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return GiskardException.wrap(t, "URL creation", f.getAbsolutePath());
		}
	}
	
	
	public static boolean isAccessCreator(MiNDAccessCommand cmd) {
		return (cmd == MiNDAccessCommand.Set) || (cmd == MiNDAccessCommand.Add);
	}

	public static boolean isAgentRead(MiNDResultType res ) {
		return (res == MiNDResultType.READ) || (res == MiNDResultType.ACCEPT_READ);
	}
}