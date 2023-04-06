package me.giskard.mind;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class GiskardApp implements GiskardConsts {
	public static final String CFGKEY_STOREPATH = "Giskard.G05.LocalStoreRoot";

	protected static class ModuleBean {
		public final String author;
		public final String token;
		public final Integer verMajor;
		public final Integer verMinor;

		public final String mainClass;

		public ModuleBean(String author, String token, Integer verMajor, Integer verMinor) {
			this(author, token, verMajor, verMinor, null);
		}

		public ModuleBean(String author, String token, Integer verMajor, Integer verMinor, String mainClass) {
			this.author = author;
			this.token = token;
			this.verMajor = verMajor;
			this.verMinor = verMinor;
			this.mainClass = mainClass;
		}

		@Override
		public String toString() {
			StringBuilder sb = GiskardUtils.sbAppend(null, SEP, false, author, token, verMajor, verMinor);
			return sb.toString();
		}
	}

	protected final ModuleBean brainBean;
	protected final ModuleBean appBean;
	
	protected String moduleRoot;

	protected GiskardApp(ModuleBean brainBean, ModuleBean appBean) {
		this.brainBean = brainBean;
		this.appBean = appBean;
	}

	protected void initApp() throws Exception {

	}

	@SuppressWarnings("unchecked")
	protected final void boot(String[] args) throws Exception {
		GiskardMind.BOOT_PARAMS.initParams(args);
		
		moduleRoot = GiskardMind.access(MindAccess.Peek, null, BootParam.Map, CFGKEY_STOREPATH);
		if ( GiskardUtils.isEmpty(moduleRoot) ) {
			GiskardException.wrap(null, "CRITICAL: Giskard local store path not set! Key:", CFGKEY_STOREPATH);
		}
		moduleRoot += "/Binary/Java";
		File f = new File(moduleRoot);
		
		if ( !f.isDirectory() ) {
			GiskardUtils.dump(", ", false, "WARNING: Giskard local binary root folder not found!", f.getCanonicalPath());
		} else {
			GiskardUtils.dump(" ", false, "INFO: Using Giskard local binary root folder:", f.getCanonicalPath());			
		}

		Class<MindBrain> cBrain = null;

		try {
			cBrain = (Class<MindBrain>) Class.forName(brainBean.mainClass);
		} catch (Throwable e) {
			ClassLoader cl = GiskardApp.getClassLoader(brainBean, moduleRoot);
			cBrain = (Class<MindBrain>) cl.loadClass(brainBean.mainClass);
		}

		MindBrain brain = cBrain.getConstructor().newInstance();
		GiskardMind.initBrain(brain);

		brain.agentExecAction(MindAction.Init);

//		initApp();

		brain.agentExecAction(MindAction.Begin);
	}

	public static ClassLoader getClassLoader(ModuleBean moduleBean, String modulePath) throws Exception {
		ArrayList<URL> urls = new ArrayList<>();

		String brainModuleName = moduleBean.toString();
		File f = new File(modulePath, brainModuleName + EXT_JAR);
		urls.add(f.toURI().toURL());

		File dir = new File(modulePath, brainModuleName);
		if ( dir.isDirectory() ) {
			for (File fLib : dir.listFiles()) {
				if ( fLib.isFile() && fLib.getName().endsWith(EXT_JAR) ) {
					urls.add(fLib.toURI().toURL());
				}
			}
		}

		URL[] uu = new URL[urls.size()];
		uu = urls.toArray(uu);

		return new URLClassLoader(uu, ClassLoader.getSystemClassLoader());
	}

}
