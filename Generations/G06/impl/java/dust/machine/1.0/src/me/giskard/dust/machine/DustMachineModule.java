package me.giskard.dust.machine;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public class DustMachineModule implements DustConsts {
	
	public static Object dustModuleInit() throws Exception {
		Dust.log(null, "Hello world from Machine module");
		
		return DustMachineBoot.bootMachine();
	}
	
}
