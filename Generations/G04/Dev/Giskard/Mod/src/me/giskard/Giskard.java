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
	
	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		if ( null == GISKARD ) {
			GiskardUtils.dump(" ", false, "Giskard.access", cmd, val, valPath);
			return null;
		}
		return GISKARD.access_(cmd, val, valPath);
	}
	
	protected void setImpl(GiskardImpl impl) {
		GISKARD = impl;
	}

}
