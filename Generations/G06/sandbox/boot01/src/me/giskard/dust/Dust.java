package me.giskard.dust;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import me.giskard.dust.utils.DustUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Dust implements DustConsts {

	private static MindDialog MACHINE;

	static final Map<String, String> envData = new TreeMap<>();
	static final ArrayList<String> argList = new ArrayList<>();

	public static <RetType> RetType access(MindHandle cmd, Object val, Object... path) {
		return MACHINE.access(cmd, val, path);
	}

	public static MindHandle lookup(MindHandle unit, String id) {
		return MACHINE.lookup(unit, id, null, null);
	}

	public static MindHandle lookup(MindHandle unit, String id, String lang, String token) {
		return MACHINE.lookup(unit, id, lang, token);
	}

	public static void broadcast(MindHandle event, Object... params) {
		if (null == MACHINE) {
			log(event, params);
		} else {
			MACHINE.broadcast(event, params);
		}
	}

	public static void log(MindHandle event, Object... params) {
		StringBuilder sb = DustUtils.sbAppend(null, ", ", false, DustUtils.strTime(), event);
		DustUtils.sbAppend(sb, ", ", false, params);
		System.out.println(sb);
	}

	public static void main(String[] args) {
		try {
			if ((null == args) || (0 == args.length)) {
				args = new String[] { "{GISKARD06_MODULES}/dust_machine_1.0.jar", "giskard.me/test01", "giskard.me/dust:0" };
				Dust.broadcast(null, "No module list given, starting with the default", args);
			}

			envData.putAll(System.getenv());

			Properties props = System.getProperties();
			for (Object k : props.keySet()) {
				String pk = DustUtils.toString(k);
				envData.put(pk, props.getProperty(pk));
			}

			for (String a : args) {
				if (a.startsWith("-")) {
					String name = a.substring(1);
					String val = null;

					int sep = name.indexOf("=");
					if (-1 != sep) {
						val = name.substring(sep + 1);
						name = name.substring(0, sep);
					}

					envData.put(name, val);
				} else {
					argList.add(a);
				}
			}

			Class cModule = DustModuleLoader.getMachineLoaderClass(args[0]);

			Method mMachineInit = cModule.getMethod(FN_MODULE_INIT);
			MACHINE = (MindDialog) mMachineInit.invoke(null);

			MACHINE.logicProcess(null);

			if (argList.size() > 1) {
				Dust.lookup(null, argList.get(1));
			}
			if (argList.size() > 2) {
				Dust.lookup(null, argList.get(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
