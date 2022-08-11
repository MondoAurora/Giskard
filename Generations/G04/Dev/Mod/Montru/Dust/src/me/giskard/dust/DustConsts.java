package me.giskard.dust;

import me.giskard.Giskard;

public interface DustConsts extends DustBootConsts {

	MiNDEntity TOKEN_STORE_BOOT = Giskard.access(MiNDAccessCommand.Get, null);

	MiNDEntity TOKEN_UNIT_MODEL = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	MiNDEntity TOKEN_UNIT_IDEA = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	MiNDEntity TOKEN_UNIT_NARRATIVE = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	MiNDEntity TOKEN_UNIT_DIALOG = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	
	MiNDEntity TOKEN_TYPE_ENTITY = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	
	MiNDEntity TOKEN_ATT_ENTITY_HANDLE = Giskard.access(MiNDAccessCommand.Get, TOKEN_STORE_BOOT);
	
	
}
