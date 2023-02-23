package me.giskard.mind;

public interface GiskardConsts {
	String EXT_JAR = ".jar";
	String SEP = "_";
	
	enum MindValType {
		Int, Real, Handle, Bin,
	};

	enum MindColl {
		Set, Arr, Map,
	};

	enum MindRoot {
		Action, Self, Dialog, 
	};

	enum MindAccess {
		Check, Peek, Commit, Get, Set, Insert, Delete,
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
