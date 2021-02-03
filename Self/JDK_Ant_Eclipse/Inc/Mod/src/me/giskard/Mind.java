package me.giskard;

public abstract class Mind implements MindConsts {

	protected static Mind THE_MIND;

	// should handle binary modules here, including Dust

	public static void setMind(Mind mind) {
		if ( (null != THE_MIND) && (THE_MIND != mind) ) {
			wrapException(null, "Multiple initialization!");
		}

		THE_MIND = mind;
	}

	public static void log(MiNDEventLevel lvl, Object... obs) {
		if ( null != THE_MIND ) {
			THE_MIND.log_(lvl, obs);
		} else {
			StringBuilder sb = MindUtils.sbAppend(null, " ", false, obs);
			if ( null != sb ) {
				System.out.println(lvl + " " + sb);				
			}
		}
	}

	public static MiNDEntity getEntity(Object unitName, Object entityId) {
		return THE_MIND.getEntity_(unitName, entityId);
	}

	public static void select(MiNDEntity target, Object... path) {
		THE_MIND.getContext().select(target, path);
	}

	public static <RetType> RetType access(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val,
			Object key) {
		return THE_MIND.getContext().access(cmd, target, tMember, val, key);
	}

	public static <FakeRet> FakeRet wrapException(Throwable src, Object... params) {
		if ( src instanceof MiNDException ) {
			throw (MiNDException) src;
		}

		log(MiNDEventLevel.CRITICAL, src, params);
		throw new MiNDException(src);
	}

	protected abstract void log_(MiNDEventLevel lvl, Object... obs);

	protected abstract MiNDEntity getEntity_(Object unitName, Object entityId);

	protected abstract MiNDContext getContext();

	protected abstract void init(String[] args);

}
