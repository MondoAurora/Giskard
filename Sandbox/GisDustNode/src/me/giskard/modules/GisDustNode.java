package me.giskard.modules;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;

public class GisDustNode implements GiskardConsts.GiskardModule {
	
	private static final String CLASSNAME_RUNTIME = "me.giskard.dust.node.DustNodeAgentRuntime";

	@Override
	public void initModule(Giskard runtime) throws Exception {
		Giskard.broadcastEvent(null, "Creating runtime agent...");
		GiskardMain node = GiskardUtils.instantiate(CLASSNAME_RUNTIME);
		node.setRuntime(runtime);
	}

}
