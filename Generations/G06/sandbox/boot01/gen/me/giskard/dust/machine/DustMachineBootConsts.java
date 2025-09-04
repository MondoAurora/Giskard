package me.giskard.dust.machine;

import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;

public interface DustMachineBootConsts extends DustConsts {
	
	String LANG_ID = "iso:639-1:en";


	DustHandle MISC = new DustHandle("giskard.me/misc");
	
	DustHandle MISC_PARENT = new DustHandle(MISC, "?");
	DustHandle MISC_TARGET = new DustHandle(MISC, "7");
	DustHandle MISC_CONN_REQUIRED = new DustHandle(MISC, "4");
	DustHandle MISC_EXTID = new DustHandle(MISC, "6");

	DustHandle MIND = new DustHandle("giskard.me/mind");
	
	DustHandle IDEA_HANDLE = new DustHandle(MIND, "?");
	DustHandle IDEA_LISTENERS = new DustHandle(MIND, "?");
	
	DustHandle AGENT_LOGIC = new DustHandle(MIND, "?");
	
	DustHandle MEMBER_FACTORIES = new DustHandle(MIND, "?");
	
	DustHandle UNIT_HANDLES = new DustHandle(MIND, "?");
	DustHandle UNIT_IDEAS = new DustHandle(MIND, "?");
	DustHandle UNIT_NEXT_ID = new DustHandle(MIND, "1");
	
	DustHandle DIALOG_IDEAS = new DustHandle(MIND, "?");
	DustHandle DIALOG_VOCABULARY = new DustHandle(MIND, "?");

	DustHandle ACTION_INIT = new DustHandle(MIND, "119");

	DustHandle ACCESS_PEEK = new DustHandle(MIND, "12");
	DustHandle ACCESS_SET = new DustHandle(MIND, "14");

	DustHandle DUST = new DustHandle("giskard.me/dust");
	DustHandle PERS_ID = new DustHandle(DUST, "0");
	DustHandle MACHINE_THREADS = new DustHandle(DUST, "1");
	DustHandle MACHINE_DIALOGS = new DustHandle(DUST, "2");
	DustHandle THREAD_DIALOG = new DustHandle(DUST, "3");

	DustHandle MACHINE_UNITS = new DustHandle(DUST, "?");
	DustHandle MACHINE_MODULES = new DustHandle(DUST, "?");
	DustHandle LOGIC_BINARY = new DustHandle(DUST, "?");
	DustHandle AGENT_BINARY = new DustHandle(DUST, "?");

	DustHandle AGENT_SELF = new DustHandle(DUST, "4");
	DustHandle AGENT_PARAM = new DustHandle(DUST, "5");

	DustHandle DUSTJAVA = new DustHandle("giskard.me/dustjava");
	DustHandle LOGIC_CLASSNAME = new DustHandle(DUSTJAVA, "?");
	DustHandle MODULE_CLASSLOADER = new DustHandle(DUSTJAVA, "?");

	
	String LOAD_TOKEN_LANG = "lang";
	String LOAD_TOKEN_UNITMAP = "unitMap";
	
	String LOAD_TOKEN_UNIT_AUTHOR = "mind:0#unitAuthor";
	
	String LOAD_TOKEN_CONN_REQUIRED = "misc:4#connRequired";
	String LOAD_TOKEN_IDEA_TOKEN = "misc:5#ideaToken";
	String LOAD_TOKEN_EXT_ID = "misc:6#extId";


}
