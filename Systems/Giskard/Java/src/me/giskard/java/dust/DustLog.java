package me.giskard.java.dust;

import java.io.PrintStream;

import me.giskard.java.MindConsts;

public class DustLog implements DustConsts, MindConsts.MiNDLog {
  static PrintStream psLog = System.out;

	@Override
	public void log(MiNDToken lvl, Object... obs) {
    long ts = 0;
    StringBuilder sb = null;

    for (Object o : obs) {
        String s = DustUtils.toString(o);

        if ((0 == ts) && !s.trim().isEmpty()) {
            ts = System.currentTimeMillis();
            psLog.print(DustUtils.strTimestamp(ts));
            psLog.print(" ");
            psLog.print(lvl);
            psLog.print(" ");
        }
        psLog.print(s);
        psLog.print(" ");
        
        sb = DustUtils.sbAppend(sb, " ", true, s);
    }
	}

}
