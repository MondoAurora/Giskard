package me.giskard.dust;

import me.giskard.Giskard;

public interface DustTokens extends DustBootConsts {

	MiNDHandle DUST_STO_ROOT = Giskard.access(MiNDAccessCommand.Get, null);

	//////////
	MiNDHandle MODEL_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle MODEL_TYP_HANDLE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle MODEL_TYP_ENTITY = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_HANDLE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_STORE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_UNIT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_CONTEXT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle MODEL_MEM_ENTITY_PRIMARYTYPE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_TYPES = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_ENTITY_TAGS = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT); // 10
	

	MiNDHandle MODEL_MEM_ENTITY_OWNER = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle MODEL_TYP_STORE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle MODEL_MEM_STORE_HANDLES = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	
	/////////
	MiNDHandle IDEA_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle IDEA_TYP_UNIT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle IDEA_TYP_TYPE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_MEM_TYPE_MEMBERS = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle IDEA_TYP_MEMBER = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_MEM_MEMBER_TYPE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_MEM_MEMBER_REVLINK = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT); // 20
	
	MiNDHandle IDEA_TYP_TAG = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle IDEA_TAG_VALTYPE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_VALTYPE_REAL = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_VALTYPE_RAW = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_VALTYPE_LINK = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_VALTYPE_INT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle IDEA_TAG_COLLTYPE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_COLLTYPE_ONE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_COLLTYPE_ARR = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_COLLTYPE_MAP = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle IDEA_TAG_COLLTYPE_SET = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	
	
	/////////
	MiNDHandle NARRATIVE_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	/////////
	MiNDHandle DIALOG_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	
	MiNDHandle DIALOG_TYP_CONTEXT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle DIALOG_MEM_CONTEXT_ENTITIES = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	
	/////////
	MiNDHandle GENERIC_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle GENERIC_TAG_LENIENT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	
	/////////
	MiNDHandle LANG_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle LANG_TAG_LANGTYPE_PRG = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_TAG_LANGTYPE_DSL = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_TAG_LANGTYPE_NAT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle LANG_TYP_LANG = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_MEM_LANG_GLOSSARY = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_MEM_LANG_TERMINOLOGY = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle LANG_TYP_TERM = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_MEM_TERM_STRING = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle LANG_MEM_TERM_MEANING = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	
	/////////
	MiNDHandle DUST_UNI = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle DUST_TYP_BRAIN = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle DUST_MEM_BRAIN_ROOTSTORE = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle DUST_MEM_BRAIN_IMPL = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);
	MiNDHandle DUST_MEM_BRAIN_DEF_LANG = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

	MiNDHandle DUST_LANG_BOOT = Giskard.access(MiNDAccessCommand.Get, DUST_STO_ROOT);

}
