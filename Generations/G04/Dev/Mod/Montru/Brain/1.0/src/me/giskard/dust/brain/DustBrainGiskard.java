package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.boot.DustBootConsts;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustGiskard {

	final DustBrainJourney journey;

	public DustBrainGiskard() {
		journey = new DustBrainJourney();
	}

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
		return journey.access(cmd, val, ref, att, key);
	}
	
	@Override
	public void broadcast_(MiNDHandle event, Object... params) {
		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", event, params);	
	}

	@Override
	public void initBootJourney(GisCollFactory<Long, DustHandle> bootFact) throws Exception {
		DustBrainJavaUtils.initBootJourney(bootFact, journey.localKnowledge);		
		DustHandle.FMT = new DustBrainUtils.JourneyHandleFormatter(journey);
		Giskard.log(null, "initBrain complete", journey.localKnowledge);
	}
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		return journey.mindAgentStep();
	}
}
