package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMachine extends GiskardConsts { 
	MiNDToken MTUNI_MACHINE = Giskard.defineToken(MiNDTokenType.Unit, "Machine"); 
	MiNDToken MTTYP_MACHINE_MACHINE = Giskard.defineToken(MiNDTokenType.Type, "Machine", MTUNI_MACHINE);
	MiNDToken MTMEM_MACHINE_MACHINE_INSTANCE = Giskard.defineToken(MiNDTokenType.Member, "Instance", MTTYP_MACHINE_MACHINE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_MACHINE_MACHINE_MODULES = Giskard.defineToken(MiNDTokenType.Member, "Modules", MTTYP_MACHINE_MACHINE, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEM_MACHINE_MACHINE_DIALOGS = Giskard.defineToken(MiNDTokenType.Member, "Dialogs", MTTYP_MACHINE_MACHINE, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTTYP_MACHINE_NATIVE = Giskard.defineToken(MiNDTokenType.Type, "Native", MTUNI_MACHINE);
	MiNDToken MTTYP_MACHINE_MODULE = Giskard.defineToken(MiNDTokenType.Type, "Module", MTUNI_MACHINE);
	MiNDToken MTMEM_MACHINE_MODULE_NATIVES = Giskard.defineToken(MiNDTokenType.Member, "Natives", MTTYP_MACHINE_MODULE, MiNDValType.Link, MiNDCollType.Map);
}
