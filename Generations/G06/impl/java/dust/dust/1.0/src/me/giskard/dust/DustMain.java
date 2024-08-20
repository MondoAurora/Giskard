package me.giskard.dust;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import me.giskard.dust.utils.DustUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustMain implements DustMainConsts {

	public static void main(String[] args) {
		try {

			if ((null == args) || (0 == args.length)) {
				args = new String[] { "dust:sandbox:1.0" };
				Dust.log(null, "No module list given, starting with the default", args);
			}

			String strMachineModule = DustUtils.getPostfix(args[0], File.separator);
			if (strMachineModule.endsWith(DUST_EXT_JAR)) {
				strMachineModule = strMachineModule.replace(DUST_SEP, DUST_SEP_ID);
				strMachineModule = DustUtils.cutPostfix(strMachineModule, DUST_EXT_JAR);
			}

			String[] mm = strMachineModule.split(DUST_SEP_ID);
			String module = mm[1];
			String modClassName = DustMain.class.getPackage().getName() + "." + module + "." + DustUtils.toUpperFirst(mm[0])
					+ DustUtils.toUpperFirst(module) + "Module";

			ClassLoader cl = Dust.class.getClassLoader();
			Class cModule = null;

			try {
				cModule = cl.loadClass(modClassName);
			} catch (ClassNotFoundException cnf) {
				File fMods;
				String modPath = args[0].contains(File.separator) ? DustUtils.cutPostfix(args[0], File.separator) : null;

				if (DustUtils.isEmpty(modPath)) {
					String home = System.getProperty("user.home");
					String root = System.getProperty(LP_GISKARD_ROOT, "");

					File fRoot = new File(home, root);
					fMods = new File(fRoot, "store/local/modules");
				} else {
					fMods = new File(modPath);
				}

				cl = getModuleClassLoader(fMods, strMachineModule);

				cModule = cl.loadClass(modClassName);
			}
			
			ArrayList<String> alArgs = new ArrayList<String>();
			for ( String a : args ) {
				alArgs.add(a);
			}

			Method mMachineInit = cModule.getMethod(FN_MACHINE_INIT, ArrayList.class);
			MindMachine machine = (MindMachine) mMachineInit.invoke(null, alArgs);
			Dust.setMachine(machine);
			
			machine.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ClassLoader getModuleClassLoader(File modRoot, String modName) throws Exception {
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
		
		Dust.log(null, "Creating classloader for module", modName, "jars", urls);

		return new URLClassLoader(uu, cl);
	}

}
