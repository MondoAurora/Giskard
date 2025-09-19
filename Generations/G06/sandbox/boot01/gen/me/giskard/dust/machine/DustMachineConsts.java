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

	MindHandle MIND_TAG_ACCESS = Dust.lookup(null, "giskard.me/mind.0:10");
	MindHandle MIND_TAG_ACCESS_CHECK = Dust.lookup(null, "giskard.me/mind.0:11");
	MindHandle MIND_TAG_ACCESS_PEEK = Dust.lookup(null, "giskard.me/mind.0:12");
	MindHandle MIND_TAG_ACCESS_GET = Dust.lookup(null, "giskard.me/mind.0:13");
	MindHandle MIND_TAG_ACCESS_SET = Dust.lookup(null, "giskard.me/mind.0:14");
	MindHandle MIND_TAG_ACCESS_INSERT = Dust.lookup(null, "giskard.me/mind.0:15");
	MindHandle MIND_TAG_ACCESS_DELETE = Dust.lookup(null, "giskard.me/mind.0:16");
	MindHandle MIND_TAG_ACCESS_RESET = Dust.lookup(null, "giskard.me/mind.0:17");
	MindHandle MIND_TAG_ACCESS_COMMIT = Dust.lookup(null, "giskard.me/mind.0:18");

	MindHandle MIND_TAG_VALTYPE = Dust.lookup(null, "giskard.me/mind.0:19");
	MindHandle MIND_TAG_VALTYPE_INT = Dust.lookup(null, "giskard.me/mind.0:20");
	MindHandle MIND_TAG_VALTYPE_REAL = Dust.lookup(null, "giskard.me/mind.0:21");
	MindHandle MIND_TAG_VALTYPE_HANDLE = Dust.lookup(null, "giskard.me/mind.0:22");
	MindHandle MIND_TAG_VALTYPE_BIN = Dust.lookup(null, "giskard.me/mind.0:23");

	MindHandle MIND_TAG_COLLTYPE = Dust.lookup(null, "giskard.me/mind.0:24");
	MindHandle MIND_TAG_COLLTYPE_ONE = Dust.lookup(null, "giskard.me/mind.0:25");
	MindHandle MIND_TAG_COLLTYPE_SET = Dust.lookup(null, "giskard.me/mind.0:26");
	MindHandle MIND_TAG_COLLTYPE_ARR = Dust.lookup(null, "giskard.me/mind.0:27");
	MindHandle MIND_TAG_COLLTYPE_MAP = Dust.lookup(null, "giskard.me/mind.0:28");

	MindHandle MIND_TAG_ACTION = Dust.lookup(null, "giskard.me/mind.0:29");
	MindHandle MIND_TAG_ACTION_INIT = Dust.lookup(null, "giskard.me/mind.0:30");
	MindHandle MIND_TAG_ACTION_BEGIN = Dust.lookup(null, "giskard.me/mind.0:31");
	MindHandle MIND_TAG_ACTION_PROCESS = Dust.lookup(null, "giskard.me/mind.0:32");
	MindHandle MIND_TAG_ACTION_END = Dust.lookup(null, "giskard.me/mind.0:33");
	MindHandle MIND_TAG_ACTION_RELEASE = Dust.lookup(null, "giskard.me/mind.0:34");

	MindHandle MIND_TAG_STATUS = Dust.lookup(null, "giskard.me/mind.0:35");
	MindHandle MIND_TAG_STATUS_IDLE = Dust.lookup(null, "giskard.me/mind.0:36");
	MindHandle MIND_TAG_STATUS_PROCESSING = Dust.lookup(null, "giskard.me/mind.0:37");
	MindHandle MIND_TAG_STATUS_WAITING = Dust.lookup(null, "giskard.me/mind.0:38");
	MindHandle MIND_TAG_STATUS_ERROR = Dust.lookup(null, "giskard.me/mind.0:39");

	MindHandle MIND_TAG_RESULT = Dust.lookup(null, "giskard.me/mind.0:40");
	MindHandle MIND_TAG_RESULT_REJECT = Dust.lookup(null, "giskard.me/mind.0:41");
	MindHandle MIND_TAG_RESULT_PASS = Dust.lookup(null, "giskard.me/mind.0:42");
	MindHandle MIND_TAG_RESULT_READ = Dust.lookup(null, "giskard.me/mind.0:43");
	MindHandle MIND_TAG_RESULT_READACCEPT = Dust.lookup(null, "giskard.me/mind.0:44");
	MindHandle MIND_TAG_RESULT_ACCEPT = Dust.lookup(null, "giskard.me/mind.0:45");

	
	MindHandle MIND_VISIT_IDEA = Dust.lookup(null, "giskard.me/mind.0:46");
	MindHandle MIND_VISIT_ATT = Dust.lookup(null, "giskard.me/mind.0:47");
	MindHandle MIND_VISIT_KEY = Dust.lookup(null, "giskard.me/mind.0:48");
	MindHandle MIND_VISIT_VALUE = Dust.lookup(null, "giskard.me/mind.0:49");
	
	MindHandle MIND_CALL_STACK = Dust.lookup(null, "giskard.me/mind.0:50");

	MindHandle MIND_TAG_COLLTYPE_STACK = Dust.lookup(null, "giskard.me/mind.0:51");

	MindHandle MIND_DIALOG_INIT = Dust.lookup(null, "giskard.me/mind.0:52");
	MindHandle MIND_DIALOG_NEXT = Dust.lookup(null, "giskard.me/mind.0:53");

	MindHandle MIND_IDEA_ATTS = Dust.lookup(null, "giskard.me/mind.0:54");

	MindHandle MIND_LOGIC_GRAPHWALKER = Dust.lookup(null, "giskard.me/mind.0:55");

	
	/**** MISC ****/

	MindHandle MISC_ATT_TARGET = Dust.lookup(null, "giskard.me/misc.0:7");
	MindHandle MISC_ATT_COUNT = Dust.lookup(null, "giskard.me/misc.0:9");
	
	/**** DUST ****/

	MindHandle DUST_THREADS = Dust.lookup(null, "giskard.me/dust.0:1");
	MindHandle DUST_DIALOGS = Dust.lookup(null, "giskard.me/dust.0:2");
	MindHandle DUST_THREADDIALOG = Dust.lookup(null, "giskard.me/dust.0:3");
	MindHandle DUST_SELF = Dust.lookup(null, "giskard.me/dust.0:4");
	MindHandle DUST_PARAM = Dust.lookup(null, "giskard.me/dust.0:5");
	
	/**** DUSTJAVA ****/

	MindHandle DUSTJAVA_ATT_ITERATOR = Dust.lookup(null, "giskard.me/dustjava.0:2");
}
