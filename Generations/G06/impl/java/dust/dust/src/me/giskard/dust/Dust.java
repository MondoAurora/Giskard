package me.giskard.dust;

import me.giskard.dust.utils.DustUtils;

public class Dust implements DustConsts {
	
	public static void log(MindHandle event, Object... params) {
		StringBuilder sb = DustUtils.sbAppend(null, " ", false, params);
		System.out.println(event + " " + sb);
	}

}
