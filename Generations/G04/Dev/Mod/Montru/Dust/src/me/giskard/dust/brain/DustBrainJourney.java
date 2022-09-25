package me.giskard.dust.brain;

import me.giskard.GiskardConsts;

public class DustBrainJourney implements DustBrainConsts, GiskardConsts.MiNDAgent {

	final DustBrainKnowledge eJourney;

	public DustBrainJourney() {
		eJourney = new DustBrainKnowledge(null);
	}

	public DustBrainKnowledge resolveHandle(MiNDHandle h) {
		return eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, h);
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
		Object ret = null;
		DustBrainKnowledge e = resolveHandle(ref);
		boolean call = true;

		if ( null == e ) {
			call = false;
			switch ( cmd ) {
			case Peek:
				ret = val;
				break;
			case Check:
			case Delete:
				ret = false;
				break;
			case Get:
			case Insert:
			case Set:
				e = new DustBrainKnowledge(ref);
				eJourney.access(MiNDAccessCommand.Insert, e, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, ref);
				call = true;
				break;
			}
		}

		if ( call ) {
			DustBrainKnowledge a = resolveHandle(att);
			CollType ct = DustBrainUtils.getCollType(eJourney, a, cmd, key);
			ret = e.access(cmd, val, att, ct, key);
		}
		
		return (RetType) ret;
	}

	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		return MiNDResultType.Accept;
	}
}
