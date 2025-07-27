package me.giskard.dust.boot;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtilsFile;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings("unchecked")
public class DustBootTest01 implements DustBootConsts {

	public static void test01() {

		File root = new File("units");
		int pl = root.getAbsolutePath().length();

		FileFilter ff = new DustUtilsFile.ExtFilter(DUST_EXT_JSON);

		DustUtilsFile.FileProcessor fp = new DustUtilsFile.FileProcessor() {
			@Override
			public boolean processFile(File f) {
				String id = f.getAbsolutePath().substring(pl + 1);

				try {
					ArrayList<Object> j = DustUtilsJson.readJson(f);

					Object meta = j.get(0);
					Dust.log(null, id, meta);

					ArrayList<Object> data = (ArrayList<Object>) j.get(1);

					int idx = 0;
					for (Object o : data) {
						Dust.log(null, "data", idx++, o);
						
					}
				} catch (Exception e) {
					DustException.swallow(e, "reading unit file", id);
				}
				return true;
			}
		};

		DustUtilsFile.procRecursive(root, fp, ff);

	}
}
