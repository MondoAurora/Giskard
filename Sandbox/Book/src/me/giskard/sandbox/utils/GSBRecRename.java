package me.giskard.sandbox.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GSBRecRename {
	Pattern pt;
	int renameCount;
	
	public GSBRecRename(String pattern) {
		pt = Pattern.compile(pattern);
	}

	void rename(File f) {
		if ( f.isDirectory() ) {
			for (File ff : f.listFiles()) {
				rename(ff);
			}
		} else {
			String fn = f.getName();

			Matcher m = pt.matcher(fn);

			if ( m.matches() ) {
				String idx = m.group(1);

				if ( !idx.startsWith("0") ) {
					String fnn = String.format("Page_%04d%s", Integer.parseInt(idx), m.group(2));
					f.renameTo(new File(f.getParentFile(), fnn));
					++renameCount;
				}
			}

		}
	}
	
	@Override
	public String toString() {
		return "Renamed " + renameCount + " files.";
	}
}