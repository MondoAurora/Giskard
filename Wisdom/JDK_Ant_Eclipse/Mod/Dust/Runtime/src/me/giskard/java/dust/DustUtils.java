package me.giskard.java.dust;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.giskard.java.MindUtils;

public class DustUtils extends MindUtils implements DustConsts {
	
	public static String strTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP_DEFAULT);
		return sdf.format(new Date());
	}

	public static String strDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_DEFAULT);
		return sdf.format(new Date());
	}

	public static String strTimestamp(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP_DEFAULT);
		return sdf.format(new Date(time));
	}
}
