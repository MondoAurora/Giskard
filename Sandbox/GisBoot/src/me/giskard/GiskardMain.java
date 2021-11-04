package me.giskard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GiskardMain extends Giskard implements GiskardConsts {

	private static class BootException extends GiskardException {
		private static final long serialVersionUID = 1L;
		Date time;
		String info;

		public BootException(Throwable cause, Object... params) {
			super(cause);
			this.time = new Date();
			StringBuilder sb = GiskardUtils.sbAppend(null, ", ", params);
			info = GiskardUtils.toString(sb);
		}

		@Override
		public String toString() {
			return GiskardUtils.sbAppend(null, " ", "Boot exception at", time, "params [", info, "]: ", super.toString())
					.toString();
		}
	}

	protected static class BootEvent {
		public final Date time;
		public final GiskardEntityRef eventType;
		public final Object[] params;

		public BootEvent(GiskardEntityRef eventType, Object... params) {
			this.time = new Date();
			this.eventType = eventType;
			this.params = Arrays.copyOf(params, params.length);
		}

		@Override
		public String toString() {
			return GiskardUtils.sbAppend(null, " ", "Boot event", eventType, "at", time, "params [",
					GiskardUtils.sbAppend(null, ", ", params), "]").toString();
		}
	}

//	private static GiskardMain BOOT;
	protected static String[] ARGS;
	protected static ArrayList<BootEvent> BOOT_EVENTS = new ArrayList<>();

//	protected static GiskardCloud BOOT_CLOUD;
//
//	public static void setBootCloud(GiskardCloud cloud) {
////		if ( null != BOOT_CLOUD ) {
////			throw new IllegalStateException("setBootCloud is already called.");
////		}
//		BOOT_CLOUD = cloud;
//	}

	public void setRuntime(Giskard runtime) {
//		if ( BOOT != RUNTIME ) {
//			throw new IllegalStateException("Giskard.MAIN had already been initialized.");
//		}
		RUNTIME = runtime;
	}

	public static void main(String[] args) {
		Throwable launchException = null;

		try {
			ARGS = args;
			RUNTIME = new GiskardMain();
			InputStream is = ClassLoader.getSystemResourceAsStream("GiskardBootModules.cfg");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] mod = line.split("\\s+");
				((GiskardMain) RUNTIME).loadModule(mod[0], mod[1]);
			}

		} catch (Throwable e) {
			launchException = e;
		}

		while (!BOOT_EVENTS.isEmpty()) {
			broadcastEvent(null, BOOT_EVENTS.remove(0));
		}

		if ( null != launchException ) {
			launchException.printStackTrace();
		} else {
			broadcastEvent(null, "Giskard main() complete.");
		}
	}

	@Override
	protected <RetType> RetType accessData_(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
		return wrapException(null, null, "Should not be here");
//		return BOOT_CLOUD.accessData(cmd, val, ctx, path);
	}

	@Override
	protected <RetType> RetType wrapException_(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		if ( exception instanceof GiskardException ) {
			throw (GiskardException) exception;
		} else {
			throw new BootException(exception, params);
		}
	}

	@Override
	protected void broadcastEvent_(GiskardEntityRef eventType, Object... params) {
		BOOT_EVENTS.add(new BootEvent(eventType, params));
	}

	@Override
	protected String toString_(GiskardEntityRef ref) {
		return GiskardUtils.toString(GiskardUtils.sbAppend(null, "", "[", toString(ref.getUnit()), "]:", ref.getID()));
	}

	@SuppressWarnings("unchecked")
	protected <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {
		ModAgent ret = null;

		ClassLoader cl = getCL(moduleName, moduleVersion);

		Class<ModAgent> ml = (Class<ModAgent>) cl
				.loadClass(GiskardConsts.class.getPackage().getName() + ".modules." + moduleName);
		Giskard.broadcastEvent(null, "Initializing module", moduleName, moduleVersion, "with loader class", ml.getName(), "...");

		if ( null != ml ) {
			ret = ml.newInstance();
			ret.initModule(RUNTIME);
			Giskard.broadcastEvent(null, "SUCCESS initialization of module", moduleName);
		}

		return ret;
	}

	protected ClassLoader getCL(String moduleName, String moduleVersion) {
		return ClassLoader.getSystemClassLoader();
	}

}
