package me.giskard.dust.forge;

import me.giskard.mind.GiskardApp;

public class ForgeApp extends GiskardApp {

	public ForgeApp() {
		super(
				new ModuleBean("Dust", "BrainJava", 1, 0), 
				new ModuleBean("Dust", "Montru", 1, 0));
	}

	public static void main(String[] args) throws Exception {
		new ForgeApp().boot(args);
	}

}
