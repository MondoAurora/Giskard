package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGuard;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensText;
import me.giskard.tools.GisToolsTokenTranslator;

public class GiskardApp implements GiskardConsts, DustTokensGeneric, DustTokensMachine, DustTokensIO, DustTokensGuard, DustTokensDB, DustTokensText {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.addModule("DustRuntime", "1.0");

		Giskard.addModule("DustText", "1.0");
		Giskard.addModule("DustIO", "1.0");
		Giskard.addModule("DustDB", "1.0");
		Giskard.addModule("DustTools", "1.0");

		Giskard.selectByPath(MTMEMBER_ACTION_TARGET);

//		testIterator();
//		testSequence();
//		testSelect();

		testDB();
		
		return MiNDResultType.ACCEPT;
	}

	public static void testDB() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DBTEST01, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Set, "com.mysql.jdbc.Driver", MTMEMBER_ACTION_TARGET, MTMEMBER_DRIVER);
		Giskard.access(MiNDAccessCommand.Set, "jdbc:mysql://localhost:3306", MTMEMBER_ACTION_TARGET, MTMEMBER_URL);
		Giskard.access(MiNDAccessCommand.Set, "dust", MTMEMBER_ACTION_TARGET, MTMEMBER_STRING);
		
		GiskardPrivate.setDB();
	}

	public static void testIO() {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_TEST01, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);
	}

	public static void testIterator() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Set, 0, MTMEMBER_ACTION_TARGET, MTMEMBER_RANGE_INT_MIN);
		Giskard.access(MiNDAccessCommand.Set, 10, MTMEMBER_ACTION_TARGET, MTMEMBER_RANGE_INT_MAX);

		linkDump("Hey, world!", MTMEMBER_LINK_ONE);
	}

	public static void testSequence() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SEQUENCE, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("one", MiNDResultType.ACCEPT);
	linkDump("not this", MiNDResultType.REJECT);
		linkDump("two", MiNDResultType.ACCEPT);
		linkDump("three", MiNDResultType.ACCEPT);
	}

	public static void testSelect() throws Exception {
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_SELECTION, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		linkDump("not this 1", MiNDResultType.REJECT);
		linkDump("not this 2", MiNDResultType.REJECT);
		linkDump("this", MiNDResultType.ACCEPT);
		linkDump("not this 4", MiNDResultType.REJECT);
	}

	public static void linkDump(String msg, MiNDResultType res) throws Exception {
		linkDump(msg, MTMEMBER_LINK_ARR);
		if ( null != res ) {
			Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(res), MTMEMBER_ACTION_PARAM);
		}
	}

	public static void linkDump(String msg, MiNDToken link) throws Exception {
		Giskard.selectByPath(MTMEMBER_ACTION_PARAM);
		Giskard.access(MiNDAccessCommand.Set, msg, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_DUMP, MTMEMBER_ACTION_PARAM, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access((MTMEMBER_LINK_ONE == link) ? MiNDAccessCommand.Set : MiNDAccessCommand.Add, MTMEMBER_ACTION_PARAM,
				MTMEMBER_ACTION_TARGET, link);
	}

}
