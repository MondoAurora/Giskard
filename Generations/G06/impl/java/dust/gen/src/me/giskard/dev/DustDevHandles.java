package me.giskard.dev;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustDevHandles extends DustConsts {

	MindHandle DH_DEV_HINT = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:0");

	MindHandle DH_DEV_TEST = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:1");

	MindHandle DH_DEV_FORGEUI = Dust.access(MindAccess.Lookup, "giskard.me:dev_1.0:2");
// No text assigned to handle giskard.me:dev_1.0:7
}

