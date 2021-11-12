package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruMindTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_MIND_MODEL = Giskard.access(GiskardAccess.Get, "Montru/Mind/Model/1/0", GiskardContext.Absolute);
	GiskardEntityRef GIS_UNI_MIND_IDEA = Giskard.access(GiskardAccess.Get, "Montru/Mind/Idea/1/0", GiskardContext.Absolute);
	GiskardEntityRef GIS_UNI_MIND_NARRATIVE = Giskard.access(GiskardAccess.Get, "Montru/Mind/Narrative/1/0", GiskardContext.Absolute);	
	GiskardEntityRef GIS_UNI_MIND_DIALOG = Giskard.access(GiskardAccess.Get, "Montru/Mind/Dialog/1/0", GiskardContext.Absolute);

	
	GiskardEntityRef GIS_TYP_MIND_ENTITY = Giskard.access(GiskardAccess.Get, "Entity", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_SELFREF = Giskard.access(GiskardAccess.Get, "EntitySelfRef", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_PRIMTYPE = Giskard.access(GiskardAccess.Get, "EntityPrimaryType", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ALLTYPES = Giskard.access(GiskardAccess.Get, "EntityAllTypes", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
 
	GiskardEntityRef GIS_TYP_MIND_UNIT = Giskard.access(GiskardAccess.Get, "Unit", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_NEXTID = Giskard.access(GiskardAccess.Get, "UnitNextId", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ENTITIES = Giskard.access(GiskardAccess.Get, "UnitEntities", GiskardContext.Absolute, GIS_UNI_MIND_MODEL);


	GiskardEntityRef GIS_TYP_MIND_TYPE = Giskard.access(GiskardAccess.Get, "Type", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TYP_MIND_ATTRIBUTE = Giskard.access(GiskardAccess.Get, "Attribute", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_ATT_MIND_REFTYPE = Giskard.access(GiskardAccess.Get, "AttributeRefType", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_VALTYPE = Giskard.access(GiskardAccess.Get, "ValType", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_INT = Giskard.access(GiskardAccess.Get, "ValTypeInt", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REAL = Giskard.access(GiskardAccess.Get, "ValTypeReal", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REF = Giskard.access(GiskardAccess.Get, "ValTypeRef", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_RAW = Giskard.access(GiskardAccess.Get, "ValTypeRaw", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_COLLTYPE = Giskard.access(GiskardAccess.Get, "CollType", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ONE = Giskard.access(GiskardAccess.Get, "CollTypeOne", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ARR = Giskard.access(GiskardAccess.Get, "CollTypeArr", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_MAP = Giskard.access(GiskardAccess.Get, "CollTypeMap", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_SET = Giskard.access(GiskardAccess.Get, "CollTypeSet", GiskardContext.Absolute, GIS_UNI_MIND_IDEA);

	
	GiskardEntityRef GIS_TAG_MIND_ACTION = Giskard.access(GiskardAccess.Get, "Action", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_INIT = Giskard.access(GiskardAccess.Get, "ActionInit", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_BEGIN = Giskard.access(GiskardAccess.Get, "ActionBegin", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_PROCESS = Giskard.access(GiskardAccess.Get, "ActionProcess", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_END = Giskard.access(GiskardAccess.Get, "ActionEnd", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_RELEASE = Giskard.access(GiskardAccess.Get, "ActionRelease", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);

	GiskardEntityRef GIS_TAG_MIND_RESPONSE = Giskard.access(GiskardAccess.Get, "Response", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_REJECT = Giskard.access(GiskardAccess.Get, "ResponseReject", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_ACCEPT = Giskard.access(GiskardAccess.Get, "ResponseAccept", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_ACCEPTREAD = Giskard.access(GiskardAccess.Get, "ResponseAcceptRead", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_READ = Giskard.access(GiskardAccess.Get, "ResponseRead", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);

	GiskardEntityRef GIS_TYP_MIND_VISITOR = Giskard.access(GiskardAccess.Get, "Visitor", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_ITERKEY = Giskard.access(GiskardAccess.Get, "VisitorIterKey", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_ITERVAL = Giskard.access(GiskardAccess.Get, "VisitorIterVal", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_VALUE = Giskard.access(GiskardAccess.Get, "VisitorValue", GiskardContext.Absolute, GIS_UNI_MIND_NARRATIVE);

	
	GiskardEntityRef GIS_TAG_MIND_CONTEXT = Giskard.access(GiskardAccess.Get, "Context", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_CONTEXT_PROCESS = Giskard.access(GiskardAccess.Get, "Process", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_CONTEXT_RELATIVE = Giskard.access(GiskardAccess.Get, "Relative", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_CONTEXT_ABSOLUTE = Giskard.access(GiskardAccess.Get, "Absolute", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	
	GiskardEntityRef GIS_TAG_MIND_ACCESS = Giskard.access(GiskardAccess.Get, "Access", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_CHECK = Giskard.access(GiskardAccess.Get, "AccessCheck", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_PEEK = Giskard.access(GiskardAccess.Get, "AccessPeek", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_GET = Giskard.access(GiskardAccess.Get, "AccessGet", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_UPDATE = Giskard.access(GiskardAccess.Get, "AccessUpdate", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_SET = Giskard.access(GiskardAccess.Get, "AccessSet", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_INSERT = Giskard.access(GiskardAccess.Get, "AccessInsert", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_DELETE = Giskard.access(GiskardAccess.Get, "AccessDelete", GiskardContext.Absolute, GIS_UNI_MIND_DIALOG);

}
