package me.giskard.dust.brain;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustTokens;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainUtilsDev implements DustBrainConsts, GisCollConsts {

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

	private static final GisToolsTranslator<String, MiNDHandle> HANDLETYPE = new GisToolsTranslator<>();

	static {
		HANDLETYPE.add("UNI", MODEL_TYP_UNIT);
		HANDLETYPE.add("TYP", IDEA_TYP_TYPE);
		HANDLETYPE.add("MEM", IDEA_TYP_MEMBER);
		HANDLETYPE.add("TAG", IDEA_TYP_TAG);
		HANDLETYPE.add("SRC", MODEL_TYP_SOURCE);
		HANDLETYPE.add("AGT", NARRATIVE_TYP_AGENT);
	}

	public static void initBootJourney(GisCollFactory<Long, DustHandle> bootFact, DustBrainJourney e) throws Exception {
		for (Long k : bootFact.keys()) {
			DustHandle dh = (DustHandle) bootFact.peek(k);
			DustBrainKnowledge be = new DustBrainKnowledge(dh);
			e.eJourney.access(MiNDAccessCommand.Insert, dh, NARRATIVE_MEM_JOURNEY_HANDLES, CollType.Map, k);
			e.eJourney.access(MiNDAccessCommand.Insert, be, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, dh);
		}

		loadEnums(DustTokens.class, ValType.class, "IDEA_TAG_VALTYPE");
		loadEnums(DustTokens.class, CollType.class, "IDEA_TAG_COLLTYPE");

		loadEnums(DustTokens.class, MiNDAccessCommand.class, "NARRATIVE_TAG_ACCESS");
		loadEnums(DustTokens.class, MiNDAction.class, "NARRATIVE_TAG_ACTION");
		loadEnums(DustTokens.class, MiNDResultType.class, "NARRATIVE_TAG_RESULT");

		e.eJourney.access(MiNDAccessCommand.Insert, GENERIC_TAG_LENIENT, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set, null);
		e.eJourney.access(MiNDAccessCommand.Set, DUST_LANG_BOOT, DUST_MEM_BRAIN_DEF_LANG, CollType.One, null);

		loadHandles(DustTokens.class, DUST_LANG_BOOT);

		DustHandle.FMT = new JourneyHandleFormatter(e);

		Giskard.log(null, "initBrain complete", e.eJourney);
	}

	@SuppressWarnings({ "rawtypes" })
	public static void loadEnums(Class<?> handleContainer, Class<?> cEnum, String prefix) throws Exception {
		for (Object e : cEnum.getEnumConstants()) {
			String n = ((Enum) e).name().toUpperCase();
			try {
				Field f = handleContainer.getDeclaredField(prefix + "_" + n);
				MiNDHandle h = (MiNDHandle) f.get(null);
				HANDLE2ENUM.add(h, (Enum) e);
			} catch (Throwable ex) {
				Giskard.log(ex, cEnum, n);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadHandles(Class<?> handleContainer, MiNDHandle hLang) throws Exception {
		GisCollFactory<String, Map> factMeta = new GisCollFactory<>(true, HashMap.class);

		for (Field f : handleContainer.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				Giskard.access(MiNDAccessCommand.Set, h.getId(), h, MODEL_MEM_KNOWLEDGE_SOURCEID);

				String[] ps = hn.split(SEP_ID);

				MiNDHandle hT = HANDLETYPE.getRight(ps[1]);
				if ( null != hT ) {
					factMeta.get(ps[1]).put(hn, h);
					Giskard.access(MiNDAccessCommand.Set, hT, h, MODEL_MEM_KNOWLEDGE_PRIMARYTYPE);

					Giskard.access(MiNDAccessCommand.Insert, hn, hLang, LANG_MEM_LANG_TERMINOLOGY, h);
					Giskard.access(MiNDAccessCommand.Insert, h, hLang, LANG_MEM_LANG_GLOSSARY, hn);
				}
			}
		}

		for (Field f : handleContainer.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				int li = hn.lastIndexOf(SEP_ID);
				String prefix = hn.substring(0, li);

				String[] ps = hn.split(SEP_ID);

				if ( 2 < ps.length ) {
					MiNDHandle hU = (MiNDHandle) factMeta.get("UNI").get(ps[0] + "_UNI");
					if ( null != hU ) {
						Giskard.access(MiNDAccessCommand.Set, hU, h, MODEL_MEM_KNOWLEDGE_UNIT);
					}
				} else if ( "UNI".equals(ps[1]) ) {
					Giskard.access(MiNDAccessCommand.Set, h, h, MODEL_MEM_KNOWLEDGE_UNIT);
				}

				switch ( ps[1] ) {
				case "TAG":
					MiNDHandle hClass = (MiNDHandle) factMeta.get("TAG").get(prefix);
					if ( null != hClass ) {
						Giskard.access(MiNDAccessCommand.Set, hClass, h, GENERIC_MEM_GEN_OWNER);
					}
					break;
				case "MEM":
					MiNDHandle hT = (MiNDHandle) factMeta.get("TYP").get(prefix.replace("MEM", "TYP"));
					if ( null == hT ) {
						hT = (MiNDHandle) factMeta.get("AGT").get(prefix.replace("MEM", "AGT"));
					}
					if ( null != hT ) {
						Giskard.access(MiNDAccessCommand.Insert, h, hT, IDEA_MEM_TYPE_MEMBERS);
						Giskard.access(MiNDAccessCommand.Set, hT, h, GENERIC_MEM_GEN_OWNER);
					}
					break;
				}
			}
		}
	}

	private static GisCollFactory<String, MiNDAgent> FACT_AGENTS = new GisCollFactory<String, MiNDAgent>(true,
			new MiNDCreator<String, MiNDAgent>() {

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
