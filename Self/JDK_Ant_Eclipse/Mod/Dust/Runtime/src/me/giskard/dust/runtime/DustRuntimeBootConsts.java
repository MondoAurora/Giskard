package me.giskard.dust.runtime;

import me.giskard.GiskardConsts;

public interface DustRuntimeBootConsts extends GiskardConsts {
	String MODULE_NAME = "DustRuntime";
	String MODULE_VER = "1.0";
	
	int HANDLE_NULL = 0;
	int HANDLE_START = 1;
	int HANDLE_INVOCATION = -1;
	int HANDLE_DIALOG = -2;
	int HANDLE_MACHINE = -3;
	int HANDLE_THIS = -4;
	
	public interface DustGiskard {
		void init(MiNDAgent agent) throws Exception;
		void afterBoot();
	}

	public interface DustNotifier {
		boolean notify(MiNDAccessCommand cmd, Object block, Object valOld, Object valNew, MiNDToken member, Object key);
	}
	
	DustNotifier NULL_NOTIF = new DustNotifier() {
		@Override
		public boolean notify(MiNDAccessCommand cmd, Object block, Object valOld, Object valNew, MiNDToken member, Object key) {
			// Do nothing
			return false;
		}
	};

}
