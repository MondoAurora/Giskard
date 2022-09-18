package me.giskard.dust;

import me.giskard.Giskard;

public interface DustTokens extends DustBootConsts {

	//////////
	MiNDHandle MODEL_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle MODEL_TYP_HANDLE = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle MODEL_TYP_SOURCE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_SOURCE_KNOWLEDGE = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle MODEL_TYP_UNIT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_UNIT_STORE = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle MODEL_TYP_KNOWLEDGE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_UNIT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_STOREID = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_HANDLE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_PRIMARYTYPE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_TYPES = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_INFORMATION = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_KNOWLEDGE_TAGS = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle MODEL_TYP_INFORMATION = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_INFORMATION_MEMBER = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_INFORMATION_KEY = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle MODEL_MEM_INFORMATION_VALUE = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle IDEA_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle IDEA_TYP_TYPE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_MEM_TYPE_MEMBERS = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle IDEA_TYP_MEMBER = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_MEM_MEMBER_TYPE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_MEM_MEMBER_REVLINK = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle IDEA_TYP_TAG = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle IDEA_TAG_VALTYPE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_VALTYPE_REAL = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_VALTYPE_RAW = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_VALTYPE_LINK = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_VALTYPE_INT = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle IDEA_TAG_COLLTYPE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_COLLTYPE_ONE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_COLLTYPE_ARR = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_COLLTYPE_MAP = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle IDEA_TAG_COLLTYPE_SET = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle NARRATIVE_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_TYP_AGENT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_AGENT_REQUEST = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_AGT_JOURNEY = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_JOURNEY_SOURCES = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_JOURNEY_HANDLES = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_JOURNEY_ACTIVITY = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_AGT_REACH = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_AGT_ATTENTION = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_AGT_FLOW = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_AGENTS = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_START = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_END = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_CURRENT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_NEXT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_MEM_FLOW_ERRORHANDLERS = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_TAG_ACCESS = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_CHECK = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_PEEK = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_GET = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_SET = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_INSERT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACCESS_DELETE = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_TAG_ACTION = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACTION_INIT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACTION_BEGIN = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACTION_PROCESS = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACTION_END = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_ACTION_RELEASE = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle NARRATIVE_TAG_RESULT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_NOTIMPLEMENTED = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_REJECT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_ACCEPT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_ACCEPTREAD = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_READ = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_PROCESSING = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle NARRATIVE_TAG_RESULT_WAITING = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle DIALOG_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle GENERIC_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle GENERIC_TYP_GEN = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle GENERIC_MEM_GEN_OWNER = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle GENERIC_MEM_GEN_REQUIRES = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle GENERIC_TYP_REFERENCE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle GENERIC_MEM_REFERENCE_UNIT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle GENERIC_MEM_REFERENCE_STOREID = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle GENERIC_TAG_LENIENT = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle LANG_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle LANG_TAG_LANGTYPE_PRG = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_TAG_LANGTYPE_DSL = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_TAG_LANGTYPE_NAT = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle LANG_TYP_LANG = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_MEM_LANG_GLOSSARY = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_MEM_LANG_TERMINOLOGY = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle LANG_TYP_TEXT = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_MEM_TEXT_STRING = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle LANG_TYP_TERM = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle LANG_MEM_TERM_MEANING = Giskard.access(MiNDAccessCommand.Get, null);

	/////////
	MiNDHandle DUST_UNI = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle DUST_TYP_BRAIN = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle DUST_MEM_BRAIN_ROOTSTORE = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle DUST_MEM_BRAIN_IMPL = Giskard.access(MiNDAccessCommand.Get, null);
	MiNDHandle DUST_MEM_BRAIN_DEF_LANG = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDHandle DUST_LANG_BOOT = Giskard.access(MiNDAccessCommand.Get, null);

}
