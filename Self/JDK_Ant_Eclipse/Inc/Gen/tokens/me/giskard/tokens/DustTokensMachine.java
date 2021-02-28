package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMachine extends MindConsts {
	MiNDToken MT_MACHINE_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "Machine");
	
	MiNDToken MT_MACHINE_MACHINE = Mind.defineToken(MiNDTokenType.TYPE, "Machine", MT_MACHINE_UNIT);
	MiNDToken MT_MACHINE_MIND = Mind.defineToken(MiNDTokenType.MEMBER, "Mind", MT_MACHINE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MT_MACHINE_CURRENTAPP = Mind.defineToken(MiNDTokenType.MEMBER, "CurrentApp", MT_MACHINE_MACHINE, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MT_MACHINE_APPLICATION = Mind.defineToken(MiNDTokenType.TYPE, "Application", MT_MACHINE_UNIT);
	MiNDToken MT_APPLICATION_MODULES = Mind.defineToken(MiNDTokenType.MEMBER, "Modules", MT_MACHINE_APPLICATION, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MT_APPLICATION_MAINAGENT = Mind.defineToken(MiNDTokenType.MEMBER, "MainAgent", MT_MACHINE_APPLICATION, MiNDValType.Ref, MiNDCollType.One);
	
	MiNDToken MT_MACHINE_MODULE = Mind.defineToken(MiNDTokenType.TYPE, "Module", MT_MACHINE_UNIT);
	MiNDToken MT_MODULE_UNITS = Mind.defineToken(MiNDTokenType.MEMBER, "Units", MT_MACHINE_MODULE, MiNDValType.Ref, MiNDCollType.Set);
	
	MiNDToken MT_MACHINE_IMPLEMENTATION = Mind.defineToken(MiNDTokenType.TYPE, "Implementation", MT_MACHINE_UNIT);
	MiNDToken MT_IMPLEMENTATION_AGENT = Mind.defineToken(MiNDTokenType.MEMBER, "Agent", MT_MACHINE_IMPLEMENTATION, MiNDValType.Ref, MiNDCollType.One);

}
