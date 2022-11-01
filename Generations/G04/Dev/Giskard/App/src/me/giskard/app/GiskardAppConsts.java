package me.giskard.app;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface GiskardAppConsts extends GiskardConsts {
	
	String GISKARD_PATH_BRAIN = "/Bin";
	String GISKARD_SEGMENT_MOD = "/Mod";
	String GISKARD_SEGMENT_APP = "/App";
	String GISKARD_SEGMENT_EXT = "/Ext";
	
	String EXT_JAR = ".jar";

	String GISKARD_PACKAGE = Giskard.class.getPackage().getName();
	String GISKARD_PREFIX_MOD = GISKARD_PACKAGE + ".mod.";
	String GISKARD_PREFIX_UNIT = GISKARD_PACKAGE + ".units.Unit";

	String GISKARD_APP_CONFIG_NAME = "GiskardBootModules.cfg";

}
