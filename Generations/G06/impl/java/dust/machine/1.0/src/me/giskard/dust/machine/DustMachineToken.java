package me.giskard.dust.machine;

import java.util.Map;

import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;

public class DustMachineToken extends DustConsts.MindToken implements DustMachineConsts, Comparable<DustMachineToken> {
	
	public interface Formatter {
		String toString(DustMachineToken h);
	}
	
	public static final Formatter DEF_FORMATTER = new Formatter() {
		@Override
		public String toString(DustMachineToken h) {
			return h.getId();
		}
	};

	static Formatter FORMATTER = DEF_FORMATTER;
	
	public static void setFormatter(Formatter fmt) {
		FORMATTER = (null == fmt) ? DEF_FORMATTER : fmt;
	}
	
	private final String id;
	
	private final String authorID;
	private final String unitID;
	private final Object key;
//	private final boolean intKey;
	
	private final Map<MindToken, Object> unit;

	public DustMachineToken(Map<MindToken, Object> iUnit, String id) {
		this.unit = iUnit;
		this.id = id;
		
		String[] spl = id.split(DUST_SEP_ID);
		authorID = spl[0];
		unitID = ( spl.length < 1 ) ? spl[1] : "";
		
		
		if ( spl.length < 3 ) {
			key = -1;
//			intKey = true;
		} else {
			Object k;
//			boolean ik = false;
			try {
				k = Integer.parseInt(spl[2]);
//				ik = true;
			} catch ( Throwable t ) {
				k = spl[3];
			}
			
			key = k;
//			intKey = ik;
		}
	}
	
	public Map<MindToken, Object> getUnitItem() {
		return unit;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int compareTo(DustMachineToken o) {
		int d = authorID.compareTo(o.authorID);
		if ( 0 == d ) {
			d = unitID.compareTo(o.unitID);
		}
		if ( 0 == d ) {
			d = DustUtils.safeCompare(key, o.key);
//			d = (intKey == o.intKey) ? DustUtils.safeCompare(key, o.key) : intKey ? 1 : -1;
		}
		
		return d;
	}
	
	@Override
	public String toString() {
		return FORMATTER.toString(this);
	}

}
