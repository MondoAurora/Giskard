package ai.montru.giskard;

public abstract class Giskard implements GiskardConsts, GiskardConsts.GiskardMeta {
	
	public static GiskardEntityRef getToken(Object tokenId, GiskardEntityRef unit) {
		return RUNTIME.gisGetToken(unit, tokenId);
	}
	
	public static <RetType> RetType access(GiskardAccess cmd, Object val, Object... path) {
		return RUNTIME.gisAccessData(cmd, val, path);
	}

	public static <RetType> RetType wrapException(Throwable exception, GiskardEntityRef exType, Object... params) {
		return RUNTIME.gisWrapException(exception, exType, params);
	}

	public static void broadcastEvent(GiskardEntityRef eventType, Object... params) {
		RUNTIME.gisBroadcastEvent(eventType, params);
	}

	public static String toString(GiskardEntityRef ref) {
		return RUNTIME.gisToString(ref);
	}
	
	public static void registerNative(GiskardEntityRef type, String className){
		RUNTIME.gisRegisterNative(type, className);
	}
	
	protected abstract <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, Object... path);
	protected abstract <RetType> RetType gisWrapException(Throwable exception, GiskardEntityRef exType, Object... params);
	protected abstract void gisBroadcastEvent(GiskardEntityRef eventType, Object... params);
	protected abstract String gisToString(GiskardEntityRef ref);
	
	protected abstract void gisRegisterNative(GiskardEntityRef type, String className);

	protected static Giskard RUNTIME;

}
