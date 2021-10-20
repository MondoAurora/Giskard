package me.giskard;

/**
 * General constants for agents that know all meta information.
 */

public interface GiskardConsts {
	
	public enum GiskardAccessCmd {
		Visit, Check, Peek, Get, Update, Set, Insert, Delete
	}
	
	public enum GiskardAgentCmd {
		Init, Begin, Process, End, Release
	}
	
	public enum GiskardPathRoot {
		This, Message, Response, Dialog
	}
	
	enum GiskardAgentResponse {
		Reject, Accept, AcceptRead, Read
	};
	
	/**
	 * You only get references, the actual data behind depends on your dialog context.
	 */
	interface GiskardEntityRef {
		GiskardEntityRef getUnit();
		Object getID();
	}
	
	public abstract class GiskardException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		protected GiskardEntityRef refInfo;
		
		protected GiskardException(Throwable cause) {
			super(cause);
		}
		
		public GiskardEntityRef getInfo() {
			return refInfo;
		}
	}
	
	/**
	 * The one and only function called by the environment.
	 * No parameters, you can use GiskardPathRoot constants a the start of the path in access functions.
	 * You must handle all exceptions at least by Giskard.wrapException that will re-throw a RuntimeException
	 */
	interface GiskardAgent {
		void gisAgentProcess() throws GiskardException;
	}

	public interface GiskardModule {
		void initModule(Giskard runtime);
	}

}
