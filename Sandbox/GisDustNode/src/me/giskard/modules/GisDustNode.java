package me.giskard.modules;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;
import me.giskard.dust.node.DustBootConsts;
import me.giskard.dust.node.DustBootUtils;
import me.giskard.dust.node.DustEntityRef;
import me.giskard.dust.node.DustEntityRef.EntityRefProcessor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GisDustNode implements DustBootConsts, GiskardConsts.GiskardModule {

	Map bootModule;
	GiskardCloud bootCloud = new GiskardCloud() {
		@Override
		public <RetType> RetType accessData(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
			if ( ctx != GiskardContext.ById ) {
				throw new IllegalAccessError("Illegal access command while booting");
			}
			
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

		Giskard.broadcastEvent(null, "Registering boot refs...");

		GiskardMain.setBootCloud(DustEntityRef.BOOT_CLOUD);

		DustEntityRef refTypeRuntime = GIS_TYP_DUST_RUNTIME;

		bootModule = GiskardUtils.instantiate(CLASSNAME_MAP);
		GiskardMain.setBootCloud(bootCloud);
		
		EntityRefProcessor brp = new EntityRefProcessor() {
			@Override
			public void processEntityRef(DustEntityRef ref, int optUnitNextIdx) {
				Giskard.access(GiskardAccessCmd.Set, ref, GiskardContext.ById, GIS_ATT_UTIL_USES, ref.getUnit().getID(),
						GIS_ATT_MIND_ENTITIES, ref.getID(), GIS_ATT_MIND_SELFREF);

				if ( 0 < optUnitNextIdx ) {
					Giskard.access(GiskardAccessCmd.Set, optUnitNextIdx, GiskardContext.ById, GIS_ATT_UTIL_USES,
							ref.getUnit().getID(), GIS_ATT_MIND_ENTITIES, ref.getID(), GIS_ATT_MIND_NEXTID);
				}
			}
		};

		DustEntityRef.finishBoot(brp);

		Giskard.access(GiskardAccessCmd.Set, CLASSNAME_RUNTIME, GiskardContext.ById, GIS_ATT_DUST_NATIVES,
				refTypeRuntime, GIS_ATT_UTIL_ID);

		Giskard.broadcastEvent(null, bootModule);

		Giskard.broadcastEvent(null, "Creating runtime agent...");
		GiskardMain node = DustBootUtils.createAgent(refTypeRuntime);

		node.setRuntime(runtime);

		Giskard.broadcastEvent(null, "SUCCESS initialization of runtime", modName);
	}

}
