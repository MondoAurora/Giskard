package me.giskard.java;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RuntimeConnector implements MindConsts, MindConsts.MiNDModuleManager {	
	File modRoot;
	File extRoot;
	Map<String, Module> modules = new TreeMap<>();

	static URL optGetUrl(File root, String name) {
		File f = MindUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return Mind.wrapException(t, "Missing library", f.getAbsolutePath());
		}
	}

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
					currLib = ln + ".jar";
					urls.add(optGetUrl(extRoot, currLib));
				}

				URL[] uu = new URL[urls.size()];
				uu = urls.toArray(uu);
				modLoader = new URLClassLoader(uu);
			} catch (Throwable e) {
				Mind.wrapException(e, libMod, currLib);
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType  createNative(String binTypeId, Object... params) {
			try {
				Class<?> c = modLoader.loadClass(binTypeId);
				return (RetType) c.newInstance();
			} catch (Exception e) {
				return Mind.wrapException(e, binTypeId);
			}
		}
	}
	
	protected RuntimeConnector(String[] args) {
		String root = System.getenv("GISKARD");
		
		if ( !MindUtils.isEmpty(root) ) {
			String gr = root + "/Brain/JRE/";
			modRoot = new File(gr + "Mod");
			extRoot = new File(gr + "Ext");
		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}
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
