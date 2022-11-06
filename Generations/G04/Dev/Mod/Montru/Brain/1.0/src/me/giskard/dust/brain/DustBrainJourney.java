package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustBrainJourney implements DustBrainConsts, GiskardConsts.MiNDAgent {
	final DustBrainKnowledge localKnowledge;

	public DustBrainJourney() {
		localKnowledge = new DustBrainKnowledge(null);
//		localKnowledge.access(MiNDAccessCommand.Set, localKnowledge, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.One, null);
	}

	private DustBrainKnowledge handleToKnowledge(MiNDHandle h) {
		DustBrainKnowledge ret = null;

//		MiNDHandle hAgent = localKnowledge.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_AGENT, null, null);
		MiNDHandle hAgent = localKnowledge.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_AGENT, CollType.One, null);

		if ( (null != hAgent) && (h != hAgent) ) {
			DustBrainKnowledge eAgent = handleToKnowledge(hAgent);
			Object o = eAgent.access(MiNDAccessCommand.Peek, null, h, CollType.One, null);
			if ( o instanceof MiNDHandle) {
				h = (MiNDHandle) o;
			}
		}

		for (DustBrainKnowledge from = localKnowledge; (null == ret) && (null != from); from = from.access(MiNDAccessCommand.Peek, null, GENERIC_MEM_GEN_OWNER, CollType.One, null)) {
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
					long li = localKnowledge.access(MiNDAccessCommand.Peek, 0L, MODEL_MEM_SOURCE_LASTID, CollType.One, null);
					ref = new DustHandle(--li);
					localKnowledge.access(MiNDAccessCommand.Set, li, MODEL_MEM_SOURCE_LASTID, CollType.One, null);
					ret = e = new DustBrainKnowledge(ref);
					localKnowledge.access(MiNDAccessCommand.Insert, ret, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, ref);
					if ( null != key ) {
						e.access(MiNDAccessCommand.Set, key, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, CollType.One, null);
					}
				}
				return (RetType) ref;
			} else {
				e = localKnowledge;
//				e = handleToKnowledge(att);
			}
		} else {
			e = handleToKnowledge(ref);
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
				localKnowledge.access(MiNDAccessCommand.Insert, e, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, ref);
			}
				call = true;
				break;
			}
		}

		if ( call ) {
			CollType ct = (null == att) ? CollType.Map : DustBrainUtils.getCollType(localKnowledge, handleToKnowledge(att), cmd, key);
			ret = e.access(cmd, val, att, ct, key);
		}

		return (RetType) ret;
	}

	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		MiNDAgent a = DustBrainUtils.getAgent();

		if ( null != a ) {
			try {
				Giskard.log("Executing agent", a);
				ret = a.mindAgentStep();
			} catch (Throwable t) {
				Giskard.log("Exception in agent", a);
				t.printStackTrace();
			} finally {

			}
		} else {
			Giskard.log("No agent in the journey");
		}

		return ret;
	}
}
