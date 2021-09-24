package me.giskard.sandbox.mind;

public abstract class GSBMind implements GSBMindConsts {
	
	private static GSBMind THE_MIND;

	public static void main(String[] args) throws Exception {
		String mc = "me.giskard.sandbox.mind.plain.GSBMindPlain";
		
		THE_MIND = (GSBMind) Class.forName(mc).newInstance();
	}
	
	
	public static Object access(MindAccessCmd cmd, Object value, Object... path) {
		return THE_MIND.access_(cmd, value, path);
	}

	public static <RetType> RetType wrapException(Throwable orig, Object... params) {
		return THE_MIND.wrapException_(orig, params);
	}
	
	public static void log(MindEventLevel level, Object... params) {
		THE_MIND.log_(level, params);
	}
	
	protected abstract Object access_(MindAccessCmd cmd, Object value, Object... path);
	protected abstract <RetType> RetType wrapException_(Throwable orig, Object... params);
	protected abstract void log_(MindEventLevel level, Object... params);
	
}
