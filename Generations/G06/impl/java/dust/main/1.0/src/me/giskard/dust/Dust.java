package me.giskard.dust;

import me.giskard.dust.utils.DustUtils;

public class Dust implements DustConsts {

	private static MindMachine MACHINE;

	public static void setMachine(MindMachine m) {
		if (m != MACHINE) {

			if (null != MACHINE) {
				DustException.wrap(null, "Multiple Dust machine initialization");
			}

			MACHINE = m;
		}
	}

	public static <RetType> RetType access(MindAccess cmd, Object val, Object... path) {
		return MACHINE.access(cmd, val, path);
	}

	public static void log(MindHandle event, Object... params) {
		if (null == MACHINE) {
			StringBuilder sb = DustUtils.sbAppend(null, ", ", false, params);
			System.out.println("BOOTLOG " + event + " " + DustUtils.toString(sb));
		} else {
			access(MindAccess.Broadcast, event, params);
		}
	}

}
