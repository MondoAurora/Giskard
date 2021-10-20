package me.giskard.modules;

import me.giskard.Giskard;
import me.giskard.GiskardConsts.GiskardModule;
import me.giskard.dust.node.DustAgentNode;

public class GisDustNode implements GiskardModule {

	@Override
	public void initModule(Giskard runtime) {
		String modName = this.getClass().getSimpleName();
		
		Giskard.broadcastEvent(null, "Initializing runtime", modName, "...");
		
		Giskard.broadcastEvent(null, "Registering boot refs...");
		
		Giskard.broadcastEvent(null, "Creating node...");
		DustAgentNode node = new DustAgentNode();
		
		node.optInitRuntime(runtime);
		
		Giskard.broadcastEvent(null, "SUCCESS initialization of runtime", modName);
	}
	
	

}
