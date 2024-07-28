package me.giskard.dust.sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;

public class DustSandboxUnitLoader implements DustSandboxConsts {

	public void loadUnits(File root, String author, String resLang, String... unitNames) throws Exception {
		Dust.log(null, "Loading units from", root.getCanonicalPath());

		File fa = new File(root, author);

		if (0 == unitNames.length) {
			unitNames = fa.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".dut");
				}
			});
		}

		for (String un : unitNames) {
			un = DustUtils.cutPostfix(un, ".");
			File fUnit = new File(fa, un + ".dut");
			File fTxt = new File(fa, "res/" + un + "_txt_" + resLang + ".dut");
			
			Dust.log(null, "Unit", fUnit.getCanonicalPath(), "resource", fTxt.getCanonicalPath());

			readunit(fUnit, fTxt);

		}
	}

	private void readunit(File fUnit, File fTxt) throws Exception {

		try (FileReader fr = new FileReader(fUnit); BufferedReader br = new BufferedReader(fr)) {
			for (String line = br.readLine(); null != line; line = br.readLine()) {
				line = line.trim();
				
				if ( DustUtils.isEmpty(line) || line.startsWith("#")) {
					continue;
				}
			}
		}
	}
}
