package me.giskard.dust.node;

import me.giskard.GiskardConsts;

public interface DustNodeConsts extends GiskardConsts {
	
	DustBootLoader DUST = new DustBootLoader();

	interface DustNode extends GiskardAgent {
		DustEntity getAgentEntity();

		DustRuntime getAgentRuntime();

		Object resolve(GiskardEntityRef ref);
	}

	interface DustEntity extends GiskardAgent {
		Object access(GiskardAccessCmd cmd, Object value, Object... path);
	}

	interface DustRuntime extends GiskardAgent {
		<RetType> RetType createInstance(GiskardEntityRef binAgent);
	}

}
