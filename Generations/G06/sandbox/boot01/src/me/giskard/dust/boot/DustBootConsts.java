package me.giskard.dust.boot;

import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;

public interface DustBootConsts extends DustConsts {
	public class DustHandle extends MindHandle {
		DustHandle unit;
		String id;
		
		public DustHandle(DustHandle unit, String id) {
			this.unit = unit;
			this.id = id;
		}
		
		public DustHandle(String id) {
			this.unit = this;
			this.id = id;
		}
		
		@Override
		public String toString() {
			return DustUtils.sbAppend(null, "", true, "[", ((this == unit) ? "" : unit), "] ", id).toString();
		}
	}
	
	DustHandle IDEA_HANDLE = new DustHandle(null, "IDEA_HANDLE");
	
	DustHandle UNIT_HANDLES = new DustHandle(null, "UNIT_HANDLES");
	DustHandle UNIT_IDEAS = new DustHandle(null, "UNIT_IDEAS");
	
	DustHandle MISC_PARENT = new DustHandle(null, "MISC_PARENT");
	DustHandle MISC_TARGET = new DustHandle(null, "MISC_TARGET");
	DustHandle MISC_CONN_REQUIRES = new DustHandle(null, "MISC_CONN_REQUIRES");

	DustHandle DIALOG_UNITS = new DustHandle(null, "DIALOG_UNITS");
	DustHandle DIALOG_IDEAS = new DustHandle(null, "DIALOG_IDEAS");
	DustHandle DIALOG_VOCABULARY = new DustHandle(null, "DIALOG_VOCABULARY");

	String LOCAL_UNIT_AUTHOR = "author";
	String LOCAL_UNIT_ID = "token";
}
