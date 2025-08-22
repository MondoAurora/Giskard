package me.giskard.dust.machine;

import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;

public interface DustMachineBootConsts extends DustConsts {
	
	String LANG_ID = "iso:639-1:en";


	DustHandle MISC = new DustHandle("giskard.me/misc");
	
	DustHandle MISC_PARENT = new DustHandle(MISC, "?");
	DustHandle MISC_TARGET = new DustHandle(MISC, "?");
	DustHandle MISC_CONN_REQUIRED = new DustHandle(MISC, "4");

	DustHandle MIND = new DustHandle("giskard.me/mind");
	
	DustHandle IDEA_HANDLE = new DustHandle(MIND, "?");
	
	DustHandle UNIT_HANDLES = new DustHandle(MIND, "?");
	DustHandle UNIT_IDEAS = new DustHandle(MIND, "?");
	DustHandle UNIT_NEXT_ID = new DustHandle(MIND, "1");
	
	DustHandle DIALOG_IDEAS = new DustHandle(MIND, "?");
	DustHandle DIALOG_VOCABULARY = new DustHandle(MIND, "?");

	DustHandle ACTION_INIT = new DustHandle(MIND, "?");

	DustHandle ACCESS_PEEK = new DustHandle(MIND, "?");
	DustHandle ACCESS_SET = new DustHandle(MIND, "?");

	DustHandle DUST = new DustHandle("giskard.me/dust");
	DustHandle PERS_ID = new DustHandle(DUST, "0");

	DustHandle MACHINE_UNITS = new DustHandle(DUST, "?");
	
	
	String LOAD_TOKEN_LANG = "lang";
	String LOAD_TOKEN_UNITMAP = "unitMap";
	
	String LOAD_TOKEN_UNIT_AUTHOR = "mind#unitAuthor";
	
	String LOAD_TOKEN_CONN_REQUIRED = "misc#connRequired";
	String LOAD_TOKEN_IDEA_TOKEN = "misc#ideaToken";
	String LOAD_TOKEN_EXT_ID = "misc#extId";

}
