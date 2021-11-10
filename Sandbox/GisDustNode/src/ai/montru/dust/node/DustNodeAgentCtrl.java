package ai.montru.dust.node;

import ai.montru.giskard.GiskardConsts.GiskardAgent;

public abstract class DustNodeAgentCtrl implements DustNodeConsts, GiskardAgent {

	public static class Visitor extends DustNodeAgentCtrl {

		@Override
		public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
			return GiskardResponse.Reject;
		}
		
	}
}
