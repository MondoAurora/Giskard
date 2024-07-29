package me.giskard.dust.sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;

public class DustSandboxUnitLoader implements DustSandboxConsts {

	enum DutStage {
		authors, units, graph
	}

	interface GraphLineProcessor {
		void processGraphLine(LoadContext ctx, String sepChar, String line);
	}

	class LoadContext {
		String author;
		String unitName;

		ArrayList<String> authors = new ArrayList<>();
		ArrayList<String> unitRefs = new ArrayList<>();

		GraphLineProcessor glp;

		public void reset(String author, String unitName, GraphLineProcessor glp) {
			this.author = author;
			this.unitName = unitName;
			this.glp = glp;

			authors.clear();
			unitRefs.clear();
		}

		public void processLine(DutStage stage, String sepChar, String line) {
			switch (stage) {
			case authors:
				authors.add(line);
				break;
			case units:
				String[] sl = line.split(sepChar);
				int authIdx = Integer.parseInt(sl[0]);
				String authName = authors.get(authIdx);
				unitRefs.add(authName + ":" + sl[1]);
				break;
			case graph:
				glp.processGraphLine(this, sepChar, line);
				break;
			}
		}

		public DustSandboxHandle resolve(String s) {
			String[] ss = s.split(":");

			String auth = author;
			String unit = unitName;
			String id = ss[0];

			if (ss.length == 2) {
				String uref = unitRefs.get(Integer.parseInt(id));
				String[] urs = uref.split(":");
				auth = urs[0];
				unit = urs[1];
				id = ss[1];
			}

			DustSandboxHandle h = brain.resolve(auth, unit, id);

			return h;
		}
	}

	DustSandboxBrain brain;
	
	GraphLineProcessor glpUnit = new GraphLineProcessor() {
		@Override
		public void processGraphLine(LoadContext ctx, String sepChar, String line) {
			String spl = line;
			int cp = spl.indexOf(sepChar);
			String s = spl.substring(0, cp);
			spl = spl.substring(cp + 1);

			DustSandboxHandle hTarget = ctx.resolve(s);

			cp = spl.indexOf(sepChar);
			s = spl.substring(0, cp);
			spl = spl.substring(cp + 1);

			DustSandboxHandle hAtt = ctx.resolve(s.substring(1));

			Object key = "-";
			char attType = s.charAt(0);

			switch (attType) {
			case 'o':
			case 's':
				break;
			case 'a':
				cp = spl.indexOf(sepChar);
				s = spl.substring(0, cp);
				spl = spl.substring(cp + 1);
				
				key = Integer.parseInt(s);

				break;
			case 'm':
				cp = spl.indexOf(sepChar);
				s = spl.substring(0, cp);
				spl = spl.substring(cp + 1);
				
				key = parseVal(ctx, s);
				break;
			}

			Object val = parseVal(ctx, spl);
			
			brain.set(hTarget, hAtt, attType, key, val);
		}
	};
	
	public DustSandboxUnitLoader(DustSandboxBrain brain) {
		this.brain = brain;
	}

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

		LoadContext lc = new LoadContext();

		for (String un : unitNames) {
			un = DustUtils.cutPostfix(un, ".");
			File fUnit = new File(fa, un + ".dut");
			File fTxt = new File(fa, "res/" + un + "_txt_" + resLang + ".dut");

			Dust.log(null, "Unit", fUnit.getCanonicalPath(), "resource", fTxt.getCanonicalPath());
			
			lc.reset(author, un, glpUnit);
			readunit(lc, fUnit);
		}
	}

	private void readunit(LoadContext lc, File fUnit) throws Exception {

		try (FileReader fr = new FileReader(fUnit); BufferedReader br = new BufferedReader(fr)) {
			DutStage stage = null;
			String sepChar = null;

			for (String line = br.readLine(); null != line; line = br.readLine()) {
				line = line.trim();
				String[] sl;

				if (DustUtils.isEmpty(line) || line.startsWith("#")) {
					continue;
				}

				if (null == sepChar) {
					if (!line.startsWith(DUT_SIGNATURE)) {
						DustException.wrap(null, "Invalid file, first line signature error", line);
					}

					int dsl = DUT_SIGNATURE.length();
					sepChar = line.substring(dsl, dsl + 1);

					sl = line.split(sepChar);

					if (!DustUtils.isEqual(lc.author, sl[3])) {
						DustException.wrap(null, "Invalid author in file", line, "required", lc.author);
					}
					if (!DustUtils.isEqual(lc.unitName, sl[4])) {
						DustException.wrap(null, "Invalid unit in file", line, "required", lc.unitName);
					}

					continue;
				}

				if (line.startsWith("!")) {
					stage = DutStage.valueOf(line.substring(1));
					continue;
				}
				
				lc.processLine(stage, sepChar, line);
			}
		}
	}
	
	private Object parseVal(LoadContext ctx, String val) {
		char valType = val.charAt(0);
		val = val.substring(1);
		
		switch (valType) {
		case 'h':
			return ctx.resolve(val);
		case 'i':
			return Long.parseLong((String)val);
		case 'r':
			return Double.parseDouble((String)val);
		}
		
		return val;
	}
}
