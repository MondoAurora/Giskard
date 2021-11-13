package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruUtilTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_UTIL_GENERIC = Giskard.getToken("Montru/Util/Generic/1/0", null);

	GiskardEntityRef GIS_TYP_UTIL_TAG = Giskard.getToken("Tag", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_TYP_UTIL_TAGGED = Giskard.getToken("Tagged", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_TAGS = Giskard.getToken("TaggedTags", GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TAG_UTIL_TEMPORAL = Giskard.getToken("Temporal", GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TYP_UTIL_VALUE = Giskard.getToken("Value", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEKEY = Giskard.getToken("ValueKey", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEINT = Giskard.getToken("ValueInt", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEREAL = Giskard.getToken("ValueReal", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUEREF = Giskard.getToken("ValueRef", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_VALUERAW = Giskard.getToken("ValueRaw", GIS_UNI_UTIL_GENERIC);

	GiskardEntityRef GIS_TYP_UTIL_CONNECTED = Giskard.getToken("Conn", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_OWNER = Giskard.getToken("ConnOwner", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_USES = Giskard.getToken("ConnUses", GIS_UNI_UTIL_GENERIC);
	GiskardEntityRef GIS_ATT_UTIL_TARGET = Giskard.getToken("ConnTarget", GIS_UNI_UTIL_GENERIC);
	
	
	GiskardEntityRef GIS_UNI_UTIL_TEXT = Giskard.getToken("Montru/Util/Text/1/0", null);

	GiskardEntityRef GIS_TYP_UTIL_IDENTIFIED = Giskard.getToken("Identified", GIS_UNI_UTIL_TEXT);
	GiskardEntityRef GIS_ATT_UTIL_ID = Giskard.getToken("Id", GIS_UNI_UTIL_TEXT);

	GiskardEntityRef GIS_TYP_UTIL_PLAINTXT = Giskard.getToken("PlainText", GIS_UNI_UTIL_TEXT);
	GiskardEntityRef GIS_ATT_UTIL_STRING = Giskard.getToken("PlainTextString", GIS_UNI_UTIL_TEXT);

	
	GiskardEntityRef GIS_UNI_UTIL_STREAM = Giskard.getToken("Montru/Util/Stream/1/0", null);

}
