package me.giskard.dust.sandbox;

import java.io.File;

public class DustSandboxMain implements DustSandboxConsts {
	public static void main(String[] args) {
		DustSandboxMachine machine = new DustSandboxMachine();
		
		machine.test();

		try {
			String fHome = System.getProperty("user.home");
			String fRoot = System.getProperty(LP_GISKARD_ROOT, "");

			File f = new File(fHome, fRoot);
			File fUnit = new File(f, "store/local/units");
			
			DustSandboxUnitLoader loader = new DustSandboxUnitLoader(fUnit);

			loader.loadAllUnits(machine, "en", "giskard.me");
//			loader.loadUnits(machine, "en", "giskard.me:mind_1.0", "giskard.me:misc_1.0", "giskard.me:event_1.0");
			
			machine.test();

			
			DustSandboxCodeGen cg = new DustSandboxCodeGen("gen", machine);
			cg.genSources();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}
	}
}
