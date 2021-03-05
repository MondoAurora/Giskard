package me.giskard.dust.runtime.narrative;

import me.giskard.GiskardConsts;

public class DustNarrativeRepeat implements DustNarrativeConsts, GiskardConsts.MiNDAgent {
	
	int repMin;
	int repMax;
	
	public DustNarrativeRepeat(int repMin, int repMax) {
		super();
		this.repMin = repMin;
		this.repMax = repMax;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Process:
			int chr = (int) params[0];
			return ((repMin <= chr) && (chr <= repMax)) ? MiNDResultType.ACCEPT : MiNDResultType.REJECT;
		default:
			return MiNDResultType.ACCEPT;
		}
	}

}
