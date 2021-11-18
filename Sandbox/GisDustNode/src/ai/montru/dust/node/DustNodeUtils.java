package ai.montru.dust.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ai.montru.giskard.Giskard;
import ai.montru.utils.MontruUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustNodeUtils implements DustNodeConsts {

	public static Object setValue(Object parent, Object key, Object val) {
		if ( parent instanceof Map ) {
			return ((Map) parent).put(key, val);
		} else if ( parent instanceof ArrayList ) {
			int idx = (Integer) key;
			List l = (List) parent;
			
			if ( key.equals(GIS_CONST_KEY_ADDLAST) ) {
				l.add(val);
				return null;
			} else if ( key.equals(GIS_CONST_KEY_ADDFIRST) ) {
				l.add(0, val);
				return null;
			} else {
				for ( int ii = l.size(); ii < idx; ++ii ) {
					l.add(null);
				}
				return l.set(idx, val);
			}
		} else {
			return ((Set) parent).add(val);
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
		Object o = MontruUtils.instantiate(contClassName);
		setValue(parent, key, o);

		return o;
	}

	public static GiskardResponse call(GiskardEntityRef target, GiskardAction action) {
		GiskardAgent a = Giskard.access(GiskardAccess.Peek, null, target, GIS_ATT_DUST_INSTANCE);
		
		if ( null == a ) {
			String cName = Giskard.access(GiskardAccess.Peek, null, target, GIS_ATT_MIND_PRIMTYPE, GIS_ATT_DUST_ASSIGNMENT, GIS_ATT_UTIL_ID);
			a = MontruUtils.instantiate(cName);
			Giskard.access(GiskardAccess.Set, a, target, GIS_ATT_DUST_INSTANCE);
		}
		
		return a.gisAgentProcess(action);
	}

	public static int incCount(GiskardEntityRef localRef, Object... path) {
		int idx = Giskard.access(GiskardAccess.Peek, 0, localRef, path);
		Giskard.access(GiskardAccess.Set, idx + 1, localRef, path);
		return idx;
	};


	public static DustNodeEntityRef createNodeUnit() {
		DustNodeEntityRef ref = new DustNodeEntityRef(null, 0);
		Giskard.access(GiskardAccess.Set, 1, null, GIS_ATT_UTIL_USES, 0, GIS_ATT_MIND_NEXTID);
		return ref;
	};


	public static DustNodeEntityRef createEntity(DustNodeEntityRef refUnit) {
		Object idUnit = (null == refUnit) ? 0 : refUnit.gisGetID();
		int id = incCount(null, GIS_ATT_UTIL_USES, idUnit, GIS_ATT_MIND_NEXTID);
		DustNodeEntityRef ref = new DustNodeEntityRef(refUnit, id);
		
		Giskard.access(GiskardAccess.Set, ref, null, GIS_ATT_UTIL_USES, idUnit, GIS_ATT_MIND_ENTITIES, id, GIS_ATT_MIND_SELFREF);
		
		return ref;
	}

	public static <RetType> RetType createAgent(DustNodeEntityRef refType) {
		String className = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_NATIVES, refType,
				GIS_ATT_UTIL_ID);

		return MontruUtils.instantiate(className);
	}

	public static String toString(DustNodeEntityRef ref) {
		String name = Giskard.access(GiskardAccess.Peek, "???", ref, GIS_ATT_UTIL_ID);
		return name + "[" + ref.gisGetID() + "]";
	}
}
