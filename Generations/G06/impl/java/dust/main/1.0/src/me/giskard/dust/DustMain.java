package me.giskard.dust;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import me.giskard.dust.utils.DustUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustMain implements DustMainConsts {
	
	private static DustMain MAIN;

	
	public static int getArgCount() {
		return MAIN.argList.size();
	}

	public static String getArg(int idx) {
		return MAIN.argList.get(idx);
	}

	public static String getEnvValue(String key, String defVal) throws Exception {
		return MAIN.envData.getOrDefault(key, defVal);
	}

	public static ClassLoader getModuleCL(File modRoot, String modName) throws Exception {
		return MAIN.getModuleClassLoader(modRoot, modName);
	}
	
	

	public static void main(String[] args) {
		try {
			if ((null == args) || (0 == args.length)) {
				args = new String[] { "{GISKARD06_MODULES}/dust_sandbox_1.0.jar" };
				Dust.broadcast(null, "No module list given, starting with the default", args);
			}
			
			runMain(new DustMain(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void runMain(DustMain main, String... args) throws Exception {
		MAIN = main;
		
		MAIN.loadEnv(args);
		
		Class cModule = MAIN.getMachineLoaderClass(args[0]);

		Method mMachineInit = cModule.getMethod(FN_MODULE_INIT);
		MindDialog machine = (MindDialog) mMachineInit.invoke(null);
		Dust.setMachine(machine);

		machine.agentInit();
	}

	
	

	private final Map<String, String> envData = new TreeMap<>();
	private final ArrayList<String> argList = new ArrayList<>();

	protected void loadEnv(String[] args) {
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
	}
	
	protected ClassLoader getModuleClassLoader(File modRoot, String modName) throws Exception {
		ClassLoader cl = Dust.class.getClassLoader();

		String modFileName = modName.replace(DUST_SEP_ID, DUST_SEP);

		File f = new File(modRoot, modFileName + DUST_EXT_JAR);

		ArrayList<URL> urls = new ArrayList<>();

		urls.add(f.toURI().toURL());

		File dir = new File(modRoot, modFileName);
		if (dir.isDirectory()) {
			for (File fLib : dir.listFiles()) {
				if (fLib.isFile() && fLib.getName().endsWith(DUST_EXT_JAR)) {
					urls.add(fLib.toURI().toURL());
				}
			}
		}

		URL[] uu = new URL[urls.size()];
		uu = urls.toArray(uu);

		Dust.broadcast(null, "Creating classloader for module", modName, "jars", urls);

		return new URLClassLoader(uu, cl);
	}
	
	protected Class getMachineLoaderClass(String machineJar) throws ClassNotFoundException, Exception {		
		String strMachineModule = DustUtils.getPostfix(machineJar, File.separator);
		strMachineModule = DustUtils.cutPostfix(strMachineModule, DUST_EXT_JAR);

		String[] mm = strMachineModule.split(DUST_SEP);
		String module = mm[1];
		String modClassName = DustMain.class.getPackage().getName() + "." + module + "." + DustUtils.toUpperFirst(mm[0])
				+ DustUtils.toUpperFirst(module) + "Module";

		Class cModule = null;

		try {
			cModule = Dust.class.getClassLoader().loadClass(modClassName);
		} catch (ClassNotFoundException cnf) {
			if ( machineJar.startsWith("{") ) {
				int refEnd = machineJar.indexOf("}");
				
				String key = machineJar.substring(1, refEnd);
				String val = envData.getOrDefault(key, "");
				machineJar = val + machineJar.substring(refEnd + 1);
			}

			String modPath = machineJar.contains(File.separator) ? DustUtils.cutPostfix(machineJar, File.separator) : null;
			File fMods = new File(modPath);

			cModule = getModuleClassLoader(fMods, strMachineModule).loadClass(modClassName);
		}
		
		return cModule;
	}

}
