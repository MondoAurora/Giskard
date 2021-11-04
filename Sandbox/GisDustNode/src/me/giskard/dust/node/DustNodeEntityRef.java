package me.giskard.dust.node;

import java.util.ArrayList;

import me.giskard.GiskardConsts;
import me.giskard.GiskardMain;
import me.giskard.utils.GisUtilsCounter;

public class DustNodeEntityRef implements GiskardConsts, GiskardConsts.GiskardEntityRef {
	public interface EntityRefProcessor {
		void processEntityRef(DustNodeEntityRef ref, int optUnitNextIdx);
	}

	private static class BootLoader extends GiskardMain {
		GisUtilsCounter refUnits = new GisUtilsCounter();
		ArrayList<DustNodeEntityRef> refs = new ArrayList<>();
		
		public BootLoader(GiskardMain runtime) {
			runtime.setRuntime(this);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected <RetType> RetType accessData_(GiskardAccessCmd cmd, Object val, GiskardContext ctx, Object... path) {
			DustNodeEntityRef unit = (path.length > 0) ? (DustNodeEntityRef) path[0] : null;
			int id = (int) val;

			if ( -1 == id ) {
				id = (null == unit) ? 0 : refUnits.add(unit);
			}
			DustNodeEntityRef ret = new DustNodeEntityRef(unit, id);

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

	public static boolean initBoot(GiskardMain runtime) {
		if ( null == BOOTLOADER ) {
			BOOTLOADER = new BootLoader(runtime);
		}
		
		return true;
	};

	public static void finishBoot(EntityRefProcessor refProc) {
		BOOTLOADER.finishBoot(refProc);
	}

	DustNodeEntityRef unit;
	final Object id;

	public DustNodeEntityRef(DustNodeEntityRef unit, Object id) {
		this.unit = (null == unit) ? this : unit;
		this.id = id;
	}

	@Override
	public GiskardEntityRef getUnit() {
		return unit;
	}

	public void setUnit(DustNodeEntityRef unit) {
		this.unit = unit;
	}

	@Override
	public Object getID() {
		return id;
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