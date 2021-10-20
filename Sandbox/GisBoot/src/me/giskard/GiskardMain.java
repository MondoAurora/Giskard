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

	protected <RetType> RetType access_(GiskardAccessCmd cmd, Object val, Object... path) {
		return null;
	}

	protected <RetType> RetType wrapException_(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		if ( exception instanceof GiskardException ) {
			throw (GiskardException) exception;
		} else {
			throw new BootException(exception, params);
		}
	}

	protected void broadcastEvent_(GiskardEntityRef eventType, Object... params) {
		BOOT_EVENTS.add(new BootEvent(eventType, params));
	}

	protected String toString_(GiskardEntityRef ref) {
		return GiskardUtils.toString(GiskardUtils.sbAppend(null, "", "[", toString(ref.getUnit()), "]:", ref.getID()));
	}

	private static GiskardMain BOOT;
	protected static String[] ARGS;
	protected static ArrayList<BootEvent> BOOT_EVENTS = new ArrayList<>();

	public static void main(String[] args) {
		Throwable launchException = null;

		try {
			ARGS = args;
			RUNTIME = BOOT = new GiskardMain();
			InputStream is = ClassLoader.getSystemResourceAsStream("GiskardBootModules.cfg");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] mod = line.split("\\s+");
				loadModule(mod[0], mod[1]);
			}

		} catch (Throwable e) {
			launchException = e;
		}

		for (BootEvent be : BOOT_EVENTS) {
			System.out.println(be);
		}

		if ( null != launchException ) {
			launchException.printStackTrace();
		}
	}

	protected static void setRuntime(GiskardMain runtime) {
		if ( BOOT != RUNTIME ) {
			throw new IllegalStateException("Giskard.MAIN had already been initialized.");
		}
		RUNTIME = runtime;
	}

	@SuppressWarnings("unchecked")
	protected static <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {
		ModAgent ret = null;

		ClassLoader cl = getCL(moduleName, moduleVersion);

		Class<ModAgent> ml = (Class<ModAgent>) cl
				.loadClass(GiskardConsts.class.getPackage().getName() + ".modules." + moduleName);
		if ( null != ml ) {
			ret = ml.newInstance();
			
			ret.initModule(RUNTIME);

//			Method mm = ml.getDeclaredMethod("initModule", GiskardMain.class);
//			if ( null != mm ) {
//				mm.invoke(ret, RUNTIME);
//			}
		}

		return ret;
	}

	protected static ClassLoader getCL(String moduleName, String moduleVersion) {
		return ClassLoader.getSystemClassLoader();
	}

}
