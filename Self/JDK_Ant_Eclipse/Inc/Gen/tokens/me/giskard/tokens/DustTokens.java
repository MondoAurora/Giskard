package me.giskard.tokens;

import me.giskard.Mind;

public class DustTokens implements DustTokensMind, DustTokensMachine, DustTokensGeneric {

	public static void registerNewModule() {
		Mind.selectByPath(MTMEMBER_ACTION_THIS);
		Mind.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTMEMBER_ACTION_PARAM, MTMEMBER_APPLICATION_MODULES);
	}

	public static void addModuleImpInfo(MiNDToken agent, Class<?> c) {
		Mind.log(MiNDEventLevel.TRACE, "Registering implementation for agent", agent, "->", c.getName());

		Mind.selectByPath(MTMEMBER_ACTION_PARAM);
		Mind.access(MiNDAccessCommand.Set, agent, MTMEMBER_ACTION_PARAM, MTMEMBER_IMPLEMENTATION_AGENT);
		Mind.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
		Mind.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_PARAM, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);
	}
}
