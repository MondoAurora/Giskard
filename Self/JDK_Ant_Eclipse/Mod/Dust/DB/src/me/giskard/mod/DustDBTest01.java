package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.db.DustDBConsts;

public class DustDBTest01 implements DustDBConsts, GiskardConsts.MiNDAgent {
	
	void runTest() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Running DB test...");
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;
		
		switch ( action ) {
		case Process:
			runTest();
			break;

		default:
			break;
		}
		
		return ret;
	}

}
