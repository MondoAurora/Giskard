package me.giskard.dust.machine;

// Generated 20251004T152445Z

import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;

public interface DustGenBoot extends DustConsts {

  String LANG_ID = "iso:639-1:en";

	DustHandle DUST = new DustHandle("giskard.me/dust.0");
	DustHandle DUSTJAVA = new DustHandle("giskard.me/dustjava.0");
	DustHandle MIND = new DustHandle("giskard.me/mind.0");
	DustHandle MISC = new DustHandle("giskard.me/misc.0");
	DustHandle TEXT = new DustHandle("giskard.me/text.0");
	DustHandle MACHINE_UNITS = new DustHandle(DUST, "6");
	DustHandle MACHINE_DIALOGS = new DustHandle(DUST, "2");
	DustHandle THREAD_DIALOG = new DustHandle(DUST, "3");
	DustHandle MACHINE_THREADS = new DustHandle(DUST, "1");
	DustHandle LOGIC_BINARY = new DustHandle(DUST, "8");
	DustHandle MACHINE_MODULES = new DustHandle(DUST, "7");
	DustHandle AGENT_BINARY = new DustHandle(DUST, "9");
	DustHandle AGENT_SELF = new DustHandle(DUST, "4");
	DustHandle AGENT_PARAM = new DustHandle(DUST, "5");
	DustHandle AGENT_UNITREFS = new DustHandle(DUST, "10");
	DustHandle PERS_ID = new DustHandle(DUST, "0");
	DustHandle MODULE_CLASSLOADER = new DustHandle(DUSTJAVA, "1");
	DustHandle LOGIC_CLASSNAME = new DustHandle(DUSTJAVA, "0");
	DustHandle UNIT_IDEAS = new DustHandle(MIND, "7");
	DustHandle AGENT_LOGIC = new DustHandle(MIND, "4");
	DustHandle ACTION_INIT = new DustHandle(MIND, "30");
	DustHandle IDEA_PRIMARYASPECT = new DustHandle(MIND, "59");
	DustHandle MEMBER_FACTORIES = new DustHandle(MIND, "5");
	DustHandle ACCESS_SET = new DustHandle(MIND, "14");
	DustHandle UNIT_NEXT_ID = new DustHandle(MIND, "1");
	DustHandle ACCESS_PEEK = new DustHandle(MIND, "12");
	DustHandle IDEA_HANDLE = new DustHandle(MIND, "2");
	DustHandle UNIT_HANDLES = new DustHandle(MIND, "6");
	DustHandle UNIT_AUTHOR = new DustHandle(MIND, "0");
	DustHandle IDEA_LISTENERS = new DustHandle(MIND, "3");
	DustHandle MISC_PARENT = new DustHandle(MISC, "8");
	DustHandle MISC_EXTID = new DustHandle(MISC, "6");
	DustHandle MISC_TARGET = new DustHandle(MISC, "7");
	DustHandle MISC_TOKEN = new DustHandle(MISC, "5");
	DustHandle MISC_PAYLOAD = new DustHandle(MISC, "14");
	DustHandle MISC_CONN_REQUIRED = new DustHandle(MISC, "4");
	DustHandle TEXT_TOKEN = new DustHandle(TEXT, "1");
	DustHandle RUNTIME_MACHINE = new DustHandle("/machine.0");
}