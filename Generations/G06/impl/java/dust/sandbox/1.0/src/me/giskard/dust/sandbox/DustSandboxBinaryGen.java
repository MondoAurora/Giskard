package me.giskard.dust.sandbox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import me.giskard.dust.utils.DustUtils;

public class DustSandboxBinaryGen implements DustSandboxConsts {

	public static String getModuleId(String vendor, String mod, String ver) {
		return vendor + DUST_SEP + mod + DUST_SEP + ver;
	}

	public static void collectFiles(final String pattern, final File folder, List<File> target) {
		if (folder.isDirectory()) {
			for (final File f : folder.listFiles()) {
				if (f.isDirectory()) {
					collectFiles(pattern, f, target);
				} else if (f.isFile()) {
					if (f.getName().matches(pattern)) {
						target.add(f);
					}
				}
			}
		}
	}

	public static void deleteRec(final File folder) {
		for (final File f : folder.listFiles()) {
			if (f.isDirectory()) {
				deleteRec(f);
			}
			f.delete();
		}
	}

	public static void buildAll(File fPrjRoot, File fModDir) throws Exception {
		List<File> arrProjects = new ArrayList<>();
		collectFiles(".project", fPrjRoot, arrProjects);
		
		for ( File pf : arrProjects ) {
			File pd = pf.getParentFile();
			
			if ( new File(pd, "dust_gen.txt").isFile()) {
				continue;
			}
			
			build(pd, fModDir);
		}
	}

	public static String build(File fPrj, File fModDir) throws Exception {
		String[] pp = fPrj.getCanonicalPath().split(File.separator);
		int i = pp.length;
		String ver = pp[--i];
		String name = pp[--i];
		String vendor = pp[--i];

		String modId = getModuleId(vendor, name, ver);

		File dSrc = new File(fPrj, "src");
		List<File> arrSources = new ArrayList<>();
		collectFiles(".*\\.java", dSrc, arrSources);

		StringBuilder sb = DustUtils.sbAppend(null, File.pathSeparator, false, dSrc);

		if (!arrSources.isEmpty()) {
			File fBuildDir = new File("build/" + modId);
			if (fBuildDir.exists()) {
				deleteRec(fBuildDir);
			} else {
				fBuildDir.mkdirs();
			}

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);
			CompilationTask task = compiler.getTask(null, fm, null, Arrays.asList("-sourcepath", sb.toString(), "-d", fBuildDir.toString()),
					null, fm.getJavaFileObjectsFromFiles(arrSources));

			if (task.call()) {
				Manifest manifest = new Manifest();
				Attributes mfAtts = manifest.getMainAttributes();
				mfAtts.put(Attributes.Name.MANIFEST_VERSION, "1.0");
				mfAtts.put(Attributes.Name.IMPLEMENTATION_VENDOR, vendor);
				mfAtts.put(Attributes.Name.IMPLEMENTATION_TITLE, name);
				mfAtts.put(Attributes.Name.IMPLEMENTATION_VERSION, ver);
//				if (app) {
//					maniAtts.put(Attributes.Name.MAIN_CLASS, GiskardApp.class.getCanonicalName());
//				}

				fModDir.mkdirs();
				File fJar = new File(fModDir, modId + DUST_EXT_JAR);

				JarOutputStream target = new JarOutputStream(new FileOutputStream(fJar), manifest);
				add(fBuildDir, null, target);
//				if (app) {
//					addFileToJar(GISKARD_APP_CONFIG_NAME, new File(dSrc, GISKARD_APP_CONFIG_NAME), target);
//				}
				target.close();
			} else {
				throw new RuntimeException("Unexpected compilation failure");
			}
		}

		return null;
	}

	private static void add(File source, String prefix, JarOutputStream target) throws Exception {
		String name = source.getPath().replace("\\", "/");

		if (null == prefix) {
			prefix = name;
		}

		name = name.substring(prefix.length());

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (source.isDirectory()) {
			if (!name.isEmpty() && !name.endsWith("/")) {
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

	public static void addFileToJar(String name, File source, JarOutputStream target) throws Exception {
		JarEntry entry = new JarEntry(name);
		entry.setTime(source.lastModified());
		target.putNextEntry(entry);
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		}
	}
}
