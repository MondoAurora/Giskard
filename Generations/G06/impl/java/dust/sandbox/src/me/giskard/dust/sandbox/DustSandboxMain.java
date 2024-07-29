package me.giskard.dust.sandbox;

import java.io.File;

public class DustSandboxMain implements DustSandboxConsts {
	public static void main(String[] args) {
		DustSandboxBrain brain = new DustSandboxBrain();

		try {
			String fHome = System.getProperty("user.home");
			String fRoot = System.getProperty(LP_GISKARD_ROOT, "");

			File f = new File(fHome, fRoot);
			File fUnit = new File(f, "store/local/units");
			
			DustSandboxUnitLoader loader = new DustSandboxUnitLoader(brain);

			loader.loadUnits(fUnit, "giskard.me", "en");
//			loader.loadUnits(fUnit, "giskard.me", "en", "mind", "misc", "dust");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}
	}
}
