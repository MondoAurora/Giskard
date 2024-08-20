package me.giskard.dust.sandbox;

import java.util.ArrayList;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public class DustSandboxModule implements DustConsts {
	public static MindMachine dustMachineInit(ArrayList<String> args) throws Exception {
		return DustSandboxMain.initMachine(null);
	}
	
	public static void dustModuleInit() throws Exception {
		Dust.log(null, "Hello world from Sandbox module");
	}
}
