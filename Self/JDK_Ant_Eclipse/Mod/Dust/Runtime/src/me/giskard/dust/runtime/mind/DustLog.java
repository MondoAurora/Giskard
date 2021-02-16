package me.giskard.dust.runtime.mind;

import java.io.PrintStream;

import me.giskard.dust.runtime.DustConsts;
import me.giskard.dust.runtime.DustUtils;
import me.giskard.utils.MindUtilsJava;

public class DustLog implements DustConsts {
	static PrintStream psLog = System.out;

	public void log(MiNDEventLevel lvl, Object... obs) {
		long ts = 0;
		StringBuilder sb = null;

		for (Object o : obs) {
			String s = MindUtilsJava.toString(o);

			if ( (0 == ts) && !s.trim().isEmpty() ) {
				ts = System.currentTimeMillis();
				psLog.print(DustUtils.strTimestamp(ts));
				psLog.print(" ");
				psLog.print(lvl);
				psLog.print(" ");
			}
			psLog.print(s);
			psLog.print(" ");

			sb = MindUtilsJava.sbAppend(sb, " ", true, s);
		}

		if ( 0 != ts ) {
			psLog.println();
		}
	}

}
