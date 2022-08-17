package me.giskard.dust.brain;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustConsts;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainUtilsDev implements DustBrainConsts {

	private static final GisToolsTranslator<String, MiNDHandle> HANDLETYPE = new GisToolsTranslator<>();

	static {
		HANDLETYPE.add("STO", GIS_TYP_MODEL_STORE);
		HANDLETYPE.add("UNI", GIS_TYP_IDEA_UNIT);
		HANDLETYPE.add("TYP", GIS_TYP_IDEA_TYPE);
		HANDLETYPE.add("MEM", GIS_TYP_IDEA_MEMBER);
		HANDLETYPE.add("TAG", GIS_TYP_IDEA_TAG);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadHandles(Class<?> handleContainer, MiNDHandle hStore) throws Exception {
		GisCollFactory<String, Map> factMeta = new GisCollFactory<>(true, HashMap.class);

		for (Field f : DustConsts.class.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				if ( null != hStore ) {
					Giskard.access(MiNDAccessCommand.Set, hStore, h, GIS_MEM_MODEL_ENTITY_STORE);
				}

				int li = hn.lastIndexOf(SEP_ID);
//				String name = hn.substring(li + 1);
				String prefix = hn.substring(0, li);

				String[] ps = prefix.split(SEP_ID);

				MiNDHandle hT = HANDLETYPE.getRight(ps[1]);
				if ( null != hT ) {
					factMeta.get(ps[1]).put(hn, h);
					Giskard.access(MiNDAccessCommand.Set, hT, h, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
					Giskard.access(MiNDAccessCommand.Set, hn, h, GIS_MEM_TEXT_NAME);
				}
			}
		}

		for (Field f : DustConsts.class.getDeclaredFields()) {
			Class<?> ft = f.getType();
			if ( MiNDHandle.class.isAssignableFrom(ft) ) {
				String hn = f.getName();
				MiNDHandle h = (MiNDHandle) f.get(null);

				int li = hn.lastIndexOf(SEP_ID);
				String prefix = hn.substring(0, li);

				String[] ps = prefix.split(SEP_ID);

				if ( 2 < ps.length ) {
					MiNDHandle hU = (MiNDHandle) factMeta.get("UNI").get("GIS_UNI_" + ps[2]);
					if ( null != hU ) {
						Giskard.access(MiNDAccessCommand.Set, hU, h, GIS_MEM_MODEL_ENTITY_UNIT);
					}
				} else if ( "GIS_UNI".equals(prefix) ) {
					Giskard.access(MiNDAccessCommand.Set, h, h, GIS_MEM_MODEL_ENTITY_UNIT);					
				}

				switch ( ps[1] ) {
				case "TAG":
					MiNDHandle hClass = (MiNDHandle) factMeta.get("TAG").get(prefix);
					if ( null != hClass ) {
						Giskard.access(MiNDAccessCommand.Set, hClass, h, GIS_MEM_MODEL_ENTITY_OWNER);
					}
					break;
				case "MEM":
					MiNDHandle hT = (MiNDHandle) factMeta.get("TYP").get(prefix.replace("MEM", "TYP"));
					if ( null != hT ) {
						Giskard.access(MiNDAccessCommand.Insert, h, hT, GIS_MEM_IDEA_TYPE_MEMBERS);
						Giskard.access(MiNDAccessCommand.Set, hT, h, GIS_MEM_MODEL_ENTITY_OWNER);
					}
					break;
				}
			}
		}
	}

}
