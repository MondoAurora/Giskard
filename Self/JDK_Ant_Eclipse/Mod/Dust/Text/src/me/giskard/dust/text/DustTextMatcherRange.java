package me.giskard.dust.text;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextMatcherRange implements DustTextConsts, GiskardConsts.MiNDAgent {
	
	int rangeMin;
	int rangeMax;
	
	public DustTextMatcherRange(int rangeMin, int rangeMax) {
		super();
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		switch ( action ) {
		case Process:
			int chr = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_VALUE_RAW);
			return ((rangeMin <= chr) && (chr <= rangeMax)) ? MiNDResultType.Accept : MiNDResultType.Reject;
		default:
			return MiNDResultType.Accept;
		}
	}

}
