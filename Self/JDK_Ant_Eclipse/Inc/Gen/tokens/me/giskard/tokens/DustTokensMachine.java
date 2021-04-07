package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMachine extends GiskardConsts {
	MiNDToken MTUNIT_MACHINE = Giskard.defineToken(MiNDTokenType.UNIT, "Machine");
	
	MiNDToken MTSHARED_MACHINE = Giskard.defineToken(MiNDTokenType.LOCAL, "Machine", MTUNIT_MACHINE);
	
	MiNDToken MTTYPE_MACHINE = Giskard.defineToken(MiNDTokenType.TYPE, "Machine", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_MACHINE_MODULES = Giskard.defineToken(MiNDTokenType.MEMBER, "Modules", MTTYPE_MACHINE, MiNDValType.Link, MiNDCollType.Set);

	MiNDToken MTTYPE_MODULE = Giskard.defineToken(MiNDTokenType.TYPE, "Module", MTUNIT_MACHINE);
	
	MiNDToken MTTYPE_IMPLEMENTATION = Giskard.defineToken(MiNDTokenType.TYPE, "Implementation", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_IMPLEMENTATION_AGENT = Giskard.defineToken(MiNDTokenType.MEMBER, "Agent", MTTYPE_IMPLEMENTATION, MiNDValType.Link, MiNDCollType.One);

}
