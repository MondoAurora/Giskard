package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMachine extends GiskardConsts { 
	MiNDToken MTUNIT_MACHINE = Giskard.defineToken(MiNDTokenType.Unit, "Machine"); 
	MiNDToken MTTYPE_MACHINE = Giskard.defineToken(MiNDTokenType.Type, "Machine", MTUNIT_MACHINE);
	MiNDToken MTTYPE_NATIVE = Giskard.defineToken(MiNDTokenType.Type, "Native", MTUNIT_MACHINE);
	MiNDToken MTTYPE_MODULE = Giskard.defineToken(MiNDTokenType.Type, "Module", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_MODULE_NATIVES = Giskard.defineToken(MiNDTokenType.Member, "Natives", MTTYPE_MODULE, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTMEMBER_MACHINE_MODULES = Giskard.defineToken(MiNDTokenType.Member, "Modules", MTTYPE_MACHINE, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_MACHINE_DIALOGS = Giskard.defineToken(MiNDTokenType.Member, "Dialogs", MTTYPE_MACHINE, MiNDValType.Link, MiNDCollType.Set);
}
