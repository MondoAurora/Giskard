package me.giskard.dust.node;

public interface DustBootConsts {
	
	String CLASSNAME_MAP = "java.util.HashMap";

	enum UnitDustNode {
		UnitDustNode, UnitMiNDModel, UnitMiNDIdea, UnitMiNDNarrative, UnitMiNDDialog, UnitUtilGeneric, UnitUtilText, UnitUtilStream,
		TypeModule, TypeNode,
		AttModuleUnitMap,
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

}
