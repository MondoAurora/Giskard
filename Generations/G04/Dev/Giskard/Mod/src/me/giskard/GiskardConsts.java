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
		Check, Peek, Get, Set, Insert, Delete, Visit, Broadcast,
	};

	enum MiNDAction {
		Init, Begin, Process, End, Release
	};

	enum MiNDResultType {
		Notimplemented, Processing, Reject, Accept, AcceptRead, Read
	};

	public interface MiNDEntity {
	};

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType mindAgentProcess() throws Exception;
	}

	public interface GiskardImpl {
		<RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath);
	}

}
