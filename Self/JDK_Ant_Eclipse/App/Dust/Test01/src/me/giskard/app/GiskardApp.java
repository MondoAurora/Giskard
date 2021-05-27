package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGeometry;
import me.giskard.tokens.DustTokensGuard;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMontru;
import me.giskard.tokens.DustTokensText;
import me.giskard.tools.GisToolsTokenTranslator;

public class GiskardApp implements GiskardConsts, DustTokensGeneric, DustTokensMachine, DustTokensIO, DustTokensGuard,
		DustTokensDB, DustTokensText, DustTokensGeometry, DustTokensGui, DustTokensMontru {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.initRuntime("DustRuntime", "1.0");

		DustTokens.addModule("DustText", "1.0");
		DustTokens.addModule("DustIO", "1.0");
		DustTokens.addModule("DustDB", "1.0");
		DustTokens.addModule("DustTools", "1.0");
		DustTokens.addModule("DustGuiSwing", "1.0");
		DustTokens.addModule("DustMontru", "1.0");
		
		String testId = (args.length > 0) ? args[0] : "visit";
		Object hTest = null;

		switch ( testId ) {
		case "visit":
			hTest = testVisit();
			break;
		case "io":
			hTest = testIO();
			break;
		case "db":
			hTest = testDB();
			break;
		case "rep":
			hTest = testIterator();
			break;
		case "seq":
			hTest = testSequence();
			break;
		case "sel":
			hTest = testSelect();
			break;
		case "ctrl":
			hTest = testCtrl();
			break;
//		case "gui":
//			hTest = testGui();
//			break;
		case "gui2":
			hTest = testGui2();
			break;
		}

		if ( null == hTest ) {
			Giskard.log(MiNDEventLevel.Error, "Test not found", testId);
			return MiNDResultType.Reject;
		} else {
			Giskard.log(MiNDEventLevel.Info, "Starting test", testId);

			Object hDlg = Giskard.access(MiNDAccessCommand.Get, MTTYPE_DIALOG);
			Giskard.access(MiNDAccessCommand.Add, hTest, hDlg, MTMEMBER_DIALOG_ACTIVITIES);
			Giskard.access(MiNDAccessCommand.Add, hDlg, MTSHARED_MACHINE, MTMEMBER_MACHINE_DIALOGS);

			return MiNDResultType.Accept;
		}
	}

	static Object testVisit() {
		Object hDBConn = testDB();

		Object hTop = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SEQUENCE);

		Object hMain = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_ITERATION);

		Object hS1 = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SEQUENCE);
		Object hList = Giskard.access(MiNDAccessCommand.Get, MTAGENT_DATA_LISTALL);
		Object hIter = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_ITERATION);
		Object hS2 = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SEQUENCE);
		Object hVisit = Giskard.access(MiNDAccessCommand.Get, MTAGENT_DATA_VISIT);
//		Object hPrint = Giskard.access(MiNDAccessCommand.Get, MTAGENT_TEXT_FORMAT);
//
//		DustTokens.setupValFormatter(hPrint);
		
		Giskard.access(MiNDAccessCommand.Add, hVisit, hS2, MTMEMBER_LINK_ARR);
//		Giskard.access(MiNDAccessCommand.Add, hPrint, hS2, MTMEMBER_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Add, hDBConn, hS2, MTMEMBER_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Set, hS2, hIter, MTMEMBER_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Add, hList, hS1, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hIter, hS1, MTMEMBER_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Set, hS1, hMain, MTMEMBER_LINK_ONE);
		
		Giskard.access(MiNDAccessCommand.Set, 500, hIter, MTMEMBER_RANGE_INT_MAX);
		Giskard.access(MiNDAccessCommand.Set, 500, hMain, MTMEMBER_RANGE_INT_MAX);
		
		
		Giskard.access(MiNDAccessCommand.Add, hMain, hTop, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hDBConn, hTop, MTMEMBER_LINK_ARR);

		
		return hTop;
	}
	
//	static void testVisitTest() {
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ENTITY_PRIMARYTYPE);
//
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR04, MTAGENT_CTRL_SEQUENCE);
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR05, MTAGENT_DATA_VISIT);
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR06, MTAGENT_TEXT_FORMAT);
//
//		DustTokens.setupValFormatter(MTMEMBER_ACTION_GPR06);
//		
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR05, MTMEMBER_ACTION_GPR05, MTMEMBER_LINK_ONE);
//		
//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR05, MTMEMBER_ACTION_GPR04, MTMEMBER_LINK_ARR);
//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR06, MTMEMBER_ACTION_GPR04, MTMEMBER_LINK_ARR);
//
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR04, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_LINK_ONE);
//	}
//
//	static void testVisit1() {
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ENTITY_PRIMARYTYPE);
//
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR01, MTAGENT_CTRL_SEQUENCE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_LINK_ONE);
//
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR02, MTAGENT_DATA_LISTALL);
//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR01, MTMEMBER_LINK_ARR);
//
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR03, MTAGENT_CTRL_ITERATION);
//		DustTokens.createAgent(MTMEMBER_ACTION_GPR04, MTAGENT_DATA_VISIT);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR04, MTMEMBER_ACTION_GPR03, MTMEMBER_LINK_ONE);
//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR03, MTMEMBER_ACTION_GPR01, MTMEMBER_LINK_ARR);
//	}
//
//	static void testVisit2() {
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
//
////		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_LISTALL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_VISIT, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, "pompompom", MTMEMBER_ACTION_GPR02, MTMEMBER_VALUE_RAW);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR01, MTMEMBER_LINK_ONE);
//
////		Giskard.access(MiNDAccessCommand.Set, 0, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MIN);
////		Giskard.access(MiNDAccessCommand.Set, 10, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MAX);
//
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_LINK_ONE);
//	}

	public static Object setCoords(Integer... coords) {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYPE_GEODATA);

		for (Integer i : coords) {
			Giskard.access(MiNDAccessCommand.Add, i, hRet, MTMEMBER_GEODATA_COORDS);
		}
		
		return hRet;
	}

	public static Object testGui2() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGENT_FRAME);
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_FRAME, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);

