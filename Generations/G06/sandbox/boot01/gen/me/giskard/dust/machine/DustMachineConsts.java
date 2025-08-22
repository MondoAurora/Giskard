package me.giskard.dust.machine;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMachineConsts extends DustConsts {
	
	enum MindValType {
		Int, Real, Handle, Bin
	};

	enum MindCollType {
		One, Set, Arr, Map
	};
	
	enum MindAccess {
		Check, Peek, Get, Set, Insert, Delete, Reset, Commit, Broadcast, Lookup, Visit, 
	};
	
	enum MindAction {
		Init, Begin, Process, End, Release,
	};

	enum MindResult {
		Reject, Pass, Read, ReadAccept, Accept,
	};

	MindHandle MIND_TAG_VALTYPE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_VALTYPE_INT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_VALTYPE_REAL = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_VALTYPE_HANDLE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_VALTYPE_BIN = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_COLLTYPE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_COLLTYPE_ONE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_COLLTYPE_SET = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_COLLTYPE_ARR = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_COLLTYPE_MAP = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_ACCESS = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_CHECK = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_PEEK = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_GET = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_SET = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_INSERT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_DELETE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_RESET = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_COMMIT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_BROADCAST = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_LOOKUP = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACCESS_VISIT = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_ACTION = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACTION_INIT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACTION_BEGIN = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACTION_PROCESS = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACTION_END = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_ACTION_RELEASE = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_STATUS = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_STATUS_IDLE = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_STATUS_PROCESSING = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_STATUS_WAITING = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_STATUS_ERROR = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_RESULT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_RESULT_REJECT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_RESULT_PASS = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_RESULT_READ = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_RESULT_READACCEPT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_TAG_RESULT_ACCEPT = Dust.lookup(null, "giskard.me/mind:?");

}
