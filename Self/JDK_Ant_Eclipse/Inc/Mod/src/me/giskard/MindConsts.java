package me.giskard;

public interface MindConsts {
	String MODULE_MIND = "MiND";
	
	String DEF_ENCODING = "UTF-8";
	String DEF_FORMAT_DATE = "YYYYMMdd";
	String DEF_FORMAT_TIMESTAMP = "YYYYMMdd_HHmmss_SSS";

	final long KEY_APPEND = -1;

	enum MiNDTokenType {
		UNIT, TYPE, MEMBER, TAG
	}

	enum MiNDValType {
		INT, REAL, REF, RAW
	}

	enum MiNDCollType {
		ONE, ARR, SET, MAP
	}
	
	public interface MiNDToken {
	}

	enum MiNDAccessCommand {
		CHK, GET, SET, ADD, DEL, VISIT
	};

	public interface MiNDContext {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		
		void selectById(MiNDToken target, String id);
		void selectByPath(MiNDToken target, Object... path);
		
		<RetType> RetType access(MiNDAccessCommand cmd, MiNDToken target, Object... valPath);
	}

	enum MiNDEventLevel {
		CRITICAL, ERROR, WARNING, INFO, TRACE, DEBUG
	};

	enum MiNDAgentAction {
		INIT, BEGIN, PROCESS, END, RELEASE
	};

	enum MiNDResultType {
		NOTIMPLEMENTED, REJECT, ACCEPT_PASS, ACCEPT, ACCEPT_READ, READ
	};

	public interface MiNDModuleManager {
		void addModule(String modName, String mainLib, String ver, String... extLibs);
		<RetType> RetType createObject(String modName, String binTypeId, Object... params);
		void deleteObject(String modName, String binTypeId, Object object);
	}

	public interface MiNDAgent extends MindConsts {
		MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception;
	}

	public static final class MiNDException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		MiNDException(Throwable src) {
			super(src);
		}
	}
}
