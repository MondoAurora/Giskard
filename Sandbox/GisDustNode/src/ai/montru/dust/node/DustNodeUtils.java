package ai.montru.dust.node;

import java.util.List;
import java.util.Map;

import ai.montru.giskard.Giskard;
import ai.montru.utils.MontruUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustNodeUtils implements DustNodeConsts {

	public static Object setValue(Object parent, Object key, Object val) {
		if ( parent instanceof Map ) {
			return ((Map) parent).put(key, val);
		} else {
			int idx = (Integer) key;
			List l = (List) parent;
			
			if ( key.equals(-1) ) {
				l.add(val);
				return null;
			} else {
				for ( int ii = l.size(); ii < idx; ++ii ) {
					l.add(null);
				}
				return l.set(idx, val);
			}
		}
	};

	public static Object getValue(Object parent, Object key, Object val) {
		if ( parent instanceof Map ) {
			return ((Map) parent).getOrDefault(key, val);
		} else {
			int idx = (Integer) key;
			List l = (List) parent;
			return (0 <= idx) && (idx < l.size()) ? l.get(idx) : val;
		}
	};

	public static Object createContainer(Object parent, Object key, String contClassName) {
		Map o = MontruUtils.instantiate(contClassName);
		if ( key instanceof DustNodeEntityRef ) {
			o.put(GIS_ATT_MIND_SELFREF, key);
		}

		setValue(parent, key, o);

		return o;
	}

	public static int incCount(GiskardContext ctx, Object... path) {
		int idx = Giskard.access(GiskardAccessCmd.Peek, 0, ctx, path);
		Giskard.access(GiskardAccessCmd.Set, idx + 1, ctx, path);
		return idx;
	};


	public static DustNodeEntityRef createNodeUnit() {
		DustNodeEntityRef ref = new DustNodeEntityRef(null, 0);
		Giskard.access(GiskardAccessCmd.Set, 1, GiskardContext.ById, GIS_ATT_UTIL_USES, 0, GIS_ATT_MIND_NEXTID);
		return ref;
	};


	public static DustNodeEntityRef createEntity(DustNodeEntityRef refUnit) {
		Object idUnit = (null == refUnit) ? 0 : refUnit.getID();
		int id = incCount(GiskardContext.ById, GIS_ATT_UTIL_USES, idUnit, GIS_ATT_MIND_NEXTID);
		DustNodeEntityRef ref = new DustNodeEntityRef(refUnit, id);
		
		Giskard.access(GiskardAccessCmd.Set, ref, GiskardContext.ById, GIS_ATT_UTIL_USES, idUnit, GIS_ATT_MIND_ENTITIES, id, GIS_ATT_MIND_SELFREF);
		
		return ref;
	}

	public static <RetType> RetType createAgent(DustNodeEntityRef refType) {
		String className = Giskard.access(GiskardAccessCmd.Peek, null, GiskardContext.ById, GIS_ATT_DUST_NATIVES, refType,
				GIS_ATT_UTIL_ID);

		return MontruUtils.instantiate(className);
	}

	public static String toString(DustNodeEntityRef ref) {
//		Object unitId = ref.getUnit().getID();
//		String unitName = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, GIS_ATT_DUST_UNITMAP, unitId,
//				GIS_ATT_TEXT_IDENTIFIED_ID);
//		String name = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, GIS_ATT_DUST_UNITMAP, unitId, GIS_ATT_MIND_GIS_UNI_ENTITYMAP,
//				ref.getID(), GIS_ATT_TEXT_IDENTIFIED_ID);
//		return "\"" + unitName + "->" + name + "\"";
		
		Object uid = (null == ref.getUnit()) ? ref.getID() : ref.getUnit().getID();

		return "\"" + uid + "::" + ref.getID() + "\"";
	}
}
