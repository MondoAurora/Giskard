package me.giskard.mind;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMindHandles extends DustConsts {


	MindHandle DH_MIND_UNIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:0");
	MindHandle DH_MIND_UNIT_HANDLES = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:1");
	MindHandle DH_MIND_UNIT_CONTENT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:2");
	MindHandle DH_MIND_UNIT_AUTHOR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:3");

	MindHandle DH_MIND_KNOWLEDGE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:4");
	MindHandle DH_MIND_KNOWLEDGE_HANDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:5");
	MindHandle DH_MIND_KNOWLEDGE_UNIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:6");
	MindHandle DH_MIND_KNOWLEDGE_TAGS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:7");
	MindHandle DH_MIND_KNOWLEDGE_LISTENERS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:8");
	MindHandle DH_MIND_KNOWLEDGE_PRIMARYASPECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:9");
	MindHandle DH_MIND_KNOWLEDGE_ASPECTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:10");

	MindHandle DH_MIND_FACTORY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:11");
	MindHandle DH_MIND_FACTORY_PRIMARYASPECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:12");
	MindHandle DH_MIND_FACTORY_DEFATTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:13");
	MindHandle DH_MIND_FACTORY_NARRATIVE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:14");

	MindHandle DH_MIND_ASPECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:15");
	MindHandle DH_MIND_ASPECT_ATTFACTORIES = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:16");

	MindHandle DH_MIND_ATTRIBUTE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:17");
	MindHandle DH_MIND_ATTRIBUTE_FACTORY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:18");

	MindHandle DH_MIND_TAG = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:19");

	MindHandle DH_MIND_NARRATIVE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:20");

	MindHandle DH_MIND_ASSEMBLY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:21");
	MindHandle DH_MIND_ASSEMBLY_UNITS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:22");
	MindHandle DH_MIND_ASSEMBLY_STARTAGENTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:23");
	MindHandle DH_MIND_ASSEMBLY_STARTCOMMITS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:24");

	MindHandle DH_MIND_DIALOG = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:25");
	MindHandle DH_MIND_DIALOG_ASSEMBLY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:26");
	MindHandle DH_MIND_DIALOG_LAUNCHPARAMS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:27");
	MindHandle DH_MIND_DIALOG_ACTIVEAGENT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:28");

	MindHandle DH_MIND_AGENT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:29");
	MindHandle DH_MIND_AGENT_NARRATIVE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:30");
	MindHandle DH_MIND_AGENT_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:31");

	MindHandle DH_MIND_CONTEXT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:32");
	MindHandle DH_MIND_CONTEXT_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:33");
	MindHandle DH_MIND_CONTEXT_SELF = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:34");
	MindHandle DH_MIND_CONTEXT_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:35");
	MindHandle DH_MIND_CONTEXT_DIALOG = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:36");
	MindHandle DH_MIND_CONTEXT_DIRECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:37");
	MindHandle DH_MIND_CONTEXT_VISITITEM = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:38");
	MindHandle DH_MIND_CONTEXT_VISITATT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:39");
	MindHandle DH_MIND_CONTEXT_VISITKEY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:40");
	MindHandle DH_MIND_CONTEXT_VISITVALUE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:41");

	MindHandle DH_MIND_VISITFOLLOWREF = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:42");
	MindHandle DH_MIND_VISITFOLLOWREF_NO = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:43");
	MindHandle DH_MIND_VISITFOLLOWREF_ONCE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:44");
	MindHandle DH_MIND_VISITFOLLOWREF_ALWAYS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:45");

	MindHandle DH_MIND_VALTYPE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:46");
	MindHandle DH_MIND_VALTYPE_INT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:47");
	MindHandle DH_MIND_VALTYPE_REAL = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:48");
	MindHandle DH_MIND_VALTYPE_HANDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:49");
	MindHandle DH_MIND_VALTYPE_BIN = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:50");

	MindHandle DH_MIND_COLLTYPE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:51");
	MindHandle DH_MIND_COLLTYPE_ONE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:52");
	MindHandle DH_MIND_COLLTYPE_SET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:53");
	MindHandle DH_MIND_COLLTYPE_ARR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:54");
	MindHandle DH_MIND_COLLTYPE_MAP = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:55");

	MindHandle DH_MIND_ACCESS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:56");
	MindHandle DH_MIND_ACCESS_CHECK = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:57");
	MindHandle DH_MIND_ACCESS_PEEK = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:58");
	MindHandle DH_MIND_ACCESS_GET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:59");
	MindHandle DH_MIND_ACCESS_SET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:60");
	MindHandle DH_MIND_ACCESS_INSERT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:61");
	MindHandle DH_MIND_ACCESS_DELETE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:62");
	MindHandle DH_MIND_ACCESS_RESET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:63");
	MindHandle DH_MIND_ACCESS_COMMIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:64");
	MindHandle DH_MIND_ACCESS_BROADCAST = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:65");
	MindHandle DH_MIND_ACCESS_LOOKUP = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:66");
	MindHandle DH_MIND_ACCESS_VISIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:67");

	MindHandle DH_MIND_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:68");
	MindHandle DH_MIND_ACTION_INIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:69");
	MindHandle DH_MIND_ACTION_BEGIN = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:70");
	MindHandle DH_MIND_ACTION_PROCESS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:71");
	MindHandle DH_MIND_ACTION_END = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:72");
	MindHandle DH_MIND_ACTION_RELEASE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:73");

	MindHandle DH_MIND_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:74");
	MindHandle DH_MIND_STATUS_IDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:75");
	MindHandle DH_MIND_STATUS_PROCESSING = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:76");
	MindHandle DH_MIND_STATUS_WAITING = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:77");
	MindHandle DH_MIND_STATUS_ERROR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:78");

	MindHandle DH_MIND_RESULT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:79");
	MindHandle DH_MIND_RESULT_REJECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:80");
	MindHandle DH_MIND_RESULT_PASS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:81");
	MindHandle DH_MIND_RESULT_READ = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:82");
	MindHandle DH_MIND_RESULT_READACCEPT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:83");
	MindHandle DH_MIND_RESULT_ACCEPT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:84");

	MindHandle DH_MIND_AUTHOR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:85");

	MindHandle DH_MIND_SELECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:86");

	MindHandle DH_MIND_JOIN = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:87");
// No text assigned to handle giskard.me:mind_1.0:89
// No text assigned to handle giskard.me:mind_1.0:90
}

