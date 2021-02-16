package me.giskard.mod;

import me.giskard.Mind;
import me.giskard.MindConsts;

public class DustRuntime implements MindConsts.MiNDAgent {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case INIT:
			Mind mind = (Mind) Class.forName("me.giskard.dust.runtime.mind.DustMind").newInstance();
			Mind.setMind(mind);
			mind.initContext();
			break;
		case BEGIN:
			Mind.log(MiNDEventLevel.INFO, "DustRuntime launch...");
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
