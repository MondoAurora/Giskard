package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;

public class GiskardApp implements GiskardConsts, DustTokensMind, DustTokensMachine, DustTokensIO {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.addModule("DustRuntime", "1.0");

		Giskard.addModule("DustText", "1.0");
		Giskard.addModule("DustIO", "1.0");

		Giskard.selectByPath(MTMEMBER_ACTION_LOCAL);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_TEST01, MTMEMBER_ACTION_LOCAL, MTMEMBER_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_LOCAL, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP, MTMEMBER_APPLICATION_MAINAGENT);

		return MiNDResultType.ACCEPT;
	}
	
}
