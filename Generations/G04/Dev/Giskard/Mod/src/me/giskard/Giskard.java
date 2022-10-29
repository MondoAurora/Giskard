package me.giskard;

public abstract class Giskard implements GiskardConsts {

	protected static GiskardImpl GISKARD;

	public static void log(Object... obs) {
		broadcast(null, obs);
	}

	public static void broadcast(MiNDHandle event, Object... params) {
		if ( null != GISKARD ) {
			GISKARD.broadcast_(event, params);
		} else {
			GiskardUtils.dump(" ", false, "Giskard.log", event, params);
		}
	}

	public static MiNDHandle create(MiNDHandle type) {
		return access(MiNDAccessCommand.Get, null, null, null, type);
	}

	public static <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att,
			Object key) {
		if ( null == GISKARD ) {
			GiskardUtils.dump(" ", false, "Giskard.access", cmd, val, ref, att, key);
			return null;
		}
		return GISKARD.access_(cmd, val, ref, att, key);
	}

	public static boolean run() throws Exception {
		MiNDResultType ret;
		
		do {
			ret = GISKARD.mindAgentStep();
		} while (ret.read);

		return ret.accept;
	}

	protected void setImpl(GiskardImpl impl) {
		GISKARD = impl;
	}

}
