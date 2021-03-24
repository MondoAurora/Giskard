package me.giskard.dust.runtime;

import java.io.PrintStream;

import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeLog implements GisCollConsts {
	static PrintStream psLog = System.out;

	public void log(MiNDEventLevel lvl, Object... obs) {
		long ts = 0;
		StringBuilder sb = null;

		for (Object o : obs) {
			String s = GiskardUtils.toString(o);

			if ( (0 == ts) && !s.trim().isEmpty() ) {
				ts = System.currentTimeMillis();
				psLog.print(DustRuntimeUtils.strTimestamp(ts));
				psLog.print(" ");
				psLog.print(lvl);
				psLog.print(" ");
			}
			psLog.print(s);
			psLog.print(" ");

			sb = GiskardUtils.sbAppend(sb, " ", true, s);
		}

		if ( 0 != ts ) {
			psLog.println();
		}
	}

}
