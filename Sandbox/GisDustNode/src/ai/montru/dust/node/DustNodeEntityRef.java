package ai.montru.dust.node;

import java.util.ArrayList;

import ai.montru.MontruMain;
import ai.montru.giskard.GiskardConsts;
import ai.montru.utils.MontruUtilsCounter;

public class DustNodeEntityRef implements GiskardConsts, GiskardConsts.GiskardEntityRef {
	public interface EntityRefProcessor {
		void processEntityRef(DustNodeEntityRef ref, int optUnitNextIdx);
	}

	private static class BootLoader extends MontruMain {
		MontruUtilsCounter refUnits = new MontruUtilsCounter();
		ArrayList<DustNodeEntityRef> refs = new ArrayList<>();
		
		public BootLoader(MontruMain runtime) {
			runtime.setRuntime(this);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected <RetType> RetType accessData_(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
			DustNodeEntityRef unit = (path.length > 0) ? (DustNodeEntityRef) path[0] : null;

			if ( GIS_CONST_IDX_ADD.equals(val) ) {
				val = (null == unit) ? 0 : refUnits.add(unit);
			}
			DustNodeEntityRef ret = new DustNodeEntityRef(unit, val);

			if ( null == unit ) {
				refUnits.add(ret);
			}
			refs.add(ret);

			return (RetType) ret;
		}
		
		void finishBoot(EntityRefProcessor refProc) {
			for (DustNodeEntityRef ref : refs) {
				refProc.processEntityRef(ref, refUnits.get(ref));
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

	public DustNodeEntityRef(DustNodeEntityRef unit, Object id) {
		this.unit = unit;
		this.id = id;
	}

	@Override
	public GiskardEntityRef getUnit() {
		return unit;
	}

	void setUnit(DustNodeEntityRef unit) {
		this.unit = unit;
	}

	@Override
	public Object getID() {
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