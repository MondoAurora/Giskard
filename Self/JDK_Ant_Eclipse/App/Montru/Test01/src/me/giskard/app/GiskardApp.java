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
		DustTokens.addModule("MontruGui", "1.0");

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET);
		
		String testId = (args.length > 0) ? args[0] : "gui2";
		
		switch ( testId ) {
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
		case "gui":
			testGui();
			break;
		case "gui2":
			testGui2();
			break;
		}

		return MiNDResultType.ACCEPT;
	}
	
	public static void setCoords(MiNDToken target, Integer... coords) {
		Giskard.access(MiNDAccessCommand.Get, target);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_GEODATA, target, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		for ( Integer i : coords ) {
			Giskard.access(MiNDAccessCommand.Add, i, target, MTMEMBER_GEODATA_COORDS);
		}
	}
	

	public static void testGui2() throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_FRAME, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MAINPANEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);

		Giskard.access(MiNDAccessCommand.Set, "Montru", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);

		setCoords(MTMEMBER_ACTION_GPR01, 600, 400);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_CENTER);

		setCoords(MTMEMBER_ACTION_GPR01, 1000, 600);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_AREA_SPAN);

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

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_WORLD,
				MTMEMBER_WORLD_RENDERER);
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

	public static void testSequence() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SEQUENCE, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("one", MiNDResultType.ACCEPT);
		linkDump("two", MiNDResultType.ACCEPT);
		linkDump("not this", MiNDResultType.REJECT);
		linkDump("three", MiNDResultType.ACCEPT);
	}

	public static void testSelect() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SELECTION, MTMEMBER_CALL_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("not this 1", MiNDResultType.REJECT);
		linkDump("not this 2", MiNDResultType.REJECT);
		linkDump("this", MiNDResultType.ACCEPT);
		linkDump("not this 4", MiNDResultType.REJECT);
	}

	public static void linkDump(String msg, MiNDResultType res) throws Exception {
		linkDump(msg, MTMEMBER_LINK_ARR);
		if ( null != res ) {
			Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(res), MTMEMBER_ACTION_GPR01,
					MTMEMBER_ENTITY_TAGS);
		}
	}

	public static void linkDump(String msg, MiNDToken link) throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, msg, MTMEMBER_ACTION_GPR01, MTMEMBER_VARIANT_VALUE);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access((MTMEMBER_LINK_ONE == link) ? MiNDAccessCommand.Set : MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01,
				MTMEMBER_CALL_TARGET, link);
	}

}
