package me.giskard.mind;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class GiskardApp implements GiskardConsts {
	protected static class PublishedBean {
		public final String publisher;
		public final String token;
		public final Integer verMajor;
		public final Integer verMinor;
		
		public PublishedBean(String publisher, String token, Integer verMajor, Integer verMinor) {
			this.publisher = publisher;
			this.token = token;
			this.verMajor = verMajor;
			this.verMinor = verMinor;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = GiskardUtils.sbAppend(null, SEP, false, publisher, token, verMajor, verMinor);
			return sb.toString();			
		}
	}
	
	protected static class BrainBean {
		public final PublishedBean pubInfo;
		public final String libPath;
		public final String brainClass;
		
		public BrainBean(PublishedBean pubInfo, String libPath, String brainClass) {
			this.pubInfo = pubInfo;
			this.libPath = libPath;
			this.brainClass = brainClass;
		}
	}
	
	protected final BrainBean brainBean;
	protected final PublishedBean appBean;
	
	protected GiskardApp(BrainBean brainBean, PublishedBean appBean) {
		this.brainBean = brainBean;
		this.appBean = appBean;
	}

	protected void initApp() throws Exception {
		
	}
	
	@SuppressWarnings("unchecked")
	protected final void boot() throws Exception {
		PublishedBean pb = brainBean.pubInfo;
		
		Class<MindBrain> cBrain = null;

		try {
			cBrain = (Class<MindBrain>) Class.forName(brainBean.brainClass);
		} catch (Throwable e) {
			ClassLoader cl = GiskardApp.getClassLoader(pb, brainBean.libPath);
			cBrain = (Class<MindBrain>) cl.loadClass(brainBean.brainClass);
		}
		
		MindBrain brain = cBrain.getConstructor().newInstance();
		GiskardMind.initBrain(brain);
		
		brain.agentExecAction(MindAction.Init);

		initApp();
		
		brain.agentExecAction(MindAction.Begin);
	}
	
	public static ClassLoader getClassLoader(PublishedBean moduleBean, String modulePath) throws Exception {
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
