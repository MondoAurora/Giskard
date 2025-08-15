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
	
	DustHandle MISC = new DustHandle("giskard.me/misc");
	
	DustHandle MISC_PARENT = new DustHandle(MISC, "MISC_PARENT");
	DustHandle MISC_TARGET = new DustHandle(MISC, "MISC_TARGET");
	DustHandle MISC_CONN_REQUIRED = new DustHandle(MISC, "4");

	DustHandle MIND = new DustHandle("giskard.me/mind");
	
	DustHandle IDEA_HANDLE = new DustHandle(MIND, "IDEA_HANDLE");
	
	DustHandle UNIT_HANDLES = new DustHandle(MIND, "UNIT_HANDLES");
	DustHandle UNIT_IDEAS = new DustHandle(MIND, "UNIT_IDEAS");
	
	DustHandle DIALOG_IDEAS = new DustHandle(MIND, "DIALOG_IDEAS");
	DustHandle DIALOG_VOCABULARY = new DustHandle(MIND, "DIALOG_VOCABULARY");

	DustHandle DUST = new DustHandle("giskard.me/dust");

	DustHandle MACHINE_UNITS = new DustHandle(DUST, "MACHINE_UNITS");
	
	
	String LOAD_TOKEN_LANG = "lang";
	String LOAD_TOKEN_UNIT_AUTHOR = "mind#unitAuthor";
	String LOAD_TOKEN_CONN_REQUIRED = "misc#connRequired";
	String LOAD_TOKEN_IDEA_TOKEN = "misc#ideaToken";
	String LOAD_TOKEN_EXT_ID = "misc#extId";

}
