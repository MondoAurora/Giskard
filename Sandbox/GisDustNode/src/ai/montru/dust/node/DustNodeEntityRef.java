package ai.montru.dust.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ai.montru.MontruMain;
import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;
import ai.montru.utils.MontruUtils;
import ai.montru.utils.MontruUtilsCounter;

public class DustNodeEntityRef implements GiskardConsts, GiskardConsts.GiskardEntityRef {
	public interface EntityRefProcessor {
		void processEntityRef(DustNodeEntityRef ref, String id, int optUnitNextIdx);
	}

	private static class BootLoader extends MontruMain {
		MontruUtilsCounter refUnits = new MontruUtilsCounter();
		ArrayList<DustNodeEntityRef> refs = new ArrayList<>();
		Map<DustNodeEntityRef, String> idMap = new HashMap<>();
		
		public BootLoader(MontruMain runtime) {
			runtime.setRuntime(this);
		}
		
		@Override
		public GiskardEntityRef gisGetToken(GiskardEntityRef unit, Object val) {
			Object id = refUnits.add(unit) - 1;
			DustNodeEntityRef ret = new DustNodeEntityRef(unit, id);
			String strId = (val instanceof String) ? (String) val : MontruUtils.toString(id);

			if ( null == unit ) {
				refUnits.add(ret);
			} else {
				strId = idMap.get(unit).split("/")[2] + "::" + strId;	
			}
			refs.add(ret);
						
 			System.out.println("Registering " + strId);
			if ( idMap.containsValue(strId)) {
				Giskard.wrapException(null, null, "Boot id conflict", strId);
			}
			idMap.put(ret, strId);

			return ret;		
		}
		
		void finishBoot(EntityRefProcessor refProc) {
			for (DustNodeEntityRef ref : refs) {
				refProc.processEntityRef(ref, idMap.get(ref), refUnits.get(ref));
			}

			refUnits = null;
			refs = null;
		}
	}

	private static BootLoader BOOTLOADER;

	public static boolean initBoot(MontruMain runtime) {
		if ( null == BOOTLOADER ) {
			BOOTLOADER = new BootLoader(runtime);
		}
		
		return true;
	};

	public static void finishBoot(EntityRefProcessor refProc) {
		BOOTLOADER.finishBoot(refProc);
	}

	private DustNodeEntityRef unit;
	private Object id;

	public DustNodeEntityRef(GiskardEntityRef unit, Object id) {
		this.unit = (DustNodeEntityRef) unit;
		this.id = id;
	}

	@Override
	public DustNodeEntityRef gisGetUnit() {
		return unit;
	}

	void setUnit(DustNodeEntityRef unit) {
		this.unit = unit;
	}

	@Override
	public Object gisGetID() {
		return id;
	}
	
	void setId(Object id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof DustNodeEntityRef ) {
			DustNodeEntityRef br = (DustNodeEntityRef) obj;
			return id.equals(br.id) && (null == unit) ? (null == br.unit) : unit.equals(br.unit);
		}
		return false;
	}

	@Override
	public String toString() {
		return DustNodeUtils.toString(this);
	}
}