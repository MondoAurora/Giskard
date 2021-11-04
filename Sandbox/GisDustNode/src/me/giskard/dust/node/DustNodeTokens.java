package me.giskard.dust.node;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustNodeTokens extends GiskardConsts {
	
	DustNodeEntityRef GIS_UNI_UTIL_GENERIC = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);
	DustNodeEntityRef GIS_UNI_UTIL_TEXT = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);
	DustNodeEntityRef GIS_UNI_UTIL_STREAM = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);

	DustNodeEntityRef GIS_UNI_MIND_MODEL = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);
	DustNodeEntityRef GIS_UNI_MIND_IDEA = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);
	DustNodeEntityRef GIS_UNI_MIND_NARRATIVE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);
	DustNodeEntityRef GIS_UNI_MIND_DIALOG = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);

	DustNodeEntityRef GIS_UNI_DUST_DUST = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById);

	DustNodeEntityRef GIS_TYP_DUST_NODE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_DUST_DUST);
	DustNodeEntityRef GIS_TYP_DUST_RUNTIME = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_DUST_DUST);
	DustNodeEntityRef GIS_ATT_DUST_NATIVES = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_DUST_DUST);

	DustNodeEntityRef GIS_TYP_MIND_UNIT = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TYP_MIND_TYPE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TYP_MIND_ATTRIBUTE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);

	DustNodeEntityRef GIS_TAG_MIND_VALTYPE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_VALTYPE_INT = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_VALTYPE_REAL = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_VALTYPE_REF = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_VALTYPE_RAW = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);

	DustNodeEntityRef GIS_TAG_MIND_COLLTYPE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_COLLTYPE_ONE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_COLLTYPE_ARR = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_COLLTYPE_MAP = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);
	DustNodeEntityRef GIS_TAG_MIND_COLLTYPE_SET = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_IDEA);

	DustNodeEntityRef GIS_TYP_MIND_ENTITY = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_SELFREF = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_PRIMTYPE = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_ALLTYPES = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_TAGS = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_NEXTID = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);
	DustNodeEntityRef GIS_ATT_MIND_ENTITIES = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_MIND_MODEL);

	DustNodeEntityRef GIS_TYP_UTIL_IDENTIFIED = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_TEXT);
	DustNodeEntityRef GIS_ATT_UTIL_ID = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_TEXT);

	DustNodeEntityRef GIS_TYP_UTIL_TAG = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_GENERIC);

	DustNodeEntityRef GIS_TYP_UTIL_CONNECTED = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_GENERIC);
	DustNodeEntityRef GIS_ATT_UTIL_OWNER = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_GENERIC);
	DustNodeEntityRef GIS_ATT_UTIL_USES = Giskard.access(GiskardAccessCmd.Get, -1, GiskardContext.ById, GIS_UNI_UTIL_GENERIC);


}
