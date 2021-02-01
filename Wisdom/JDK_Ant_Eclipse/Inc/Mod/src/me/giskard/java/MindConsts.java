package me.giskard.java;

public interface MindConsts {

	public interface MiNDEntity {
	}

	public interface MiNDContext {
		void select(MiNDEntity target, Object... path);
		<RetType> RetType access(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val, Object key);
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

	interface EntityWrapper {
		MiNDEntity getEntity();
	}

}
