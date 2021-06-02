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
	public MiNDResultType mindAgentProcess() throws Exception {
		int chr = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_VALUE_RAW);
		return ((rangeMin <= chr) && (chr <= rangeMax)) ? MiNDResultType.Accept : MiNDResultType.Reject;
	}

}
