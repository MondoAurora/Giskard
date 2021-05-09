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

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET);

		String testId = (args.length > 0) ? args[0] : "visit";

		switch ( testId ) {
		case "visit":
			testVisit();
			break;
		case "io":
			testIO();
			break;
		case "db":
			testDB();
			break;
		case "rep":
			testIterator();
			break;
		case "seq":
			testSequence();
			break;
		case "sel":
			testSelect();
			break;
		case "ctrl":
			testCtrl();
			break;
		case "gui":
			testGui();
			break;
		case "gui2":
			testGui2();
			break;
		default:
			testId = "UNKNOWN";
			break;
		}

		Giskard.log(MiNDEventLevel.Info, "Starting test", testId);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_DIALOG, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_GPR01, MTMEMBER_DIALOG_ACTIVITIES);

		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTSHARED_MACHINE, MTMEMBER_MACHINE_DIALOGS);

		return MiNDResultType.Accept;
	}

	static void testVisitNew() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		// TODO sequence instead of containing!
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_LISTALL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR03);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_VISIT, MTMEMBER_ACTION_GPR03, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access( MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR03, MTMEMBER_ACTION_GPR02, MTMEMBER_LINK_ONE);
		Giskard.access( MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR01, MTMEMBER_LINK_ONE);
		Giskard.access( MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_CALL_TARGET, MTMEMBER_LINK_ONE);
	}

	static void testVisit() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_LISTALL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DATA_VISIT, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "pompompom", MTMEMBER_ACTION_GPR02, MTMEMBER_VALUE_RAW);
		Giskard.access( MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR01, MTMEMBER_LINK_ONE);
		
//		Giskard.access(MiNDAccessCommand.Set, 0, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MIN);
//		Giskard.access(MiNDAccessCommand.Set, 10, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MAX);


		Giskard.access( MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_CALL_TARGET, MTMEMBER_LINK_ONE);
	}

	public static void setCoords(MiNDToken target, Integer... coords) {
		Giskard.access(MiNDAccessCommand.Get, target);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_GEODATA, target, MTMEMBER_ENTITY_PRIMARYTYPE);

		for (Integer i : coords) {
			Giskard.access(MiNDAccessCommand.Add, i, target, MTMEMBER_GEODATA_COORDS);
		}
	}

	public static void testGui2() throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_FRAME, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);

//		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
//		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MAINPANEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Set, "Montru", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);

		setCoords(MTMEMBER_ACTION_GPR01, 600, 400);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_CENTER);

		setCoords(MTMEMBER_ACTION_GPR01, 1000, 600);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_SPAN);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTUNIT_MONTRU, MTMEMBER_UNIT_SERVICES, MTSERVICE_GUI);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);

//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_MACHINE_DIALOGS);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS);
	}

	public static void testGui() throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_WINDOW, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MAINPANEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Set, "Montru", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);

		setCoords(MTMEMBER_ACTION_GPR01, 600, 400);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_CENTER);

		setCoords(MTMEMBER_ACTION_GPR01, 1000, 600);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_SPAN);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_PLATFORMS,
				MTTAG_PLATFORM_SWING, MTMEMBER_PLATFORM_MANAGER);
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_PARAM, MTMEMBER_ACTION_THIS);
	}

	public static void testDB() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DBTEST01, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Set, "com.mysql.cj.jdbc.Driver", MTMEMBER_CALL_TARGET, MTMEMBER_DRIVER);
		Giskard.access(MiNDAccessCommand.Set, "jdbc:mysql://localhost:3306", MTMEMBER_CALL_TARGET, MTMEMBER_URL);
		Giskard.access(MiNDAccessCommand.Set, "dust", MTMEMBER_CALL_TARGET, MTMEMBER_PLAIN_STRING);

//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEMBER_CALL_TARGET, MTMEMBER_ACCOUNTID);
//		Giskard.access(MiNDAccessCommand.Set, ":-)", MTMEMBER_CALL_TARGET, MTMEMBER_PASSWORD);
//    The actual values are in this class, which is of course, on gitignore...
		GiskardPrivate.setDB();
	}

	public static void testIO() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_TEST01, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);
	}

	public static void testIterator() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Set, 0, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MIN);
		Giskard.access(MiNDAccessCommand.Set, 10, MTMEMBER_CALL_TARGET, MTMEMBER_RANGE_INT_MAX);

		linkDump("Hey, world!", MTMEMBER_LINK_ONE);
	}

	public static void testCtrl() throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS, MTMEMBER_CALL_TARGET);

		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SEQUENCE, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		linkDump("Testing Iterator", MTMEMBER_LINK_ARR, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET);
		testIterator();
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
		
		linkDump("Testing Selection", MTMEMBER_LINK_ARR, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET);
		testSelect();
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);

		linkDump("Testing Sequence", MTMEMBER_LINK_ARR, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET);
		testSequence();
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS);

	}

	public static void testSequence() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SEQUENCE, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("one", MiNDResultType.Accept);
		linkDump("two", MiNDResultType.Accept);
		linkDump("not this", MiNDResultType.Reject);
		linkDump("three", MiNDResultType.Accept);
	}

	public static void testSelect() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SELECTION, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("not this 1", MiNDResultType.Reject);
		linkDump("not this 2", MiNDResultType.Reject);
		linkDump("this", MiNDResultType.Accept);
		linkDump("not this 4", MiNDResultType.Reject);
	}

	public static void linkDump(String msg, MiNDResultType res) throws Exception {
		linkDump(msg, MTMEMBER_LINK_ARR);
		if ( null != res ) {
			Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(res), MTMEMBER_ACTION_GPR01,
					MTMEMBER_ENTITY_TAGS);
		}
	}

	public static void linkDump(String msg, MiNDToken link) throws Exception {
		linkDump(msg, link, MTMEMBER_CALL_TARGET);
	}

	public static void linkDump(String msg, MiNDToken link, MiNDToken target) throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, msg, MTMEMBER_ACTION_GPR01, MTMEMBER_VALUE_RAW);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access((MTMEMBER_LINK_ONE == link) ? MiNDAccessCommand.Set : MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01,
				target, link);
	}

}
