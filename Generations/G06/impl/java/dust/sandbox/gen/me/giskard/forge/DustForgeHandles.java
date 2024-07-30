package me.giskard.forge;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustForgeHandles extends DustConsts {


	MindHandle MATH_ASP_HOST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:1");
	MindHandle MATH_ATT_HOST_PORT = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:2");
	MindHandle MATH_ATT_HOST_IPV4 = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:3");

	MindHandle MATH_TAG_AGGRTYPE = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:4");
	MindHandle MATH_TAG_AGGRTYPE_FIRST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:5");
	MindHandle MATH_TAG_AGGRTYPE_LAST = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:6");
	MindHandle MATH_TAG_AGGRTYPE_MIN = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:7");
	MindHandle MATH_TAG_AGGRTYPE_MAX = Dust.access(MindAccess.Lookup, "giskard.me:forge_1.0:8");
// No text assigned to handle giskard.me:forge_1.0:9
// No text assigned to handle giskard.me:forge_1.0:15
}

