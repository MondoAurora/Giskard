package me.giskard.dust.sandbox;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public class DustSandboxModule implements DustConsts {
//	public static MindMachine dustMachineInit(ArrayList<String> args) throws Exception {
//	}
	
	public static Object dustModuleInit() throws Exception {
		Dust.log(null, "Hello world from Sandbox module");
		return DustSandboxMain.initMachine();
	}
}
