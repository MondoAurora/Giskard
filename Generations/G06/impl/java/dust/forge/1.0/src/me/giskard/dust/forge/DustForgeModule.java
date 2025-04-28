package me.giskard.dust.forge;

import me.giskard.dust.Dust;

public class DustForgeModule {
	public static void dustModuleInit() {
		Dust.broadcast(null, "Hello world from Forge");
	}
}
