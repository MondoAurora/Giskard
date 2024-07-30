package me.giskard.dust.sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;

public class DustSandboxUnitLoader implements DustSandboxConsts {

	enum DutStage {
		authors, units, graph
	}
	enum TextAtt {
		owner, tags, tagLang, tagType, text
	}

	interface GraphLineProcessor {
		void reset();

		void processGraphLine(LoadContext ctx, String sepChar, String line);
	}

	class LoadContext {
		String author;
		String unitId;
		String uref;

		ArrayList<String> authors = new ArrayList<>();
		ArrayList<String> unitRefs = new ArrayList<>();

		GraphLineProcessor glp;

		public void reset(String author, String unitId, GraphLineProcessor glp) {
			this.author = author;
			this.unitId = unitId;
			this.uref = author + ":" + unitId;
			this.glp = glp;

			authors.clear();
			unitRefs.clear();

			glp.reset();
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
				unitRefs.add(authName + ":" + sl[1] + "_" + sl[2]);
				break;
			case graph:
				glp.processGraphLine(this, sepChar, line);
				break;
			}
		}

		public DustSandboxHandle resolve(String s) {
			String[] ss = s.split(":");

			String uref;
			String id = ss[0];

			if (ss.length == 2) {
				uref = unitRefs.get(Integer.parseInt(id));
				id = ss[1];
			} else {
				uref = this.uref;
				id = ss[0];
			}

			DustSandboxHandle h = machine.resolve(uref, id);

			return h;
		}
	}

	DustSandboxMachine machine;

	GraphLineProcessor glpUnit = new GraphLineProcessor() {
		@Override
		public void reset() {
		}

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

			machine.set(hTarget, hAtt, attType, key, val);
		}
	};

	GraphLineProcessor glpText = new GraphLineProcessor() {

		String currTarget;
		Map<String, TextAtt> txtAtts = new TreeMap<>();
		EnumMap<TextInfo, Object> txtData = new EnumMap<>(TextInfo.class);

		public void reset() {
			currTarget = null;
		}

		@Override
		public void processGraphLine(LoadContext ctx, String sepChar, String line) {
			String[] spl = line.split(sepChar);
			String tId = spl[0];

			if (!DustUtils.isEqual(currTarget, tId)) {
				if (null == currTarget) {
					txtAtts.clear();

					for (int i = ctx.unitRefs.size(); i-- > 0;) {
						String un = ctx.unitRefs.get(i).split("_")[0];

						switch (un) {
						case "giskard.me:mind":
							txtAtts.put(i + ":7", TextAtt.tags);
							break;
						case "giskard.me:misc":
							txtAtts.put(i + ":1", TextAtt.owner);
							break;
						case "giskard.me:text":
							txtAtts.put(i + ":3", TextAtt.text);
							txtAtts.put(i + ":0", TextAtt.tagLang);
							txtAtts.put(i + ":4", TextAtt.tagType);

							break;
						}
					}
				} else {
					machine.setText(txtData);
					txtData.clear();
				}

				currTarget = tId;
			} else {
				TextAtt ti = txtAtts.get(spl[1].substring(1));
				if (null != ti) {
					String p1 = spl[2].substring(1);

					switch (ti) {
					case tags:
						TextAtt ta = txtAtts.get(p1);
						if (null != ta) {
							Object pv = ctx.resolve(spl[3].substring(1));
							switch (ta) {
							case tagLang:
								txtData.put(TextInfo.txtLang, pv);
								break;
							case tagType:
								txtData.put(TextInfo.txtType, pv);
								break;
							default:
								break;
							}
						}
						break;
					case owner:
						txtData.put(TextInfo.owner, ctx.resolve(p1));
						break;
					case text:
						txtData.put(TextInfo.text, p1);
						break;
					default:
						break;
					}
				}
			}
		}
	};

	public DustSandboxUnitLoader(DustSandboxMachine machine) {
		this.machine = machine;
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

			Dust.log(null, "Loading unit", fUnit.getCanonicalPath());

			lc.reset(author, un, glpUnit);
			readunit(lc, un, fUnit);
		}

		for (String un : unitNames) {
			String pf = DustUtils.getPostfix(un, "_");
			String fName = DustUtils.cutPostfix(un, "_") + "_txt_" + resLang + "_" + pf;
			File fTxt = new File(fa, "res/" + fName);

			if (fTxt.isFile()) {
				un = DustUtils.cutPostfix(fName, ".");
				Dust.log(null, "Loading resource", fTxt.getCanonicalPath());

				lc.reset(author, un, glpText);
				readunit(lc, un, fTxt);
			}
		}
	}

	private void readunit(LoadContext lc, String unitName, File fUnit) throws Exception {

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
					if (!DustUtils.isEqual(lc.unitId, sl[4] + "_" + sl[5])) {
						DustException.wrap(null, "Invalid unit in file", line, "required", lc.unitId);
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
			return Long.parseLong((String) val);
		case 'r':
			return Double.parseDouble((String) val);
		}

		return val;
	}
}
