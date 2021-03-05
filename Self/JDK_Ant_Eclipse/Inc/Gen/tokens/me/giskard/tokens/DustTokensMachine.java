package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMachine extends GiskardConsts {
	MiNDToken MTUNIT_MACHINE = Giskard.defineToken(MiNDTokenType.UNIT, "Machine");
	
	MiNDToken MTSHARED_MACHINE = Giskard.defineToken(MiNDTokenType.LOCAL, "Machine");
	
	MiNDToken MTTYPE_MACHINE = Giskard.defineToken(MiNDTokenType.TYPE, "Machine", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_MACHINE_MIND = Giskard.defineToken(MiNDTokenType.MEMBER, "Mind", MTTYPE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_MACHINE_CURRENTAPP = Giskard.defineToken(MiNDTokenType.MEMBER, "CurrentApp", MTTYPE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MTTYPE_APPLICATION = Giskard.defineToken(MiNDTokenType.TYPE, "Application", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_APPLICATION_MODULES = Giskard.defineToken(MiNDTokenType.MEMBER, "Modules", MTTYPE_APPLICATION, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_APPLICATION_MAINAGENT = Giskard.defineToken(MiNDTokenType.MEMBER, "MainAgent", MTTYPE_APPLICATION, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MTTYPE_MODULE = Giskard.defineToken(MiNDTokenType.TYPE, "Module", MTUNIT_MACHINE);
	
	MiNDToken MTTYPE_IMPLEMENTATION = Giskard.defineToken(MiNDTokenType.TYPE, "Implementation", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_IMPLEMENTATION_AGENT = Giskard.defineToken(MiNDTokenType.MEMBER, "Agent", MTTYPE_IMPLEMENTATION, MiNDValType.Ref, MiNDCollType.One);

}
