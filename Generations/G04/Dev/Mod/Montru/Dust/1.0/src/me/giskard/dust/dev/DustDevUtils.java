package me.giskard.dust.dev;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.app.GiskardApp;
import me.giskard.app.GiskardAppModuleServices;
import me.giskard.app.GiskardAppUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustTokens;
import me.giskard.dust.brain.DustBrainKnowledge;
import me.giskard.dust.brain.DustBrainUtils;
import me.giskard.tools.GisToolsTranslator;

public class DustDevUtils implements GiskardDevConsts, GisCollConsts {

	private static final GisToolsTranslator<String, MiNDHandle> HANDLETYPE = new GisToolsTranslator<>();

	static {
		HANDLETYPE.add("UNI", MODEL_TYP_UNIT);
		HANDLETYPE.add("TYP", IDEA_TYP_TYPE);
		HANDLETYPE.add("MEM", IDEA_TYP_MEMBER);
		HANDLETYPE.add("TAG", IDEA_TYP_TAG);
		HANDLETYPE.add("SRC", MODEL_TYP_SOURCE);
		HANDLETYPE.add("AGT", NARRATIVE_TYP_AGENT);
	}

	public static void initBootJourney(GisCollFactory<Long, DustHandle> bootFact, DustBrainKnowledge journeyKnowledge) throws Exception {

		for (Long k : bootFact.keys()) {
			DustHandle dh = (DustHandle) bootFact.peek(k);
			DustBrainKnowledge be = new DustBrainKnowledge(dh);
			journeyKnowledge.access(MiNDAccessCommand.Insert, dh, NARRATIVE_MEM_JOURNEY_HANDLES, CollType.Map, k);
			journeyKnowledge.access(MiNDAccessCommand.Insert, be, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, dh);
		}

		loadEnums(DustTokens.class, ValType.class, "IDEA_TAG_VALTYPE");
		loadEnums(DustTokens.class, CollType.class, "IDEA_TAG_COLLTYPE");

		loadEnums(DustTokens.class, MiNDAccessCommand.class, "NARRATIVE_TAG_ACCESS");
		loadEnums(DustTokens.class, MiNDAction.class, "NARRATIVE_TAG_ACTION");
		loadEnums(DustTokens.class, MiNDResultType.class, "NARRATIVE_TAG_RESULT");

		journeyKnowledge.access(MiNDAccessCommand.Insert, GENERIC_TAG_LENIENT, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set, null);
		journeyKnowledge.access(MiNDAccessCommand.Set, DUST_LANG_BOOT, DUST_MEM_BRAIN_DEF_LANG, CollType.One, null);

		loadHandles(DustTokens.class, DUST_LANG_BOOT);
	}

