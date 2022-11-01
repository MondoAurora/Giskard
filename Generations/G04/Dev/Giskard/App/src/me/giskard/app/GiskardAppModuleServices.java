package me.giskard.app;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardException;

public class GiskardAppModuleServices implements GiskardAppConsts {
	private static final ClassLoader clSys = ClassLoader.getSystemClassLoader();

	private static File fMod;
	private static File fExt;

	public static String getModuleId(String vendor, String mod, String ver) {
		return vendor + SEP_ID + mod + SEP_ID + ver;
	}

	public static String getModClassName(String mod) {
		return GISKARD_PREFIX_MOD + mod + "Module";
	}

	public static void loadModule(String vendor, String mod, String ver) throws Exception {
		ClassLoader cl = getClassLoader(vendor, mod, ver);
		initModule(cl, mod);
	}

	public static ClassLoader getClassLoader(String vendor, String mod, String ver) {
		String modClassName = getModClassName(mod);
		String moduleId = getModuleId(vendor, mod, ver);

		try {
			clSys.loadClass(modClassName);

			Giskard.log(null, "Module", moduleId, "available locally.");
			return clSys;
		} catch (Throwable t) {
			// try dynamic
		}

		ArrayList<URL> urls = new ArrayList<>();

		try {
			if ( null == fMod ) {
				fMod = GiskardAppUtils.getLocalFolder(GISKARD_PATH_BRAIN, GISKARD_SEGMENT_MOD);
				fExt = GiskardAppUtils.getLocalFolder(GISKARD_PATH_BRAIN, GISKARD_SEGMENT_EXT);
			}

			URL urlModMain = GiskardAppUtils.optGetFileUrl(fMod, moduleId + EXT_JAR);

			if ( null == urlModMain ) {
				GiskardException.wrap(null, "No jar found for module", moduleId);
			}

			urls.add(urlModMain);

			File dir = new File(fExt, moduleId);
			if ( dir.isDirectory() ) {
				for (File fLib : dir.listFiles()) {
					if ( fLib.isFile() && fLib.getName().endsWith(EXT_JAR) ) {
						try {
							urls.add(fLib.toURI().toURL());
						} catch (Throwable e) {
							GiskardException.swallow(e, "Loading ext jar", fLib.getAbsolutePath(), "for module", moduleId);
						}
					}
				}
			}

			URL[] uu = new URL[urls.size()];
			uu = urls.toArray(uu);

			ClassLoader ret = new URLClassLoader(uu, clSys);
			ret.loadClass(modClassName);

			Giskard.log(null, "Module", moduleId, "available externally:", urls);

			return ret;
		} catch (Throwable t) {
			Giskard.log(null, "Classloader not found for module", moduleId, "with URLs", urls);
		}

		return null;
	}

	public static MiNDAgent initModule(ClassLoader cl, String mod) throws Exception {
		MiNDAgent modAgent = null;

		if ( null != cl ) {
			Class<?> cMod = cl.loadClass(getModClassName(mod));
			if ( null != cMod ) {
				modAgent = (MiNDAgent) cMod.newInstance();
				modAgent.mindAgentStep();
			}
		}
		
		return modAgent;
	}

}