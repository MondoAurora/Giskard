package me.giskard.tokens;

import me.giskard.Giskard;

public class DustTokens implements DustTokensMind, DustTokensMachine, DustTokensGeneric, DustTokensText {

	public static void addModule(String mod, String ver) throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_MODULE, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, mod, MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Set, ver, MTMEMBER_ACTION_THIS, MTMEMBER_VERSIONED_SIGNATURE);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_MACHINE_MODULES);
	}

	public static void addModuleImpInfo(MiNDToken agent, Class<?> c) throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Registering implementation for agent", agent, "->", c.getName());

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_GPR01, MTMEMBER_VALUE_RAW);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_MODULE_NATIVES, agent);
	}
}
