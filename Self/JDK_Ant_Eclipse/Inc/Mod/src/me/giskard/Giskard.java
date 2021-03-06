package me.giskard;

public abstract class Giskard implements GiskardConsts {

	protected static Giskard GISKARD;

	public static void setImplementation(Giskard g) {
		if ( (null != GISKARD) && (GISKARD != g) ) {
			GiskardException.wrap(null, "Multiple initialization!");
		}

		GISKARD = g;
		g.initContext();
	}

	public static void log(MiNDEventLevel lvl, Object... obs) {
		if ( null != GISKARD ) {
			if ( 0 < obs.length ) {
				GISKARD.log_(lvl, obs);
			} else {
				GISKARD.log_(lvl, GISKARD);
			}
		} else {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", false, obs);
			if ( null != sb ) {
				System.out.println(lvl + " " + sb);
			}
		}
	}

	public static MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		return GISKARD.getContext().defineToken(type, name, params);
	}

	public static void selectByPath(MiNDToken target, Object... path) {
		GISKARD.getContext().selectByPath(target, path);
	}

	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		return GISKARD.getContext().access(cmd, val, target, valPath);
	}

	public static MiNDResultType invoke(Object... agentPath) {
		return GISKARD.invoke_(agentPath);
	}

	protected abstract MiNDResultType invoke_(Object... agentPath);

	protected abstract void log_(MiNDEventLevel lvl, Object... obs);

	protected abstract MiNDContext getContext();

	protected void initContext() {
	}

}
