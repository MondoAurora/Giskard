package me.giskard.dust.runtime.machine;

import me.giskard.dust.runtime.DustRuntimeConsts;

public interface DustMachineConsts extends DustRuntimeConsts {
	
	interface NativeConnector extends MiNDAgent {
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath);

	}
}
