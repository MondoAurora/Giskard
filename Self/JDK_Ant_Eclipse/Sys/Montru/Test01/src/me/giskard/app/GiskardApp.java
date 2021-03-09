package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class GiskardApp implements GiskardConsts {

	public static final MiNDResultType boot(String[] args) throws Exception {
		Giskard.init("DustRuntime", "1.0");

		Giskard.addModule("DustText", "1.0");
		Giskard.addModule("DustIO", "1.0");

//		machine.launch();
//		
//		MiNDAgent a = machine.testCreateAgent(MTAGENT_MATCHCONST);
//		a.process(MiNDAgentAction.Init);
		
		return MiNDResultType.ACCEPT;
	}
	
}
