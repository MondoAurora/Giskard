package me.giskard;

public interface MindConsts {
	String MODULE_MIND = "MiND";
	
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
		<RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath);
	};

	public interface MiNDContext extends MiNDAccessor {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		
		void selectById(MiNDToken target, String id);
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

	public interface MiNDAgent extends MindConsts {
		MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception;
	}

	public static final class MiNDException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public MiNDException(Throwable src) {
			super(src);
		}
	}

	public interface MiNDMachine extends MiNDAccessor {
		void addModule(String modName, String mainLib, String ver, String... extLibs);
	}
	
	interface MiNDCreator<Key, Val> {
		Val create(Key key);
	}

	abstract class MiNDBuilder<Key, Val> implements MiNDCreator<Key, Val>	{
		public abstract void release(Key key, Val v);
	}

}