//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MAINPANEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Set, "Montru", hRet, MTMEMBER_PLAIN_STRING);

		Giskard.access(MiNDAccessCommand.Set, setCoords(600, 400), hRet, MTMEMBER_AREA_CENTER);
		Giskard.access(MiNDAccessCommand.Set, setCoords(1000, 600), hRet, MTMEMBER_AREA_SPAN);

		Object hGui = Giskard.access(MiNDAccessCommand.Get, null, MTUNIT_MONTRU, MTMEMBER_UNIT_SERVICES, MTSERVICE_GUI);
		Giskard.access(MiNDAccessCommand.Set, hGui, hRet, MTMEMBER_LINK_ONE);

//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_MACHINE_DIALOGS);

//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ACTION_THIS);
		return hRet;
	}

//	public static void testGui() throws Exception {
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
//		Giskard.access(MiNDAccessCommand.Set, MTTYPE_WINDOW, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MAINPANEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
//
//		Giskard.access(MiNDAccessCommand.Set, "Montru", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
//
//		setCoords(MTMEMBER_ACTION_GPR01, 600, 400);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_CENTER);
//
//		setCoords(MTMEMBER_ACTION_GPR01, 1000, 600);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_SPAN);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTIVITY_INSTANCE, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_PLATFORMS,
//				MTTAG_PLATFORM_SWING, MTMEMBER_PLATFORM_MANAGER);
////		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_PARAM, MTMEMBER_ACTION_THIS);
//	}

	public static Object testDB() {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYPE_DBCONN);

		Giskard.access(MiNDAccessCommand.Set, "com.mysql.cj.jdbc.Driver", hRet, MTMEMBER_DRIVER);
		Giskard.access(MiNDAccessCommand.Set, "jdbc:mysql://localhost:3306", hRet, MTMEMBER_URL);
		Giskard.access(MiNDAccessCommand.Set, "dust", hRet, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Set, "serverTimezone=CET", hRet, MTMEMBER_DBCONN_OPTIONS);

//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEMBER_CALL_TARGET, MTMEMBER_ACCOUNTID);
//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEMBER_CALL_TARGET, MTMEMBER_PASSWORD);
//    The actual values are in this class, which is of course, on gitignore...
		
		GiskardPrivate.setDBCredentials(hRet);
		
		Object hAgent = Giskard.access(MiNDAccessCommand.Get, MTAGENT_DBTEST01);
		Giskard.access(MiNDAccessCommand.Set, hRet, hAgent, MTMEMBER_DBUSER_CONN);
		
		return hAgent;
	}

	public static Object testIO() {
		return Giskard.access(MiNDAccessCommand.Get, MTAGENT_TEST01);
	}

	public static Object testIterator() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_ITERATION);

		Giskard.access(MiNDAccessCommand.Set, 0, hRet, MTMEMBER_RANGE_INT_MIN);
		Giskard.access(MiNDAccessCommand.Set, 10, hRet, MTMEMBER_RANGE_INT_MAX);

		linkDump(hRet, "Hey, world!", MTMEMBER_LINK_ONE);
		
		return hRet;
	}

	public static Object testSequence() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SEQUENCE);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SEQUENCE, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump(hRet, "one", MiNDResultType.Accept);
		linkDump(hRet, "two", MiNDResultType.Accept);
		linkDump(hRet, "not this", MiNDResultType.Reject);
		linkDump(hRet, "three", MiNDResultType.Accept);
		
		return hRet;
	}

	public static Object testSelect() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SELECTION);
		
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SELECTION, MTMEMBER_ACTIVITY_INSTANCE, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump(hRet, "not this 1", MiNDResultType.Reject);
		linkDump(hRet, "not this 2", MiNDResultType.Reject);
		linkDump(hRet, "this", MiNDResultType.Accept);
		linkDump(hRet, "not this 4", MiNDResultType.Reject);
		
		return hRet;
	}

	public static Object testCtrl() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGENT_CTRL_SEQUENCE);

		linkDump(hRet, "Testing Iterator", MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testIterator(), hRet, MTMEMBER_LINK_ARR);

		linkDump(hRet, "Testing Selection", MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testSelect(), hRet, MTMEMBER_LINK_ARR);

		linkDump(hRet, "Testing Sequence", MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testSequence(), hRet, MTMEMBER_LINK_ARR);

		return hRet;
	}

	public static Object linkDump(Object target, String msg, MiNDResultType res) throws Exception {
		Object hDump = linkDump(target, msg, MTMEMBER_LINK_ARR);
		if ( null != res ) {
			Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(res), hDump,
					MTMEMBER_ENTITY_TAGS);
		}
		return hDump;
	}

	public static Object linkDump(Object target, String msg, MiNDToken link) throws Exception {
		Object hDump = Giskard.access(MiNDAccessCommand.Get, MTAGENT_DUMP);
		Giskard.access(MiNDAccessCommand.Set, msg, hDump, MTMEMBER_VALUE_RAW);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access((MTMEMBER_LINK_ONE == link) ? MiNDAccessCommand.Set : MiNDAccessCommand.Add, hDump,
				target, link);
		
		return hDump;
	}

}
