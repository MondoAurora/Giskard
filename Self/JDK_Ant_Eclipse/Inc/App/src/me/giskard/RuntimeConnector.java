package me.giskard;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.utils.MindUtils;

public class RuntimeConnector implements MindConsts, MindConsts.MiNDMachine {

	static URL optGetUrl(File root, String name) {
		File f = MindUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return MindUtils.wrapException(t, "Missing library", f.getAbsolutePath());
		}
	}
	
	private static ClassLoader appLoader = ClassLoader.getSystemClassLoader();

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
				modLoader = new URLClassLoader(uu, appLoader);
				
				Class<?> cMind = modLoader.loadClass(Mind.class.getCanonicalName());
				
				String rootPkg = cMind.getPackage().getName();
				Class<?> cMod = modLoader.loadClass(rootPkg + ".mod." + libMod);
				if ( null != cMod ) {
					modAgent = (MiNDAgent) cMod.newInstance();
					modAgent.process(MiNDAgentAction.INIT);
				}
			} catch (Throwable e) {
				MindUtils.wrapException(e, libMod, currLib);
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType createNative(String binTypeId, Object... params) {
			try {
				Class<?> c = modLoader.loadClass(binTypeId);
				return (RetType) c.newInstance();
			} catch (Exception e) {
				return MindUtils.wrapException(e, binTypeId);
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

	protected final void launch() throws Exception {
		Mind.log(MiNDEventLevel.INFO, "GISKARD boot success.");
		
		Module mod = modules.get(MODULE_MIND);
		mod.modAgent.process(MiNDAgentAction.BEGIN);
		
		Mind.log(MiNDEventLevel.INFO, "GISKARD finished.");
	}

	@Override
	public final void addModule(String modName, String mainLib, String ver, String... extLibs) {
		if ( modules.containsKey(modName) ) {
			MindUtils.wrapException(null, "Module already loaded", modName);
		}
		modules.put(modName, new Module(mainLib, ver, extLibs));
	}
	
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		return null;
	}

}
