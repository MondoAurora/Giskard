package ai.montru.dust.app.test01;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public class T01Agent01 implements T01Consts, GiskardConsts.GiskardAgent {

	@Override
	public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
		Giskard.broadcastEvent(null, getClass().getSimpleName(), "says: Hello, world!");

		return GiskardResponse.Accept;
	}

}
