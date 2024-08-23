package me.giskard.dev;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustDevHandles extends DustConsts {

	MindHandle DEV_ATT_HINT = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:0");

	MindHandle DEV_TAG_TEST = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:1");

	MindHandle DEV_NAR_FORGEUI = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:2");
// No text assigned to handle giskard.me:dev_1.0:7
}

