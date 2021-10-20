package me.giskard.dust.node;

import java.util.HashMap;
import java.util.Map;

public class Dust implements DustNodeConsts {
	
	class BootRef implements GiskardEntityRef {
		GiskardEntityRef unit;
		Object id;
		
		
		public BootRef(GiskardEntityRef unit, Object id) {
			this.unit = (null == unit) ? this : unit;
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
			if ( obj instanceof BootRef ) {
				BootRef br = (BootRef) obj;
				return id.equals(br.id) && (this == unit) ? (br == br.unit) : unit.equals(br.unit);
			}
			return false;
		}
	}
	
	private Map<BootRef, BootRef> bootRefs = null;
	
	DustNode theNode;
	
	public GiskardEntityRef getRef(GiskardEntityRef unit, Object id) {
		if ( null == theNode ) {
			if ( null == bootRefs ) {
				bootRefs = new HashMap<>();
			}
			BootRef br = new BootRef(unit, id);
			BootRef orig = bootRefs.get(br);
			if (null == orig ) {
				bootRefs.put(br, br);
				return br;
			} else {
				return orig;
			}
		} else {
			return null;
		}
	}
	
	public void init() throws Exception {
		theNode = (DustNode) Class.forName(getClass().getPackage().getName() + ".DustAgentNode").newInstance();
	}
}
