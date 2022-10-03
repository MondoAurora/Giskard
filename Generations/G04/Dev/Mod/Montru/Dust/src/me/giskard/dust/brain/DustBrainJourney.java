package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustBrainJourney implements DustBrainConsts, GiskardConsts.MiNDAgent {

	final DustBrainKnowledge eJourney;

	public DustBrainJourney() {
		eJourney = new DustBrainKnowledge(null);
	}

	private DustBrainKnowledge resolveHandle(MiNDHandle h) {
		DustBrainKnowledge ret = null;

		MiNDHandle hA = eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_AGENT, null, null);

		if ( (null != hA) && (h != hA) ) {
			DustBrainKnowledge eAgent = resolveHandle(hA);
			MiNDHandle o = eAgent.access(MiNDAccessCommand.Peek, null, h, CollType.One, null);
			if ( null != o ) {
				h = o;
			}
		}

		for (DustBrainKnowledge from = eJourney; (null == ret) && (null != from); from = from.access(MiNDAccessCommand.Peek, null, GENERIC_MEM_GEN_OWNER,
				CollType.One, null)) {
			ret = from.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, h);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
		Object ret = null;
		DustBrainKnowledge e = null;

		if ( null == ref ) {
			if ( null == att ) {
				if ( cmd == MiNDAccessCommand.Get ) {
					long li = eJourney.access(MiNDAccessCommand.Peek, 0L, MODEL_MEM_SOURCE_LASTID, CollType.One, null);
					ref = new DustHandle(--li);
					eJourney.access(MiNDAccessCommand.Set, li, MODEL_MEM_SOURCE_LASTID, CollType.One, null);
					ret = e = new DustBrainKnowledge(ref);
					eJourney.access(MiNDAccessCommand.Insert, ret, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, ref);
					if ( null != key ) {
						e.access(MiNDAccessCommand.Set, key, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, CollType.One, null);
					}
				}
				return (RetType) ref;
			} else {
				e = eJourney;
//				e = resolveHandle(att);
			}
		} else {
			e = resolveHandle(ref);
		}

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
//				if ( null != ref ) 
			{
				eJourney.access(MiNDAccessCommand.Insert, e, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, ref);
			}
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
		MiNDResultType ret = MiNDResultType.Accept;

		MiNDAgent a = DustBrainUtilsDev.getAgent();

		if ( null != a ) {
			try {
				Giskard.log("Executing agent", a);
				ret = a.mindAgentStep();
			} catch (Throwable t) {

			} finally {

			}
		} else {
			Giskard.log("No agent in the journey");
		}

		return ret;
	}
}
