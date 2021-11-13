package ai.montru;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;
import ai.montru.utils.MontruUtils;

public class MontruMain extends Giskard implements GiskardConsts {

	private static class BootException extends GiskardException {
		private static final long serialVersionUID = 1L;
		Date time;
		String info;

		public BootException(Throwable cause, Object... params) {
			super(cause);
			this.time = new Date();
			StringBuilder sb = MontruUtils.sbAppend(null, ", ", params);
			info = MontruUtils.toString(sb);
		}

		@Override
		public String toString() {
			return MontruUtils.sbAppend(null, " ", "Boot exception at", time, "params [", info, "]: ", super.toString())
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
			return MontruUtils.sbAppend(null, " ", "Boot event", eventType, "at", time, "params [",
					MontruUtils.sbAppend(null, ", ", params), "]").toString();
		}
	}

	protected static String[] ARGS;
	protected static ArrayList<BootEvent> BOOT_EVENTS = new ArrayList<>();

	public void setRuntime(Giskard runtime) {
		RUNTIME = runtime;
	}

	public static void main(String[] args) {
		Throwable launchException = null;

		try {
			ARGS = args;
			RUNTIME = new MontruMain();
			InputStream is = ClassLoader.getSystemResourceAsStream("GiskardBootModules.cfg");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] mod = line.split("\\s+");
				((MontruMain) RUNTIME).loadModule(mod[0], mod[1]);
			}

		} catch (Throwable e) {
			launchException = e;
		}

		for (Object e : BOOT_EVENTS.toArray() ) {
			broadcastEvent(null, e);
		}
		
		BOOT_EVENTS.clear();

		if ( null != launchException ) {
			launchException.printStackTrace();
		} else {
			broadcastEvent(null, "Giskard main() complete.");
		}
	}
	
	@Override
	public GiskardEntityRef gisGetToken(GiskardEntityRef unit, Object tokenId) {
		return wrapException(null, null, "Should not be here");
	}

	@Override
	protected <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, GiskardEntityRef localRef, Object... path) {
		return wrapException(null, null, "Should not be here");
	}

	@Override
	protected <RetType> RetType gisWrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		if ( exception instanceof GiskardException ) {
			throw (GiskardException) exception;
		} else {
			throw new BootException(exception, params);
		}
	}

	@Override
	protected void gisBroadcastEvent(GiskardEntityRef eventType, Object... params) {
		BOOT_EVENTS.add(new BootEvent(eventType, params));
	}

	@Override
	protected String gisToString(GiskardEntityRef ref) {
		return MontruUtils.toString(MontruUtils.sbAppend(null, "", "[", toString(ref.gisGetUnit()), "]:", ref.gisGetID()));
	}

	@SuppressWarnings("unchecked")
	protected <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {
		ModAgent ret = null;

		ClassLoader cl = getCL(moduleName, moduleVersion);

		Class<ModAgent> ml = (Class<ModAgent>) cl
				.loadClass(MontruMain.class.getPackage().getName() + ".modules." + moduleName);
		Giskard.broadcastEvent(null, "Initializing module", moduleName, moduleVersion, "with loader class", ml.getName(), "...");

		if ( null != ml ) {
			ret = ml.newInstance();
			ret.gisInitModule(RUNTIME, moduleName, moduleVersion);
			Giskard.broadcastEvent(null, "SUCCESS initialization of module", moduleName);
		}

		return ret;
	}

	protected ClassLoader getCL(String moduleName, String moduleVersion) {
		return ClassLoader.getSystemClassLoader();
	}

}
