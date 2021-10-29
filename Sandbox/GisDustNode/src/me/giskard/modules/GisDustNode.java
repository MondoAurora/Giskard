package me.giskard.modules;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardConsts.GiskardModule;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;
import me.giskard.dust.node.DustBootConsts;
import me.giskard.dust.node.DustBootUtils;
import me.giskard.dust.node.DustEntityRef;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GisDustNode implements DustBootConsts, GiskardConsts, GiskardModule {

	Map bootModule;
	GiskardCloud bootCloud = new GiskardCloud() {
		@Override
		public <RetType> RetType accessData(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
			int l = path.length;
			int lastIdx = 0;

			Object parent;
			Object lastOb;

			for (lastIdx = 0, lastOb = parent = bootModule; lastIdx < l; ++lastIdx) {
				if ( null == lastOb ) {
					lastOb = DustBootUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
				}
				parent = lastOb;
				lastOb = DustBootUtils.getValue(parent, path[lastIdx], null);
			}

			switch ( cmd ) {
			case Peek:
				break;
			case Get:
				if ( null == lastOb ) {
					lastOb = DustBootUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
				}
				break;
			case Set:
				lastOb = DustBootUtils.setValue(parent, path[lastIdx - 1], val);
				break;
			default:
				throw new IllegalAccessError("Illegal access command while booting");
			}

			return (RetType) lastOb;
		}
	};

	@Override
	public void initModule(Giskard runtime) throws Exception {
		String modName = this.getClass().getSimpleName();

		Giskard.broadcastEvent(null, "Initializing runtime", modName, "...");

//		Giskard.broadcastEvent(null, "UNIT_UTIL_STREAM", UNIT_UTIL_STREAM.getUnit().getID() , "::", UNIT_UTIL_STREAM.getID());

		Giskard.broadcastEvent(null, "Registering boot refs...");

		bootModule = GiskardUtils.instantiate(CLASSNAME_MAP);
		GiskardMain.setBootCloud(bootCloud);
		
//		DustEntityRef nodeUnits = BOOT_REFS.get(UnitDustNode.UnitDustNode);
		DustEntityRef refTypeRuntime = TYPE_NODE_RUNTIME;// BOOT_REFS.get(UnitDustNode.TypeRuntime);


		BootRefProcessor brp = new BootRefProcessor() {
			@Override
			public void processBootRef(DustEntityRef ref, int optUnitNextIdx) {
				Giskard.access(GiskardAccessCmd.Set, ref, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP, ref.getUnit().getID(),
						ATT_MODEL_UNIT_ENTITYMAP, ref.getID(), ATT_MODEL_ENTITY_REF);

				if ( 0 < optUnitNextIdx ) {
					Giskard.access(GiskardAccessCmd.Set, optUnitNextIdx, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,
							ref.getUnit().getID(), ATT_MODEL_UNIT_ENTITYMAP, ref.getID(), ATT_MODEL_UNIT_NEXTID);
				}
			}
		};

		DustEntityRef.finishBoot(brp);

//		Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_UNITMAP);

//		String prefEnumName = UnitDustNode.class.getName();
//		prefEnumName = prefEnumName.substring(0, prefEnumName.lastIndexOf("$") + 1);
//
//
//		for (UnitDustNode key : UnitDustNode.values()) {
//			if ( DustBootUtils.isUnit(key) ) {
//				DustEntityRef refUnit = BOOT_REFS.get(key);
//				refUnit.setUnit(nodeUnits);
//				int unitId = key.ordinal();
////				Giskard.access(GiskardAccessCmd.Set, refUnit, GiskardContext.Module, ATT_UNITMAP, unitId, ATT_ENTITYREF);
//				Giskard.access(GiskardAccessCmd.Set, GiskardUtils.enumToKey(key), GiskardContext.Module,
//						ATT_NODE_MODULE_UNITMAP, unitId, ATT_TEXT_IDENTIFIED_ID);
//
//				Class ce = Class.forName(prefEnumName + key.name());
//				for (Object k2 : ce.getEnumConstants()) {
//					Enum eKey = (Enum) k2;
//					DustEntityRef r = BOOT_REFS.get(eKey);
//					r.setUnit(refUnit);
////					Giskard.access(GiskardAccessCmd.Set, r, GiskardContext.Module, ATT_UNITMAP, unitId, ATT_ENTITYMAP,
////							eKey.ordinal(), ATT_ENTITYREF);
//					Giskard.access(GiskardAccessCmd.Set, GiskardUtils.enumToKey(eKey), GiskardContext.Module,
//							ATT_NODE_MODULE_UNITMAP, unitId, ATT_MODEL_UNIT_ENTITYMAP, eKey.ordinal(), ATT_TEXT_IDENTIFIED_ID);
//				}
//			}
//		}

		Giskard.access(GiskardAccessCmd.Set, CLASSNAME_RUNTIME, GiskardContext.Module, ATT_NODE_MODULE_NATIVEMAP,
				refTypeRuntime, ATT_TEXT_IDENTIFIED_ID);

		Giskard.broadcastEvent(null, bootModule);

		Giskard.broadcastEvent(null, "Creating runtime agent...");
		GiskardMain node = DustBootUtils.createAgent(refTypeRuntime);

		node.setRuntime(runtime);

		Giskard.broadcastEvent(null, "SUCCESS initialization of runtime", modName);
	}

//	private void initBootEntity(Map map, Enum key) {
//		map.put(ATT_ENTITYREF, BOOT_REFS.get(key));
////		map.put(ATT_TXT_ID, GiskardUtils.enumToKey(key));
//	}
}
