package me.giskard.dust.node.logic;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;
import me.giskard.dust.node.DustBootConsts;
import me.giskard.dust.node.DustBootUtils;
import me.giskard.dust.node.DustEntityRef;
import me.giskard.utils.GisUtilsConsts.Creator;
import me.giskard.utils.GisUtilsFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustBootLoader implements DustBootConsts, GiskardConsts {

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

	public static void boot(Giskard runtime) throws Exception {
		GisUtilsFactory<Object, Map> factBootModule = new GisUtilsFactory<>(BOOT_CREATOR);

		GiskardMain.setBootModule(factBootModule);
		Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_UNITMAP);

		String prefEnumName = UnitDustNode.class.getName();
		prefEnumName = prefEnumName.substring(0, prefEnumName.lastIndexOf("$") + 1);

		DustEntityRef nodeUnits = BOOT_REFS.get(UnitDustNode.UnitDustNode);

		for (UnitDustNode key : UnitDustNode.values()) {
			if ( DustBootUtils.isUnit(key) ) {
				CURR_KEY = key;
				DustEntityRef refUnit = BOOT_REFS.get(key);
				refUnit.setUnit(nodeUnits);
				int unitId = key.ordinal();
				Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_UNITMAP, unitId);

				Class ce = Class.forName(prefEnumName + key.name());
				for (Object k2 : ce.getEnumConstants()) {
					Enum eKey = (Enum) k2;
					DustEntityRef r = BOOT_REFS.get(eKey);
					r.setUnit(refUnit);
					Map map = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_UNITMAP, unitId,
							ATT_ENTITYMAP, eKey.ordinal());
					initEntity(map, eKey);
				}
			}
		}

		DustEntityRef refTypeNode = BOOT_REFS.get(UnitDustNode.TypeNode);
		Giskard.access(GiskardAccessCmd.Set, CLASSNAME_NODE, GiskardContext.Module, ATT_NATIVEMAP,
				refTypeNode, ATT_TXT_ID);

		Giskard.broadcastEvent(null, factBootModule);

		Giskard.broadcastEvent(null, "Creating runtime agent...");
		GiskardMain node = DustBootUtils.createAgent(refTypeNode);

		node.setRuntime(runtime);
	}

	public static void initEntity(Map map, Enum key) {
		map.put(ATT_ENTITYREF, BOOT_REFS.get(key));
		map.put(ATT_TXT_ID, GiskardUtils.enumToKey(key));
	}

}
