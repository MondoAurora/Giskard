package me.giskard.java8;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.giskard.GiskardUtils;

public class GiskardJavaUtils extends GiskardUtils implements GiskardJavaConsts {
	
	public static String enumToId(Enum<?> eUnit, Enum<?> e) {
		StringBuilder sb = new StringBuilder(eUnit.name()).append(SEP_TOKEN).append(e.getClass().getSimpleName()).append(SEP_TOKEN).append(e.name());
		return sb.toString();
	}

	public static String tsNow() {
		SimpleDateFormat sdf = new SimpleDateFormat(FMT_TIMESTAMP);
		return sdf.format(new Date());
	}
	
}
