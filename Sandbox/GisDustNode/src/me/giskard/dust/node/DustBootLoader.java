package me.giskard.dust.node;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;
import me.giskard.utils.GisUtilsConsts.Creator;
import me.giskard.utils.GisUtilsFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustBootLoader implements DustBootConsts, GiskardConsts {

	static Map<Enum, DustEntityRef> BOOT_REFS = new GisUtilsFactory<Enum, DustEntityRef>(
			new Creator<Enum, DustEntityRef>() {
				@Override
				public DustEntityRef create(Enum key, Object... params) {
					return new DustEntityRef(null, key.ordinal());
				}
			});

	static DustEntityRef ATT_NAME = BOOT_REFS.get(UnitUtilText.AttIdentifiedId);
	static DustEntityRef ATT_ENTITYREF = BOOT_REFS.get(UnitMiNDModel.AttEntityRef);
	static DustEntityRef ATT_ENTITYMAP = BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap);

	static Enum CURR_KEY;

	static Creator<Object, Map> BOOT_CREATOR = new Creator<Object, Map>() {
		@Override
		public Map create(Object key, Object... params) {
			Map map = new GisUtilsFactory<>(BOOT_CREATOR);

			if ( null != CURR_KEY ) {
				initEntity(map, CURR_KEY);
				CURR_KEY = null;
			}

			return map;
		}
	};

	public static void boot() throws Exception {
		GisUtilsFactory<Object, Map> factBootEntities = new GisUtilsFactory<>(BOOT_CREATOR);

		GiskardMain.setBootUnitMap(factBootEntities);

		String prefEnumName = UnitDustNode.class.getName();
		prefEnumName = prefEnumName.substring(0, prefEnumName.lastIndexOf("$") + 1);

		DustEntityRef nodeUnits = BOOT_REFS.get(UnitDustNode.UnitDustNode);

		for (UnitDustNode key : UnitDustNode.values()) {
			if ( isUnit(key) ) {
				CURR_KEY = key;
				DustEntityRef refUnit = BOOT_REFS.get(key);
				refUnit.unit = nodeUnits;
				int unitId = key.ordinal();
				Giskard.access(GiskardAccessCmd.Get, null, unitId);

				Class ce = Class.forName(prefEnumName + key.name());
				for (Object k2 : ce.getEnumConstants()) {
					Enum eKey = (Enum) k2;
					DustEntityRef r = BOOT_REFS.get(eKey);
					r.unit = refUnit;
					Map map = Giskard.access(GiskardAccessCmd.Get, null, unitId, ATT_ENTITYMAP, eKey.ordinal());
					initEntity(map, eKey);
				}
			}
		}

		Giskard.broadcastEvent(null, factBootEntities);
	}

	public static boolean isUnit(Enum eKey) {
		return eKey.name().startsWith("Unit");
	}

	public static void initEntity(Map map, Enum key) {
		map.put(ATT_ENTITYREF, BOOT_REFS.get(key));
		map.put(ATT_NAME, GiskardUtils.enumToKey(key));
	}

	public static String toString(DustEntityRef ref) {
		Object unitId = ref.getUnit().getID();
		String unitName = Giskard.access(GiskardAccessCmd.Get, "??", unitId, ATT_NAME);
		String name = Giskard.access(GiskardAccessCmd.Get, "??", unitId, ATT_ENTITYMAP, ref.getID(), ATT_NAME);
		return "\"" + unitName + "->" + name + "\"";
	}
}
