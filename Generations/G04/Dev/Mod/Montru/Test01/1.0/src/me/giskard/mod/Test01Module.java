package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class Test01Module implements GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		Giskard.log(null, this.getClass().getName(), "module initializing");
		
		return MiNDResultType.Accept;
	}

}
