package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class GiskardAppAgent implements GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		Giskard.log(null, this.getClass().getName(), "App initializing");	

		DustBrainTest.initTest();		

		return MiNDResultType.Accept;
	}

}
