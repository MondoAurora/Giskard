package me.giskard;

import me.giskard.utils.MindUtils;

public abstract class Mind implements MindConsts {

	protected static Mind THE_MIND;

	public static Mind getMind() {
		return THE_MIND;
	}

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
	
	public static MiNDToken defineToken(MiNDTokenType type, String name, Object... params)  {
		return THE_MIND.getContext().defineToken(type, name, params);
	}
	
	public static void selectById(MiNDToken target, String id)  {
		THE_MIND.getContext().selectById(target, id);
	}

	public static void selectByPath(MiNDToken target, Object... path) {
		THE_MIND.getContext().selectByPath(target, path);
	}

	
	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		return THE_MIND.getContext().access( cmd, val, target, valPath);
	}

	public static <FakeRet> FakeRet wrapException(Throwable src, Object... params) {
		if ( src instanceof MiNDException ) {
			throw (MiNDException) src;
		}

		log(MiNDEventLevel.CRITICAL, src, params);
		throw new MiNDException(src);
	}

	protected abstract void log_(MiNDEventLevel lvl, Object... obs);

	protected abstract MiNDContext getContext();

	public void initContext() {
	}

}
