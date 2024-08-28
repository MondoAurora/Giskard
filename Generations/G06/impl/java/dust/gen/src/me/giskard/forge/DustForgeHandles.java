package me.giskard.forge;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustForgeHandles extends DustConsts {


	MindHandle DH_MATH_HOST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:1");
	MindHandle DH_MATH_HOST_PORT = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:2");
	MindHandle DH_MATH_HOST_IPV4 = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:3");

	MindHandle DH_MATH_AGGRTYPE = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:4");
	MindHandle DH_MATH_AGGRTYPE_FIRST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:5");
	MindHandle DH_MATH_AGGRTYPE_LAST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:6");
	MindHandle DH_MATH_AGGRTYPE_MIN = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:7");
	MindHandle DH_MATH_AGGRTYPE_MAX = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:8");
	MindHandle DH_MATH_AGGRTYPE_AVG = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:9");
// No text assigned to handle giskard.me:forge_1.0:15
}

