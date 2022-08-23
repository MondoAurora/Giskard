package me.giskard.dust.brain;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustTokens;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainUtilsDev implements DustBrainConsts {

	private static final GisToolsTranslator<String, MiNDHandle> HANDLETYPE = new GisToolsTranslator<>();

	static {
		HANDLETYPE.add("STO", MODEL_TYP_STORE);
		HANDLETYPE.add("UNI", IDEA_TYP_UNIT);
		HANDLETYPE.add("TYP", IDEA_TYP_TYPE);
		HANDLETYPE.add("MEM", IDEA_TYP_MEMBER);
		HANDLETYPE.add("TAG", IDEA_TYP_TAG);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadHandles(Class<?> handleContainer, MiNDHandle hStore, MiNDHandle hLang) throws Exception {
		GisCollFactory<String, Map> factMeta = new GisCollFactory<>(true, HashMap.class);

		for (Field f : DustTokens.class.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				if ( null != hStore ) {
					Giskard.access(MiNDAccessCommand.Set, hStore, h, MODEL_MEM_ENTITY_STORE);
				}

				String[] ps = hn.split(SEP_ID);

				MiNDHandle hT = HANDLETYPE.getRight(ps[1]);
				if ( null != hT ) {
					factMeta.get(ps[1]).put(hn, h);
					Giskard.access(MiNDAccessCommand.Set, hT, h, MODEL_MEM_ENTITY_PRIMARYTYPE);
					
					Giskard.access(MiNDAccessCommand.Insert, hn, hLang, LANG_MEM_LANG_TERMINOLOGY, h);
					Giskard.access(MiNDAccessCommand.Insert, h, hLang, LANG_MEM_LANG_GLOSSARY, hn);
				}
			}
		}

		for (Field f : DustTokens.class.getDeclaredFields()) {
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
						Giskard.access(MiNDAccessCommand.Set, hU, h, MODEL_MEM_ENTITY_UNIT);
					}
				} else if ( "UNI".equals(ps[1]) ) {
					Giskard.access(MiNDAccessCommand.Set, h, h, MODEL_MEM_ENTITY_UNIT);					
				}

				switch ( ps[1] ) {
				case "TAG":
					MiNDHandle hClass = (MiNDHandle) factMeta.get("TAG").get(prefix);
					if ( null != hClass ) {
						Giskard.access(MiNDAccessCommand.Set, hClass, h, MODEL_MEM_ENTITY_OWNER);
					}
					break;
				case "MEM":
					MiNDHandle hT = (MiNDHandle) factMeta.get("TYP").get(prefix.replace("MEM", "TYP"));
					if ( null != hT ) {
						Giskard.access(MiNDAccessCommand.Insert, h, hT, IDEA_MEM_TYPE_MEMBERS);
						Giskard.access(MiNDAccessCommand.Set, hT, h, MODEL_MEM_ENTITY_OWNER);
					}
					break;
				}
			}
		}
	}

}
