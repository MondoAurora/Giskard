package me.giskard.dust.sandbox;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustSandboxCodeGen implements DustSandboxConsts {
	DustSandboxMachine machine;
	File root;

	private static final Set<String> LINEBREAK = Set.of("NAR", "ASP");

	public DustSandboxCodeGen(String r, DustSandboxMachine m) {
		root = new File(r);
		machine = m;
	}

	public void genSources(String... units) throws Exception {
		for (String author : machine.handleCatalog.keys()) {
			StringBuilder sb = null;
			String[] spl = author.split("\\.");

			for (int i = spl.length; i-- > 0;) {
				sb = DustUtils.sbAppend(sb, ".", false, spl[i]);
			}

			String pAuthor = sb.toString();

			DustUtilsFactory<String, DustUtilsFactory> fUnit = machine.handleCatalog.peek(author);

			for (String unitId : fUnit.keys()) {
				String unit = DustUtils.cutPostfix(unitId, "_").toLowerCase();
				String pUnit = pAuthor + "." + unit;
				String ifName = "Dust" + unit.substring(0, 1).toUpperCase() + unit.substring(1) + "Handles";

				File dir = new File(root, pUnit.replace(".", "/"));
				dir.mkdirs();

				File f = new File(dir, ifName + ".java");
				DustUtilsFactory<String, DustSandboxHandle> fHandles = fUnit.peek(unitId);

				ArrayList<String> hIds = new ArrayList<>((Set) fHandles.keys());

				hIds.sort(new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return Integer.parseInt(o1) - Integer.parseInt(o2);
					}
				});

				try (PrintWriter pw = new PrintWriter(f)) {
					pw.print("package ");
					pw.print(pUnit);
					pw.println(";\n");

					if (!DustUtils.isEqual(pUnit, "me.giskard.dust")) {
						pw.println("import me.giskard.dust.Dust;");
						pw.println("import me.giskard.dust.DustConsts;\n");
					}

					pw.print("public interface ");
					pw.print(ifName);
					pw.println(" extends DustConsts {\n");
					pw.flush();

					String tagPrefix = "!!!";

					for (String lookupId : hIds) {
						DustSandboxHandle h = fHandles.peek(lookupId);
						String name = h.toString();

						if (name.startsWith(author)) {
							pw.println("// No text assigned to handle " + h);
						} else {
							String[] ns = name.split("_");
							if (1 < ns.length) {
								String type = ns[1];
								if (LINEBREAK.contains(type)) {
									pw.println();
								} else if ( "TAG".equals(type) ) {
									if ( !name.startsWith(tagPrefix) ) {
										tagPrefix = name;
										pw.println();
									}
								}
							} else {
								pw.println("// Not standard handle name " + name);
								continue;
							}

							pw.print("\tMindHandle ");
							pw.print(name);
							pw.print(" = Dust.access(MindAccess.Lookup, \"");
							pw.print(author);
							pw.print(":");
							pw.print(unitId);
							pw.print(":");
							pw.print(lookupId);
							pw.println("\");");
							pw.flush();
						}
					}

					pw.println("}\n");
					pw.flush();
				}
			}
		}
	}
}
