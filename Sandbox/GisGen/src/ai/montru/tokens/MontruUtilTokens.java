package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruUtilTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_UTIL_GENERIC = Giskard.access(GiskardAccess.Get, "Montru/Util/Generic/1/0", GiskardContext.Absolute);

	GiskardEntityRef GIS_TYP_UTIL_TAG = Giskard.access(GiskardAccess.Get, "Tag", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_TYP_UTIL_TAGGED = Giskard.access(GiskardAccess.Get, "Tagged", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_TAGS = Giskard.access(GiskardAccess.Get, "TaggedTags", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TAG_UTIL_TEMPORAL = Giskard.access(GiskardAccess.Get, "Temporal", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TYP_UTIL_VALUE = Giskard.access(GiskardAccess.Get, "Value", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEKEY = Giskard.access(GiskardAccess.Get, "ValueKey", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEINT = Giskard.access(GiskardAccess.Get, "ValueInt", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEREAL = Giskard.access(GiskardAccess.Get, "ValueReal", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEREF = Giskard.access(GiskardAccess.Get, "ValueRef", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUERAW = Giskard.access(GiskardAccess.Get, "ValueRaw", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TYP_UTIL_CONNECTED = Giskard.access(GiskardAccess.Get, "Conn", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_OWNER = Giskard.access(GiskardAccess.Get, "ConnOwner", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_USES = Giskard.access(GiskardAccess.Get, "ConnUses", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_TARGET = Giskard.access(GiskardAccess.Get, "ConnTarget", GiskardContext.Absolute, GIS_UNI_UTIL_GENERIC);
	
	
	GiskardEntityRef GIS_UNI_UTIL_TEXT = Giskard.access(GiskardAccess.Get, "Montru/Util/Text/1/0", GiskardContext.Absolute);

	GiskardEntityRef GIS_TYP_UTIL_IDENTIFIED = Giskard.access(GiskardAccess.Get, "Identified", GiskardContext.Absolute, GIS_UNI_UTIL_TEXT);
	GiskardEntityRef GIS_ATT_UTIL_ID = Giskard.access(GiskardAccess.Get, "Id", GiskardContext.Absolute, GIS_UNI_UTIL_TEXT);

	GiskardEntityRef GIS_TYP_UTIL_PLAINTXT = Giskard.access(GiskardAccess.Get, "PlainText", GiskardContext.Absolute, GIS_UNI_UTIL_TEXT);
	GiskardEntityRef GIS_ATT_UTIL_STRING = Giskard.access(GiskardAccess.Get, "PlainTextString", GiskardContext.Absolute, GIS_UNI_UTIL_TEXT);

	
	GiskardEntityRef GIS_UNI_UTIL_STREAM = Giskard.access(GiskardAccess.Get, "Montru/Util/Stream/1/0", GiskardContext.Absolute);

}
