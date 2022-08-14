package me.giskard.dust;

import me.giskard.Giskard;

public interface DustConsts extends DustBootConsts {

	MiNDHandle HANDLE_STORE_ROOT = Giskard.access(MiNDAccessCommand.Get, null);

	//////////
	MiNDHandle HANDLE_UNIT_MODEL = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_HANDLE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_ENTITY = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_HANDLE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_STORE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_CONTEXT = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_ATT_ENTITY_PRIMARYTYPE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_TYPES = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_TAGS = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_UNIT = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_STORE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_STORE_HANDLES = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	
	/////////
	MiNDHandle HANDLE_UNIT_IDEA = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_TYPE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_TYPE_MEMBERS = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_MEMBER = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_MEMBER_TYPE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_MEMBER_REVLINK = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_TAG = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TAG_VALTYPE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_VALTYPE_REAL = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_VALTYPE_RAW = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_VALTYPE_LINK = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_VALTYPE_INT = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	MiNDHandle HANDLE_TAG_COLLTYPE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_COLLTYPE_ONE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_COLLTYPE_ARR = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_COLLTYPE_MAP = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_TAG_COLLTYPE_SET = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	
	
	/////////
	MiNDHandle HANDLE_UNIT_NARRATIVE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	/////////
	MiNDHandle HANDLE_UNIT_DIALOG = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	
	MiNDHandle HANDLE_TYPE_CONTEXT = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_CONTEXT_ENTITIES = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	
	/////////
	MiNDHandle HANDLE_UNIT_GENERIC = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_ENTITY_OWNER = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	MiNDHandle HANDLE_TAG_LENIENT = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	
	/////////
	MiNDHandle HANDLE_UNIT_BRAIN = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	MiNDHandle HANDLE_TYPE_BRAIN = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);

	MiNDHandle HANDLE_ATT_BRAIN_ROOTSTORE = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);
	MiNDHandle HANDLE_ATT_BRAIN_IMPL = Giskard.access(MiNDAccessCommand.Get, HANDLE_STORE_ROOT);


}
