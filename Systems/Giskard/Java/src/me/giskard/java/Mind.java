package me.giskard.java;

public abstract class Mind implements MindConsts {

	protected static Mind THE_MIND;
	protected static MiNDToken LOGLEVEL_EXCEPTION;

	public static void main(String[] args) throws Exception {
		setMind((Mind) Class.forName("me.giskard.java.dust.mind.DustMind").newInstance());
		THE_MIND.init(args);
	}

	public static void setMind(Mind mind) {
		if ( (null != THE_MIND) && (THE_MIND != mind) ) {
			wrapException(null, "Multiple initialization!");
		}

		THE_MIND = mind;
	}

	public static void log(MiNDToken lvl, Object... obs) {
		THE_MIND.log_(lvl, obs);
	}

	public static MiNDToken getToken(String dslId, String termId) {
		return THE_MIND.getToken_(dslId, termId);
	}

	public static void select(MiNDToken target, MiNDToken... path) {
		THE_MIND.select_(target, path);
	}

	public static <RetType> RetType access(MiNDToken cmd, MiNDToken target, MiNDToken tMember, RetType val, Object key) {
		return THE_MIND.access_(cmd, target, tMember, val, key);
	}

	public static <FakeRet> FakeRet wrapException(Throwable src, Object... params) {
		if ( src instanceof MiNDException ) {
			throw (MiNDException) src;
		}

		log(LOGLEVEL_EXCEPTION, src, params);
		throw new MiNDException(src);
	}

	protected abstract void log_(MiNDToken lvl, Object... obs);

	protected abstract MiNDToken getToken_(String dslId, String termId);

	protected abstract void select_(MiNDToken target, MiNDToken... path);

	protected abstract <RetType> RetType access_(MiNDToken cmd, MiNDToken target, MiNDToken tMember, RetType val,
			Object key);

	protected abstract void init(String[] args);

}
