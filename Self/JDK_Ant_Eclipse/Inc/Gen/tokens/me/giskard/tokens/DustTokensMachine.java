package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMachine extends MindConsts {
	MiNDToken MTUNIT_MACHINE = Mind.defineToken(MiNDTokenType.UNIT, "Machine");
	
	MiNDToken MTSHARED_MACHINE = Mind.defineToken(MiNDTokenType.LOCAL, "Machine");
	
	MiNDToken MTTYPE_MACHINE = Mind.defineToken(MiNDTokenType.TYPE, "Machine", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_MACHINE_MIND = Mind.defineToken(MiNDTokenType.MEMBER, "Mind", MTTYPE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_MACHINE_CURRENTAPP = Mind.defineToken(MiNDTokenType.MEMBER, "CurrentApp", MTTYPE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MTTYPE_APPLICATION = Mind.defineToken(MiNDTokenType.TYPE, "Application", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_APPLICATION_MODULES = Mind.defineToken(MiNDTokenType.MEMBER, "Modules", MTTYPE_APPLICATION, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_APPLICATION_MAINAGENT = Mind.defineToken(MiNDTokenType.MEMBER, "MainAgent", MTTYPE_APPLICATION, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MTTYPE_MODULE = Mind.defineToken(MiNDTokenType.TYPE, "Module", MTUNIT_MACHINE);
	
	MiNDToken MTTYPE_IMPLEMENTATION = Mind.defineToken(MiNDTokenType.TYPE, "Implementation", MTUNIT_MACHINE);
	MiNDToken MTMEMBER_IMPLEMENTATION_AGENT = Mind.defineToken(MiNDTokenType.MEMBER, "Agent", MTTYPE_IMPLEMENTATION, MiNDValType.Ref, MiNDCollType.One);

}
