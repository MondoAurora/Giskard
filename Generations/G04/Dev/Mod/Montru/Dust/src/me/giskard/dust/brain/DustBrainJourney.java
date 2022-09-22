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
		
		if ((null == key) && (cmd == MiNDAccessCommand.Peek) ) {
			return CollType.One;
		}
		
		DustBrainKnowledge eMember = eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE,
				CollType.Map, hMember);
		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set,
				IDEA_TAG_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(hMember) ) {
				h = HANDLE2ENUM.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set, IDEA_TAG_COLLTYPE);
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
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = null;
		int last = valPath.length - 1;
		int i = -1;

			if ( 0 > last ) {
				// probably create a temporary entity?
			} else {
				ret = valPath[0];

				for (i = 1; i <= last;) {
					DustBrainKnowledge e = resolveHandle((MiNDHandle) ret);
					MiNDHandle h = (DustHandle) valPath[i++];
					Object key = (i <= last) ? valPath[i] : null;

					CollType ct = getCollType(h, MiNDAccessCommand.Peek, key);
					if ( (null != ct) && ct.hasKey ) {
						++i;
					}

					if ( null == e ) {
						break;
					} else {
						ret = (i < last) ? e.access(MiNDAccessCommand.Peek, null, h, ct, key)
								: e.access(cmd, val, h, getCollType(h, cmd, key), key);
					}
				}
			}

		if ( null == ret ) {
			switch ( cmd ) {
			case Check:
			case Delete:
				ret = false;
				break;
			case Get:
				if ( i == last ) {
					// also, put?
				} else {
					ret = val;
				}
				break;
			case Insert:
				ret = null;
				break;
			case Peek:
				ret = val;
				break;
			case Set:
				ret = null;
				break;
			}
		}

		return (RetType) ret;
	}
}
