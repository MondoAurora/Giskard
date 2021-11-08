package ai.montru.modules;

import ai.montru.MontruMain;
import ai.montru.dust.node.DustNodeEntityRef;
import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;
import ai.montru.utils.MontruUtils;

public class GisDustNode implements GiskardConsts.GiskardModule {
	
	private static final String CLASSNAME_RUNTIME = "ai.montru.dust.node.DustNodeAgentRuntime";

	@Override
	public void initModule(Giskard runtime) throws Exception {
		Giskard.broadcastEvent(null, "Creating runtime agent...");
		DustNodeEntityRef.initBoot((MontruMain) runtime);
		MontruMain node = MontruUtils.instantiate(CLASSNAME_RUNTIME);
		node.setRuntime(runtime);
	}

}
