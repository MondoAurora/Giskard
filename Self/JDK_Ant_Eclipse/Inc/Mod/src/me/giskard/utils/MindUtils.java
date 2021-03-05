package me.giskard.utils;

import me.giskard.Giskard;

public class MindUtils extends MindUtilsJava {

	public static final MiNDAgent LOGGER = new MiNDAgent() {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			Giskard.log(MiNDEventLevel.INFO, action,  MindUtils.sbAppend(null, ",", true, params));
			return MiNDResultType.ACCEPT_READ;
		}
	};
	
	public static String getRoot() {
		String root = System.getenv("GISKARD");
		return isEmpty(root) ? System.getenv("GISKARD_ECLIPSE") : root;
	}
	
	public static boolean isAccessCreator(MiNDAccessCommand cmd) {
		return (cmd == MiNDAccessCommand.Set) || (cmd == MiNDAccessCommand.Add);
	}

	public static <FakeRet> FakeRet wrapException(Throwable src, Object... params) {
		if ( src instanceof MiNDException ) {
			throw (MiNDException) src;
		}
	
		Giskard.log(MiNDEventLevel.CRITICAL, src, params);
		throw new MiNDException(src);
	}
	
	public static void swallowException(Throwable src, Object... params) {
		Giskard.log(MiNDEventLevel.WARNING, src, params);
	}
	
	public static boolean isAgentRead(MiNDResultType res ) {
		return (res == MiNDResultType.READ) || (res == MiNDResultType.ACCEPT_READ);
	}
}