	@SuppressWarnings({ "rawtypes" })
	public static void loadEnums(Class<?> handleContainer, Class<?> cEnum, String prefix) throws Exception {
		for (Object e : cEnum.getEnumConstants()) {
			String n = ((Enum) e).name().toUpperCase();
			try {
				Field f = handleContainer.getDeclaredField(prefix + "_" + n);
				MiNDHandle h = (MiNDHandle) f.get(null);
				DustBrainUtils.addEnum(h, (Enum) e);
			} catch (Throwable ex) {
				Giskard.log(ex, cEnum, n);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadHandles(Class<?> handleContainer, MiNDHandle hLang) throws Exception {
		GisCollFactory<String, Map> factMeta = new GisCollFactory<>(true, HashMap.class);

		for (Field f : handleContainer.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				Giskard.access(MiNDAccessCommand.Set, h.getId(), h, MODEL_MEM_KNOWLEDGE_SOURCEID, null);

				String[] ps = hn.split(SEP_ID);

				MiNDHandle hT = HANDLETYPE.getRight(ps[1]);
				if ( null != hT ) {
					factMeta.get(ps[1]).put(hn, h);
					Giskard.access(MiNDAccessCommand.Set, hT, h, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, null);

					Giskard.access(MiNDAccessCommand.Insert, hn, hLang, LANG_MEM_LANG_TERMINOLOGY, h);
					Giskard.access(MiNDAccessCommand.Insert, h, hLang, LANG_MEM_LANG_GLOSSARY, hn);
				}
			}
		}

		for (Field f : handleContainer.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				int li = hn.lastIndexOf(SEP_ID);
				String prefix = hn.substring(0, li);

				String[] ps = hn.split(SEP_ID);

				if ( 2 < ps.length ) {
					MiNDHandle hU = (MiNDHandle) factMeta.get("UNI").get(ps[0] + "_UNI");
					if ( null != hU ) {
						Giskard.access(MiNDAccessCommand.Set, hU, h, MODEL_MEM_KNOWLEDGE_UNIT, null);
					}
				} else if ( "UNI".equals(ps[1]) ) {
					Giskard.access(MiNDAccessCommand.Set, h, h, MODEL_MEM_KNOWLEDGE_UNIT, null);
				}

				switch ( ps[1] ) {
				case "TAG":
					MiNDHandle hClass = (MiNDHandle) factMeta.get("TAG").get(prefix);
					if ( null != hClass ) {
						Giskard.access(MiNDAccessCommand.Set, hClass, h, GENERIC_MEM_GEN_OWNER, null);
					}
					break;
				case "MEM":
					MiNDHandle hT = (MiNDHandle) factMeta.get("TYP").get(prefix.replace("MEM", "TYP"));
					if ( null == hT ) {
						hT = (MiNDHandle) factMeta.get("AGT").get(prefix.replace("MEM", "AGT"));
					}
					if ( null != hT ) {
						Giskard.access(MiNDAccessCommand.Insert, h, hT, IDEA_MEM_TYPE_MEMBERS, null);
						Giskard.access(MiNDAccessCommand.Set, hT, h, GENERIC_MEM_GEN_OWNER, null);
					}
					break;
				}
			}
		}
	}

	private static GisCollFactory<String, MiNDAgent> FACT_AGENTS = new GisCollFactory<String, MiNDAgent>(true, new MiNDCreator<String, MiNDAgent>() {

		@Override
		public MiNDAgent create(String key) {
			try {
				return (MiNDAgent) Class.forName(key).newInstance();
			} catch (Exception e) {
				Giskard.log("Failed to create agent", key);
			}
			return null;
		}

	});

	public static MiNDAgent getAgent() {
		MiNDAgent ret = null;

		MiNDHandle hA = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_JOURNEY_AGENT, null);

		if ( null != hA ) {
			MiNDHandle hT = Giskard.access(MiNDAccessCommand.Peek, null, hA, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, null);

			String cn = Giskard.access(MiNDAccessCommand.Peek, null, DUST_BRAIN, DUST_MEM_BRAIN_IMPL, hT);
			if ( !GiskardUtils.isEmpty(cn) ) {
				ret = FACT_AGENTS.get(cn);
			}
		}

		return ret;
	}

	public static void collectFiles(final String pattern, final File folder, List<File> target) {
		if ( folder.isDirectory() ) {
			for (final File f : folder.listFiles()) {
				if ( f.isDirectory() ) {
					collectFiles(pattern, f, target);
				} else if ( f.isFile() ) {
					if ( f.getName().matches(pattern) ) {
						target.add(f);
					}
				}
			}
		}
	}

