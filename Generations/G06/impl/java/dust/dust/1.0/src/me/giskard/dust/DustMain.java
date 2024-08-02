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
		if ( (null == args) || ( 0 == args.length) ) {
			args = new String[] {"dust:sandbox:1.0"};
			
			Dust.log(null, "No module list given, starting with the default", args);
		}
		
		for (String a : args) {
			try {
				loadModule(a);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ClassLoader loadModule(String moduleRef) throws Exception {
		String[] mm = moduleRef.split(DUST_SEP_ID);

		String module = mm[1];
		String modClass = DustMain.class.getPackage().getName() + "." + module + "." + DustUtils.toUpperFirst(mm[0])
				+ DustUtils.toUpperFirst(module) + "Module";
		ClassLoader cl = Dust.class.getClassLoader();

		Class cModule = null;

		try {
			cl.loadClass(modClass);
		} catch (ClassNotFoundException cnf) {
			// no worries
		}

		if (null == cModule) {
			String home = System.getProperty("user.home");
			String root = System.getProperty(LP_GISKARD_ROOT, "");

			File fRoot = new File(home, root);
			File fMods = new File(fRoot, "store/local/modules");

			ArrayList<URL> urls = new ArrayList<>();

			String modName = moduleRef.replace(DUST_SEP_ID, DUST_SEP);
			File f = new File(fMods, modName + DUST_EXT_JAR);

			if (f.isFile()) {
				urls.add(f.toURI().toURL());

				File dir = new File(fMods, modName);
				if (dir.isDirectory()) {
					for (File fLib : dir.listFiles()) {
						if (fLib.isFile() && fLib.getName().endsWith(DUST_EXT_JAR)) {
							urls.add(fLib.toURI().toURL());
						}
					}
				}

				URL[] uu = new URL[urls.size()];
				uu = urls.toArray(uu);

				cl = new URLClassLoader(uu, cl);

				try {
					cModule = cl.loadClass(modClass);
				} catch (ClassNotFoundException cnf) {
					// no worries
				}
			}
		}

		if (null != cModule) {
			Method m = cModule.getMethod("dustModuleInit");
			m.invoke(null);
		}

		return cl;

	}
}
