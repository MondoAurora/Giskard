package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class GiskardAppAgent implements GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		Giskard.log(null, this.getClass().getName(), "App initializing");	
				
		GiskardAppModuleServices.loadModule("Montru", "Brain", "1.0");
		GiskardAppModuleServices.loadModule("Montru", "Forge", "1.0");
		GiskardAppModuleServices.loadModule("Montru", "Test01", "1.0");

		DustBrainTest.initTest();		

		return MiNDResultType.Accept;
	}

}
