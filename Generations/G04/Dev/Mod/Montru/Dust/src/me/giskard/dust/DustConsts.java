package me.giskard.dust;

import me.giskard.Giskard;

public interface DustConsts extends DustBootConsts {

	MiNDEntity TOKEN_STORE_ROOT = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDEntity TOKEN_UNIT_MODEL = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_UNIT_IDEA = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_UNIT_NARRATIVE = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_UNIT_DIALOG = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	
	MiNDEntity TOKEN_TYPE_ENTITY = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_ATT_ENTITY_HANDLE = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	
	MiNDEntity TOKEN_TYPE_STORE = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_ATT_STORE_ENTITIES = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	
	MiNDEntity TOKEN_TYPE_CONTEXT = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	MiNDEntity TOKEN_ATT_CONTEXT_ENTITIES = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);
	
	MiNDEntity TOKEN_UNIT_BRAIN = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);

	MiNDEntity TOKEN_TYPE_BRAIN = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);

	MiNDEntity TOKEN_ATT_BRAIN_IMPL = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_ROOT);

}
