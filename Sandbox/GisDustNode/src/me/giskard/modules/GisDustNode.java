package me.giskard.modules;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardConsts.GiskardModule;
import me.giskard.GiskardMain;
import me.giskard.dust.node.DustBootConsts;
import me.giskard.dust.node.DustBootUtils;
import me.giskard.dust.node.DustEntityRef;
import me.giskard.utils.GisUtilsConsts.Creator;
import me.giskard.utils.GisUtilsFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GisDustNode implements DustBootConsts, GiskardConsts, GiskardModule {

	Enum CURR_KEY;

	Creator<Object, Map> BOOT_CREATOR = new Creator<Object, Map>() {
		@Override
		public Map create(Object key, Object... params) {
			Map map = new GisUtilsFactory<>(BOOT_CREATOR);

			if ( null != CURR_KEY ) {
				initBootEntity(map, CURR_KEY);
				CURR_KEY = null;
			}

			return map;
		}
	};

	@Override
	public void initModule(Giskard runtime) throws Exception {
		String modName = this.getClass().getSimpleName();

		Giskard.broadcastEvent(null, "Initializing runtime", modName, "...");

		Giskard.broadcastEvent(null, "Registering boot refs...");

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
					initBootEntity(map, eKey);
				}
			}
		}

		DustEntityRef refTypeRuntime = BOOT_REFS.get(UnitDustNode.TypeRuntime);
		Giskard.access(GiskardAccessCmd.Set, CLASSNAME_RUNTIME, GiskardContext.Module, ATT_NATIVEMAP, refTypeRuntime, ATT_TXT_ID);

		Giskard.broadcastEvent(null, factBootModule);

		Giskard.broadcastEvent(null, "Creating runtime agent...");
		GiskardMain node = DustBootUtils.createAgent(refTypeRuntime);

		node.setRuntime(runtime);
		
		Giskard.broadcastEvent(null, "SUCCESS initialization of runtime", modName);
	}

	private void initBootEntity(Map map, Enum key) {
		map.put(ATT_ENTITYREF, BOOT_REFS.get(key));
//		map.put(ATT_TXT_ID, GiskardUtils.enumToKey(key));
	}
}
