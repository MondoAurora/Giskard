package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruMindTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_MIND_MODEL = Giskard.access(GiskardAccessCmd.Get, "Montru/Mind/Model/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_TYP_MIND_ENTITY = Giskard.access(GiskardAccessCmd.Get, "Entity", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_SELFREF = Giskard.access(GiskardAccessCmd.Get, "EntitySelfRef", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_PRIMTYPE = Giskard.access(GiskardAccessCmd.Get, "EntityPrimaryType", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ALLTYPES = Giskard.access(GiskardAccessCmd.Get, "EntityAllTypes", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_TAGS = Giskard.access(GiskardAccessCmd.Get, "EntityTags", GiskardContext.ById, GIS_UNI_MIND_MODEL);

	GiskardEntityRef GIS_TYP_MIND_UNIT = Giskard.access(GiskardAccessCmd.Get, "Unit", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_NEXTID = Giskard.access(GiskardAccessCmd.Get, "UnitNextId", GiskardContext.ById, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ENTITIES = Giskard.access(GiskardAccessCmd.Get, "UnitEntities", GiskardContext.ById, GIS_UNI_MIND_MODEL);

	
	GiskardEntityRef GIS_UNI_MIND_IDEA = Giskard.access(GiskardAccessCmd.Get, "Montru/Mind/Idea/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_TYP_MIND_TYPE = Giskard.access(GiskardAccessCmd.Get, "Type", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TYP_MIND_ATTRIBUTE = Giskard.access(GiskardAccessCmd.Get, "Attribute", GiskardContext.ById, GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_VALTYPE = Giskard.access(GiskardAccessCmd.Get, "ValType", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_INT = Giskard.access(GiskardAccessCmd.Get, "ValTypeInt", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REAL = Giskard.access(GiskardAccessCmd.Get, "ValTypeReal", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REF = Giskard.access(GiskardAccessCmd.Get, "ValTypeRef", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_RAW = Giskard.access(GiskardAccessCmd.Get, "ValTypeRaw", GiskardContext.ById, GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_COLLTYPE = Giskard.access(GiskardAccessCmd.Get, "CollType", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ONE = Giskard.access(GiskardAccessCmd.Get, "CollTypeOne", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ARR = Giskard.access(GiskardAccessCmd.Get, "CollTypeArr", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_MAP = Giskard.access(GiskardAccessCmd.Get, "CollTypeMap", GiskardContext.ById, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_SET = Giskard.access(GiskardAccessCmd.Get, "CollTypeSet", GiskardContext.ById, GIS_UNI_MIND_IDEA);

	
	GiskardEntityRef GIS_UNI_MIND_NARRATIVE = Giskard.access(GiskardAccessCmd.Get, "Montru/Mind/Narrative/1/0", GiskardContext.ById);

	
	GiskardEntityRef GIS_UNI_MIND_DIALOG = Giskard.access(GiskardAccessCmd.Get, "Montru/Mind/Dialog/1/0", GiskardContext.ById);

}
