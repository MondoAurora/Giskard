package ai.montru.dust.app.test01;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public class T01Agent01 implements T01Consts, GiskardConsts.GiskardNativeLogic {

	@Override
	public GiskardResponse gisExecLogic() throws GiskardException {
		GiskardAction action = GiskardAction.Process;
		Giskard.broadcastEvent(null, getClass().getSimpleName(), "to command", action, "says",
				Giskard.access(GiskardAccess.Peek, "huh?", null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_ID));

		return GiskardResponse.Accept;
	}

}
