package me.giskard.dust.node.agents;

import me.giskard.GiskardConsts;

public interface DustNodeConsts extends GiskardConsts {
	
	interface DustRuntime extends GiskardAgent {
		Object resolve(GiskardEntityRef ref);
	}

}
