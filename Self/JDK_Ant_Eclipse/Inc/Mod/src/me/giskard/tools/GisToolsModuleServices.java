package me.giskard.tools;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

public class GisToolsModuleServices implements GiskardConsts {
	private static final ClassLoader clSys = ClassLoader.getSystemClassLoader();

	private static File fMod;
	private static File fExt;
	
	public static String getModuleId(String mod, String ver) {
		return mod + "-" + ver;
	}

	public static String getModClassName(String mod) {
		return GISKARD_PREFIX_MOD + mod + "Module";
	}

	public static ClassLoader getClassLoader(String mod, String ver) {
		String modClassName = getModClassName(mod);
		String moduleId = getModuleId(mod, ver);

		try {
			clSys.loadClass(modClassName);
			
			Giskard.log(MiNDEventLevel.Trace, "Module", moduleId, "available locally.");
			return clSys;
		} catch (Throwable t) {
			// try dynamic
		}

		ArrayList<URL> urls = new ArrayList<>();

		try {
			if ( null == fMod ) {
				fMod = GiskardUtils.getBrainFolder(GISKARD_SEGMENT_MOD);
				fExt = GiskardUtils.getBrainFolder(GISKARD_SEGMENT_EXT);
			}

			urls.add(GiskardUtils.optGetFileUrl(fMod, moduleId + EXT_JAR));

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
			
			Giskard.log(MiNDEventLevel.Trace, "Module", moduleId, "avaliable externally:", urls);

			return ret;
		} catch (Throwable t) {
			Giskard.log(MiNDEventLevel.Warning, "Classloader not found for module", moduleId, "with URLs", urls);
		}

		return null;
	}
	
	public static MiNDAgent initModule(ClassLoader cl, String mod) throws Exception {
		MiNDAgent modAgent = null;

		Class<?> cMod = cl.loadClass(getModClassName(mod));
		if ( null != cMod ) {
			modAgent = (MiNDAgent) cMod.newInstance();
			modAgent.process(MiNDAgentAction.Init);
		}

		return modAgent;
	}

}