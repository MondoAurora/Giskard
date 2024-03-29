package me.giskard.tools;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class GisToolsUtils {
	public static void logMemUsage() {
		MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
		Giskard.log(null, "Memory usage - heap:", mem.getHeapMemoryUsage(), "non heap:",
				mem.getNonHeapMemoryUsage());
	}
	
	public static class DevTimer {
		String name;
		long start;

		public DevTimer(String header) {
			this.name = header;
			this.start = System.currentTimeMillis();
			Giskard.log(null, header, "starting...");
		}

		@Override
		public String toString() {
			return name + " Elapsed: " + ((double) System.currentTimeMillis() - start) / 1000 + "(s)";
		}

		public void log() {
			Giskard.log(null, this);
		}
	}

	public static class DevMonitor extends DevTimer {
		boolean showMem = false;

		long interval;
		long next;

		long totalCount;
		long lastCount;

		public DevMonitor(String name, long interval) {
			super(name);
			this.interval = interval;
			reset();
		}

		public void reset() {
			totalCount = lastCount = 0;
			start = System.currentTimeMillis();
			next = start + interval;
		}

		public void setShowMem(boolean showMem) {
			this.showMem = showMem;
		}

		public boolean step() {
			boolean ret = false;
			++totalCount;
			++lastCount;
			long t = System.currentTimeMillis();
			if ( t > next ) {
				next = t + interval;
				if ( showMem ) {
					logMemUsage();
				}
				log();
				lastCount = 0;
				ret = true;
			}
			return ret;
		}

		@Override
		public String toString() {
			return GiskardUtils
					.sbAppend(null, " ", false, super.toString(), "Total count:", totalCount, "Since last:", lastCount)
					.toString();
		}
	}
}
