package me.giskard.app;

import java.io.File;
import java.net.URL;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

public class GiskardAppUtils extends GiskardUtils implements GiskardAppConsts {
		
	public static String getRoot() {
		String root = System.getenv("GISKARD");
		return isEmpty(root) ? System.getenv("GISKARD_ECLIPSE") : root;
	}
	
	public static File getLocalFolder(String role, String segment) {
		String root = GiskardAppUtils.getRoot();

		if ( !GiskardAppUtils.isEmpty(root) ) {
			return new File(root + role + segment);
		} else {
			throw new RuntimeException("GISKARD local filesystem root not found!");
		}
	}
	
	public static URL optGetFileUrl(File root, String name) {
		File f = GiskardAppUtils.isEmpty(name) ? root : new File(root, name);

		try {
			return f.isFile() ? f.toURI().toURL() : null;
		} catch (Throwable t) {
			return GiskardException.wrap(t, "URL creation", f.getAbsolutePath());
		}
	}
	
}
