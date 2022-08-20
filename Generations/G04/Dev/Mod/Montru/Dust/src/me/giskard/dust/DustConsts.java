package me.giskard.dust;

import me.giskard.Giskard;

public interface DustConsts extends DustBootConsts {

	MiNDHandle GIS_STO_ROOT = Giskard.access(MiNDAccessCommand.Get, null);

	//////////
	MiNDHandle GIS_UNI_MODEL = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_MODEL_HANDLE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_MODEL_ENTITY = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_HANDLE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_STORE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_UNIT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_CONTEXT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_MEM_MODEL_ENTITY_PRIMARYTYPE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_TYPES = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_ENTITY_TAGS = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT); // 10
	

	MiNDHandle GIS_MEM_MODEL_ENTITY_OWNER = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_MODEL_STORE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_MODEL_STORE_HANDLES = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	
	/////////
	MiNDHandle GIS_UNI_IDEA = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_IDEA_UNIT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_IDEA_TYPE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_IDEA_TYPE_MEMBERS = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_IDEA_MEMBER = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_IDEA_MEMBER_TYPE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_IDEA_MEMBER_REVLINK = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT); // 20
	
	MiNDHandle GIS_TYP_IDEA_TAG = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TAG_IDEA_VALTYPE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_VALTYPE_REAL = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_VALTYPE_RAW = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_VALTYPE_LINK = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_VALTYPE_INT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TAG_IDEA_COLLTYPE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_COLLTYPE_ONE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_COLLTYPE_ARR = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_COLLTYPE_MAP = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_IDEA_COLLTYPE_SET = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	
	
	/////////
	MiNDHandle GIS_UNI_NARRATIVE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	/////////
	MiNDHandle GIS_UNI_DIALOG = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	
	MiNDHandle GIS_TYP_DIALOG_CONTEXT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_DIALOG_CONTEXT_ENTITIES = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	
	/////////
	MiNDHandle GIS_UNI_GENERIC = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TAG_GENERIC_LENIENT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	
	/////////
	MiNDHandle GIS_UNI_TEXT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TAG_TEXT_LANGTYPE_PRG = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_TEXT_LANGTYPE_DSL = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_TAG_TEXT_LANGTYPE_NAT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TYP_TEXT_LANG = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_TEXT_LANG_INDEX = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TYP_TEXT_TEXT = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_TEXT_TEXT_NAME = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	
	/////////
	MiNDHandle GIS_UNI_DUST = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_TYP_DUST_BRAIN = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

	MiNDHandle GIS_MEM_DUST_BRAIN_ROOTSTORE = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);
	MiNDHandle GIS_MEM_DUST_BRAIN_IMPL = Giskard.access(MiNDAccessCommand.Get, GIS_STO_ROOT);

}
