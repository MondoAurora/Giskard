package me.giskard.dust.brain;

public class DustBrainJourney implements DustBrainConsts {

	final DustBrainKnowledge eJourney;

	public boolean isLearning(MiNDHandle hMember) {
		Boolean learning = eJourney.access(MiNDAccessCommand.Check, GENERIC_TAG_LENIENT, MODEL_MEM_KNOWLEDGE_TAGS,
				CollType.Set, null);

		if ( Boolean.TRUE.equals(learning) ) {
			return true;
		}

		return false;
	}

	public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {

		if ( (null == key) && (cmd == MiNDAccessCommand.Peek) ) {
			return CollType.One;
		}

		DustBrainKnowledge eMember = resolveHandle(hMember);
		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Map,
				IDEA_TAG_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(hMember) ) {
				h = HANDLE2ENUM.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Map, IDEA_TAG_COLLTYPE);
			}
		} else {
			ret = HANDLE2ENUM.getRight(h);
		}

		return ret;
	}

	public DustBrainJourney() {
		eJourney = new DustBrainKnowledge(null);
	}

	public DustBrainKnowledge resolveHandle(MiNDHandle h) {
		return eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, h);
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
		Object ret = null;
		CollType ct = getCollType(att, cmd, key);
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
			ret = e.access(cmd, val, att, ct, key);
		}
		
		return (RetType) ret;
	}

}
