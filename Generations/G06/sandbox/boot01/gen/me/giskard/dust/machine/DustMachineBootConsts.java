package me.giskard.dust.machine;

import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;

public interface DustMachineBootConsts extends DustGenBoot {
	DustHandle RUNTIME_MACHINE = new DustHandle("/machine.0");
	DustHandle RUNTIME_MAINDIALOG = new DustHandle("/maindialog.0");

	String LOAD_TOKEN_LANG = "lang";
	String LOAD_TOKEN_UNITMAP = "unitMap";
	
	String LOAD_TOKEN_UNIT_AUTHOR = "mind:0#MIND_UNIT_AUTHOR";	
	String LOAD_TOKEN_CONN_REQUIRED = "misc:4#MISC_CONN_REQUIRED";
	String LOAD_TOKEN_IDEA_TOKEN = "misc:5#MISC_GEN_TOKEN";
	String LOAD_TOKEN_EXT_ID = "misc:6#MISC_GEN_EXTID";
	String LOAD_TOKEN_COMMIT_ID = "misc:2#MISC_VER_COMMIT";
}
