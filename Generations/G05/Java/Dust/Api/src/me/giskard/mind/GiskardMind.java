package me.giskard.mind;

public class GiskardMind implements GiskardConsts {

	private static MindBrain BRAIN = null;

	@SuppressWarnings("unchecked")
	public static void initBrain(String publisher, String module, int version, String modulePath, String brainClassName) throws Exception {
		if ( null != BRAIN ) {
			GiskardException.wrap(null, "Brain already set");
		}

		Class<MindBrain> cBrain = null;

		try {
			cBrain = (Class<MindBrain>) Class.forName(brainClassName);
		} catch (Throwable e) {
			ClassLoader cl = GiskardUtils.getClassLoader(module, modulePath);
			cBrain = (Class<MindBrain>) cl.loadClass(brainClassName);
		}
		
		BRAIN = cBrain.getConstructor().newInstance();
		
		BRAIN.agentExecAction(MindAction.Init);
	}

	public static void launch() {
		BRAIN.agentExecAction(MindAction.Begin);
	}

	public static <RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path) {
		return BRAIN.access(cmd, val, root, path);
	}

	public static void dump(Object... obs) {
		log(null, obs);
	}

	public static void log(Object event, Object... params) {
		BRAIN.log(event, params);
	}
}
