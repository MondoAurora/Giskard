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
		Check, Peek, Get, Set, Insert, Delete, Reset, Commit, // Visit, 
	};
	
	enum MindAction {
		Init, Begin, Process, End, Release,
	};

	enum MindResult {
		Reject, Pass, Read, ReadAccept, Accept,
	};
	
	/**** MIND ****/

	MindHandle MIND_TAG_ACCESS = Dust.lookup(null, "giskard.me/mind:10");
	MindHandle MIND_TAG_ACCESS_CHECK = Dust.lookup(null, "giskard.me/mind:11");
	MindHandle MIND_TAG_ACCESS_PEEK = Dust.lookup(null, "giskard.me/mind:12");
	MindHandle MIND_TAG_ACCESS_GET = Dust.lookup(null, "giskard.me/mind:13");
	MindHandle MIND_TAG_ACCESS_SET = Dust.lookup(null, "giskard.me/mind:14");
	MindHandle MIND_TAG_ACCESS_INSERT = Dust.lookup(null, "giskard.me/mind:15");
	MindHandle MIND_TAG_ACCESS_DELETE = Dust.lookup(null, "giskard.me/mind:16");
	MindHandle MIND_TAG_ACCESS_RESET = Dust.lookup(null, "giskard.me/mind:17");
	MindHandle MIND_TAG_ACCESS_COMMIT = Dust.lookup(null, "giskard.me/mind:18");
//	MindHandle MIND_TAG_ACCESS_VISIT = Dust.lookup(null, "giskard.me/mind:19");

	MindHandle MIND_LOGIC_GRAPHWALKER = Dust.lookup(null, "giskard.me/mind:19");

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

	MindHandle MIND_TAG_ACTION = Dust.lookup(null, "giskard.me/mind:118");
	MindHandle MIND_TAG_ACTION_INIT = Dust.lookup(null, "giskard.me/mind:119");
	MindHandle MIND_TAG_ACTION_BEGIN = Dust.lookup(null, "giskard.me/mind:120");
	MindHandle MIND_TAG_ACTION_PROCESS = Dust.lookup(null, "giskard.me/mind:121");
	MindHandle MIND_TAG_ACTION_END = Dust.lookup(null, "giskard.me/mind:122");
	MindHandle MIND_TAG_ACTION_RELEASE = Dust.lookup(null, "giskard.me/mind:123");

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

	
	MindHandle MIND_VISIT_IDEA = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_VISIT_ATT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_VISIT_KEY = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_VISIT_VALUE = Dust.lookup(null, "giskard.me/mind:?");
	
	MindHandle MIND_CALL_STACK = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_TAG_COLLTYPE_STACK = Dust.lookup(null, "giskard.me/mind:?");

	MindHandle MIND_DIALOG_INIT = Dust.lookup(null, "giskard.me/mind:?");
	MindHandle MIND_DIALOG_NEXT = Dust.lookup(null, "giskard.me/mind:?");

	
	/**** MISC ****/

	MindHandle MISC_ATT_TARGET = Dust.lookup(null, "giskard.me/misc:7");
	MindHandle MISC_ATT_COUNT = Dust.lookup(null, "giskard.me/misc:8");
	
	/**** DUST ****/

	MindHandle DUST_THREADS = Dust.lookup(null, "giskard.me/dust:1");
	MindHandle DUST_DIALOGS = Dust.lookup(null, "giskard.me/dust:2");
	MindHandle DUST_THREADDIALOG = Dust.lookup(null, "giskard.me/dust:3");
	MindHandle DUST_SELF = Dust.lookup(null, "giskard.me/dust:4");
	MindHandle DUST_PARAM = Dust.lookup(null, "giskard.me/dust:5");
	
	/**** DUSTJAVA ****/

	MindHandle DUSTJAVA_ATT_ITERATOR = Dust.lookup(null, "giskard.me/dustjava:?");
}
