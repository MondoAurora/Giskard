package ai.montru.giskard;

public abstract class Giskard implements GiskardConsts, GiskardConsts.GiskardMeta {
	
	public static GiskardEntityRef getToken(Object tokenId, GiskardEntityRef unit) {
		return RUNTIME.gisGetToken(unit, tokenId);
	}
	
	public static <RetType> RetType access(GiskardAccess cmd, Object val, GiskardEntityRef localRef, Object... path) {
		return RUNTIME.gisAccessData(cmd, val, localRef, path);
	}

	public static <RetType> RetType wrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		return RUNTIME.gisWrapException(exception, exType, params);
	}

	public static void registerNative(GiskardEntityRef type, String className){
		RUNTIME.gisRegisterNative(type, className);
	}

	public static void initiateProcess(GiskardEntityRef proc){
		RUNTIME.gisInitiateProcess(proc);
	}

	public static void broadcastEvent(GiskardEntityRef eventType, Object... params) {
		RUNTIME.gisBroadcastEvent(eventType, params);
	}

	public static String toString(GiskardEntityRef ref) {
		return RUNTIME.gisToString(ref);
	}
	
	protected abstract <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, GiskardEntityRef localRef, Object... path);
	protected abstract <RetType> RetType gisWrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException;
	protected abstract void gisRegisterNative(GiskardEntityRef type, String className);
	protected abstract void gisInitiateProcess(GiskardEntityRef proc);
	protected abstract void gisBroadcastEvent(GiskardEntityRef eventType, Object... params);
	
	protected abstract String gisToString(GiskardEntityRef ref);

	protected static Giskard RUNTIME;

}
