package me.giskard.dust.sandbox;

import java.io.File;

import me.giskard.dust.Dust;

public class DustSandboxMain implements DustSandboxConsts {
	public static void main(String[] args) {
		try {
			String fHome = System.getProperty("user.home");
			String fRoot = System.getProperty(LP_GISKARD_ROOT, "");

			File f = new File(fHome, fRoot);
			File fUnit = new File(f, "store/local/units");

			DustSandboxMachine machine = new DustSandboxMachine("en", fUnit);
			
			Dust.log(null, "***** Machine test *****");

			machine.test();
			
			Dust.log(null, "***** Load all units *****");

			machine.loadAllUnits();

//			machine.unitLoader.loadAllUnits(machine, "giskard.me");
//			loader.loadUnits(machine, "giskard.me:mind_1.0", "giskard.me:misc_1.0", "giskard.me:text_1.0");

//			machine.test();

			Dust.log(null, "***** Generating sources *****");

			File fGenRoot = new File(f, "impl/java/dust/gen/src");
			DustSandboxCodeGen cg = new DustSandboxCodeGen(fGenRoot, machine);
			cg.genSources();
			
			File fPrjRoot = new File(f, "impl/java");
			File fMods = new File(f, "store/local/modules");

			DustSandboxBinaryGen.buildAll(fPrjRoot, fMods);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}
	}
}
