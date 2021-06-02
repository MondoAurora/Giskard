package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokensDev;
import me.giskard.tokens.DustTokensDevJava;
import me.giskard.tokens.DustTokensGeometry;
import me.giskard.tokens.DustTokensGuard;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensMontru;
import me.giskard.tokens.DustTokensTemp;
import me.giskard.tokens.DustTokensText;
import me.giskard.tools.GisToolsTokenTranslator;

public class GiskardApp implements DustTokensMind, DustTokensMachine, DustTokensIO, DustTokensGuard,
		DustTokensDB, DustTokensText, DustTokensGeometry, DustTokensGui, DustTokensMontru, DustTokensDev, DustTokensDevJava, DustTokensTemp {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.initRuntime("DustRuntime", "1.0");

		DustTokens.addModule("DustText", "1.0");
		DustTokens.addModule("DustIO", "1.0");
		DustTokens.addModule("DustDB", "1.0");
		DustTokens.addModule("DustTools", "1.0");
		DustTokens.addModule("DustGuiSwing", "1.0");
		DustTokens.addModule("DustDevJava", "1.0");
		DustTokens.addModule("DustMontru", "1.0");
		
		String testId = (args.length > 0) ? args[0] : "src";
		Object hTest = null;

		switch ( testId ) {
		case "src":
			hTest = testSrc();
			break;
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

			Object hDlg = Giskard.access(MiNDAccessCommand.Get, MTTYP_DIALOG_CONTEXT);
			Giskard.access(MiNDAccessCommand.Add, hTest, hDlg, MTMEM_DIALOG_CONTEXT_ACTIVITIES);
			Giskard.access(MiNDAccessCommand.Add, hDlg, MTLOC_MACHINE_THEMACHINE, MTMEM_MACHINE_MACHINE_DIALOGS);

			return MiNDResultType.Accept;
		}
	}

	static Object testSrc() {
		
		Giskard.log(MiNDEventLevel.Info, "Loading not referred units", MTUNI_DEVJAVA, MTUNI_GEOMETRY, MTUNI_GUARD, MTUNI_TEMP);
		
		Object hList = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_DATALISTALL);
		Object hDevTokens = Giskard.access(MiNDAccessCommand.Get, MTAGN_DEV_GENTOKENS);

		Object hS1 = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);		
		Giskard.access(MiNDAccessCommand.Add, hList, hS1, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hDevTokens, hS1, MTMEM_GENERIC_LINK_ARR);

		
		Object hIter = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLITERATION);
		Giskard.access(MiNDAccessCommand.Set, 500, hIter, MTMEM_GENERIC_RANGE_INTMAX);
		Giskard.access(MiNDAccessCommand.Set, hS1, hIter, MTMEM_GENERIC_LINK_ONE);
		
		Object hTop = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);
		Giskard.access(MiNDAccessCommand.Add, hIter, hTop, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hDevTokens, hTop, MTMEM_GENERIC_LINK_ARR);
		
		return hTop;
	}
	
	static Object testVisit() {
		Object hDBConn = testDB();

		Object hTop = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);

		Object hMain = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLITERATION);

		Object hS1 = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);
		Object hList = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_DATALISTALL);
		Object hIter = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLITERATION);
		Object hS2 = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);
		Object hVisit = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_DATAVISIT);
		
		Giskard.access(MiNDAccessCommand.Add, hVisit, hS2, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hDBConn, hS2, MTMEM_GENERIC_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Set, hS2, hIter, MTMEM_GENERIC_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Add, hList, hS1, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hIter, hS1, MTMEM_GENERIC_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Set, hS1, hMain, MTMEM_GENERIC_LINK_ONE);
		
		Giskard.access(MiNDAccessCommand.Set, 500, hIter, MTMEM_GENERIC_RANGE_INTMAX);
		Giskard.access(MiNDAccessCommand.Set, 500, hMain, MTMEM_GENERIC_RANGE_INTMAX);
		
		
		Giskard.access(MiNDAccessCommand.Add, hMain, hTop, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, hDBConn, hTop, MTMEM_GENERIC_LINK_ARR);
		
		return hTop;
	}
	
	public static Object setCoords(Integer... coords) {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYP_GEOMETRY_GEODATA);

		for (Integer i : coords) {
			Giskard.access(MiNDAccessCommand.Add, i, hRet, MTMEM_GEOMETRY_GEODATA_COORDS);
		}
		
		return hRet;
	}

	public static Object testGui2() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_FRAME);
