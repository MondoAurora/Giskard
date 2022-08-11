package me.giskard;

public abstract class Giskard implements GiskardConsts {

	protected static Giskard GISKARD;

	public static void log(MiNDEntity e, Object... obs) {
		if ( null != GISKARD ) {
			GISKARD.access_(MiNDAccessCommand.Broadcast, e, obs);
		} else {
			GiskardUtils.dump(" ", false, "Giskard.log", e, obs);
		}
	}

	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		if ( null == GISKARD ) {
			GiskardUtils.dump(" ", false, "Giskard.access", cmd, val, valPath);
			return null;
		}
		return GISKARD.access_(cmd, val, valPath);
	}
	
	protected Giskard () {
		GISKARD = this;
	}

	protected abstract <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath);

}
