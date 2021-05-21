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
			return (ob instanceof MiNDNamed) ? ((MiNDNamed)ob).getMiNDName() : ob.toString();
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

	public static final MiNDAgent LOGGER = new MiNDAgentBlock() {
		
		@Override
		public MiNDResultType mindAgentEnd() throws Exception {
			Giskard.log(MiNDEventLevel.Info, sbAppend(null, ",", true, "End"));
			return null;
		}
		
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			Giskard.log(MiNDEventLevel.Info, sbAppend(null, ",", true, "Process"));
			return MiNDResultType.AcceptRead;
		}

		@Override
		public MiNDResultType mindAgentBegin() throws Exception {
			Giskard.log(MiNDEventLevel.Info, sbAppend(null, ",", true, "Begin"));
			return null;
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
