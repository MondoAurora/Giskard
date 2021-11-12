package ai.montru.giskard;

/**
 * General constants for agents that know all meta information.
 */

public interface GiskardConsts {
	
	public enum GiskardAccess {
		Check, Peek, Get, Update, Set, Insert, Delete,
	}
	
	public enum GiskardAction {
		Init, Begin, Process, End, Release,
	}
	
	public enum GiskardContext {
		 Process, Relative, Absolute,
	}
	
	enum GiskardResponse {
		Reject, Accept, AcceptRead, Read,
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
		GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException;
	}

	public interface GiskardModule {
		void initModule(Giskard runtime, String name, String version) throws Exception;
	}
	
	
	Integer GIS_CONST_KEY_ADDLAST = -1;
	Integer GIS_CONST_KEY_ADDFIRST = -2;
	

}
