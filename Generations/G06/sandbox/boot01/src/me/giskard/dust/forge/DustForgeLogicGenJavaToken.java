package me.giskard.dust.forge;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.DustException;
import me.giskard.dust.machine.DustMachineBootConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.machine.DustMachineUtils;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;
import me.giskard.dust.utils.DustUtilsFile;

public class DustForgeLogicGenJavaToken implements DustConsts.MindLogic, DustForgeConsts, DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		Integer count = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_AGENT_PARAM, MISC_PROC_COUNT);
		boolean last = false;
		boolean log = false;

		Object att = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_ATT);

		switch (a) {
		case Init:
		case Release:
			return MIND_TAG_RESULT_ACCEPT;
		case Begin:
			++count;
			break;
		case End:
			--count;
			last = null == Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_HANDLE);
			break;
		case Process:
//			log = true;
			Object val = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE);
			DustHandle handle = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_HANDLE);
			DustHandle target = Dust.access(MIND_TAG_ACCESS_PEEK, null, handle, MISC_GEN_TARGET);
			String lang = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, TEXT_ATT_LANG);

			if ((MIND_IDEA_PRIMARYASPECT == att) && (TEXT_TAG_TOKEN == val)) {
				String token = DustMachineUtils.getTokenStr(handle, lang);

				boolean newToken = Dust.access(MIND_TAG_ACCESS_INSERT, token, null, DUST_AGENT_SELF, MISC_PROC_SEEN);
				if (newToken) {

					DustHandle classTag = Dust.access(MIND_TAG_ACCESS_PEEK, null, target, MIND_IDEA_TAGS, FORGE_TAG_CLASS);
					DustHandle bootTag = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, FORGE_BOOT_TAG);

					boolean bt = (classTag == bootTag);

					PrintWriter pw = getWriter(classTag, bt, lang);

					String codeLine = null;

					if (bt) {
						if (target.isUnit()) {
							optStoreUnitToken(target, lang, pw);
						} else {
							String unitToken = optStoreUnitToken(target.getUnit(), lang, pw);
							codeLine = "\tDustHandle " + token + " = new DustHandle(" + unitToken + ", \"" + target.getId() + "\");";
						}
					} else {
						DustHandle hUnit = target.getUnit();
						String strUnit = hUnit.getId();
						String unitRef = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_UNITREFS, strUnit);
						String lookupStr = target.isUnit() ? target.getId() : unitRef + DUST_SEP_ID + target.getId();

						if (lookupStr.contains("giskard.me")) {
							Dust.log(null, "Invalid lookup string", lookupStr);
							break;
						}

						codeLine = "\tMindHandle " + token + " = Dust.lookup(null, \"" + lookupStr + "\");";
					}

					if (!DustUtils.isEmpty(codeLine)) {
						pw.println(codeLine);
						pw.flush();
					}
				} else {
					Dust.log(null, "Repeated token", token);
				}
			}
			break;
		}

		Dust.access(MIND_TAG_ACCESS_SET, count, null, DUST_AGENT_PARAM, MISC_PROC_COUNT);

		if (log) {
			Dust.log(null, a, "JavaGen processing", "local count: " + count,
					"language: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, TEXT_ATT_LANG),
					"walk depth: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MISC_PROC_DEPTH),
					"handle: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_HANDLE), "att: " + att,
					"key: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_KEY),
					"value: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE));
		}

		if (last) {
			Dust.log(null, a, "JavaGen processing complete", count);

			Collection<Object> writers = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, FORGE_CLASS_WRITERS, MISC_MAP_KEYS);

			if (null != writers) {
				for (Object wk : writers) {
					PrintWriter pw = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, FORGE_CLASS_WRITERS, wk);

					try {
						pw.print("}");
						pw.flush();
						pw.close();
					} catch (Throwable t) {
						DustException.swallow(t, "Failed to close class writer", wk);
					}
				}

				Dust.access(MIND_TAG_ACCESS_DELETE, null, null, DUST_AGENT_PARAM, FORGE_CLASS_WRITERS);
			}
		}

		return MIND_TAG_RESULT_ACCEPT;
	}

	public PrintWriter getWriter(MindHandle hClassAtt, boolean bootClass, String lang) throws Exception {
		String cn = Dust.access(MIND_TAG_ACCESS_PEEK, null, hClassAtt, JAVA_CLASSNAME);

		PrintWriter pw = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, FORGE_CLASS_WRITERS, cn);

		if (null == pw) {
			String srcRoot = Dust.access(MIND_TAG_ACCESS_PEEK, ".", null, DUST_AGENT_PARAM, FORGE_SRC_PATH);

			int lidx = cn.lastIndexOf(".");

			String pkg = cn.substring(0, lidx);

			File f = new File(srcRoot + File.separatorChar + pkg.replace('.', File.separatorChar));

			DustUtilsFile.ensureDir(f);

			String genClass = cn.substring(lidx + 1);

			pw = new PrintWriter(new File(f, genClass + DUST_EXT_JAVA));
			Dust.access(MIND_TAG_ACCESS_SET, pw, null, DUST_AGENT_PARAM, FORGE_CLASS_WRITERS, cn);

			pw.println("package " + pkg + ";");
			pw.println();
			pw.println("// Generated " + DustUtils.strTime());
			pw.println();

			Class<?>[] importClasses = bootClass ? new Class[] { DustConsts.class, DustHandle.class } : new Class[] { DustConsts.class, Dust.class };

			for (Class<?> ic : importClasses) {
				pw.println("import " + ic.getName().replace('$', '.') + ";");
			}

			pw.println();
			pw.println("public interface " + genClass + " extends DustConsts {");
			pw.println();

			if (bootClass) {
				pw.println("  String LANG_ID = \"" + lang + "\";");
				pw.println();
			}

			pw.flush();
		}

		return pw;
	}

	String optStoreUnitToken(DustHandle handle, String lang, PrintWriter pw) {
		String unitToken = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, FORGE_BOOT_UNITS, handle);

		if (null == unitToken) {
			unitToken = DustMachineUtils.getTokenStr(handle, lang);
			Dust.access(MIND_TAG_ACCESS_SET, unitToken, null, DUST_AGENT_PARAM, FORGE_BOOT_UNITS, handle);
			pw.println("\tDustHandle " + unitToken + " = new DustHandle(\"" + handle.getId() + "\");");
		}

		return unitToken;
	}
}