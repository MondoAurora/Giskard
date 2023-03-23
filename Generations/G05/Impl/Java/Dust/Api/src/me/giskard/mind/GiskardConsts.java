package me.giskard.mind;

public interface GiskardConsts {
	String EXT_JAR = ".jar";
	String EXT_JSON = ".json";
	String SEP = "_";
	
	int KEY_ADD = -1;
	int KEY_SIZE = -2;
	int KEY_ITER = -3;
	int KEY_KEYS = -4;
	
	enum MindValType {
		Int, Real, Handle, Bin,
	};

	enum MindColl {
		One, Set, Arr, Map,
		;
		
		public String wrapSize(Number size) {
			switch (this) {
			case Arr:
				return "[" + size + "]";
			case Map:
				return "{" + size + "}";
			case One:
				return  size.toString() ;
			case Set:
				return "(" + size + ")";			
			}
			
			return "?";
		}
	};

	enum MindRoot {
		Action, Self, Dialog, 
	};

	enum MindAccess {
		Check(false), Peek(false), Commit(false), Get(true), Set(true), Insert(true), Delete(false),
		
		;
		
		public final boolean add;

		private MindAccess(boolean add) {
			this.add = add;
		}
		
		
	};

	enum MindAction {
		Init, Begin, Process, End, Release,
	};

	enum MindStatus {
		Waiting, Processing, Reject, Read, ReadAccept, Accept, Error,
	};

	enum MindListener {
		Interceptor, Monitor, Processor, Follower,
	};

	interface MindHandle {
		Object getUnitToken();
		Object getId();
	};

	interface MindAgent {
		MindStatus agentExecAction(MindAction action) throws Exception;
	}

	interface MindBrain extends MindAgent {
		<RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path);
		void log(Object event, Object... params);
	}
}
