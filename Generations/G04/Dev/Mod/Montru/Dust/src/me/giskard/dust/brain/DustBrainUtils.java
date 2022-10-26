package me.giskard.dust.brain;

import me.giskard.tools.GisToolsTranslator;

public class DustBrainUtils implements DustBrainConsts {
	@SuppressWarnings("rawtypes")
	static final GisToolsTranslator<MiNDHandle, Enum> HANDLE2ENUM = new GisToolsTranslator<>();
	
	static void addEnum(MiNDHandle h, Enum<?> e) {
		HANDLE2ENUM.add(h, e);
	}

	public static CollType guessCollType(MiNDAccessCommand cmd, Object key) {
		return (null == key)
				? (cmd == MiNDAccessCommand.Set) ? CollType.One : (cmd == MiNDAccessCommand.Insert) ? CollType.Set : null
				: (key instanceof Number) ? CollType.Arr : CollType.Map;
	}

	public static ValType guessValType(Object val) {
		if ( null == val ) {
			return ValType.Any;
		} else if ( val instanceof Number ) {
			return Long.class.isAssignableFrom(val.getClass()) ? ValType.Int : ValType.Real;
		} else {
			return (val instanceof MiNDHandle) ? ValType.Link : ValType.Raw;
		}
	}

	public static boolean isLearning(DustBrainKnowledge eJourney, DustBrainKnowledge eMember) {
		Boolean learning = eJourney.access(MiNDAccessCommand.Check, GENERIC_TAG_LENIENT, MODEL_MEM_KNOWLEDGE_TAGS,
				CollType.Set, null);

		if ( Boolean.TRUE.equals(learning) ) {
			return true;
		}

		return false;
	}

	public static CollType getCollType(DustBrainKnowledge eJourney, DustBrainKnowledge eMember, MiNDAccessCommand cmd, Object key) {

		if ( (null == key) && (cmd == MiNDAccessCommand.Peek) ) {
			return CollType.One;
		}

		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Map,
				IDEA_TAG_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(eJourney, eMember) ) {
				h = HANDLE2ENUM.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Map, IDEA_TAG_COLLTYPE);
			}
		} else {
			ret = HANDLE2ENUM.getRight(h);
		}

		return ret;
	}
}
