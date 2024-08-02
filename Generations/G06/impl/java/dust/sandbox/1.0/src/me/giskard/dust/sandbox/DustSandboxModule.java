package me.giskard.dust.sandbox;

import me.giskard.dust.Dust;

public class DustSandboxModule {
	public static void dustModuleInit() throws Exception {
		DustSandboxMain.initMachine();
		Dust.log(null, "Hello world from Sandbox module");
	}
}
