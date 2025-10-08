package me.giskard.dust.machine;

// Generated 20251008T135942Z

import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;

public interface DustGenBoot extends DustConsts {

  String LANG_ID = "iso:639-1:en";

	DustHandle DUST = new DustHandle("giskard.me/dust.0");
	DustHandle DUST_AGENT_UNITREFS = new DustHandle(DUST, "10");
	DustHandle DUST_PERS_ID = new DustHandle(DUST, "0");
	DustHandle DUST_MACHINE_THREADS = new DustHandle(DUST, "1");
	DustHandle DUST_MACHINE_DIALOGS = new DustHandle(DUST, "2");
	DustHandle DUST_THREAD_DIALOG = new DustHandle(DUST, "3");
	DustHandle DUST_AGENT_SELF = new DustHandle(DUST, "4");
	DustHandle DUST_AGENT_PARAM = new DustHandle(DUST, "5");
	DustHandle DUST_MACHINE_UNITS = new DustHandle(DUST, "6");
	DustHandle DUST_MACHINE_MODULES = new DustHandle(DUST, "7");
	DustHandle DUST_LOGIC_BINARY = new DustHandle(DUST, "8");
	DustHandle DUST_AGENT_BINARY = new DustHandle(DUST, "9");
	DustHandle JAVA = new DustHandle("giskard.me/dustjava.0");
	DustHandle JAVA_LOGIC_CLASSNAME = new DustHandle(JAVA, "0");
	DustHandle JAVA_MODULE_CLASSLOADER = new DustHandle(JAVA, "1");
	DustHandle FORGE = new DustHandle("giskard.me/forge.0");
	DustHandle MIND = new DustHandle("giskard.me/mind.0");
	DustHandle MIND_IDEA_PRIMARYASPECT = new DustHandle(MIND, "59");
	DustHandle MIND_UNIT_AUTHOR = new DustHandle(MIND, "0");
	DustHandle MIND_UNIT_NEXT_ID = new DustHandle(MIND, "1");
	DustHandle MIND_IDEA_HANDLE = new DustHandle(MIND, "2");
	DustHandle MIND_IDEA_LISTENERS = new DustHandle(MIND, "3");
	DustHandle MIND_AGENT_LOGIC = new DustHandle(MIND, "4");
	DustHandle MIND_MEMBER_FACTORIES = new DustHandle(MIND, "5");
	DustHandle MIND_UNIT_HANDLES = new DustHandle(MIND, "6");
	DustHandle MIND_UNIT_IDEAS = new DustHandle(MIND, "7");
	DustHandle MIND_IDEA_TAGS = new DustHandle(MIND, "8");
	DustHandle MIND_TAG_ACCESS_PEEK = new DustHandle(MIND, "12");
	DustHandle MIND_TAG_ACCESS_SET = new DustHandle(MIND, "14");
	DustHandle MIND_TAG_ACTION_INIT = new DustHandle(MIND, "30");
	DustHandle MISC = new DustHandle("giskard.me/misc.0");
	DustHandle MISC_GEN_PAYLOAD = new DustHandle(MISC, "14");
	DustHandle MISC_CONN_REQUIRED = new DustHandle(MISC, "4");
	DustHandle MISC_GEN_TOKEN = new DustHandle(MISC, "5");
	DustHandle MISC_GEN_EXTID = new DustHandle(MISC, "6");
	DustHandle MISC_GEN_TARGET = new DustHandle(MISC, "7");
	DustHandle MISC_GEN_PARENT = new DustHandle(MISC, "8");
	DustHandle TEXT = new DustHandle("giskard.me/text.0");
	DustHandle TEXT_TAG_TOKEN = new DustHandle(TEXT, "1");
}