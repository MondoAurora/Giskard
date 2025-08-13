package me.giskard.dust.boot;

import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;

//@SuppressWarnings({"rawtypes", "unchecked"})
public interface DustBootConsts extends DustConsts {
	public class DustHandle extends MindHandle {
		DustHandle unit;
		String id;
		
		public DustHandle(DustHandle unit, String id) {
			this.unit = unit;
			this.id = id;
		}
		
		@Override
		public String toString() {
			return DustUtils.sbAppend(null, "", true, "[", unit, "] ", id).toString();
		}
	}
	
	DustHandle IDEA_HANDLE = new DustHandle(null, "IDEA_HANDLE");
	DustHandle UNIT_HANDLES = new DustHandle(null, "UNIT_HANDLES");
	
	DustHandle MISC_PARENT = new DustHandle(null, "MISC_PARENT");
	DustHandle MISC_TARGET = new DustHandle(null, "MISC_TARGET");

	String REF_PREFIX = "->";


}
