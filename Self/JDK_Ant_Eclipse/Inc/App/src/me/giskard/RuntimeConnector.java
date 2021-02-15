package me.giskard;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.utils.MindUtils;

public class RuntimeConnector implements MindConsts, MindConsts.MiNDModuleManager {

	static URL optGetUrl(File root, String name) {
		File f = MindUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return Mind.wrapException(t, "Missing library", f.getAbsolutePath());
		}
	}

	private Mind mind;

	private File modRoot;
	private File extRoot;
	private Map<String, Module> modules = new TreeMap<>();

	class Module {
		String libMod;
		URLClassLoader modLoader;
		MiNDAgent modAgent;

		public Module(String libMod, String ver, String... libExt) {
			this.libMod = libMod;

			String currLib = null;

			try {
				ArrayList<URL> urls = new ArrayList<>();

				urls.add(optGetUrl(modRoot, libMod + "-" + ver + ".jar"));

				for (String ln : libExt) {
					currLib = libMod + "/" + ln;
					urls.add(optGetUrl(extRoot, currLib));
				}

				URL[] uu = new URL[urls.size()];
				uu = urls.toArray(uu);
				modLoader = new URLClassLoader(uu);
				
				Class<?> cMind = modLoader.loadClass(Mind.class.getCanonicalName());

				if ( null != mind ) {
					cMind.getMethod("setMind", cMind).invoke(null, mind);
				}
				
				String rootPkg = cMind.getPackage().getName();
				Class<?> cMod = modLoader.loadClass(rootPkg + "." + libMod);
				if ( null != cMod ) {
					modAgent = (MiNDAgent) cMod.newInstance();
					modAgent.process(MiNDAgentAction.INIT);
				}

				if ( null == mind ) {
					mind = (Mind) cMind.getMethod("getMind").invoke(null);
					Mind.setMind(mind);
				}
			} catch (Throwable e) {
				Mind.wrapException(e, libMod, currLib);
			}
		}

		void initModule() throws Exception {
			Class<?> c = modLoader.loadClass(Mind.class.getCanonicalName());
			Method method = c.getMethod("setMind", c);
			method.invoke(null, mind);
			String extInit = ", no custom init";

			try {
				String rootPkg = c.getPackage().getName();
				Class<?> cMod = modLoader.loadClass(rootPkg + "." + libMod);
				if ( null != cMod ) {
					modAgent = (MiNDAgent) cMod.newInstance();
					modAgent.process(MiNDAgentAction.INIT);
					extInit = ", " + cMod.toString() + " called";
				}
			} catch (ClassNotFoundException e1) {
				// no problem
			}

			Mind.log(MiNDEventLevel.TRACE, "Module", libMod, "initialized" + extInit + ".");
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType createNative(String binTypeId, Object... params) {
			try {
				Class<?> c = modLoader.loadClass(binTypeId);
				return (RetType) c.newInstance();
			} catch (Exception e) {
				return Mind.wrapException(e, binTypeId);
			}
		}
	}

	protected RuntimeConnector(String[] args) {
		Mind.log(MiNDEventLevel.TRACE, "GISKARD boot started...");

		String root = MindUtils.getRoot();

		if ( !MindUtils.isEmpty(root) ) {
			String gr = root + "/Brain/JRE/";
			modRoot = new File(gr + "Mod");
			extRoot = new File(gr + "Ext");
		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}
	}

	@Override
	public final void addModule(String modName, String mainLib, String ver, String... extLibs) {
		modules.put(modName, new Module(mainLib, ver, extLibs));
	}

	protected final void launch() throws Exception {
		Mind.log(MiNDEventLevel.INFO, "GISKARD boot success.");
		
		Module mod = modules.get(MODULE_MIND);
		mod.modAgent.process(MiNDAgentAction.BEGIN);
		
		Mind.log(MiNDEventLevel.INFO, "GISKARD finished.");
	}

	@Override
	public final <RetType> RetType createObject(String modName, String binTypeId, Object... params) {
		return modules.get(modName).createNative(binTypeId, params);
	}

	@Override
	public final void deleteObject(String modName, String binTypeId, Object object) {
	}

}
