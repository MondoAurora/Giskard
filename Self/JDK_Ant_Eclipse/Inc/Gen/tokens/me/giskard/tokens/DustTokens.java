package me.giskard.tokens;

import me.giskard.Giskard;

public class DustTokens implements DustTokensMind, DustTokensMachine, DustTokensGeneric {

	public static void registerNewModule() throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTMEMBER_ACTION_PARAM, MTMEMBER_APPLICATION_MODULES);
	}

	public static void addModuleImpInfo(MiNDToken agent, Class<?> c) throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "Registering implementation for agent", agent, "->", c.getName());

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_TEMP01);
		Giskard.access(MiNDAccessCommand.Set, agent, MTMEMBER_ACTION_TEMP01, MTMEMBER_IMPLEMENTATION_AGENT);
		Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_TEMP01, MTMEMBER_VARIANT_VALUE);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);
	}
}
