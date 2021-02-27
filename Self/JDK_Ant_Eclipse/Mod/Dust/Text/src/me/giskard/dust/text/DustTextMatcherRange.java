package me.giskard.dust.text;

import me.giskard.MindConsts;

public class DustTextMatcherRange implements DustTextConsts, MindConsts.MiNDAgent {
	
	int rangeMin;
	int rangeMax;
	
	public DustTextMatcherRange(int rangeMin, int rangeMax) {
		super();
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Process:
			int chr = (int) params[0];
			return ((rangeMin <= chr) && (chr <= rangeMax)) ? MiNDResultType.ACCEPT : MiNDResultType.REJECT;
		default:
			return MiNDResultType.ACCEPT;
		}
	}

}
