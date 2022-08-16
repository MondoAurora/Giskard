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
				String name = hn.substring(li +1);
				String prefix = hn.substring(0, li);
				
				String[] ps = prefix.split(SEP_ID);
				
				MiNDHandle hT = HANDLETYPE.getRight(ps[1]);
				if ( null != hT ) {
					factMeta.get(ps[1]).put(hn, h);
					Giskard.access(MiNDAccessCommand.Set, hT, h, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
					Giskard.access(MiNDAccessCommand.Set, name, h, GIS_MEM_TEXT_NAME);
				}
				
				Giskard.log(null, "Boot handle", hn);
			}
		}
	}

}