//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTION_THIS);
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_FRAME, MTMEM_GENERIC_ACTION_THIS, MTMEM_MODEL_ENTITY_PRIMARYTYPE);

//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTION_GPR01);
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_MAINPANEL, MTMEM_GENERIC_ACTION_GPR01, MTMEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEM_GENERIC_ACTION_GPR01, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Set, "Montru", hRet, MTMEM_TEXT_PLAINTEXT_STRING);

		Giskard.access(MiNDAccessCommand.Set, setCoords(600, 400), hRet, MTMEM_GEOMETRY_AREA_CENTER);
		Giskard.access(MiNDAccessCommand.Set, setCoords(1000, 600), hRet, MTMEM_GEOMETRY_AREA_SPAN);

		Object hGui = Giskard.access(MiNDAccessCommand.Get, null, MTUNI_MONTRU, MTMEM_MODEL_UNIT_SERVICES, MTSVC_MONTRU_GUIMAIN);
		Giskard.access(MiNDAccessCommand.Set, hGui, hRet, MTMEM_GENERIC_LINK_ONE);

//		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_ACTION_THIS, MTLOC_MACHINE, MTMEM_MACHINE_DIALOGS);

//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTIVITY_INSTANCE, MTMEM_GENERIC_ACTION_THIS);
		return hRet;
	}

//	public static void testGui() throws Exception {
//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTION_THIS);
//		Giskard.access(MiNDAccessCommand.Set, MTTYP_WINDOW, MTMEM_GENERIC_ACTION_THIS, MTMEM_MODEL_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTION_GPR01);
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_MAINPANEL, MTMEM_GENERIC_ACTION_GPR01, MTMEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEM_GENERIC_ACTION_GPR01, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ONE);
//
//		Giskard.access(MiNDAccessCommand.Set, "Montru", MTMEM_GENERIC_ACTION_THIS, MTMEM_TEXT_PLAINTEXT_STRING);
//
//		setCoords(MTMEM_GENERIC_ACTION_GPR01, 600, 400);
//		Giskard.access(MiNDAccessCommand.Set, MTMEM_GENERIC_ACTION_GPR01, MTMEM_GENERIC_ACTION_THIS, MTMEM_AREA_CENTER);
//
//		setCoords(MTMEM_GENERIC_ACTION_GPR01, 1000, 600);
//		Giskard.access(MiNDAccessCommand.Set, MTMEM_GENERIC_ACTION_GPR01, MTMEM_GENERIC_ACTION_THIS, MTMEM_AREA_SPAN);
//
//		Giskard.access(MiNDAccessCommand.Get, MTMEM_GENERIC_ACTIVITY_INSTANCE, MTLOC_MACHINE, MTMEM_GUIOWNER_PLATFORMS,
//				MTTAG_PLATFORM_SWING, MTMEM_PLATFORM_MANAGER);
////		Giskard.access(MiNDAccessCommand.Get, MTMEM_CALL_PARAM, MTMEM_GENERIC_ACTION_THIS);
//	}

	public static Object testDB() {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYP_DB_CONNECTION);

		Giskard.access(MiNDAccessCommand.Set, "com.mysql.cj.jdbc.Driver", hRet, MTMEM_DB_CONNECTION_DRIVER);
		Giskard.access(MiNDAccessCommand.Set, "jdbc:mysql://localhost:3306", hRet, MTMEM_IO_STREAM_URL);
		Giskard.access(MiNDAccessCommand.Set, "dust", hRet, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Set, "serverTimezone=CET", hRet, MTMEM_DB_CONNECTION_OPTIONS);

