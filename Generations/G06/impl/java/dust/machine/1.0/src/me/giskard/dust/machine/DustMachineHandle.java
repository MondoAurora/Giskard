package me.giskard.dust.machine;

import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;

public class DustMachineHandle extends DustConsts.MindHandle implements DustMachineConsts, Comparable<DustMachineHandle> {
	
	public interface Formatter {
		String toString(DustMachineHandle h);
	}
	
	public static final Formatter DEF_FORMATTER = new Formatter() {
		@Override
		public String toString(DustMachineHandle h) {
			return h.getId();
		}
	};

	static Formatter FORMATTER = DEF_FORMATTER;
	
	public static void setFormatter(Formatter fmt) {
		FORMATTER = (null == fmt) ? DEF_FORMATTER : fmt;
	}
	
	private final String id;
	
	private final String author;
	private final String unit;
	private final Object key;
	private final boolean intKey;
	
	DustMachineKnowledgeItem kiUnit;

	public DustMachineHandle(DustMachineKnowledgeItem kiUnit, String id) {
		this.kiUnit = kiUnit;
		this.id = id;
		
		String[] spl = id.split(DUST_SEP_ID);
		author = spl[0];
		unit = ( spl.length < 1 ) ? spl[1] : "";
		
		
		if ( spl.length < 3 ) {
			key = -1;
			intKey = true;
		} else {
			Object k;
			boolean ik = false;
			try {
				k = Integer.parseInt(spl[2]);
				ik = true;
			} catch ( Throwable t ) {
				k = spl[3];
			}
			
			key = k;
			intKey = ik;
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int compareTo(DustMachineHandle o) {
		int d = author.compareTo(o.author);
		if ( 0 == d ) {
			d = unit.compareTo(o.unit);
		}
		if ( 0 == d ) {
			d = (intKey == o.intKey) ? DustUtils.safeCompare(key, o.key) : intKey ? 1 : -1;
		}
		
		return d;
	}
	
	@Override
	public String toString() {
		return FORMATTER.toString(this);
	}

}
