package me.giskard.dust.boot;

import me.giskard.dust.Dust;

public class DustBootModule implements DustBootConsts {
	public static Object dustModuleInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");
		return new DustBootMachine();
	}
}
