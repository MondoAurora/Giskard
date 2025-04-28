package me.giskard.mind;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMindTokens extends DustConsts {


	MindToken TOKEN_MIND_UNIT = Dust.access(null, "giskard.me:mind_1.0:0");
	MindToken TOKEN_MIND_UNIT_HANDLES = Dust.access(null, "giskard.me:mind_1.0:1");
	MindToken TOKEN_MIND_UNIT_CONTENT = Dust.access(null, "giskard.me:mind_1.0:2");
	MindToken TOKEN_MIND_UNIT_AUTHOR = Dust.access(null, "giskard.me:mind_1.0:3");

	MindToken TOKEN_MIND_KNOWLEDGE = Dust.access(null, "giskard.me:mind_1.0:4");
	MindToken TOKEN_MIND_KNOWLEDGE_HANDLE = Dust.access(null, "giskard.me:mind_1.0:5");
	MindToken TOKEN_MIND_KNOWLEDGE_UNIT = Dust.access(null, "giskard.me:mind_1.0:6");
	MindToken TOKEN_MIND_KNOWLEDGE_TAGS = Dust.access(null, "giskard.me:mind_1.0:7");
	MindToken TOKEN_MIND_KNOWLEDGE_LISTENERS = Dust.access(null, "giskard.me:mind_1.0:8");
	MindToken TOKEN_MIND_KNOWLEDGE_PRIMARYASPECT = Dust.access(null, "giskard.me:mind_1.0:9");
	MindToken TOKEN_MIND_KNOWLEDGE_ASPECTS = Dust.access(null, "giskard.me:mind_1.0:10");

	MindToken TOKEN_MIND_FACTORY = Dust.access(null, "giskard.me:mind_1.0:11");
	MindToken TOKEN_MIND_FACTORY_PRIMARYASPECT = Dust.access(null, "giskard.me:mind_1.0:12");
	MindToken TOKEN_MIND_FACTORY_DEFATTS = Dust.access(null, "giskard.me:mind_1.0:13");
	MindToken TOKEN_MIND_FACTORY_NARRATIVE = Dust.access(null, "giskard.me:mind_1.0:14");

	MindToken TOKEN_MIND_ASPECT = Dust.access(null, "giskard.me:mind_1.0:15");
	MindToken TOKEN_MIND_ASPECT_ATTFACTORIES = Dust.access(null, "giskard.me:mind_1.0:16");

	MindToken TOKEN_MIND_ATTRIBUTE = Dust.access(null, "giskard.me:mind_1.0:17");
	MindToken TOKEN_MIND_ATTRIBUTE_FACTORY = Dust.access(null, "giskard.me:mind_1.0:18");

	MindToken TOKEN_MIND_TAG = Dust.access(null, "giskard.me:mind_1.0:19");

	MindToken TOKEN_MIND_NARRATIVE = Dust.access(null, "giskard.me:mind_1.0:20");

	MindToken TOKEN_MIND_ASSEMBLY = Dust.access(null, "giskard.me:mind_1.0:21");
	MindToken TOKEN_MIND_ASSEMBLY_UNITS = Dust.access(null, "giskard.me:mind_1.0:22");
	MindToken TOKEN_MIND_ASSEMBLY_STARTAGENTS = Dust.access(null, "giskard.me:mind_1.0:23");
	MindToken TOKEN_MIND_ASSEMBLY_STARTCOMMITS = Dust.access(null, "giskard.me:mind_1.0:24");

	MindToken TOKEN_MIND_DIALOG = Dust.access(null, "giskard.me:mind_1.0:25");
	MindToken TOKEN_MIND_DIALOG_ASSEMBLY = Dust.access(null, "giskard.me:mind_1.0:26");
	MindToken TOKEN_MIND_DIALOG_LAUNCHPARAMS = Dust.access(null, "giskard.me:mind_1.0:27");
	MindToken TOKEN_MIND_DIALOG_ACTIVEAGENT = Dust.access(null, "giskard.me:mind_1.0:28");

	MindToken TOKEN_MIND_AGENT = Dust.access(null, "giskard.me:mind_1.0:29");
	MindToken TOKEN_MIND_AGENT_NARRATIVE = Dust.access(null, "giskard.me:mind_1.0:30");
	MindToken TOKEN_MIND_AGENT_TARGET = Dust.access(null, "giskard.me:mind_1.0:31");

	MindToken TOKEN_MIND_CONTEXT = Dust.access(null, "giskard.me:mind_1.0:32");
	MindToken TOKEN_MIND_CONTEXT_ACTION = Dust.access(null, "giskard.me:mind_1.0:33");
	MindToken TOKEN_MIND_CONTEXT_SELF = Dust.access(null, "giskard.me:mind_1.0:34");
	MindToken TOKEN_MIND_CONTEXT_TARGET = Dust.access(null, "giskard.me:mind_1.0:35");
	MindToken TOKEN_MIND_CONTEXT_DIALOG = Dust.access(null, "giskard.me:mind_1.0:36");
	MindToken TOKEN_MIND_CONTEXT_DIRECT = Dust.access(null, "giskard.me:mind_1.0:37");
	MindToken TOKEN_MIND_CONTEXT_VISITITEM = Dust.access(null, "giskard.me:mind_1.0:38");
	MindToken TOKEN_MIND_CONTEXT_VISITATT = Dust.access(null, "giskard.me:mind_1.0:39");
	MindToken TOKEN_MIND_CONTEXT_VISITKEY = Dust.access(null, "giskard.me:mind_1.0:40");
	MindToken TOKEN_MIND_CONTEXT_VISITVALUE = Dust.access(null, "giskard.me:mind_1.0:41");

	MindToken TOKEN_MIND_VISITFOLLOWREF = Dust.access(null, "giskard.me:mind_1.0:42");
	MindToken TOKEN_MIND_VISITFOLLOWREF_NO = Dust.access(null, "giskard.me:mind_1.0:43");
	MindToken TOKEN_MIND_VISITFOLLOWREF_ONCE = Dust.access(null, "giskard.me:mind_1.0:44");
	MindToken TOKEN_MIND_VISITFOLLOWREF_ALWAYS = Dust.access(null, "giskard.me:mind_1.0:45");

	MindToken TOKEN_MIND_VALTYPE = Dust.access(null, "giskard.me:mind_1.0:46");
	MindToken TOKEN_MIND_VALTYPE_INT = Dust.access(null, "giskard.me:mind_1.0:47");
	MindToken TOKEN_MIND_VALTYPE_REAL = Dust.access(null, "giskard.me:mind_1.0:48");
	MindToken TOKEN_MIND_VALTYPE_HANDLE = Dust.access(null, "giskard.me:mind_1.0:49");
	MindToken TOKEN_MIND_VALTYPE_BIN = Dust.access(null, "giskard.me:mind_1.0:50");

	MindToken TOKEN_MIND_COLLTYPE = Dust.access(null, "giskard.me:mind_1.0:51");
	MindToken TOKEN_MIND_COLLTYPE_ONE = Dust.access(null, "giskard.me:mind_1.0:52");
	MindToken TOKEN_MIND_COLLTYPE_SET = Dust.access(null, "giskard.me:mind_1.0:53");
	MindToken TOKEN_MIND_COLLTYPE_ARR = Dust.access(null, "giskard.me:mind_1.0:54");
	MindToken TOKEN_MIND_COLLTYPE_MAP = Dust.access(null, "giskard.me:mind_1.0:55");

	MindToken TOKEN_MIND_ACCESS = Dust.access(null, "giskard.me:mind_1.0:56");
	MindToken TOKEN_MIND_ACCESS_CHECK = Dust.access(null, "giskard.me:mind_1.0:57");
	MindToken TOKEN_MIND_ACCESS_PEEK = Dust.access(null, "giskard.me:mind_1.0:58");
	MindToken TOKEN_MIND_ACCESS_GET = Dust.access(null, "giskard.me:mind_1.0:59");
	MindToken TOKEN_MIND_ACCESS_SET = Dust.access(null, "giskard.me:mind_1.0:60");
	MindToken TOKEN_MIND_ACCESS_INSERT = Dust.access(null, "giskard.me:mind_1.0:61");
	MindToken TOKEN_MIND_ACCESS_DELETE = Dust.access(null, "giskard.me:mind_1.0:62");
	MindToken TOKEN_MIND_ACCESS_RESET = Dust.access(null, "giskard.me:mind_1.0:63");
	MindToken TOKEN_MIND_ACCESS_COMMIT = Dust.access(null, "giskard.me:mind_1.0:64");
	MindToken TOKEN_MIND_ACCESS_BROADCAST = Dust.access(null, "giskard.me:mind_1.0:65");
	MindToken TOKEN_MIND_ACCESS_LOOKUP = Dust.access(null, "giskard.me:mind_1.0:66");
	MindToken TOKEN_MIND_ACCESS_VISIT = Dust.access(null, "giskard.me:mind_1.0:67");

	MindToken TOKEN_MIND_ACTION = Dust.access(null, "giskard.me:mind_1.0:68");
	MindToken TOKEN_MIND_ACTION_INIT = Dust.access(null, "giskard.me:mind_1.0:69");
	MindToken TOKEN_MIND_ACTION_BEGIN = Dust.access(null, "giskard.me:mind_1.0:70");
	MindToken TOKEN_MIND_ACTION_PROCESS = Dust.access(null, "giskard.me:mind_1.0:71");
	MindToken TOKEN_MIND_ACTION_END = Dust.access(null, "giskard.me:mind_1.0:72");
	MindToken TOKEN_MIND_ACTION_RELEASE = Dust.access(null, "giskard.me:mind_1.0:73");

	MindToken TOKEN_MIND_STATUS = Dust.access(null, "giskard.me:mind_1.0:74");
	MindToken TOKEN_MIND_STATUS_IDLE = Dust.access(null, "giskard.me:mind_1.0:75");
	MindToken TOKEN_MIND_STATUS_PROCESSING = Dust.access(null, "giskard.me:mind_1.0:76");
	MindToken TOKEN_MIND_STATUS_WAITING = Dust.access(null, "giskard.me:mind_1.0:77");
	MindToken TOKEN_MIND_STATUS_ERROR = Dust.access(null, "giskard.me:mind_1.0:78");

	MindToken TOKEN_MIND_RESULT = Dust.access(null, "giskard.me:mind_1.0:79");
	MindToken TOKEN_MIND_RESULT_REJECT = Dust.access(null, "giskard.me:mind_1.0:80");
	MindToken TOKEN_MIND_RESULT_PASS = Dust.access(null, "giskard.me:mind_1.0:81");
	MindToken TOKEN_MIND_RESULT_READ = Dust.access(null, "giskard.me:mind_1.0:82");
	MindToken TOKEN_MIND_RESULT_READACCEPT = Dust.access(null, "giskard.me:mind_1.0:83");
	MindToken TOKEN_MIND_RESULT_ACCEPT = Dust.access(null, "giskard.me:mind_1.0:84");

	MindToken TOKEN_MIND_AUTHOR = Dust.access(null, "giskard.me:mind_1.0:85");

	MindToken TOKEN_MIND_SELECT = Dust.access(null, "giskard.me:mind_1.0:86");

	MindToken TOKEN_MIND_JOIN = Dust.access(null, "giskard.me:mind_1.0:87");
// No text assigned to handle giskard.me:mind_1.0:89
// No text assigned to handle giskard.me:mind_1.0:90
}

