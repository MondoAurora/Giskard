package me.giskard.mind;

public class GiskardMind implements GiskardConsts {

	static GiskardBootParams BOOT_PARAMS = new GiskardBootParams();
	private static MindBrain BRAIN = BOOT_PARAMS;

	static void initBrain(MindBrain brain) throws Exception {
		if ( BOOT_PARAMS != BRAIN ) {
			GiskardException.wrap(null, "Brain already set");
		} else {
			BRAIN = brain;
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					releaseBrain();
				}
			});
		}
	}
	
	public static synchronized void releaseBrain() {
		if ( null != BRAIN ) {
			try {
				BRAIN.agentExecAction(MindAction.End);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				BRAIN.agentExecAction(MindAction.Release);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			BRAIN = null;
		}
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
