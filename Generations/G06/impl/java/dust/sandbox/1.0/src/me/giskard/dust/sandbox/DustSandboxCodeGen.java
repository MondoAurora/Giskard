package me.giskard.dust.sandbox;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustSandboxCodeGen implements DustSandboxConsts {
	DustSandboxMachine machine;
	File root;

	private static final Set<String> LINEBREAK = new HashSet<String>();

	static {
		LINEBREAK.add("ASP");
		LINEBREAK.add("NAR");
	}

	public DustSandboxCodeGen(File root, DustSandboxMachine m) {
		this.root = root;
		machine = m;
	}

	public void genSources(String... units) throws Exception {
		try (PrintWriter pw = new PrintWriter(new File(root.getParentFile(), "dust_gen.txt"))) {

			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = new Date();

			String strDate = utcFormat.format(date);

			pw.println(strDate);
			pw.flush();
		}

		for (String author : machine.getAuthors()) {
			StringBuilder sb = null;
			String[] spl = author.split("\\.");

			for (int i = spl.length; i-- > 0;) {
				sb = DustUtils.sbAppend(sb, ".", false, spl[i]);
			}

			String pAuthor = sb.toString();

			DustUtilsFactory<String, DustUtilsFactory> fUnit = machine.getAuthorUnits(author);

			for (String unitId : fUnit.keys()) {
				String unit = DustUtils.cutPostfix(unitId, "_").toLowerCase();
				String pUnit = pAuthor + "." + unit;
				String ifName = "Dust" + unit.substring(0, 1).toUpperCase() + unit.substring(1) + "Tokens";

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
						StringBuilder handleName = null;

						if (name.startsWith(author)) {
							pw.println("// No text assigned to handle " + h);
						} else {
							String[] ns = name.split("_");
							if (1 < ns.length) {
								String type = ns[1];
								if (LINEBREAK.contains(type)) {
									pw.println();
								} else if ("TAG".equals(type)) {
									if (!name.startsWith(tagPrefix)) {
										tagPrefix = name;
										pw.println();
									}
								}
								
								handleName = DustUtils.sbAppend(handleName, DUST_SEP, true, (ns.length < 3) ? "DU" : "TOKEN", ns[0]);
								for ( int i = 2; i < ns.length; ++i ) {
									handleName = DustUtils.sbAppend(handleName, DUST_SEP, true, ns[i]);
								}
							} else {
								pw.println("// Not standard handle name " + name);
								continue;
							}

							pw.print("\tMindToken ");
							pw.print(handleName);
							pw.print(" = Dust.access(null, \"");
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
