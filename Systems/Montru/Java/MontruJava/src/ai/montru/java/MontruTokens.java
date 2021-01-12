package ai.montru.java;

public interface MontruTokens extends Dust {

	enum Units {
		MIND_MODEL, MIND_IDEA, MIND_NARRATIVE, MIND_DIALOG,

		UTIL_TEXT, UTIL_EVENT,
	};

	enum AgentAction {
		INIT, BEGIN, PROCESS, END, RELEASE
	}

	Token DT_MIND_NARRATIVE_ACTION_INIT = Token.getToken(Units.MIND_NARRATIVE, "0", AgentAction.INIT);
	Token DT_MIND_NARRATIVE_ACTION_BEGIN = Token.getToken(Units.MIND_NARRATIVE, "1", AgentAction.BEGIN);
	Token DT_MIND_NARRATIVE_ACTION_PROCESS = Token.getToken(Units.MIND_NARRATIVE, "2", AgentAction.PROCESS);
	Token DT_MIND_NARRATIVE_ACTION_END = Token.getToken(Units.MIND_NARRATIVE, "3", AgentAction.END);
	Token DT_MIND_NARRATIVE_ACTION_RELEASE = Token.getToken(Units.MIND_NARRATIVE, "4", AgentAction.RELEASE);

	enum AgentResult {
		UNKNOWN, REJECT, READ, ACCEPT_READ, ACCEPT_PASS, ACCEPT
	}
	
	Token DT_MIND_NARRATIVE_RESULT_UNKNOWN = Token.getToken(Units.MIND_NARRATIVE, "10", AgentResult.UNKNOWN);
	Token DT_MIND_NARRATIVE_RESULT_REJECT = Token.getToken(Units.MIND_NARRATIVE, "11", AgentResult.REJECT);
	Token DT_MIND_NARRATIVE_RESULT_READ = Token.getToken(Units.MIND_NARRATIVE, "12", AgentResult.READ);
	Token DT_MIND_NARRATIVE_RESULT_ACCEPT_READ = Token.getToken(Units.MIND_NARRATIVE, "13", AgentResult.ACCEPT_READ);
	Token DT_MIND_NARRATIVE_RESULT_ACCEPT_PASS = Token.getToken(Units.MIND_NARRATIVE, "14", AgentResult.ACCEPT_PASS);
	Token DT_MIND_NARRATIVE_RESULT_ACCEPT = Token.getToken(Units.MIND_NARRATIVE, "15", AgentResult.ACCEPT);

	
	enum ValType {
		INT, REAL, REF, RAW
	}
	
	Token DT_MIND_IDEA_VALTYPE_INT = Token.getToken(Units.MIND_IDEA, "0", ValType.INT);
	Token DT_MIND_IDEA_VALTYPE_REAL = Token.getToken(Units.MIND_IDEA, "1", ValType.REAL);
	Token DT_MIND_IDEA_VALTYPE_REF = Token.getToken(Units.MIND_IDEA, "2", ValType.REF);
	Token DT_MIND_IDEA_VALTYPE_RAW = Token.getToken(Units.MIND_IDEA, "3", ValType.RAW);

	enum CollType {
		ONE, ARR, SET, MAP
	}

	Token DT_MIND_IDEA_COLLTYPE_ONE = Token.getToken(Units.MIND_IDEA, "10", CollType.ONE);
	Token DT_MIND_IDEA_COLLTYPE_ARR = Token.getToken(Units.MIND_IDEA, "11", CollType.ARR);
	Token DT_MIND_IDEA_COLLTYPE_SET = Token.getToken(Units.MIND_IDEA, "12", CollType.SET);
	Token DT_MIND_IDEA_COLLTYPE_MAP = Token.getToken(Units.MIND_IDEA, "13", CollType.MAP);

	
	enum DataCmd {
		CHK, GET, SET, ADD, DEL, VISIT
	};

	Token DT_MIND_DIALOG_DATACMD_CHK = Token.getToken(Units.MIND_DIALOG, "0", DataCmd.CHK);
	Token DT_MIND_DIALOG_DATACMD_GET = Token.getToken(Units.MIND_DIALOG, "1", DataCmd.GET);
	Token DT_MIND_DIALOG_DATACMD_SET = Token.getToken(Units.MIND_DIALOG, "2", DataCmd.SET);
	Token DT_MIND_DIALOG_DATACMD_ADD = Token.getToken(Units.MIND_DIALOG, "3", DataCmd.ADD);
	Token DT_MIND_DIALOG_DATACMD_DEL = Token.getToken(Units.MIND_DIALOG, "4", DataCmd.DEL);
	Token DT_MIND_DIALOG_DATACMD_VISIT = Token.getToken(Units.MIND_DIALOG, "5", DataCmd.VISIT);


	enum EventLevel {
		CRITICAL, ERROR, WARNING, INFO, TRACE, DEBUG
	};

	Token DT_UTIL_EVENT_CRITICAL = Token.getToken(Units.UTIL_EVENT, "0", EventLevel.CRITICAL);
	Token DT_UTIL_EVENT_ERROR = Token.getToken(Units.UTIL_EVENT, "1", EventLevel.ERROR);
	Token DT_UTIL_EVENT_WARNING = Token.getToken(Units.UTIL_EVENT, "2", EventLevel.WARNING);
	Token DT_UTIL_EVENT_INFO = Token.getToken(Units.UTIL_EVENT, "3", EventLevel.INFO);
	Token DT_UTIL_EVENT_TRACE = Token.getToken(Units.UTIL_EVENT, "4", EventLevel.TRACE);
	Token DT_UTIL_EVENT_DEBUG = Token.getToken(Units.UTIL_EVENT, "5", EventLevel.DEBUG);

	
	Token DT_UTIL_TEXT_LANG = Token.getToken(Units.UTIL_TEXT, "0");

	Token DT_UTIL_TEXT_PLAIN = Token.getToken(Units.UTIL_TEXT, "0");
	Token DT_UTIL_TEXT_PLAIN_LANG = Token.getToken(Units.UTIL_TEXT, "0");

}
