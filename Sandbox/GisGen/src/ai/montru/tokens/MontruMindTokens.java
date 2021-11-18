package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruMindTokens extends GiskardConsts {
	
	GiskardEntityRef GIS_UNI_MIND_MODEL = Giskard.getToken("Montru/Mind/Model/1/0", null);
	GiskardEntityRef GIS_UNI_MIND_IDEA = Giskard.getToken("Montru/Mind/Idea/1/0", null);
	GiskardEntityRef GIS_UNI_MIND_NARRATIVE = Giskard.getToken("Montru/Mind/Narrative/1/0", null);	
	GiskardEntityRef GIS_UNI_MIND_DIALOG = Giskard.getToken("Montru/Mind/Dialog/1/0", null);

	
	GiskardEntityRef GIS_TYP_MIND_ENTITY = Giskard.getToken("Entity", GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_SELFREF = Giskard.getToken("EntitySelfRef", GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_PRIMTYPE = Giskard.getToken("EntityPrimaryType", GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ALLTYPES = Giskard.getToken("EntityAllTypes", GIS_UNI_MIND_MODEL);
 
	GiskardEntityRef GIS_TYP_MIND_UNIT = Giskard.getToken("Unit", GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_NEXTID = Giskard.getToken("UnitNextId", GIS_UNI_MIND_MODEL);
	GiskardEntityRef GIS_ATT_MIND_ENTITIES = Giskard.getToken("UnitEntities", GIS_UNI_MIND_MODEL);


	GiskardEntityRef GIS_TYP_MIND_TYPE = Giskard.getToken("Type", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TYP_MIND_ATTRIBUTE = Giskard.getToken("Attribute", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_ATT_MIND_REFTYPE = Giskard.getToken("AttributeRefType", GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_VALTYPE = Giskard.getToken("ValType", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_INT = Giskard.getToken("ValTypeInt", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REAL = Giskard.getToken("ValTypeReal", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_REF = Giskard.getToken("ValTypeRef", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_VALTYPE_RAW = Giskard.getToken("ValTypeRaw", GIS_UNI_MIND_IDEA);

	GiskardEntityRef GIS_TAG_MIND_COLLTYPE = Giskard.getToken("CollType", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ONE = Giskard.getToken("CollTypeOne", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_ARR = Giskard.getToken("CollTypeArr", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_MAP = Giskard.getToken("CollTypeMap", GIS_UNI_MIND_IDEA);
	GiskardEntityRef GIS_TAG_MIND_COLLTYPE_SET = Giskard.getToken("CollTypeSet", GIS_UNI_MIND_IDEA);

	
	GiskardEntityRef GIS_TAG_MIND_ACTION = Giskard.getToken("Action", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_INIT = Giskard.getToken("ActionInit", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_BEGIN = Giskard.getToken("ActionBegin", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_PROCESS = Giskard.getToken("ActionProcess", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_END = Giskard.getToken("ActionEnd", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_ACTION_RELEASE = Giskard.getToken("ActionRelease", GIS_UNI_MIND_NARRATIVE);

	GiskardEntityRef GIS_TAG_MIND_RESPONSE = Giskard.getToken("Response", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_REJECT = Giskard.getToken("ResponseReject", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_ACCEPT = Giskard.getToken("ResponseAccept", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_ACCEPTREAD = Giskard.getToken("ResponseAcceptRead", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TAG_MIND_RESPONSE_READ = Giskard.getToken("ResponseRead", GIS_UNI_MIND_NARRATIVE);

	GiskardEntityRef GIS_TYP_MIND_ITERATOR = Giskard.getToken("Iterator", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TYP_MIND_SEQUENCE = Giskard.getToken("Sequence", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_TYP_MIND_SELECTION = Giskard.getToken("Selection", GIS_UNI_MIND_NARRATIVE);
	
	GiskardEntityRef GIS_TYP_MIND_VISITOR = Giskard.getToken("Visitor", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_ITERKEY = Giskard.getToken("VisitorIterKey", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_ITERVAL = Giskard.getToken("VisitorIterVal", GIS_UNI_MIND_NARRATIVE);
	GiskardEntityRef GIS_ATT_MIND_VALUE = Giskard.getToken("VisitorValue", GIS_UNI_MIND_NARRATIVE);

	
	GiskardEntityRef GIS_TAG_MIND_ACCESS = Giskard.getToken("Access", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_CHECK = Giskard.getToken("AccessCheck", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_PEEK = Giskard.getToken("AccessPeek", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_GET = Giskard.getToken("AccessGet", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_UPDATE = Giskard.getToken("AccessUpdate", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_SET = Giskard.getToken("AccessSet", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_INSERT = Giskard.getToken("AccessInsert", GIS_UNI_MIND_DIALOG);
	GiskardEntityRef GIS_TAG_MIND_ACCESS_DELETE = Giskard.getToken("AccessDelete", GIS_UNI_MIND_DIALOG);

}
