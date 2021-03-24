package me.giskard.dust.runtime;

import me.giskard.GiskardConsts;

public interface DustRuntimeBootConsts extends GiskardConsts {
	String MODULE_NAME = "DustRuntime";
	
	int HANDLE_NULL = 0;
	int HANDLE_START = 1;
	int HANDLE_DIALOG = -1;
	int HANDLE_INVOCATION = -2;
	
	public interface DustGiskard {
		void init(MiNDAgent agent) throws Exception;
		void afterBoot() throws Exception;
	}

}
