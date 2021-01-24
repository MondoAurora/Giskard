package me.giskard.java;

public interface MindConsts {
	
	public interface MiNDToken {
	}
	
	public interface MiNDAgent {
		void process() throws Exception;
	}
	
	public static final class MiNDException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		MiNDException(Throwable src) {
			super(src);
		}
	}

}
