package me.giskard.misc;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustMiscHandles extends DustConsts {


	MindHandle MISC_ASP_CONN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:0");
	MindHandle MISC_ATT_CONN_OWNER = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:1");
	MindHandle MISC_ATT_CONN_PARENT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:2");
	MindHandle MISC_ATT_CONN_SOURCE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:3");
	MindHandle MISC_ATT_CONN_TARGET = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:4");
	MindHandle MISC_ATT_CONN_REQUIRES = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:5");
	MindHandle MISC_ATT_CONN_MEMBERMAP = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:6");
	MindHandle MISC_ATT_CONN_MEMBERARR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:7");
	MindHandle MISC_ATT_CONN_MEMBERSET = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:8");
	MindHandle MISC_ATT_CONN_SPACE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:9");
	MindHandle MISC_ATT_CONN_NEXT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:10");
	MindHandle MISC_ATT_CONN_PREV = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:11");

	MindHandle MISC_ASP_ALIAS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:12");

	MindHandle MISC_ASP_GEN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:13");
	MindHandle MISC_ATT_GEN_COUNT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:14");
	MindHandle MISC_ATT_GEN_SEP_ITEM = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:15");
	MindHandle MISC_ATT_GEN_SEP_LINE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:16");
	MindHandle MISC_ATT_GEN_TARGET_ATT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:17");
	MindHandle MISC_ATT_GEN_EXTMAP = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:18");

	MindHandle MISC_TAG_EMPTY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:19");

	MindHandle MISC_TAG_ACTIVE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:20");

	MindHandle MISC_TAG_NEGATE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:21");

	MindHandle MISC_TAG_DBLHASH = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:22");

	MindHandle MISC_TAG_READONLY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:23");

	MindHandle MISC_TAG_HIDDEN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:24");

	MindHandle MISC_TAG_SORTED = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:25");

	MindHandle MISC_TAG_TRANSACTION = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:26");

	MindHandle MISC_TAG_LOADING = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:27");

	MindHandle MISC_TAG_ROOT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:28");

	MindHandle MISC_TAG_DIRECTION = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:29");
	MindHandle MISC_TAG_DIRECTION_IN = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:30");
	MindHandle MISC_TAG_DIRECTION_OUT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:31");

	MindHandle MISC_ASP_REF = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:32");
	MindHandle MISC_ATT_REF_PATH = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:33");

	MindHandle MISC_ASP_SPACE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:34");
	MindHandle MISC_ATT_SPACE_DIMENSIONS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:35");

	MindHandle MISC_ASP_SHAPE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:36");
	MindHandle MISC_ATT_SHAPE_VECTORS = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:37");

	MindHandle MISC_ASP_VECTOR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:38");
	MindHandle MISC_ATT_VECTOR_COORDINATES = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:39");

	MindHandle MISC_ASP_VARIANT = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:40");
	MindHandle MISC_ATT_VARIANT_VALUE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:41");

	MindHandle MISC_NAR_ATTCOLLECTOR = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:42");

	MindHandle MISC_NAR_TABLE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:43");

	MindHandle MISC_NAR_FACTORY = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:44");
	MindHandle MISC_ATT_FACTORY_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:45");

	MindHandle MISC_NAR_COUNTER = Dust.access(MindAccess.Lookup, "giskard.me:misc_1.0:46");
}

