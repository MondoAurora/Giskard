package me.giskard.app;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustAppHandles extends DustConsts {

	MindHandle APP_MACHINE_MAIN = Dust.access(MindAccess.Lookup, "giskard.me:app_1.0:0");
	MindHandle APP_MODULE_MAIN = Dust.access(MindAccess.Lookup, "giskard.me:app_1.0:1");
// No text assigned to handle giskard.me:app_1.0:2
}

