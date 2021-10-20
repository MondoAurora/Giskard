package me.giskard.dust.node;

import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustBootLoader implements DustBootConsts, GiskardConsts {

	static Class<? extends Map> CLASS_MAP;
	static Map ROOT;

	static Map NODE_UNITS;

	static Map<Enum, DustEntityRef> BOOT_REFS = new HashMap<Enum, DustEntityRef>() {
		private static final long serialVersionUID = 1L;

		@Override
		public DustEntityRef get(Object key) {
			DustEntityRef ret = super.get(key);

			if ( null == ret ) {
				Enum k = (Enum) key;

				ret = new DustEntityRef(null, k.ordinal());
				put(k, ret);
			}

			return ret;
		}
	};

	public static Object getNodeEntity() {
		return ROOT;
	}

	public static void boot() throws Exception {
		if ( null != ROOT ) {
			throw new IllegalStateException("boot is already called.");
		}

		String prefEnumName = UnitDustNode.class.getName();
		prefEnumName = prefEnumName.substring(0, prefEnumName.lastIndexOf("$") + 1);

		CLASS_MAP = (Class<? extends Map>) Class.forName(CLASSNAME_MAP);

		ROOT = CLASS_MAP.newInstance();
		NODE_UNITS = CLASS_MAP.newInstance();

		ROOT.put(BOOT_REFS.get(UnitDustNode.AttModuleUnitMap), NODE_UNITS);

		DustEntityRef nodeUnits = BOOT_REFS.get(UnitDustNode.UnitDustNode);

		for (UnitDustNode key : UnitDustNode.values()) {
			if ( isUnit(key) ) {
				DustEntityRef refUnit = optRegisterEntity(key, NODE_UNITS);
				refUnit.unit = nodeUnits;
				Map parent = (Map) ((Map) NODE_UNITS.get(key.ordinal())).get(BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap));

				Class ce = Class.forName(prefEnumName + key.name());
				for (Object k2 : ce.getEnumConstants()) {
					Enum eKey = (Enum) k2;
					DustEntityRef r = optRegisterEntity(eKey, parent);
					r.unit = refUnit;
				}
			}
		}

		Giskard.broadcastEvent(null, ROOT);
	}

	public static DustEntityRef optRegisterEntity(Enum key, Map parent) throws Exception {
		DustEntityRef ref = BOOT_REFS.get(key);

		Object id = key.ordinal();
		Map map = (Map) parent.get(id);

		if ( null == map ) {
			map = CLASS_MAP.newInstance();
			map.put(BOOT_REFS.get(UnitMiNDModel.AttEntityRef), ref);
			map.put(BOOT_REFS.get(UnitUtilText.AttIdentifiedId), key.getClass().getSimpleName() + "::" + key.name());

			if ( isUnit(key) ) {
				map.put(BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap), CLASS_MAP.newInstance());
			}
			parent.put(id, map);
		}

		return ref;
	}

	public static boolean isUnit(Enum eKey) {
		return eKey.name().startsWith("Unit");
	}

	public static String toString(DustEntityRef ref) {
		Object nodeId = ref.getUnit().getID();

		Map m = (Map) NODE_UNITS.get(nodeId);
		String unitName = (String) m.get(BOOT_REFS.get(UnitUtilText.AttIdentifiedId));

		m = (Map) m.get(BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap));
		m = (Map) m.get(ref.getID());

		String name = (String) m.get(BOOT_REFS.get(UnitUtilText.AttIdentifiedId));

		return "[" + unitName + "->" + name + "]";
	}
}
