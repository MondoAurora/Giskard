package me.giskard.dust.forge;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import me.giskard.GiskardUtils;
import me.giskard.app.GiskardApp;
import me.giskard.app.GiskardAppModuleServices;
import me.giskard.app.GiskardAppUtils;

public class GiskardForgeUtils implements GiskardForgeConsts {

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
		mods.add(build(GISKARD_SEGMENT_MOD, "Montru", "Brain", "1.0", null));
		mods.add(build(GISKARD_SEGMENT_MOD, "Montru", "Forge", "1.0", null));
		mods.add(build(GISKARD_SEGMENT_MOD, "Montru", "Test01", "1.0", null));

		build(GISKARD_SEGMENT_APP, "Montru", "Test01", "1.0", mods);
	}

	public static String build(String segment, String vendor, String name, String ver, List<String> mods) throws Exception {
		boolean app = GiskardUtils.isEqual(segment, GISKARD_SEGMENT_APP);

		String modId = GiskardAppModuleServices.getModuleId(vendor, name, ver);
		String modPath = modId.replaceAll(SEP_ID, File.separator);

		File fModRoot = GiskardAppUtils.getLocalFolder(GISKARD_PATH_DEV, segment);
		File fModDir = new File(fModRoot, modPath);

		File dLib = new File(fModDir, "lib");
		
		if ( dLib.isDirectory() ) {
			File fExt = new File(GiskardAppUtils.getLocalFolder(GISKARD_PATH_BRAIN, GISKARD_SEGMENT_EXT), modId);
			
			fExt.mkdirs();

			for ( File f : dLib.listFiles() ) {
				Files.copy(f.toPath(), new File(fExt, f.getName()).toPath());
			}
		}

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
