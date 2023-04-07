package me.giskard.mind;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GiskardApp implements GiskardConsts {
	public static final String CFGKEY_LOCALSTOREROOT = "Giskard.G05.LocalStoreRoot";
	public static final String CFGKEY_MODULEMAIN = "Giskard.G05.ModuleMain";

	protected static class ModuleBean {
		public final String author;
		public final String token;
		public final Integer verMajor;
		public final Integer verMinor;
		
		private ClassLoader cl;
		private MindAgent mainAgent;

		public ModuleBean(String author, String token, Integer verMajor, Integer verMinor) {
			this.author = author;
			this.token = token;
			this.verMajor = verMajor;
			this.verMinor = verMinor;
		}

		@Override
		public String toString() {
			StringBuilder sb = GiskardUtils.sbAppend(null, SEP, false, author, token, verMajor, verMinor);
			return sb.toString();
		}
		
		@SuppressWarnings("unchecked")
		public MindAgent getMainAgent() throws Exception {
			if ( null == cl ) {
				String modID = toString();
				String modClass = GiskardMind.access(MindAccess.Peek, null, BootParam.LaunchConfig, CFGKEY_MODULEMAIN + "." + modID);
				cl = ClassLoader.getSystemClassLoader();
				Class<MindAgent> cAgent = null;
				
				if ( null != modClass ) {
					cAgent = (Class<MindAgent>) cl.loadClass(modClass);
				}
				
				if ( null == cAgent ) {
					ArrayList<URL> urls = new ArrayList<>();

					String brainModuleName = modID;
					File f = new File(moduleRootDir, brainModuleName + EXT_JAR);
					urls.add(f.toURI().toURL());

					File dir = new File(moduleRootDir, brainModuleName);
					if ( dir.isDirectory() ) {
						for (File fLib : dir.listFiles()) {
							if ( fLib.isFile() && fLib.getName().endsWith(EXT_JAR) ) {
								urls.add(fLib.toURI().toURL());
							}
						}
					}

					URL[] uu = new URL[urls.size()];
					uu = urls.toArray(uu);

					cl = new URLClassLoader(uu, ClassLoader.getSystemClassLoader());
					
					InputStream isMF = cl.getResourceAsStream("META-INF/MANIFEST.MF");
					if ( null != isMF ) {
						BufferedReader br = new BufferedReader(new InputStreamReader(isMF));
						String line;
						Pattern pt = Pattern.compile("Main-Class:(.*)");

						while (null != (line = br.readLine())) {
							GiskardUtils.dump(", ", false, line);

							Matcher m = pt.matcher(line);
							if ( m.matches() ) {
								modClass = m.group(1).trim();
								cAgent = (Class<MindAgent>) cl.loadClass(modClass);
								break;
							}
						}

						br.close();
					}					
				}

				if ( null != cAgent ) {
					mainAgent = cAgent.getConstructor().newInstance();
					mainAgent.agentExecAction(MindAction.Init);

					if ( mainAgent instanceof MindBrain ) {
						GiskardMind.initBrain((MindBrain) mainAgent);
					}
				}
			}
			
			return mainAgent;
		}
		
	}

	protected final ModuleBean brainBean;
	protected final ModuleBean appBean;

	protected static File moduleRootDir;

	protected GiskardApp(ModuleBean brainBean, ModuleBean appBean) {
		this.brainBean = brainBean;
		this.appBean = appBean;
	}

	protected void initApp() throws Exception {

	}

	protected final void boot(String[] args) throws Exception {
		GiskardMind.BOOT_PARAMS.initParams(args);

		String storeRoot = GiskardMind.access(MindAccess.Peek, null, BootParam.LaunchConfig, CFGKEY_LOCALSTOREROOT);
		if ( GiskardUtils.isEmpty(storeRoot) ) {
			GiskardException.wrap(null, "CRITICAL: Giskard local store path not set! Key:", CFGKEY_LOCALSTOREROOT);
		}
		moduleRootDir = new File(storeRoot + "/Binary/Java");
		String pModRoot = moduleRootDir.getCanonicalPath();

		if ( !moduleRootDir.isDirectory() ) {
			GiskardUtils.dump(", ", false, "WARNING: Giskard local binary root folder not found!", pModRoot);
		} else {
			GiskardUtils.dump(" ", false, "INFO: Using Giskard local binary root folder:", pModRoot);
		}

		GiskardMind.BOOT_PARAMS.access(MindAccess.Set, appBean, BootParam.AppModule);

		MindAgent brain = brainBean.getMainAgent();
		

//		String cnBrain = getModClassName(brainBean);
//		if ( null != cnBrain ) {
//			Class<MindBrain> cBrain = (Class<MindBrain>) Class.forName(brainBean.mainClass);
//			brain = cBrain.getConstructor().newInstance();
//			GiskardMind.initBrain(brain);
//
//			brain.agentExecAction(MindAction.Init);
//		} else {
//			ClassLoader cl = GiskardApp.getClassLoader(brainBean);
//			brain = (MindBrain) optInitModule(brainBean, cl);
//		}

//		Class<MindBrain> cBrain = null;
//
//		try {
//			cBrain = (Class<MindBrain>) Class.forName(brainBean.mainClass);
//		} catch (Throwable e) {
//			ClassLoader cl = GiskardApp.getClassLoader(brainBean);
//			cBrain = (Class<MindBrain>) cl.loadClass(brainBean.mainClass);
//		}
//
//		MindBrain brain = cBrain.getConstructor().newInstance();
//		GiskardMind.initBrain(brain);
//
//		brain.agentExecAction(MindAction.Init);

//		initApp();

		brain.agentExecAction(MindAction.Begin);
	}

//	@SuppressWarnings("unchecked")
//	public static MindAgent optInitModule(ModuleBean moduleBean, ClassLoader cl) throws Exception {
//		MindAgent aModule = null;
//		String agentClass = getModClassName(moduleBean);
//
//		InputStream isMF = cl.getResourceAsStream("META-INF/MANIFEST.MF");
//		if ( null != isMF ) {
//			BufferedReader br = new BufferedReader(new InputStreamReader(isMF));
//			String line;
//			Pattern pt = Pattern.compile("Main-Class:(.*)");
//
//			while (null != (line = br.readLine())) {
//				Matcher m = pt.matcher(line);
//				if ( m.matches() ) {
//					agentClass = m.group(1).trim();
//					break;
//				}
//			}
//
//			br.close();
//		}
//
//		if ( null != agentClass ) {
//			Class<MindAgent> cAgent = (Class<MindAgent>) Class.forName(agentClass);
//			aModule = cAgent.getConstructor().newInstance();
//
//			if ( aModule instanceof MindBrain ) {
//				GiskardMind.initBrain((MindBrain) aModule);
//			}
//			aModule.agentExecAction(MindAction.Init);
//		}
//
//		return aModule;
//	}
//
//	public static String getModClassName(ModuleBean moduleBean) {
//		return GiskardMind.access(MindAccess.Peek, null, BootParam.LaunchConfig, CFGKEY_MODULEMAIN + "." + moduleBean.toString());
//	}



}
