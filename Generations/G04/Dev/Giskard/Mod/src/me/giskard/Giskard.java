package me.giskard;

public abstract class Giskard implements GiskardConsts {

	protected static Giskard GISKARD;
	protected static String[] ARGS;

	public static void log(MiNDEntity e, Object... obs) {
		if ( null != GISKARD ) {
			GISKARD.access_(MiNDAccessCommand.Broadcast, e, obs);
		} else {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", false, obs);
			if ( null != sb ) {
				System.out.println(e + " " + sb);
			}
		}
	}

	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		if ( null == GISKARD ) {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", false, valPath);
			if ( null != sb ) {
				System.out.println(cmd + " " + sb);
			}
			return null;
		}
		return GISKARD.access_(cmd, val, valPath);
	}

	protected abstract <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath);

}