	public static void deleteRec(final File folder) {
		for (final File f : folder.listFiles()) {
			if ( f.isDirectory() ) {
				deleteRec(f);
			}
			f.delete();
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> mods = new ArrayList<>();
		mods.add(build(GISKARD_SEGMENT_MOD, "Montru", "Dust", "1.0", null));
		mods.add(build(GISKARD_SEGMENT_MOD, "Montru", "Test01", "1.0", null));

		build(GISKARD_SEGMENT_APP, "Montru", "Test01", "1.0", mods);
	}

	public static String build(String segment, String vendor, String name, String ver, List<String> mods) throws Exception {
		boolean app = GiskardUtils.isEqual(segment, GISKARD_SEGMENT_APP);

		String modId = GiskardAppModuleServices.getModuleId(vendor, name, ver);
		String modPath = modId.replaceAll(SEP_ID, File.separator);

		File fModRoot = GiskardAppUtils.getLocalFolder(GISKARD_PATH_DEV, segment);
		File fModDir = new File(fModRoot, modPath);

		File dSrc = new File(fModDir, "src");

		List<File> arrSources = new ArrayList<>();
		collectFiles(".*\\.java", dSrc, arrSources);

		StringBuilder sb = GiskardUtils.sbAppend(null, File.pathSeparator, false, dSrc);

		if ( app ) {
			File f1 = GiskardAppUtils.getLocalFolder(GISKARD_PATH_DEV, "/Giskard" + GISKARD_SEGMENT_APP + "/src");
			collectFiles(".*\\.java", f1, arrSources);
			File f2 = GiskardAppUtils.getLocalFolder(GISKARD_PATH_DEV, "/Giskard" + GISKARD_SEGMENT_MOD + "/src");
			collectFiles(".*\\.java", f2, arrSources);

			GiskardUtils.sbAppend(sb, File.pathSeparator, false, f1, f2);

			for (String m : mods) {
				File m1 = GiskardAppUtils.getLocalFolder(GISKARD_PATH_DEV, GISKARD_SEGMENT_MOD);
				File m2 = new File(m1, m);

				File dTok = new File(m2, "shared");
				if ( dTok.isDirectory() ) {
					collectFiles(".*\\.java", dTok, arrSources);
					GiskardUtils.sbAppend(sb, File.pathSeparator, false, dTok);
				}
			}
		}

		if ( !arrSources.isEmpty() ) {
			File fBuildDir = new File(GiskardAppUtils.getLocalFolder(GISKARD_PATH_BUILD, segment), modPath);
			if ( fBuildDir.exists() ) {
				deleteRec(fBuildDir);
			} else {
				fBuildDir.mkdirs();
			}

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			CompilationTask task;

			StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);

			task = compiler.getTask(null, fm, null, Arrays.asList("-sourcepath", sb.toString(), "-d", fBuildDir.toString()), null, fm.getJavaFileObjectsFromFiles(arrSources));

			if ( task.call() ) {
				Manifest manifest = new Manifest();
				Attributes maniAtts = manifest.getMainAttributes();
				maniAtts.put(Attributes.Name.MANIFEST_VERSION, "1.0");
				if ( app ) {
					maniAtts.put(Attributes.Name.MAIN_CLASS, GiskardApp.class.getCanonicalName());
				}
				File fJar = new File(GiskardAppUtils.getLocalFolder(GISKARD_PATH_BRAIN, segment), modId + EXT_JAR);

				fJar.getParentFile().mkdirs();

				JarOutputStream target = new JarOutputStream(new FileOutputStream(fJar), manifest);
				add(fBuildDir, null, target);
				if ( app ) {
					addFileToJar(GISKARD_APP_CONFIG_NAME, new File(dSrc, GISKARD_APP_CONFIG_NAME), target);
				}
				target.close();
			} else {
				throw new RuntimeException("Unexpected compilation failure");
			}
		}

		return modPath;
	}

	private static void add(File source, String prefix, JarOutputStream target) throws IOException {
		String name = source.getPath().replace("\\", "/");

		if ( null == prefix ) {
			prefix = name;
		}

		name = name.substring(prefix.length());

		if ( name.startsWith("/") ) {
			name = name.substring(1);
		}

		if ( source.isDirectory() ) {
			if ( !name.isEmpty() && !name.endsWith("/") ) {
				name += "/";
			}
			JarEntry entry = new JarEntry(name);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			target.closeEntry();
			for (File nestedFile : source.listFiles()) {
				add(nestedFile, prefix, target);
			}
		} else {
			addFileToJar(name, source, target);
		}
	}

	public static void addFileToJar(String name, File source, JarOutputStream target) throws IOException, FileNotFoundException {
		JarEntry entry = new JarEntry(name);
		entry.setTime(source.lastModified());
		target.putNextEntry(entry);
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if ( count == -1 )
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		}
	}
}
