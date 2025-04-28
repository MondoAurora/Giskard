package me.giskard.dust;

import me.giskard.dust.utils.DustUtils;

public class Dust implements DustConsts {

	private static MindDialog MACHINE;

	public static void setMachine(MindDialog m) {
		if (m != MACHINE) {

			if (null != MACHINE) {
				DustException.wrap(null, "Multiple Dust machine initialization");
			}

			MACHINE = m;
		}
	}

	public static <RetType> RetType access(MindToken cmd, Object val, Object... path) {
		return MACHINE.access(cmd, val, path);
	}

	public static void broadcast(MindToken event, Object... params) {
		if (null == MACHINE) {
			StringBuilder sb = DustUtils.sbAppend(null, ", ", false, params);
			System.out.println("BOOTLOG " + event + " " + DustUtils.toString(sb));
		} else {
			MACHINE.broadcast(event, params);
		}
	}

}
