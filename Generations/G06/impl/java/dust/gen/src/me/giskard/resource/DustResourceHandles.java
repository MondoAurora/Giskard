package me.giskard.resource;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustResourceHandles extends DustConsts {


	MindHandle DH_RESOURCE_URL = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:0");
	MindHandle DH_RESOURCE_URL_SCHEME = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:1");
	MindHandle DH_RESOURCE_URL_USERINFO = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:2");
	MindHandle DH_RESOURCE_URL_HOST = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:3");
	MindHandle DH_RESOURCE_URL_PATH = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:4");
	MindHandle DH_RESOURCE_URL_QUERY = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:5");
	MindHandle DH_RESOURCE_URL_FRAGMENT = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:6");

	MindHandle DH_RESOURCE_STREAM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:7");
	MindHandle DH_RESOURCE_STREAM_CTYPEMAP = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:8");

	MindHandle DH_RESOURCE_STREAMTYPE = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:9");
	MindHandle DH_RESOURCE_STREAMTYPE_RAW = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:10");
	MindHandle DH_RESOURCE_STREAMTYPE_TEXT = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:11");
	MindHandle DH_RESOURCE_STREAMTYPE_ZIPENTRY = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:12");

	MindHandle DH_RESOURCE_PROCESSOR = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:13");
	MindHandle DH_RESOURCE_PROCESSOR_STREAM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:14");
	MindHandle DH_RESOURCE_PROCESSOR_DATA = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:15");

	MindHandle DH_RESOURCE_CACHE = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:16");
	MindHandle DH_RESOURCE_CACHE_REQUEST = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:17");

	MindHandle DH_RESOURCE_FILESYSTEM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:18");

	MindHandle DH_RESOURCE_ZIPREADER = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:19");

	MindHandle DH_RESOURCE_XMLDOM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:20");

	MindHandle DH_RESOURCE_JSONDOM = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:21");

	MindHandle DH_RESOURCE_CSVSAX = Dust.access(MindAccess.Lookup, "giskard.me:resource_1.0:22");
}

