package me.giskard.mind;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMindHandles extends DustConsts {


	MindHandle MIND_ASP_UNIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:0");
	MindHandle MIND_ATT_UNIT_HANDLES = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:1");
	MindHandle MIND_ATT_UNIT_CONTENT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:2");
	MindHandle MIND_ATT_UNIT_AUTHOR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:3");

	MindHandle MIND_ASP_KNOWLEDGE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:4");
	MindHandle MIND_ATT_KNOWLEDGE_HANDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:5");
	MindHandle MIND_ATT_KNOWLEDGE_UNIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:6");
	MindHandle MIND_ATT_KNOWLEDGE_TAGS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:7");
	MindHandle MIND_ATT_KNOWLEDGE_LISTENERS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:8");
	MindHandle MIND_ATT_KNOWLEDGE_PRIMARYASPECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:9");
	MindHandle MIND_ATT_KNOWLEDGE_ASPECTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:10");
// No text assigned to handle giskard.me:mind_1.0:11
	MindHandle MIND_ATT_FACTORY_PRIMARYASPECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:12");
	MindHandle MIND_ATT_FACTORY_DEFATTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:13");
	MindHandle MIND_ATT_FACTORY_NARRATIVE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:14");
// No text assigned to handle giskard.me:mind_1.0:15
	MindHandle MIND_ATT_ASPECT_ATTFACTORIES = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:16");
// No text assigned to handle giskard.me:mind_1.0:17
	MindHandle MIND_ATT_ATTRIBUTE_FACTORY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:18");
// No text assigned to handle giskard.me:mind_1.0:19
// No text assigned to handle giskard.me:mind_1.0:20
// No text assigned to handle giskard.me:mind_1.0:21
	MindHandle MIND_ATT_ASSEMBLY_UNITS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:22");
	MindHandle MIND_ATT_ASSEMBLY_STARTAGENTS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:23");
	MindHandle MIND_ATT_ASSEMBLY_STARTCOMMITS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:24");

	MindHandle MIND_ASP_DIALOG = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:25");
	MindHandle MIND_ATT_DIALOG_ASSEMBLY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:26");
	MindHandle MIND_ATT_DIALOG_LAUNCHPARAMS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:27");
	MindHandle MIND_ATT_DIALOG_ACTIVEAGENT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:28");
// No text assigned to handle giskard.me:mind_1.0:29
	MindHandle MIND_ATT_AGENT_NARRATIVE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:30");
	MindHandle MIND_ATT_AGENT_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:31");

	MindHandle MIND_TAG_CONTEXT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:32");
	MindHandle MIND_TAG_CONTEXT_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:33");
	MindHandle MIND_TAG_CONTEXT_SELF = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:34");
	MindHandle MIND_TAG_CONTEXT_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:35");
	MindHandle MIND_TAG_CONTEXT_DIALOG = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:36");
	MindHandle MIND_TAG_CONTEXT_DIRECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:37");
	MindHandle MIND_TAG_CONTEXT_VISITITEM = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:38");
	MindHandle MIND_TAG_CONTEXT_VISITATT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:39");
	MindHandle MIND_TAG_CONTEXT_VISITKEY = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:40");
	MindHandle MIND_TAG_CONTEXT_VISITVALUE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:41");

	MindHandle MIND_TAG_VISITFOLLOWREF = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:42");
	MindHandle MIND_TAG_VISITFOLLOWREF_NO = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:43");
	MindHandle MIND_TAG_VISITFOLLOWREF_ONCE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:44");
	MindHandle MIND_TAG_VISITFOLLOWREF_ALWAYS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:45");

	MindHandle MIND_TAG_UNIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:46");
// No text assigned to handle giskard.me:mind_1.0:47

	MindHandle MIND_TAG_VALTYPE_INT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:48");

	MindHandle MIND_TAG_VALTYPE_REAL = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:49");

	MindHandle MIND_TAG_VALTYPE_HANDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:50");
// No text assigned to handle giskard.me:mind_1.0:51
// No text assigned to handle giskard.me:mind_1.0:52
// No text assigned to handle giskard.me:mind_1.0:53

	MindHandle MIND_TAG_COLLTYPE_SET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:54");

	MindHandle MIND_TAG_COLLTYPE_ARR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:55");

	MindHandle MIND_TAG_COLLTYPE_MAP = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:56");

	MindHandle MIND_TAG_ACCESS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:57");
	MindHandle MIND_TAG_ACCESS_CHECK = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:58");
	MindHandle MIND_TAG_ACCESS_PEEK = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:59");
	MindHandle MIND_TAG_ACCESS_GET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:60");
	MindHandle MIND_TAG_ACCESS_SET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:61");
	MindHandle MIND_TAG_ACCESS_INSERT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:62");
	MindHandle MIND_TAG_ACCESS_DELETE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:63");
	MindHandle MIND_TAG_ACCESS_RESET = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:64");
	MindHandle MIND_TAG_ACCESS_COMMIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:65");
	MindHandle MIND_TAG_ACCESS_BROADCAST = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:66");
	MindHandle MIND_TAG_ACCESS_LOOKUP = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:67");
	MindHandle MIND_TAG_ACCESS_VISIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:68");

	MindHandle MIND_TAG_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:69");
	MindHandle MIND_TAG_ACTION_INIT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:70");
	MindHandle MIND_TAG_ACTION_BEGIN = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:71");
	MindHandle MIND_TAG_ACTION_PROCESS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:72");
	MindHandle MIND_TAG_ACTION_END = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:73");
	MindHandle MIND_TAG_ACTION_RELEASE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:74");

	MindHandle MIND_TAG_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:75");
	MindHandle MIND_TAG_STATUS_IDLE = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:76");
	MindHandle MIND_TAG_STATUS_PROCESSING = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:77");
	MindHandle MIND_TAG_STATUS_WAITING = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:78");
	MindHandle MIND_TAG_STATUS_ERROR = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:79");

	MindHandle MIND_TAG_RESULT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:80");
	MindHandle MIND_TAG_RESULT_REJECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:81");
	MindHandle MIND_TAG_RESULT_PASS = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:82");
	MindHandle MIND_TAG_RESULT_READ = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:83");
	MindHandle MIND_TAG_RESULT_READACCEPT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:84");
	MindHandle MIND_TAG_RESULT_ACCEPT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:85");

	MindHandle MIND_NAR_SELECT = Dust.access(MindAccess.Lookup, "giskard.me:mind_1.0:87");
// No text assigned to handle giskard.me:mind_1.0:88
}

