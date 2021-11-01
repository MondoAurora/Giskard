package me.giskard.dust.node.agents;

public interface DustNodeConsts extends DustNodeTokens {
	
	interface DustRuntime extends GiskardAgent {
		Object resolve(GiskardEntityRef ref);
	}

}
