package me.giskard.dust.node;

import java.util.List;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustBootUtils implements DustBootConsts {

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
		Map o = GiskardUtils.instantiate(contClassName);
		if ( key instanceof DustEntityRef ) {
			o.put(ATT_MODEL_ENTITY_REF, key);
		}

		setValue(parent, key, o);

		return o;
	}

	public static int incCount(GiskardContext ctx, Object... path) {
		int idx = Giskard.access(GiskardAccessCmd.Peek, 0, ctx, path);
		Giskard.access(GiskardAccessCmd.Set, idx + 1, ctx, path);
		return idx;
	};


	public static DustEntityRef createNodeUnit() {
		DustEntityRef ref = new DustEntityRef(null, 0);
		Giskard.access(GiskardAccessCmd.Set, 1, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, 0, ATT_MODEL_UNIT_NEXTID);
		return ref;
	};


	public static DustEntityRef createEntity(DustEntityRef refUnit) {
		Object idUnit = (null == refUnit) ? 0 : refUnit.getID();
		int id = incCount(GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, idUnit, ATT_MODEL_UNIT_NEXTID);
		DustEntityRef ref = new DustEntityRef(refUnit, id);
		
		Giskard.access(GiskardAccessCmd.Set, ref, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, idUnit, ATT_MODEL_UNIT_ENTITYMAP, id, ATT_MODEL_ENTITY_REF);
		
		return ref;
	}

	public static <RetType> RetType createAgent(DustEntityRef refType) {
		String className = Giskard.access(GiskardAccessCmd.Peek, null, GiskardContext.Module, ATT_NODE_MODULE_NATIVEMAP, refType,
				ATT_TEXT_IDENTIFIED_ID);

		return GiskardUtils.instantiate(className);
	}

	public static boolean isUnit(Enum eKey) {
		return eKey.name().startsWith("Unit");
	}

	public static String toString(DustEntityRef ref) {
//		Object unitId = ref.getUnit().getID();
//		String unitName = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, unitId,
//				ATT_TEXT_IDENTIFIED_ID);
//		String name = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, unitId, ATT_MODEL_UNIT_ENTITYMAP,
//				ref.getID(), ATT_TEXT_IDENTIFIED_ID);
//		return "\"" + unitName + "->" + name + "\"";

		return "\"" + ref.getUnit().getID() + "::" + ref.getID() + "\"";
	}
}
