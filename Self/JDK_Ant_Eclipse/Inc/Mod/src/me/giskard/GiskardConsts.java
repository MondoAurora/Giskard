package me.giskard;

public interface GiskardConsts {
	String SEP_ID = "_";

	String DEF_ENCODING = "UTF-8";
	String DEF_FORMAT_DATE = "YYYYMMdd";
	String DEF_FORMAT_TIMESTAMP = "YYYYMMdd_HHmmss_SSS";

	final long KEY_APPEND = -1;

	enum MiNDTokenType {
		UNIT, AGENT, TYPE, MEMBER, TAG, LOCAL
	}

	enum MiNDValType {
		Int, Real, Ref, Raw
	}

	enum MiNDCollType {
		One, Arr, Set, Map
	}
	
	public interface MiNDToken {
	}

	enum MiNDAccessCommand {
		Chk, Get, Set, Add, Del, Use
	};

	public interface MiNDAccessor {
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath);
	};

	public interface MiNDContext extends MiNDAccessor {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		void selectByPath(MiNDToken target, Object... path);		
	}

	enum MiNDEventLevel {
		CRITICAL, ERROR, WARNING, INFO, TRACE, DEBUG
	};

	enum MiNDAgentAction {
		Init, Begin, Process, End, Release
	};

	enum MiNDResultType {
		NOTIMPLEMENTED, REJECT, ACCEPT_PASS, ACCEPT, ACCEPT_READ, READ
	};

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception;
	}

	public interface MiNDMachine extends MiNDAccessor {
		void init() throws Exception;
		Object addModule(String modName, String ver);
		void launch() throws Exception;
		MiNDAgent testCreateAgent(MiNDToken token) throws Exception;
	}

}
