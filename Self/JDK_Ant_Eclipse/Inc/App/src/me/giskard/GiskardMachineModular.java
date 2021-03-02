package me.giskard;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.utils.MindUtils;

public class GiskardMachineModular extends GiskardMachineMonolith {

	private static ClassLoader APP_CLASSLOADER = ClassLoader.getSystemClassLoader();
	
	protected static String getModuleKey(String modName, String ver) {
		return modName + "-" + ver;
	}

	static URL optGetUrl(File root, String name) {
		File f = MindUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return MindUtils.wrapException(t, "Missing library", f.getAbsolutePath());
		}
	}

	protected File modRoot;
	protected File extRoot;

	protected String mindModule;
	protected String mindVersion;
	private Map<String, DynamicModule> modules = new TreeMap<>();

	class DynamicModule extends MachineModule {
		String libMod;

		public DynamicModule(String libMod_, String ver) {
			this.libMod = getModuleKey(libMod_, ver);

			String currLib = null;

			try {
				ArrayList<URL> urls = new ArrayList<>();

				urls.add(optGetUrl(modRoot, libMod + ".jar"));
				
				File dir = new File(extRoot, libMod);
				if ( dir.isDirectory() ) {
					for ( File fLib : dir.listFiles() ) {
						if (fLib.isFile() && fLib.getName().endsWith(".jar")) {
							urls.add(fLib.toURI().toURL());
						}
					}
				}

				URL[] uu = new URL[urls.size()];
				uu = urls.toArray(uu);
				setLoader(new URLClassLoader(uu, APP_CLASSLOADER));

				Class<?> cMind = cl.loadClass(Mind.class.getCanonicalName());

				String rootPkg = cMind.getPackage().getName();
				Class<?> cMod = cl.loadClass(rootPkg + ".mod." + libMod_);
				if ( null != cMod ) {
					modAgent = (MiNDAgent) cMod.newInstance();
					modAgent.process(MiNDAgentAction.Init);
				}
				
				optLoadNativeConn();
			} catch (Throwable e) {
				MindUtils.wrapException(e, libMod, currLib);
			}
		}
	}

	public GiskardMachineModular(String mindModule, String mindVersion, String[] args) {
		super(null, args);

		this.mindModule = mindModule;
		this.mindVersion = mindVersion;
	}

	public void init() {
		String root = MindUtils.getRoot();

		if ( !MindUtils.isEmpty(root) ) {
			String gr = root + "/Brain/JRE/";
			modRoot = new File(gr + "Mod");
			extRoot = new File(gr + "Ext");

			Mind.log(MiNDEventLevel.TRACE, "Modular machine using", gr);

		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}

		modMind = addModule(mindModule, mindVersion);
		
		setNativeConnector();
	}

	@Override
	public DynamicModule addModule(String modName, String ver) {
		if ( modules.containsKey(modName) ) {
			MindUtils.wrapException(null, "Module already loaded", modName);
		}
		
		Mind.log(MiNDEventLevel.TRACE, "Adding module", modName, ver);
		DynamicModule mod = new DynamicModule(modName, ver);
		modules.put(modName, mod);
		return mod;
	}

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		return super.access(cmd, val, target, valPath);
	}
}
