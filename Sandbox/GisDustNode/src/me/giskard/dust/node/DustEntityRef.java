package me.giskard.dust.node;

import java.util.ArrayList;

import me.giskard.GiskardConsts.GiskardEntityRef;
import me.giskard.dust.node.DustBootConsts.BootRefProcessor;
import me.giskard.utils.GisUtilsCounter;

public class DustEntityRef implements GiskardEntityRef {
	private static GisUtilsCounter BOOT_REF_UNITS = new GisUtilsCounter();
	private static ArrayList<DustEntityRef> BOOT_REFS = new ArrayList<>();
	
	public static DustEntityRef createBootRef(DustEntityRef unit) {
		return createBootRef(unit, (null == unit) ? 0 : BOOT_REF_UNITS.add(unit));
	}
	
	public static DustEntityRef createBootRef(DustEntityRef unit, int id) {
		DustEntityRef ret = new DustEntityRef(unit, id);
		
		if ( null == unit ) {
			BOOT_REF_UNITS.add(ret);
		}
		BOOT_REFS.add(ret);
		
		return ret;
	}
	
	public static void finishBoot(BootRefProcessor refProc) {
		for ( DustEntityRef ref : BOOT_REFS ) {
			refProc.processBootRef(ref, BOOT_REF_UNITS.get(ref));
		}
		
		BOOT_REF_UNITS = null;
		BOOT_REFS = null;
	}
	
	DustEntityRef unit;
	final Object id;
	
	public DustEntityRef(DustEntityRef unit, Object id) {
		this.unit = (null == unit) ? this : unit;
		this.id = id;
	}

	@Override
	public GiskardEntityRef getUnit() {
		return unit;
	}

	public void setUnit(DustEntityRef unit) {
		this.unit = unit;
	}

	@Override
	public Object getID() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof DustEntityRef ) {
			DustEntityRef br = (DustEntityRef) obj;
			return id.equals(br.id) && (null == unit) ? (null == br.unit) : unit.equals(br.unit);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return DustBootUtils.toString(this);
	}
}