package me.giskard.dust.machine.sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.DustMainConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.machine.DustMachineHandle;
import me.giskard.dust.machine.DustMachineNarrative;
import me.giskard.dust.utils.DustUtils;

public class SandboxUnitLoader implements DustMachineConsts, DustMainConsts {

	enum DutStage {
		authors, units, graph
	}

	enum TextAtt {
		owner, tags, tagLang, tagType, text
	}

	interface GraphLineProcessor {
		void reset();

		void close(LoadContext ctx);

		void processGraphLine(LoadContext ctx, String sepChar, String line);
	}

	class LoadContext {
		DustMachineNarrative machine;
		
		Set<String> queue = new HashSet<>();
		Set<String> loadText = new HashSet<>();

		String uref;
		ArrayList<String> authors = new ArrayList<>();
		ArrayList<String> unitRefs = new ArrayList<>();

		GraphLineProcessor glp;
		
		public LoadContext(DustMachineNarrative machine) {
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
		
		void close() {
			glp.close(this);
		}

		public DustMachineHandle resolve(String s) {
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

			DustMachineHandle h = Dust.access(MindAccess.Lookup, uref + DUST_SEP_ID + id);

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
				
				un = un.replace("/res/", ":");

				reset(un, glpUnit);
				readunit(fName);
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

			DustMachineHandle hTarget = ctx.resolve(s);

			cp = spl.indexOf(sepChar);
			s = spl.substring(0, cp);
			spl = spl.substring(cp + 1);

			DustMachineHandle hAtt = ctx.resolve(s.substring(1));

			Object key = "-";
			char attType = s.charAt(0);
			MindCollType ct = null;

			switch (attType) {
			case 'o':
				ct = MindCollType.One;
				break;
			case 's':
				ct = MindCollType.Set;
				break;
			case 'a':
				ct = MindCollType.Arr;
				cp = spl.indexOf(sepChar);
				s = spl.substring(0, cp);
				spl = spl.substring(cp + 1);

				key = Integer.parseInt(s);

				break;
			case 'm':
				ct = MindCollType.Map;

				cp = spl.indexOf(sepChar);
				s = spl.substring(0, cp);
				spl = spl.substring(cp + 1);

				key = parseVal(ctx, s);
				break;
			}

			Object val = parseVal(ctx, spl);
			
			ctx.machine.set(hTarget, hAtt, val, ct, key);
		}

		@Override
		public void close(LoadContext ctx) {
		}
	};

	DustMachineNarrative machine;
	File root;
	Set<String> loadedUnits = new HashSet<>();
	LoadContext lc;

	public SandboxUnitLoader(DustMachineNarrative machine) {
		this.machine = machine;
		
		String fHome = System.getProperty("user.home");
		String fRoot = System.getProperty(LP_GISKARD_ROOT, "");

		File f = new File(fHome, fRoot);
		
		root = new File(f, "store/local/units");
	}

	public void loadUnits(String... unitNames) throws Exception {
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
					if (!line.startsWith(DUST_DUT_SIGNATURE)) {
						DustException.wrap(null, "Invalid file, first line signature error", line);
					}

					int dsl = DUST_DUT_SIGNATURE.length();
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
			
			lc.close();
		}
	}
}
