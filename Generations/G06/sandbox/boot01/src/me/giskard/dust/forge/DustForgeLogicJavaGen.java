package me.giskard.dust.forge;

import java.io.File;
import java.io.PrintWriter;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineBootConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.machine.DustMachineUtils;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;
import me.giskard.dust.utils.DustUtilsFile;

public class DustForgeLogicJavaGen implements DustConsts.MindLogic, DustForgeConsts, DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		Integer count = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_PARAM, MISC_ATT_COUNT);
		boolean last = false;
		boolean log = false;

		Object att = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_ATT);

		switch (a) {
		case Init:
		case Release:
			return MIND_TAG_RESULT_ACCEPT;
		case Begin:
			++count;
			break;
		case End:
			--count;
			last = null == Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE);
			break;
		case Process:
//			log = true;
			Object val = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE);
			DustHandle handle = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE);
			String lang = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, TEXT_ATT_LANG);

			if (DUST_BOOTTOKEN == att) {
				String token = (String) val;

				Object collision = Dust.access(MIND_TAG_ACCESS_SET, handle, null, DUST_PARAM, FORGE_BOOT_TOKENS, token);
				if ((null != collision) && (handle != collision)) {
					Dust.log(null, "Boot token collision for token", token, handle, collision);
				} else {
					PrintWriter pw = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, FORGE_BOOT_WRITER);

					if (null == pw) {
						pw = initWriter(FORGE_BOOT_WRITER, FORGE_BOOT_CLASSNAME, DustConsts.class, DustHandle.class);

						pw.println("  String LANG_ID = \"" + lang + "\";");
						pw.println();

						pw.flush();
					}

					if (handle.isUnit()) {
						optStoreUnitToken(handle, pw);
					} else {
						String unitToken = optStoreUnitToken(handle.getUnit(), pw);
						pw.println("\tDustHandle " + token + " = new DustHandle(" + unitToken + ", \"" + handle.getId() + "\");");
					}

					pw.flush();

				}
			} else if (MIND_IDEA_PRIMARYASPECT == att) {
				if (TEXT_TOKEN == val) {
					String token = DustMachineUtils.getTokenStr(handle, lang);
//					String t2 = Dust.access(MIND_TAG_ACCESS_PEEK, null, handle, MISC_PAYLOAD);
//					
//					if ( !DustUtils.isEqual(token, t2) ) {
//						Dust.log(null, "Token mismatch", handle, token, t2);
//						break;
//					} else {
//						Dust.log(null, "Token OK", handle, token);
//					}
					boolean newToken = Dust.access(MIND_TAG_ACCESS_INSERT, token, null, DUST_SELF, MISC_SEEN);
					if (newToken) {
						DustHandle target = Dust.access(MIND_TAG_ACCESS_PEEK, null, handle, MISC_ATT_TARGET);

						PrintWriter pw = getWriter(FORGE_MODULE_WRITER, FORGE_MODULE_CLASSNAME, DustConsts.class, Dust.class);

						DustHandle hUnit = target.getUnit();
						String strUnit = hUnit.getId();
						String unitRef = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_UNITREFS, strUnit);

						String lookupStr = unitRef + DUST_SEP_ID + target.getId();

						pw.println("\tMindHandle " + token + " = Dust.lookup(null, \"" + lookupStr + "\");");
						pw.flush();
					} else {
						Dust.log(null, "Repeated token", token);
					}
//					Dust.log(null, "Store lookup handle", token, target);
				}
			}
			break;
		}

		Dust.access(MIND_TAG_ACCESS_SET, count, null, DUST_PARAM, MISC_ATT_COUNT);

		if (log) {
			Dust.log(null, a, "JavaGen processing", "local count: " + count, "language: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, TEXT_ATT_LANG),
					"walk depth: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MISC_DEPTH),
					"handle: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE), "att: " + att,
					"key: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_KEY),
					"value: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE));
		}

		if (last) {
			Dust.log(null, a, "JavaGen processing complete", count);

			optCloseWriter(FORGE_BOOT_WRITER);
			optCloseWriter(FORGE_MODULE_WRITER);
		}

		return MIND_TAG_RESULT_ACCEPT;
	}

	public void optCloseWriter(MindHandle hWriter) {
		PrintWriter pw = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, hWriter);
		if (null != pw) {
			pw.print("}");
			pw.flush();
			pw.close();
			Dust.access(MIND_TAG_ACCESS_DELETE, null, null, DUST_PARAM, hWriter);
		}
	}

	public PrintWriter getWriter(MindHandle hWriter, MindHandle hClassAtt, Class<?>... importClasses) throws Exception {
		PrintWriter pw = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, hWriter);

		return (null == pw) ? initWriter(hWriter, hClassAtt, importClasses) : pw;
	}

	public PrintWriter initWriter(MindHandle hWriter, MindHandle hClassAtt, Class<?>... importClasses) throws Exception {
		PrintWriter pw;
		String srcRoot = Dust.access(MIND_TAG_ACCESS_PEEK, ".", null, DUST_PARAM, FORGE_SRC_PATH);
		String pkg = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, FORGE_GEN_PACKAGE);

		File f = new File(srcRoot + File.separatorChar + pkg.replace('.', File.separatorChar));

		DustUtilsFile.ensureDir(f);

		String genClass = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, hClassAtt);

		pw = new PrintWriter(new File(f, genClass + DUST_EXT_JAVA));
		Dust.access(MIND_TAG_ACCESS_SET, pw, null, DUST_PARAM, hWriter);

		pw.println("package " + pkg + ";");
		pw.println();
		pw.println("// Generated " + DustUtils.strTime());
		pw.println();

		for (Class<?> ic : importClasses) {
			pw.println("import " + ic.getName().replace('$', '.') + ";");
		}

		pw.println();
		pw.println("public interface " + genClass + " extends DustConsts {");
		pw.println();

		return pw;
	}

	String optStoreUnitToken(DustHandle handle, PrintWriter pw) {
		String unitToken = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, FORGE_BOOT_UNITS, handle);

		if (null == unitToken) {
			unitToken = Dust.access(MIND_TAG_ACCESS_PEEK, null, handle, DUST_BOOTTOKEN);
			Dust.access(MIND_TAG_ACCESS_SET, unitToken, null, DUST_PARAM, FORGE_BOOT_UNITS, handle);
			pw.println("\tDustHandle " + unitToken + " = new DustHandle(\"" + handle.getId() + "\");");
		}

		return unitToken;
	}
}