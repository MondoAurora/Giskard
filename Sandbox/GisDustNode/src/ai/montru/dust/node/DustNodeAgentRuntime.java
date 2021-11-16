package ai.montru.dust.node;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ai.montru.MontruMain;
import ai.montru.dust.node.DustNodeEntityRef.EntityRefProcessor;
import ai.montru.giskard.Giskard;
import ai.montru.modules.GisDustNode;
import ai.montru.utils.MontruUtils;

public class DustNodeAgentRuntime extends MontruMain
		implements DustNodeConsts, DustNodeConsts.DustRuntime, GisDustNode.BootModule {

	PrintStream out = System.out;

	Map<Object, DustNodeEntityRef> loadedUnits = new HashMap<>();
	Map<DustNodeEntityRef, Map<Object, Object>> ctxRoot = new HashMap<>();

	EntityInitializer unitInit = new EntityInitializer() {
		@Override
		public void initNewEntity(DustNodeEntityRef ref, Map<Object, Object> eData) {
			eData.put(GIS_ATT_MIND_PRIMTYPE, GIS_TYP_MIND_UNIT);
			Map<DustNodeEntityRef, Map<Object, Object>> m = new HashMap<>();
			eData.put(GIS_ATT_MIND_ENTITIES, m);
			loadedUnits.put(ref.gisGetID(), ref);
		}
	};

	@SuppressWarnings("unused")
	@Override
	public void boot(Giskard runtime, String name, String version) {
		if ( runtime instanceof DustNodeAgentRuntime ) {
			Giskard.wrapException(null, null, "Multiple boot calls?", name, version);
		}

		GiskardEntityRef refNodeType = GIS_TYP_DUST_RUNTIME;
		GiskardEntityRef refAttNextId = GIS_ATT_MIND_NEXTID;
		GiskardEntityRef refAttId = GIS_ATT_UTIL_ID;

		super.setRuntime(this);

		EntityRefProcessor brp = new EntityRefProcessor() {
			@Override
			public void processEntityRef(DustNodeEntityRef ref, String id, int optUnitNextIdx) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> e = (Map<Object, Object>) resolve(ref, true);
				e.put(refAttId, id);
				if ( 0 < optUnitNextIdx ) {
					e.put(refAttNextId, optUnitNextIdx);
				}
			}
		};

		DustNodeEntityRef.finishBoot(brp);

		DustNodeUtils.createContainer(resolve(GIS_ATT_UTIL_TAGS, false), GIS_ATT_UTIL_TAGS, CLASSNAME_SET);
		Giskard.access(GiskardAccess.Set, GIS_TAG_MIND_COLLTYPE_SET, GIS_ATT_UTIL_TAGS, GIS_ATT_UTIL_TAGS);
		
//		GiskardEntityRef rMod = gisAccessData(GiskardAccess.Insert, GIS_TYP_DUST_MODULE, null);
//		gisAccessData(GiskardAccess.Set, name, rMod, GIS_ATT_UTIL_ID);
//
//		GiskardEntityRef rNA = gisAccessData(GiskardAccess.Insert, GIS_TYP_DUST_NATIVEASSIGNMENT, null);
//
//		gisAccessData(GiskardAccess.Set, getClass().getName(), rNA, GIS_ATT_UTIL_ID);
//		gisAccessData(GiskardAccess.Set, rNA, GIS_TYP_DUST_RUNTIME, GIS_ATT_DUST_ASSIGNMENT);

		for (BootEvent e : BOOT_EVENTS) {
			gisBroadcastEvent(e.eventType, e.params);
		}

		BOOT_EVENTS.clear();

		Giskard.broadcastEvent(null, ctxRoot);
	}

	@Override
	public Object resolve(GiskardEntityRef ref, boolean createIfMissing) {
		Object ret = null;
		DustNodeEntityRef entRef = (DustNodeEntityRef) ref;
		DustNodeEntityRef uidRef = entRef.gisGetUnit();

		if ( null == uidRef ) {
			ret = getEntity(entRef, ctxRoot, true, unitInit);
		} else {
			@SuppressWarnings("unchecked")
			Map<DustNodeEntityRef,  Map<Object, Object>> unit = (Map<DustNodeEntityRef,  Map<Object, Object>>) getEntity(uidRef, ctxRoot, true, unitInit).get(GIS_ATT_MIND_ENTITIES);
			ret = getEntity(entRef, unit, createIfMissing, null);
		}

		return ret;
	}

	Map<Object, Object> getEntity(DustNodeEntityRef ref, Map<DustNodeEntityRef,  Map<Object, Object>> parent, boolean createIfMissing, EntityInitializer init) {
		Map<Object, Object> ret = (Map<Object, Object>) parent.get(ref);
		
		if ( (null == ret) && createIfMissing ) {
			ret = new HashMap<>();
			if ( null != init ) {
				init.initNewEntity(ref, ret);
			}
			parent.put(ref, ret);
		}

		return ret;
	}

	@Override
	protected <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, GiskardEntityRef localRef, Object... path) {
		Object root = (null == localRef) ? ctxRoot : resolve(localRef, false);
		return gisAccessData(cmd, val, root, path);
	}

	@SuppressWarnings("unchecked")
	protected <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, Object root, Object... path) {
		int pLen = path.length;
		Object lastOb = root;

		if ( 0 == pLen ) {
			switch ( cmd ) {
			case Insert:
				break;
			default:
				throw new IllegalAccessError("Unhandled access command");
			}
		} else {

			int lastIdx = 0;
			Object parent;

			for (parent = lastOb; lastIdx < pLen; ++lastIdx) {
				Object key = path[lastIdx];
				if ( null == lastOb ) {
					lastOb = DustNodeUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
				}
				parent = (lastOb instanceof GiskardEntityRef) ? resolve((GiskardEntityRef) lastOb, true) : lastOb;
				lastOb = DustNodeUtils.getValue(parent, key, null);
			}

			switch ( cmd ) {
			case Peek:
				break;
			case Get:
				if ( null == lastOb ) {
					lastOb = DustNodeUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
				}
				break;
			case Set:
				lastOb = DustNodeUtils.setValue(lastOb, path[lastIdx - 1], val);
				break;
			default:
				throw new IllegalAccessError("Unhandled access command");
			}
		}

		return (RetType) lastOb;
	}

	@Override
	public GiskardEntityRef gisGetToken(GiskardEntityRef unit, Object tokenId) {
		return super.gisGetToken(unit, tokenId);
	}

	@Override
	protected <RetType> RetType gisWrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void gisBroadcastEvent(GiskardEntityRef eventType, Object... params) {
		out.println(MontruUtils.sbAppend(null, " ", "Normal event", eventType, "at", new Date(), "params [",
				MontruUtils.sbAppend(null, ", ", params), "]"));
	}

	@Override
	protected String gisToString(GiskardEntityRef ref) {
		return super.gisToString(ref);
	}

	@Override
	public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
		return GiskardResponse.Reject;
	}

	protected <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {
		return super.loadModule(moduleName, moduleVersion);
	}

	protected ClassLoader getCL(String moduleName, String moduleVersion) {
		return super.getCL(moduleName, moduleVersion);
	}

}
