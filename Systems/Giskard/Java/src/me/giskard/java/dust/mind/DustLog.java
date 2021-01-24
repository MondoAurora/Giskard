package me.giskard.java.dust.mind;

import java.io.PrintStream;

import me.giskard.java.MindUtilsJava;
import me.giskard.java.dust.DustConsts;

public class DustLog implements DustConsts {
  static PrintStream psLog = System.out;

	public void log(MiNDToken lvl, Object... obs) {
    long ts = 0;
    StringBuilder sb = null;

    for (Object o : obs) {
        String s = MindUtilsJava.toString(o);

        if ((0 == ts) && !s.trim().isEmpty()) {
            ts = System.currentTimeMillis();
            psLog.print(MindUtilsJava.strTimestamp(ts));
            psLog.print(" ");
            psLog.print(lvl);
            psLog.print(" ");
        }
        psLog.print(s);
        psLog.print(" ");
        
        sb = MindUtilsJava.sbAppend(sb, " ", true, s);
    }
	}

}
