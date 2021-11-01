package me.giskard.dust.node;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustBootConsts extends GiskardConsts {
	
	String CLASSNAME_MAP = "java.util.HashMap";
	String CLASSNAME_ARR = "java.util.ArrayList";
	String CLASSNAME_SET = "java.util.HashSet";
	
	String CLASSNAME_RUNTIME = "me.giskard.dust.node.agents.DustNodeAgentRuntime";
	String CLASSNAME_CLOUD = "me.giskard.dust.node.agents.DustNodeAgentCloud";
	
	static DustEntityRef UNIT_DUST_NODE = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, null, null, -1);
	static DustEntityRef ATT_NODE_MODULE_UNITMAP = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, null,UNIT_DUST_NODE, -1);

	static DustEntityRef UNIT_MIND_MODEL = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_MIND_IDEA = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_MIND_NARRATIVE = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_MIND_DIALOG = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_UTIL_GENERIC = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_UTIL_TEXT = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef UNIT_UTIL_STREAM = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);

	static DustEntityRef TYPE_NODE_MODULE = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef TYPE_NODE_RUNTIME = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);
	static DustEntityRef ATT_NODE_MODULE_NATIVEMAP = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_DUST_NODE, -1);

	static DustEntityRef TYPE_MODEL_ENTITY = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_MIND_MODEL, -1);
	static DustEntityRef TYPE_MODEL_UNIT = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_MIND_MODEL, -1);
	static DustEntityRef ATT_MODEL_ENTITY_REF = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_MIND_MODEL, -1);
	static DustEntityRef ATT_MODEL_UNIT_NEXTID = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_MIND_MODEL, -1);
	static DustEntityRef ATT_MODEL_UNIT_ENTITYMAP = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_MIND_MODEL, -1);

	static DustEntityRef TYPE_TEXT_IDENTIFIED = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_UTIL_TEXT, -1);
	static DustEntityRef ATT_TEXT_IDENTIFIED_ID = Giskard.access(GiskardAccessCmd.Get, null, GiskardContext.Module, ATT_NODE_MODULE_UNITMAP,UNIT_UTIL_TEXT, -1);

}
