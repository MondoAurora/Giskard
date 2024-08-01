package me.giskard.resource;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustResourceHandles extends DustConsts {


	MindHandle RESOURCE_ASP_URL = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:0");
	MindHandle RESOURCE_ATT_URL_SCHEME = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:1");
	MindHandle RESOURCE_ATT_URL_USERINFO = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:2");
	MindHandle RESOURCE_ATT_URL_HOST = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:3");
	MindHandle RESOURCE_ATT_URL_PATH = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:4");
	MindHandle RESOURCE_ATT_URL_QUERY = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:5");
	MindHandle RESOURCE_ATT_URL_FRAGMENT = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:6");

	MindHandle RESOURCE_ASP_STREAM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:7");
	MindHandle RESOURCE_ATT_STREAM_CTYPEMAP = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:8");

	MindHandle RESOURCE_TAG_STREAMTYPE = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:9");
	MindHandle RESOURCE_TAG_STREAMTYPE_RAW = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:10");
	MindHandle RESOURCE_TAG_STREAMTYPE_TEXT = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:11");
	MindHandle RESOURCE_TAG_STREAMTYPE_ZIPENTRY = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:12");

	MindHandle RESOURCE_ASP_PROCESSOR = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:13");
	MindHandle RESOURCE_ATT_PROCESSOR_STREAM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:14");
	MindHandle RESOURCE_ATT_PROCESSOR_DATA = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:15");
// No text assigned to handle giskard.me:resource_1.0:17
}

