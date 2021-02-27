package me.giskard.mod;

import me.giskard.MindConsts;

public class DustText implements MindConsts.MiNDAgent {
	void initModule() throws Exception {
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			initModule();
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
