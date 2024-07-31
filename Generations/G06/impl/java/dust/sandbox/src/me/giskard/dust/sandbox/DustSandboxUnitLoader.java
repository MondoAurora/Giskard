package me.giskard.dust.sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
		DustSandboxMachine machine;

		Set<String> queue = new HashSet<>();
		Set<String> loadText = new HashSet<>();

		String uref;
		ArrayList<String> authors = new ArrayList<>();
		ArrayList<String> unitRefs = new ArrayList<>();

		GraphLineProcessor glp;

		public LoadContext(DustSandboxMachine machine) {
			this.machine = machine;
		}

		public void reset(String uref, GraphLineProcessor glp) {
			this.uref = uref;
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

		public void verifyFileSignature(String author, String unit, String version) {
			String[] au = uref.split(":");

			if (!DustUtils.isEqual(au[0], author)) {
				DustException.wrap(null, "Invalid author in file", author, "required", au[0]);
			}
			String uf = unit + "_" + version;
			if (!DustUtils.isEqual(au[1], uf)) {
				DustException.wrap(null, "Invalid unit in file", uf, "required", au[1]);
			}
		}

		public void addUnit(String un) {
			if (!loadedUnits.contains(un)) {
				queue.add(un);
			}
		}

		public void doProcess() throws Exception {
			while (!queue.isEmpty()) {
				Iterator<String> it = queue.iterator();
				String un = it.next();
				it.remove();
				loadedUnits.add(un);
				loadText.add(un);

				String fName = un.replace(':', '/') + ".dut";

				reset(un, glpUnit);
				readunit(fName);
			}

			for (String un : loadText) {
				String pf = DustUtils.getPostfix(un, "_");
				un = DustUtils.cutPostfix(un, "_") + "_txt_" + machine.getLanguage() + "_" + pf;
				String fName = un.replace(":", "/res/") + ".dut";

				reset(un, glpText);
				readunit(fName);
			}

//			loadedUnits.clear();
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

			ctx.machine.set(hTarget, hAtt, attType, key, val);
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
					ctx.machine.setText(txtData);
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

	File root;
	Set<String> loadedUnits = new HashSet<>();
	LoadContext lc;

	public DustSandboxUnitLoader(File root) {
		this.root = root;
	}

	public void loadAllUnits(DustSandboxMachine machine) throws Exception {
//		File fa = new File(root, defAuthor);

		File[] authorDirs = root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});

		for (File fa : authorDirs) {
			String author = fa.getName();

			String[] unitNames = fa.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".dut");
				}
			});

			if (unitNames.length > 0) {
				for (int i = unitNames.length; i-- > 0;) {
					unitNames[i] = DustUtils.cutPostfix(author + ":" + unitNames[i], ".dut");
				}

				loadUnits(machine, unitNames);
			}
		}
	}

	public void loadUnits(DustSandboxMachine machine, String... unitNames) throws Exception {
//		Dust.log(null, "Loading units from", root.getCanonicalPath());

		boolean doProcess = false;

		if (null == lc) {
			lc = new LoadContext(machine);
			doProcess = true;
		}

		for (String un : unitNames) {
			lc.addUnit(un);
		}

		if (doProcess) {
			try {
				lc.doProcess();
			} finally {
				lc = null;
			}
		}
	}

	private void readunit(String fName) throws Exception {
		File fUnit = new File(root, fName);

		if (!fUnit.isFile()) {
			return;
		}

		Dust.log(null, "Loading", fUnit.getCanonicalPath());

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

					lc.verifyFileSignature(sl[3], sl[4], sl[5]);

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
}
