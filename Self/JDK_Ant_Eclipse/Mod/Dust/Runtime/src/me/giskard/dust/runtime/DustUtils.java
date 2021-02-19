package me.giskard.dust.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.giskard.coll.MindCollConsts;
import me.giskard.utils.MindUtils;

public class DustUtils extends MindUtils implements MindCollConsts {
	
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
}
