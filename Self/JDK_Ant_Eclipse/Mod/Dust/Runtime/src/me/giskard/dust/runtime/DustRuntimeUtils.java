package me.giskard.dust.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.MindCollConsts;

public class DustRuntimeUtils extends GiskardUtils implements MindCollConsts, DustRuntimeConsts {
	static boolean BOOT_DONE = false;
	
	public static boolean isBootDone() {
		return BOOT_DONE;
	}
	
	public static String strTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date());
	}

	public static String strDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_DATE);
		return sdf.format(new Date());
	}

	public static String strTimestamp(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date(time));
	}
	
	@SuppressWarnings("unchecked")
	public static <CType> Class<CType> getRuntimeClass(String path) {
		String cn = PACKAGENAME_RUNTIME + "." + path;
		try {
			return (Class<CType>) Class.forName(cn);
		} catch (ClassNotFoundException e) {
			return GiskardException.wrap(e, "Failed to load class", cn);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <CType> CType createRuntimeComponent(String path) {
			try {
				return (CType) getRuntimeClass(path).newInstance();
			} catch (Exception e) {
				return GiskardException.wrap(e, "createRuntimeComponent Failed to create", path);
			}
	}
}
