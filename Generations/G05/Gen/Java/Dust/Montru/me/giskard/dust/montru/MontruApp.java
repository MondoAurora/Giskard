package me.giskard.dust.montru;

import me.giskard.mind.GiskardApp;

public class MontruApp extends GiskardApp {

	public MontruApp() {
		super(
				new ModuleBean("Dust", "Brain", 1, 0, "me.giskard.dust.mod.brain.DustBrain"), 
				new ModuleBean("Dust", "Montru", 1, 0));
	}

	public static void main(String[] args) throws Exception {
		new MontruApp().boot(args);
	}

}
