package me.giskard.dust.node;

import me.giskard.GiskardConsts.GiskardEntityRef;

class DustEntityRef implements GiskardEntityRef {
	DustEntityRef unit;
	final Object id;
	
	public DustEntityRef(DustEntityRef unit, Object id) {
		this.unit = unit;
		this.id = id;
	}

	@Override
	public GiskardEntityRef getUnit() {
		return unit;
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
		return DustBootLoader.toString(this);
	}
}