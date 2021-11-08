package ai.montru.giskard;

public abstract class Giskard implements GiskardConsts {//, GiskardConsts.GiskardCloud {
	
	public static <RetType> RetType access(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
		return RUNTIME.accessData_(cmd, val, ctx, path);
	}

	public static <RetType> RetType wrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		return RUNTIME.wrapException_(exception, exType, params);
	}

	public static void broadcastEvent(GiskardEntityRef eventType, Object... params) {
		RUNTIME.broadcastEvent_(eventType, params);
	}

	public static String toString(GiskardEntityRef ref) {
		return RUNTIME.toString_(ref);
	}
	
	protected abstract <RetType> RetType accessData_(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path);
	protected abstract <RetType> RetType wrapException_(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException;
	protected abstract void broadcastEvent_(GiskardEntityRef eventType, Object... params);
	protected abstract String toString_(GiskardEntityRef ref);

	protected static Giskard RUNTIME;

}
