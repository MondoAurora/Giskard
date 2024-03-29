package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts.MiNDCreator;
import me.giskard.coll.GisCollFactory;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainUtils implements DustBrainConsts {
	static class JourneyHandleFormatter implements DustHandleFormatter {
		DustBrainJourney journey;

		public JourneyHandleFormatter(DustBrainJourney journey) {
			this.journey = journey;
		}

		@Override
		public String toString(DustHandle h) {
			String hn = journey.access(MiNDAccessCommand.Peek, null, DUST_LANG_BOOT, LANG_MEM_LANG_TERMINOLOGY, h);
			DustHandle hpt = journey.access(MiNDAccessCommand.Peek, null, h, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, null);
			String ptn = (null == hpt) ? "???"
					: journey.access(MiNDAccessCommand.Peek, "???", DUST_LANG_BOOT, LANG_MEM_LANG_TERMINOLOGY, hpt);

			return (null == hn) ? DEF_FMT.toString(h) : ptn + ":" + hn;
		}
	};

	@SuppressWarnings("rawtypes")
	static final GisToolsTranslator<MiNDHandle, Enum> HANDLE2ENUM = new GisToolsTranslator<>();
	
	public static void addEnum(MiNDHandle h, Enum<?> e) {
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
	
	private static GisCollFactory<String, MiNDAgent> FACT_AGENTS = new GisCollFactory<String, MiNDAgent>(true, new MiNDCreator<String, MiNDAgent>() {

		@Override
		public MiNDAgent create(String key) {
			try {
				return (MiNDAgent) Class.forName(key).newInstance();
			} catch (Exception e) {
				Giskard.log("Failed to create agent", key);
			}
			return null;
		}

	});

	public static MiNDAgent getAgent() {
		MiNDAgent ret = null;

		MiNDHandle hA = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_JOURNEY_AGENT, null);

		if ( null != hA ) {
			MiNDHandle hT = Giskard.access(MiNDAccessCommand.Peek, null, hA, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE, null);

			String cn = Giskard.access(MiNDAccessCommand.Peek, null, DUST_BRAIN, DUST_MEM_BRAIN_IMPL, hT);
			if ( !GiskardUtils.isEmpty(cn) ) {
				ret = FACT_AGENTS.get(cn);
			}
		}

		return ret;
	}

}
