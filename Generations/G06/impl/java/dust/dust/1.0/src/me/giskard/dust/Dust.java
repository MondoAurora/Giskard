package me.giskard.dust;

import me.giskard.dust.utils.DustUtils;

public class Dust implements DustConsts {
	
	private static Machine MACHINE;

	public static abstract class Machine {
		protected Machine() {
			if ( null != MACHINE ) {
				DustException.wrap(null, "Multiple Dust machine initialization");
			}

			MACHINE = this;
		}

		protected abstract <RetType> RetType access(MindAccess cmd, Object val, Object... path);
	}

	public static <RetType> RetType access(MindAccess cmd, Object val, Object... path) {
		return MACHINE.access(cmd, val, path);
	}

	public static void log(MindHandle event, Object... params) {
		if ( null == MACHINE ) {
			StringBuilder sb = DustUtils.sbAppend(null, ", ", false, params);
			System.out.println(event + " " + DustUtils.toString(sb));
		} else {
			access(MindAccess.Broadcast, event, params);
		}
	}

}