//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEM_CALL_TARGET, MTMEM_ACCOUNTID);
//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEM_CALL_TARGET, MTMEM_PASSWORD);
//    The actual values are in this class, which is of course, on gitignore...
		
		GiskardPrivate.setDBCredentials(hRet);
		
		Object hAgent = Giskard.access(MiNDAccessCommand.Get, MTAGN_DB_TEST01);
		Giskard.access(MiNDAccessCommand.Set, hRet, hAgent, MTMEM_DB_USER_CONN);
		
		return hAgent;
	}

	public static Object testIO() {
		return Giskard.access(MiNDAccessCommand.Get, MTAGN_IO_TEST01);
	}

	public static Object testIterator() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLITERATION);

		Giskard.access(MiNDAccessCommand.Set, 0, hRet, MTMEM_GENERIC_RANGE_INTMIN);
		Giskard.access(MiNDAccessCommand.Set, 10, hRet, MTMEM_GENERIC_RANGE_INTMAX);

		linkDump(hRet, "Hey, world!", MTMEM_GENERIC_LINK_ONE);
		
		return hRet;
	}

	public static Object testSequence() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_NARRATIVE_CTRL_SEQUENCE, MTMEM_GENERIC_ACTIVITY_INSTANCE, MTMEM_MODEL_ENTITY_PRIMARYTYPE);

		linkDump(hRet, "one", MiNDResultType.Accept);
		linkDump(hRet, "two", MiNDResultType.Accept);
		linkDump(hRet, "not this", MiNDResultType.Reject);
		linkDump(hRet, "three", MiNDResultType.Accept);
		
		return hRet;
	}

	public static Object testSelect() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSELECTION);
		
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_NARRATIVE_CTRL_SELECTION, MTMEM_GENERIC_ACTIVITY_INSTANCE, MTMEM_MODEL_ENTITY_PRIMARYTYPE);

		linkDump(hRet, "not this 1", MiNDResultType.Reject);
		linkDump(hRet, "not this 2", MiNDResultType.Reject);
		linkDump(hRet, "this", MiNDResultType.Accept);
		linkDump(hRet, "not this 4", MiNDResultType.Reject);
		
		return hRet;
	}

	public static Object testCtrl() throws Exception {
		Object hRet = Giskard.access(MiNDAccessCommand.Get, MTAGN_NARRATIVE_CTRLSEQUENCE);

		linkDump(hRet, "Testing Iterator", MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testIterator(), hRet, MTMEM_GENERIC_LINK_ARR);

		linkDump(hRet, "Testing Selection", MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testSelect(), hRet, MTMEM_GENERIC_LINK_ARR);

		linkDump(hRet, "Testing Sequence", MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, testSequence(), hRet, MTMEM_GENERIC_LINK_ARR);

		return hRet;
	}

	public static Object linkDump(Object target, String msg, MiNDResultType res) throws Exception {
		Object hDump = linkDump(target, msg, MTMEM_GENERIC_LINK_ARR);
		if ( null != res ) {
			Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(res), hDump,
					MTMEM_MODEL_ENTITY_TAGS);
		}
		return hDump;
	}

	public static Object linkDump(Object target, String msg, MiNDToken link) throws Exception {
		Object hDump = Giskard.access(MiNDAccessCommand.Get, MTAGN_GENERIC_DUMP);
		Giskard.access(MiNDAccessCommand.Set, msg, hDump, MTMEM_GENERIC_VALUE_RAW);
//		Giskard.access(MiNDAccessCommand.Set, MTAGN_DUMP, MTMEM_GENERIC_ACTION_GPR01, MTMEM_MODEL_ENTITY_PRIMARYTYPE);

		Giskard.access((MTMEM_GENERIC_LINK_ONE == link) ? MiNDAccessCommand.Set : MiNDAccessCommand.Add, hDump,
				target, link);
		
		return hDump;
	}

}
