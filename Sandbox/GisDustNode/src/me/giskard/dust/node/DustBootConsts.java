package me.giskard.dust.node;

import java.util.Map;

import me.giskard.GiskardConsts;
import me.giskard.utils.GisUtilsConsts.Creator;
import me.giskard.utils.GisUtilsFactory;

public interface DustBootConsts extends GiskardConsts {
	
	String CLASSNAME_MAP = "java.util.HashMap";
	String CLASSNAME_NODE = "me.giskard.dust.node.logic.DustAgentNode";

	enum UnitDustNode {
		UnitDustNode, UnitMiNDModel, UnitMiNDIdea, UnitMiNDNarrative, UnitMiNDDialog, UnitUtilGeneric, UnitUtilText, UnitUtilStream,
		TypeModule, TypeNode,
		AttModuleUnitMap, AttModuleNativeMap,
	}

	enum UnitMiNDModel {
		TypeUnit,
		AttUnitEntityMap,
		AttEntityRef,
	}

	enum UnitMiNDIdea {

	}

	enum UnitMiNDNarrative {

	}

	enum UnitMiNDDialog {

	}

	enum UnitUtilGeneric {

	}

	enum UnitUtilText {
		TypeIdentified,
		AttIdentifiedId,
	}

	enum UnitUtilStream {

	}

	@SuppressWarnings("rawtypes")
	Map<Enum, DustEntityRef> BOOT_REFS = new GisUtilsFactory<Enum, DustEntityRef>(
			new Creator<Enum, DustEntityRef>() {
				@Override
				public DustEntityRef create(Enum key, Object... params) {
					return new DustEntityRef(null, key.ordinal());
				}
			});

	static DustEntityRef ATT_TXT_ID = BOOT_REFS.get(UnitUtilText.AttIdentifiedId);
	static DustEntityRef ATT_ENTITYREF = BOOT_REFS.get(UnitMiNDModel.AttEntityRef);
	static DustEntityRef ATT_ENTITYMAP = BOOT_REFS.get(UnitMiNDModel.AttUnitEntityMap);
	static DustEntityRef ATT_UNITMAP = BOOT_REFS.get(UnitDustNode.AttModuleUnitMap);
	static DustEntityRef ATT_NATIVEMAP = BOOT_REFS.get(UnitDustNode.AttModuleNativeMap);

}
