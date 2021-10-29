package me.giskard.dust.node;

import me.giskard.GiskardConsts;

public interface DustBootConsts extends GiskardConsts {
	
	String CLASSNAME_MAP = "java.util.HashMap";
	String CLASSNAME_ARR = "java.util.ArrayList";
	String CLASSNAME_SET = "java.util.HashSet";
	
	String CLASSNAME_RUNTIME = "me.giskard.dust.node.agents.DustNodeAgentRuntime";
	
//	enum UnitDustNode {
//		UnitDustNode, UnitMiNDModel, UnitMiNDIdea, UnitMiNDNarrative, UnitMiNDDialog, UnitUtilGeneric, UnitUtilText, UnitUtilStream,
//		TypeModule, TypeRuntime,
//		AttModuleUnitMap, AttModuleNativeMap,
//	}
//
//	enum UnitMiNDModel {
//		TypeUnit,
//		AttUnitLastID,
//		AttUnitEntityMap,
//		AttEntityRef,
//	}
//
//	enum UnitMiNDIdea {
//
//	}
//
//	enum UnitMiNDNarrative {
//
//	}
//
//	enum UnitMiNDDialog {
//
//	}
//
//	enum UnitUtilGeneric {
//
//	}
//
//	enum UnitUtilText {
//		TypeIdentified,
//		AttIdentifiedId,
//	}
//
//	enum UnitUtilStream {
//
//	}
//
//	@SuppressWarnings("rawtypes")
//	Map<Enum, DustEntityRef> BOOT_REFS = new GisUtilsFactory<Enum, DustEntityRef>(
//			new Creator<Enum, DustEntityRef>() {
//				@Override
//				public DustEntityRef create(Enum key, Object... params) {
//					return new DustEntityRef(null, key.ordinal());
//				}
//			});
//	
	static DustEntityRef UNIT_DUST_NODE = DustEntityRef.createBootRef(null);
	
	static DustEntityRef UNIT_MIND_MODEL = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_MIND_IDEA = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_MIND_NARRATIVE = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_MIND_DIALOG = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_UTIL_GENERIC = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_UTIL_TEXT = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef UNIT_UTIL_STREAM = DustEntityRef.createBootRef(UNIT_DUST_NODE);

	static DustEntityRef TYPE_NODE_MODULE = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef TYPE_NODE_RUNTIME = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef ATT_NODE_MODULE_UNITMAP = DustEntityRef.createBootRef(UNIT_DUST_NODE);
	static DustEntityRef ATT_NODE_MODULE_NATIVEMAP = DustEntityRef.createBootRef(UNIT_DUST_NODE);

	static DustEntityRef TYPE_MODEL_ENTITY = DustEntityRef.createBootRef(UNIT_MIND_MODEL);
	static DustEntityRef TYPE_MODEL_UNIT = DustEntityRef.createBootRef(UNIT_MIND_MODEL);
	static DustEntityRef ATT_MODEL_ENTITY_REF = DustEntityRef.createBootRef(UNIT_MIND_MODEL);
	static DustEntityRef ATT_MODEL_UNIT_NEXTID = DustEntityRef.createBootRef(UNIT_MIND_MODEL);
	static DustEntityRef ATT_MODEL_UNIT_ENTITYMAP = DustEntityRef.createBootRef(UNIT_MIND_MODEL);

	static DustEntityRef TYPE_TEXT_IDENTIFIED = DustEntityRef.createBootRef(UNIT_UTIL_TEXT);
	static DustEntityRef ATT_TEXT_IDENTIFIED_ID = DustEntityRef.createBootRef(UNIT_UTIL_TEXT);
	
//	static DustEntityRef ATT_TEXT_IDENTIFIED_ID = BOOT_REFS.get(UnitUtilText.AttIdentifiedId);
//	static DustEntityRef ATT_MODEL_ENTITY_REF = BOOT_REFS.get(UnitMiNDModel.AttEntityRef);
//	static DustEntityRef ATT_MODEL_UNIT_NEXTID = BOOT_REFS.get(UnitMiNDModel.AttUnitLastID);
//	static DustEntityRef ATT_MODEL_UNIT_ENTITYMAP = BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap);
//	static DustEntityRef ATT_NODE_MODULE_UNITMAP = BOOT_REFS.get(UnitDustNode.AttModuleUnitMap);
//	static DustEntityRef ATT_NODE_MODULE_NATIVEMAP = BOOT_REFS.get(UnitDustNode.AttModuleNativeMap);

	interface BootRefProcessor {
		void processBootRef(DustEntityRef ref, int optUnitNextIdx);
	}


}
