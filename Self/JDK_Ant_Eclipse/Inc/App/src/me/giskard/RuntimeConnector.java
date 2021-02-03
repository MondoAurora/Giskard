package me.giskard;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
	private ArrayList<Module> boot = new ArrayList<>();

	class Module {
		String libMod;
		URLClassLoader modLoader;

		public Module(String libMod, String... libExt) {
			this.libMod = libMod;

			String currLib = null;

			try {
				ArrayList<URL> urls = new ArrayList<>();

				urls.add(optGetUrl(modRoot, libMod + ".jar"));
				
				for (String ln : libExt) {
					currLib = libMod + "/" + ln;
					urls.add(optGetUrl(extRoot, currLib));
				}

				URL[] uu = new URL[urls.size()];
				uu = urls.toArray(uu);
				modLoader = new URLClassLoader(uu);

				if ( null != mind ) {
					initModule();
				} else {
					boot.add(this);
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
				Method mInit = cMod.getMethod("initModule");
				mInit.invoke(null);
				extInit = ", " + mInit.toString() + " called";
			} catch (ClassNotFoundException e1) {
				// no problem
			} catch (NoSuchMethodException e2) {
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

		String root = System.getenv("GISKARD");

		if ( !MindUtils.isEmpty(root) ) {
			String gr = root + "/Brain/JRE/";
			modRoot = new File(gr + "Mod");
			extRoot = new File(gr + "Ext");
		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}
	}

	protected void initMind(String modName, String binTypeId, String[] args) throws Exception {
		Module mod = modules.get(modName);
		mind = mod.createNative(binTypeId);
		Mind.setMind(mind);

		for (Module m : boot) {
			m.initModule();
		}
		
		Mind.log(MiNDEventLevel.INFO, "GISKARD boot complete.");

		mind.init(args);
	}

	@Override
	public void addModule(String modName, String mainLib, String... extLibs) {
		modules.put(modName, new Module(mainLib, extLibs));
	}

	@Override
	public <RetType> RetType createObject(String modName, String binTypeId, Object... params) {
		return modules.get(modName).createNative(binTypeId, params);
	}

	@Override
	public void deleteObject(String modName, String binTypeId, Object object) {
	}

}
