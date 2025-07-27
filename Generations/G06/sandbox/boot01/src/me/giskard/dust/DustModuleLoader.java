package me.giskard.dust;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings("rawtypes")
public class DustModuleLoader implements DustUtilsConsts {
	
	protected static ClassLoader getModuleClassLoader(File modRoot, String modName) throws Exception {
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
	
	protected static Class getMachineLoaderClass(String machineJar) throws ClassNotFoundException, Exception {		
		String strMachineModule = DustUtils.getPostfix(machineJar, File.separator);
		strMachineModule = DustUtils.cutPostfix(strMachineModule, DUST_EXT_JAR);

		String[] mm = strMachineModule.split(DUST_SEP);
		String module = mm[1];
		String modClassName = Dust.class.getPackage().getName() + "." + module + "." + DustUtils.toUpperFirst(mm[0])
				+ DustUtils.toUpperFirst(module) + "Module";

		Class cModule = null;

		try {
			cModule = Dust.class.getClassLoader().loadClass(modClassName);
		} catch (ClassNotFoundException cnf) {
			if ( machineJar.startsWith("{") ) {
				int refEnd = machineJar.indexOf("}");
				
				String key = machineJar.substring(1, refEnd);
				String val = Dust.envData.getOrDefault(key, "");
				machineJar = val + machineJar.substring(refEnd + 1);
			}

			String modPath = machineJar.contains(File.separator) ? DustUtils.cutPostfix(machineJar, File.separator) : null;
			File fMods = new File(modPath);

			cModule = getModuleClassLoader(fMods, strMachineModule).loadClass(modClassName);
		}
		
		return cModule;
	}
}
