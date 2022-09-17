package me.giskard;

public interface GiskardConsts {
	String SEP_ID = "_";

	String DEF_ENCODING = "UTF-8";
	String DEF_FORMAT_DATE = "YYYYMMdd";
	String DEF_FORMAT_TIMESTAMP = "YYYYMMdd_HHmmss_SSS";

	final int KEY_APPEND = -1;
	final int KEY_SIZE = -2; // return the item count of a member
	final int KEY_FORMAT_STRING = -3; // "toString" of the current member (formatters should be sent into the runtime)

	enum MiNDAccessCommand {
		Check(false), Peek(false), Get(true), Set(true), Insert(true), Delete(false), //Visit(false), Broadcast(false),
		
		;
		
		public final boolean creator;

		private MiNDAccessCommand(boolean creator) {
			this.creator = creator;
		}
	};

	enum MiNDAction {
		Init, Begin, Process, End, Release
	};

	enum MiNDResultType {
		Notimplemented(false, false), 
		Reject(false, false), Accept(true, false), 
		AcceptRead(true, true), Read(false, true), 
		Processing(false, true), Waiting(false, true),
		
		;
		
		boolean accept;
		boolean read;
		
		private MiNDResultType(boolean accept, boolean read) {
			this.accept = accept;
			this.read = read;
		}
	};

	public interface MiNDHandle {
		Object getId();
	};

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType mindAgentProcess() throws Exception;
	}

	public interface GiskardImpl {
		<RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath);
		void broadcast_(MiNDHandle event, Object... params);
	}

}
