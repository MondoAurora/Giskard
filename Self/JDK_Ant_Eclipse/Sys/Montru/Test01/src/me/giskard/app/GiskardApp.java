package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMachine;

public class GiskardApp implements GiskardConsts, DustTokensGeneric, DustTokensMachine, DustTokensIO {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.addModule("DustRuntime", "1.0");

		Giskard.addModule("DustText", "1.0");
		Giskard.addModule("DustIO", "1.0");

		Giskard.selectByPath(MTMEMBER_ACTION_TARGET);
//	Giskard.access(MiNDAccessCommand.Set, MTAGENT_TEST01, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Set, 0, MTMEMBER_ACTION_TARGET, MTMEMBER_RANGE_INT_MIN);
		Giskard.access(MiNDAccessCommand.Set, 10, MTMEMBER_ACTION_TARGET, MTMEMBER_RANGE_INT_MAX);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_CTRL_ITERATION, MTMEMBER_ACTION_TARGET, MTMEMBER_ENTITY_PRIMARYTYPE);

		return MiNDResultType.ACCEPT;
	}

}
