package ai.montru.dust.node;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ai.montru.MontruMain;
import ai.montru.dust.node.DustNodeAgentCtrl.DustIterator;
import ai.montru.dust.node.DustNodeAgentCtrl.DustVisitor;
import ai.montru.dust.node.DustNodeEntityRef.EntityRefProcessor;
import ai.montru.giskard.Giskard;
import ai.montru.modules.GisDustNode;
import ai.montru.utils.MontruUtils;

public class DustNodeAgentRuntime extends MontruMain
		implements DustNodeConsts, DustNodeConsts.GiskardAgent, GisDustNode.BootModule {

	PrintStream out = System.out;

	Map<Object, Object> ctxRoot = new HashMap<>();
	Map<Object, DustNodeEntityRef> loadedUnits = new HashMap<>();
	Map<DustNodeEntityRef, Map<Object, Object>> ctxRootEntities = new HashMap<>();

	GiskardEntityRef rRuntime;
	GiskardEntityRef rCurrMod;

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
				Map<Object, Object> e = (Map<Object, Object>) resolve(ref, null, false);
				if ( 0 < optUnitNextIdx ) {
					e.put(refAttNextId, optUnitNextIdx);
					loadedUnits.put(id, ref);
					e.put(refAttId, id.split("/")[2]);
				} else {
					e.put(refAttId, id);
				}
			}
		};

		ctxRoot.put(GIS_ATT_MIND_ENTITIES, ctxRootEntities);

		DustNodeEntityRef.finishBoot(brp);

		ctxRoot.put(refAttNextId, ctxRootEntities.size());

		DustNodeUtils.createContainer(resolve(GIS_ATT_UTIL_TAGS, null, false), GIS_ATT_UTIL_TAGS, CLASSNAME_SET);
		Giskard.access(GiskardAccess.Set, GIS_TAG_MIND_COLLTYPE_SET, GIS_ATT_UTIL_TAGS, GIS_ATT_UTIL_TAGS);

		rRuntime = gisAccessData(GiskardAccess.Insert, GIS_TYP_DUST_RUNTIME, null);
		gisAccessData(GiskardAccess.Set, this, rRuntime, GIS_ATT_DUST_INSTANCE);
		gisAccessData(GiskardAccess.Set, loadedUnits, rRuntime, GIS_ATT_DUST_LOADEDUNITS);

		setBootMod(name, version);

		registerNative(GIS_TYP_DUST_RUNTIME, getClass().getName());

		registerNative(GIS_TYP_MIND_ITERATOR, DustIterator.class.getName());
		registerNative(GIS_TYP_MIND_VISITOR, DustVisitor.class.getName());

		for (BootEvent e : BOOT_EVENTS) {
			gisBroadcastEvent(e.eventType, e.params);
		}

		BOOT_EVENTS.clear();

		Giskard.broadcastEvent(null, loadedUnits);
		Giskard.broadcastEvent(null, ctxRootEntities);

	}

	public Object resolve(GiskardEntityRef ref, GiskardEntityRef type, boolean forceLocal) {
		return getEntity((DustNodeEntityRef) ref, type, (null != ref.gisGetUnit()) && forceLocal);
	}

	static DustNodeAgentRuntime getRuntime() {
		return (DustNodeAgentRuntime) RUNTIME;
	}

	@SuppressWarnings("unchecked")
	Map<Object, Object> getEntity(DustNodeEntityRef ref, GiskardEntityRef type, boolean forceLocal) {
		Map<Object, Object> ret = null;

		DustNodeEntityRef uRef = ref.gisGetUnit();
		Map<Object, Object> unit = (null == uRef) ? ctxRoot : getEntity(uRef, GIS_TYP_MIND_UNIT, false);

		Map<DustNodeEntityRef, Map<Object, Object>> pe = (Map<DustNodeEntityRef, Map<Object, Object>>) unit
				.get(GIS_ATT_MIND_ENTITIES);
		if ( null == pe ) {
			pe = new HashMap<>();
			unit.put(GIS_ATT_MIND_ENTITIES, pe);
		} else {
			ret = pe.get(ref);
		}

		if ( null == ret ) {
			ret = new HashMap<>();
			ret.put(GIS_ATT_MIND_SELFREF, ref);
			if ( null != type ) {
				ret.put(GIS_ATT_MIND_PRIMTYPE, type);
			}
			pe.put(ref, ret);

			Integer nextId = (Integer) unit.getOrDefault(GIS_ATT_MIND_NEXTID, 1);
			Object refId = ref.gisGetID();
			boolean updateNext = false;

			if ( null == refId ) {
				ref.setId(nextId);
				nextId++;
				updateNext = true;
			} else {
				int limit = (refId instanceof Integer) ? ((Integer) refId) + 1 : pe.size();
				if ( nextId < limit ) {
					nextId = limit;
					updateNext = true;
				}
			}

			if ( updateNext ) {
				unit.put(GIS_ATT_MIND_NEXTID, nextId);
			}
		}

		return ret;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <RetType> RetType gisAccessData(GiskardAccess cmd, Object val, GiskardEntityRef localRef, Object... path) {
		int pLen = path.length;
		Object lastOb = val;

		if ( 0 == pLen ) {
			switch ( cmd ) {
			case Insert:
				lastOb = resolve(new DustNodeEntityRef(localRef, null), (GiskardEntityRef) val, false);
				lastOb = ((Map<Object, Object>) lastOb).get(GIS_ATT_MIND_SELFREF);
				break;
			default:
				throw new IllegalAccessError("Unhandled access command");
			}
		} else {
			lastOb = (null == localRef) ? ctxRootEntities : resolve(localRef, null, false);
			int lastIdx = 0;
			Object parent;

			for (parent = lastOb; lastIdx < pLen; ++lastIdx) {
				Object key = path[lastIdx];
				if ( null == lastOb ) {
					lastOb = DustNodeUtils.createContainer(parent, path[lastIdx - 1],
							(key instanceof Integer) ? CLASSNAME_ARR : CLASSNAME_MAP);
				}
				parent = (lastOb instanceof GiskardEntityRef) ? resolve((GiskardEntityRef) lastOb, null, false) : lastOb;
				lastOb = DustNodeUtils.getValue(parent, key, null);
			}

			switch ( cmd ) {
			case Peek:
				if ( null == lastOb ) {
					lastOb = val;
				}
				break;
			case Get:
				if ( null == lastOb ) {
					lastOb = DustNodeUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
				}
				break;
			case Insert:
			case Set:
				lastOb = DustNodeUtils.setValue((lastOb instanceof Set) ? lastOb : parent, path[lastIdx - 1], val);
				break;
			default:
				throw new IllegalAccessError("Unhandled access command");
			}
		}

		return (RetType) lastOb;
	}

	@Override
	public GiskardEntityRef gisGetToken(GiskardEntityRef unit, Object tokenId) {
		// TODO - lookup by id!

		GiskardEntityRef refToken = gisAccessData(GiskardAccess.Insert, null, unit);
		gisAccessData(GiskardAccess.Set, tokenId, refToken, GIS_ATT_UTIL_ID);
		return refToken;
	}

	@Override
	protected <RetType> RetType gisWrapException(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void gisRegisterNative(GiskardEntityRef type, String className) {
		GiskardEntityRef rNA = gisAccessData(GiskardAccess.Insert, GIS_TYP_DUST_NATIVEASSIGNMENT, null);
		gisAccessData(GiskardAccess.Set, className, rNA, GIS_ATT_UTIL_ID);
		gisAccessData(GiskardAccess.Set, rNA, type, GIS_ATT_DUST_ASSIGNMENT);

		gisAccessData(GiskardAccess.Set, rNA, rCurrMod, GIS_ATT_DUST_NATIVES, type);

	}

	@Override
	protected void gisInitiateProcess(GiskardEntityRef rProc) {
		gisAccessData(GiskardAccess.Insert, rProc, rRuntime, GIS_ATT_DUST_PROCESSES, GIS_CONST_KEY_ADDLAST);
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
		switch ( action ) {
		case Init:
			break;
		case Begin:
			Iterable<GiskardEntityRef> iProc = gisAccessData(GiskardAccess.Peek, Collections.EMPTY_SET, rRuntime,
					GIS_ATT_DUST_PROCESSES);
			for (GiskardEntityRef rProc : iProc) {
				DustNodeUtils.execute(rProc);
			}
			break;
		case Process:
			break;
		case End:
			break;
		case Release:
			break;
		}
		return GiskardResponse.Reject;
	}

	protected <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {

		setBootMod(moduleName, moduleVersion);

		return super.loadModule(moduleName, moduleVersion);
	}

	private void setBootMod(String moduleName, String version) {
		rCurrMod = gisAccessData(GiskardAccess.Insert, GIS_TYP_DUST_MODULE, null);
		gisAccessData(GiskardAccess.Set, moduleName, rCurrMod, GIS_ATT_UTIL_ID);
		gisAccessData(GiskardAccess.Set, rCurrMod, rRuntime, GIS_ATT_DUST_LOADEDMODULES, moduleName);
	}

	protected ClassLoader getCL(String moduleName, String moduleVersion) {
		return super.getCL(moduleName, moduleVersion);
	}

}
