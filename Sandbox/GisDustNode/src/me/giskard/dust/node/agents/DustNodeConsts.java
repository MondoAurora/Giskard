package me.giskard.dust.node.agents;

import me.giskard.GiskardConsts;

public interface DustNodeConsts extends GiskardConsts {
	
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
