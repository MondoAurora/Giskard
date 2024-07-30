package me.giskard.dust.sandbox;

import java.io.File;

public class DustSandboxMain implements DustSandboxConsts {
	public static void main(String[] args) {
		DustSandboxMachine machine = new DustSandboxMachine();

		try {
			String fHome = System.getProperty("user.home");
			String fRoot = System.getProperty(LP_GISKARD_ROOT, "");

			File f = new File(fHome, fRoot);
			File fUnit = new File(f, "store/local/units");
			
			DustSandboxUnitLoader loader = new DustSandboxUnitLoader(machine);

			loader.loadUnits(fUnit, "giskard.me", "en");
//			loader.loadUnits(fUnit, "giskard.me", "en", "mind_1.0", "misc_1.0", "dust_1.0");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}
	}
}
