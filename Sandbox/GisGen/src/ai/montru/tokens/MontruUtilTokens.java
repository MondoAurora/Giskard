package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruUtilTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_UTIL_GENERIC = Giskard.access(GiskardAccessCmd.Get, "Montru/Util/Generic/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_TYP_UTIL_TAG = Giskard.access(GiskardAccessCmd.Get, "Tag", GiskardContext.ById, GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TYP_UTIL_CONNECTED = Giskard.access(GiskardAccessCmd.Get, "Conn", GiskardContext.ById, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_OWNER = Giskard.access(GiskardAccessCmd.Get, "ConnOwner", GiskardContext.ById, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_USES = Giskard.access(GiskardAccessCmd.Get, "ConnUses", GiskardContext.ById, GIS_UNI_UTIL_GENERIC);
	
	
	GiskardEntityRef GIS_UNI_UTIL_TEXT = Giskard.access(GiskardAccessCmd.Get, "Montru/Util/Text/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_TYP_UTIL_IDENTIFIED = Giskard.access(GiskardAccessCmd.Get, "Identified", GiskardContext.ById, GIS_UNI_UTIL_TEXT);
	GiskardEntityRef GIS_ATT_UTIL_ID = Giskard.access(GiskardAccessCmd.Get, "Id", GiskardContext.ById, GIS_UNI_UTIL_TEXT);

	
	GiskardEntityRef GIS_UNI_UTIL_STREAM = Giskard.access(GiskardAccessCmd.Get, "Montru/Util/Stream/1/0", GiskardContext.ById);

}
