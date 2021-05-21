package me.giskard;

import java.lang.reflect.Method;
import java.util.Arrays;

import me.giskard.tools.GisToolsModuleServices;

public abstract class Giskard implements GiskardConsts {

	protected static Giskard GISKARD;
	protected static String[] ARGS;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		ARGS = Arrays.copyOf(args, args.length);

		Class clApp = Class.forName(GISKARD_CLASS_APP);
		Method boot = clApp.getMethod("boot", String[].class);
		boot.invoke(null, new Object[] { args });

		log(MiNDEventLevel.Trace, "Success. Or at least, no exception, exiting Giskard.main() :-)");
	}

	public static void log(MiNDEventLevel lvl, Object... obs) {
		if ( null != GISKARD ) {
			if ( 0 < obs.length ) {
				GISKARD.log_(lvl, obs);
			} else {
				GISKARD.log_(lvl, GISKARD);
			}
		} else {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", false, obs);
			if ( null != sb ) {
				System.out.println(lvl + " " + sb);
			}
		}
	}

	public static void initRuntime(String mod, String ver) throws Exception {
		ClassLoader cl = GisToolsModuleServices.getClassLoader(mod, ver);
		MiNDAgent rt = GisToolsModuleServices.initModule(cl, mod);
		((MiNDAgentResource)rt).mindAgentInit();
	}

	public static MiNDToken defineToken(MiNDTokenType type, Object id, Object... params) {
		return GISKARD.defineToken_(type, id, params);
	}

	public static <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		return GISKARD.access_(cmd, val, valPath);
	}

	protected static void setImplementation(Giskard g) {
		if ( (null != GISKARD) && (GISKARD != g) ) {
			GiskardException.wrap(null, "Multiple initialization!");
		}
		GISKARD = g;
	}

	protected abstract void log_(MiNDEventLevel lvl, Object... obs);

	protected abstract <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath);

	protected abstract MiNDToken defineToken_(MiNDTokenType type, Object id, Object... params);

}
