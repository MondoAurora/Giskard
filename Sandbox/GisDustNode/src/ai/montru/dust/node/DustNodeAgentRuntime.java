package ai.montru.dust.node;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ai.montru.MontruMain;
import ai.montru.dust.node.DustNodeEntityRef.EntityRefProcessor;
import ai.montru.giskard.Giskard;
import ai.montru.utils.MontruUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustNodeAgentRuntime extends MontruMain implements DustNodeConsts, DustNodeConsts.DustRuntime {

	PrintStream out = System.out;

	Map runtimeEntity;
	Map loadedUnits;

	public DustNodeAgentRuntime() {
		runtimeEntity = new HashMap<>();
		loadedUnits = new HashMap<>();

		runtimeEntity.put(GIS_ATT_MIND_ENTITIES, loadedUnits);
	}

	@Override
	public Object resolve(GiskardEntityRef ref, boolean createIfMissing) {
		Object ret = null;
		Object id = ref.getID();

		if ( null != id ) {
			GiskardEntityRef uidRef = ref.getUnit();
			Object uid = (null == uidRef) ? id : uidRef.getID();

			Map unit = getEntity(uidRef, loadedUnits, uid, createIfMissing, GIS_TYP_MIND_UNIT);

			if ( (null != uidRef) && (null != unit) ) {
				ret = getEntity(ref, unit, id, createIfMissing, null);
			} else {
				ret = unit;
			}
		}

		return ret;
	}

	Map getEntity(GiskardEntityRef ref, Map parent, Object id, boolean createIfMissing, GiskardEntityRef pType) {
		Map ret = (Map) parent.get(id);
		if ( (null == ret) && createIfMissing ) {
			ret = new HashMap<>();
			ret.put(GIS_ATT_MIND_SELFREF, ref);
			if ( null != pType ) {
				ret.put(GIS_ATT_MIND_PRIMTYPE, pType);
			}
			parent.put(id, ret);
		}

		return ret;
	}

	public void setRuntime(Giskard runtime) {
		if ( !(runtime instanceof DustNodeAgentRuntime) ) {
			GiskardEntityRef refNodeType = GIS_TYP_DUST_RUNTIME;
			GiskardEntityRef refAttNextId = GIS_ATT_MIND_NEXTID;
			GiskardEntityRef refAttId = GIS_ATT_UTIL_ID;

			super.setRuntime(this);
			
			EntityRefProcessor brp = new EntityRefProcessor() {
				@Override
				public void processEntityRef(DustNodeEntityRef ref, int optUnitNextIdx) {
					Object e = resolve(ref, true);
					if ( 0 < optUnitNextIdx ) {
						((Map) e).put(refAttNextId, optUnitNextIdx);
					}
				}
			};

			DustNodeEntityRef.finishBoot(brp);

			Giskard.access(GiskardAccessCmd.Set, getClass().getName(), GiskardContext.ById, GIS_ATT_DUST_NATIVES,
					refNodeType, refAttId);

			for (BootEvent e : BOOT_EVENTS) {
				broadcastEvent_(e.eventType, e.params);
			}

			BOOT_EVENTS.clear();

			Giskard.broadcastEvent(null, loadedUnits);
		}
	}

	@Override
	protected <RetType> RetType accessData_(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
		if ( ctx != GiskardContext.ById ) {
			throw new IllegalAccessError("Illegal access command while booting");
		}

		int l = path.length;

		int lastIdx = 0;
		Object parent;
		Object lastOb;

		for (lastIdx = 0, lastOb = parent = loadedUnits; lastIdx < l; ++lastIdx) {
			if ( null == lastOb ) {
				lastOb = DustNodeUtils.createContainer(parent, path[lastIdx - 1], CLASSNAME_MAP);
			}
			parent = lastOb;
			lastOb = DustNodeUtils.getValue(parent, path[lastIdx], null);
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
			lastOb = DustNodeUtils.setValue(parent, path[lastIdx - 1], val);
			break;
		default:
			throw new IllegalAccessError("Illegal access command while booting");
		}

		return (RetType) lastOb;
	}

	@Override
	protected <RetType> RetType wrapException_(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void broadcastEvent_(GiskardEntityRef eventType, Object... params) {
		out.println(MontruUtils.sbAppend(null, " ", "Normal event", eventType, "at", new Date(), "params [",
				MontruUtils.sbAppend(null, ", ", params), "]"));
	}

	@Override
	protected String toString_(GiskardEntityRef ref) {
		return super.toString_(ref);
	}

	@Override
	public void gisAgentProcess() throws GiskardException {
		// TODO Auto-generated method stub
	}

	protected <ModAgent extends GiskardModule> ModAgent loadModule(String moduleName, String moduleVersion)
			throws Exception {
		return super.loadModule(moduleName, moduleVersion);
	}

	protected ClassLoader getCL(String moduleName, String moduleVersion) {
		return super.getCL(moduleName, moduleVersion);
	}

}
