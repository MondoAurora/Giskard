package me.giskard.modules;

import me.giskard.Giskard;
import me.giskard.GiskardConsts.GiskardModule;
import me.giskard.dust.node.DustAgentNode;
import me.giskard.dust.node.DustBootLoader;

public class GisDustNode implements GiskardModule {

	@Override
	public void initModule(Giskard runtime) throws Exception {
		String modName = this.getClass().getSimpleName();
		
		Giskard.broadcastEvent(null, "Initializing runtime", modName, "...");
		
		Giskard.broadcastEvent(null, "Registering boot refs...");
		
		DustBootLoader.boot();
		
		Giskard.broadcastEvent(null, "Creating node...");
		DustAgentNode node = new DustAgentNode();
		
		node.setRuntime(runtime);
		
		Giskard.broadcastEvent(null, "SUCCESS initialization of runtime", modName);
	}
	
	

}
