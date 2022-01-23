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
	
	enum GiskardLogicState {
		Idle, Processing, Waiting, Completed, Exception,
	}
	
	enum GiskardResponse {
		Reject, Accept, AcceptRead, Read,
	};
	
	enum GiskardChange {
		Created, ChgValue, ChgEntity, ChgCommit, Deleted
	};
	
	/**
	 * You only get references, the actual data behind depends on your dialog context.
	 */
	interface GiskardEntityRef {
		GiskardEntityRef gisGetUnit();
		Object gisGetID();
	}
	
	interface GiskardMeta {
		GiskardEntityRef gisGetToken(GiskardEntityRef unit, Object tokenId);
	}
	
	public abstract class GiskardException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		protected GiskardEntityRef refInfo;
		
		protected GiskardException(Throwable cause) {
			super(cause);
		}
		
		public GiskardEntityRef gisGetInfo() {
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
		void gisInitModule(Giskard runtime, String name, String version) throws Exception;
	}
	
	Integer GIS_CONST_KEY_ADDLAST = -1;
	Integer GIS_CONST_KEY_ADDFIRST = -2;
}
