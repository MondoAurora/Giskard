package me.giskard.java;

public class Mind implements MindConsts {
	protected static MiNDLog LOG;
	protected static MiNDTokenSource TOKEN_SOURCE;
	
	protected static MiNDRuntime RUNTIME;
	protected static MiNDToken LOGLEVEL_CRITICAL;

	public static void log(MiNDToken lvl, Object... obs) {
		LOG.log(lvl, obs);
	}

	public static MiNDToken getToken(String dslId, String termId) {
		return (MiNDToken) TOKEN_SOURCE.resolve(dslId, termId);
	}

	public static void select(MiNDToken target, MiNDToken... path) {
		RUNTIME.select(target, path);
	}

	public static <RetType> RetType access(MiNDToken cmd, MiNDToken target, MiNDToken tMember, RetType val, Object key) {
		return RUNTIME.access(cmd, target, tMember, val, key);
	}

	public static final class MindException extends RuntimeException implements MindConsts {
		private static final long serialVersionUID = 1L;

		MindException(Throwable src) {
			super(src);
		}
	}
	
	public static <FakeRet> FakeRet throwException(Throwable src, Object... params) {
		if ( src instanceof MindException ) {
			throw (MindException) src;
		}

		log(LOGLEVEL_CRITICAL, params);
		throw new MindException(src);
	}

}
