package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.db.DustDBConsts;

public class DustDBTest01 implements DustDBConsts, GiskardConsts.MiNDAgent {
	
	void runTest() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "Running DB test...");
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT_PASS;
		
		switch ( action ) {
		case Process:
			runTest();
			ret = MiNDResultType.ACCEPT;
			break;

		default:
			break;
		}
		
		return ret;
	}

}
