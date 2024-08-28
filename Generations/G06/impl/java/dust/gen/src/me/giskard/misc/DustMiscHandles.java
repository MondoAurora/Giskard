package me.giskard.misc;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMiscHandles extends DustConsts {


	MindHandle DH_MISC_CONN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:0");
	MindHandle DH_MISC_CONN_OWNER = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:1");
	MindHandle DH_MISC_CONN_PARENT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:2");
	MindHandle DH_MISC_CONN_SOURCE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:3");
	MindHandle DH_MISC_CONN_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:4");
	MindHandle DH_MISC_CONN_REQUIRES = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:5");
	MindHandle DH_MISC_CONN_MEMBERMAP = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:6");
	MindHandle DH_MISC_CONN_MEMBERARR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:7");
	MindHandle DH_MISC_CONN_MEMBERSET = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:8");
	MindHandle DH_MISC_CONN_SPACE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:9");
	MindHandle DH_MISC_CONN_NEXT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:10");
	MindHandle DH_MISC_CONN_PREV = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:11");

	MindHandle DH_MISC_ALIAS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:12");

	MindHandle DH_MISC_GEN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:13");
	MindHandle DH_MISC_GEN_COUNT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:14");
	MindHandle DH_MISC_GEN_SEP_ITEM = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:15");
	MindHandle DH_MISC_GEN_SEP_LINE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:16");
	MindHandle DH_MISC_GEN_TARGET_ATT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:17");
	MindHandle DH_MISC_GEN_EXTMAP = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:18");

	MindHandle DH_MISC_EMPTY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:19");

	MindHandle DH_MISC_ACTIVE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:20");

	MindHandle DH_MISC_NEGATE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:21");

	MindHandle DH_MISC_DBLHASH = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:22");

	MindHandle DH_MISC_READONLY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:23");

	MindHandle DH_MISC_HIDDEN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:24");

	MindHandle DH_MISC_SORTED = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:25");

	MindHandle DH_MISC_TRANSACTION = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:26");

	MindHandle DH_MISC_LOADING = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:27");

	MindHandle DH_MISC_ROOT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:28");

	MindHandle DH_MISC_DIRECTION = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:29");
	MindHandle DH_MISC_DIRECTION_IN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:30");
	MindHandle DH_MISC_DIRECTION_OUT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:31");

	MindHandle DH_MISC_REF = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:32");
	MindHandle DH_MISC_REF_PATH = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:33");

	MindHandle DH_MISC_SPACE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:34");
	MindHandle DH_MISC_SPACE_DIMENSIONS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:35");

	MindHandle DH_MISC_SHAPE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:36");
	MindHandle DH_MISC_SHAPE_VECTORS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:37");

	MindHandle DH_MISC_VECTOR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:38");
	MindHandle DH_MISC_VECTOR_COORDINATES = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:39");

	MindHandle DH_MISC_VARIANT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:40");
	MindHandle DH_MISC_VARIANT_VALUE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:41");

	MindHandle DH_MISC_ATTCOLLECTOR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:42");

	MindHandle DH_MISC_TABLE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:43");

	MindHandle DH_MISC_FACTORY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:44");
	MindHandle DH_MISC_FACTORY_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:45");

	MindHandle DH_MISC_COUNTER = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:46");
}

