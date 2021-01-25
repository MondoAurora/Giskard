package me.giskard.java;

public abstract class Mind implements MindConsts {

	protected static Mind THE_MIND;
	protected static MiNDEntity LOGLEVEL_EXCEPTION;

	// should handle binary modules here, including Dust

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

	public static void log(MiNDEntity lvl, Object... obs) {
		THE_MIND.log_(lvl, obs);
	}

	public static MiNDEntity getEntity(Object unitName, Object entityId) {
		return THE_MIND.getEntity_(unitName, entityId);
	}

	public static void select(MiNDEntity target, Object... path) {
		THE_MIND.select_(target, path);
	}

	public static <RetType> RetType access(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val,
			Object key) {
		return THE_MIND.access_(cmd, target, tMember, val, key);
	}

	public static <FakeRet> FakeRet wrapException(Throwable src, Object... params) {
		if ( src instanceof MiNDException ) {
			throw (MiNDException) src;
		}

		log(LOGLEVEL_EXCEPTION, src, params);
		throw new MiNDException(src);
	}

	protected abstract void log_(MiNDEntity lvl, Object... obs);

	protected abstract MiNDEntity getEntity_(Object unitName, Object entityId);

	protected abstract void select_(MiNDEntity target, Object... path);

	protected abstract <RetType> RetType access_(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val,
			Object key);

	protected abstract void init(String[] args);

}
