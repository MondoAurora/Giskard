package ai.montru.modules;

import ai.montru.MontruMain;
import ai.montru.dust.node.DustNodeEntityRef;
import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;
import ai.montru.giskard.GiskardConsts.GiskardEntityRef;
import ai.montru.utils.MontruUtils;

public class GisDustNode implements GiskardConsts.GiskardModule {
	
	public interface BootModule {
		void boot(Giskard runtime, String name, String version);
	}
	
	private static final String CLASSNAME_RUNTIME = "ai.montru.dust.node.DustNodeAgentRuntime";

	@Override
	public Iterable<GiskardEntityRef> gisInitModule(Giskard runtime, String name, String version) throws Exception {
		Giskard.broadcastEvent(null, "Creating runtime agent...");
		DustNodeEntityRef.initBoot((MontruMain) runtime);
		BootModule node = MontruUtils.instantiate(CLASSNAME_RUNTIME);
		node.boot(runtime, name, version);
		
		return null;
	}

}
