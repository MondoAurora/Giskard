package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.boot.DustBootGiskard;

public class DustModule implements GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(null, this.getClass().getName(), "module initializing");
		
		new DustBootGiskard();
		
		return MiNDResultType.Accept;
	}

}
