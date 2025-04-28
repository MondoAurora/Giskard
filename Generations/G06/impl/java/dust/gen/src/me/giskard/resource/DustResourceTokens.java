package me.giskard.resource;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustResourceTokens extends DustConsts {


	MindToken TOKEN_RESOURCE_URL = Dust.access(null, "giskard.me:resource_1.0:0");
	MindToken TOKEN_RESOURCE_URL_SCHEME = Dust.access(null, "giskard.me:resource_1.0:1");
	MindToken TOKEN_RESOURCE_URL_USERINFO = Dust.access(null, "giskard.me:resource_1.0:2");
	MindToken TOKEN_RESOURCE_URL_HOST = Dust.access(null, "giskard.me:resource_1.0:3");
	MindToken TOKEN_RESOURCE_URL_PATH = Dust.access(null, "giskard.me:resource_1.0:4");
	MindToken TOKEN_RESOURCE_URL_QUERY = Dust.access(null, "giskard.me:resource_1.0:5");
	MindToken TOKEN_RESOURCE_URL_FRAGMENT = Dust.access(null, "giskard.me:resource_1.0:6");

	MindToken TOKEN_RESOURCE_STREAM = Dust.access(null, "giskard.me:resource_1.0:7");
	MindToken TOKEN_RESOURCE_STREAM_CTYPEMAP = Dust.access(null, "giskard.me:resource_1.0:8");

	MindToken TOKEN_RESOURCE_STREAMTYPE = Dust.access(null, "giskard.me:resource_1.0:9");
	MindToken TOKEN_RESOURCE_STREAMTYPE_RAW = Dust.access(null, "giskard.me:resource_1.0:10");
	MindToken TOKEN_RESOURCE_STREAMTYPE_TEXT = Dust.access(null, "giskard.me:resource_1.0:11");
	MindToken TOKEN_RESOURCE_STREAMTYPE_ZIPENTRY = Dust.access(null, "giskard.me:resource_1.0:12");

	MindToken TOKEN_RESOURCE_PROCESSOR = Dust.access(null, "giskard.me:resource_1.0:13");
	MindToken TOKEN_RESOURCE_PROCESSOR_STREAM = Dust.access(null, "giskard.me:resource_1.0:14");
	MindToken TOKEN_RESOURCE_PROCESSOR_DATA = Dust.access(null, "giskard.me:resource_1.0:15");

	MindToken TOKEN_RESOURCE_CACHE = Dust.access(null, "giskard.me:resource_1.0:16");
	MindToken TOKEN_RESOURCE_CACHE_REQUEST = Dust.access(null, "giskard.me:resource_1.0:17");

	MindToken TOKEN_RESOURCE_FILESYSTEM = Dust.access(null, "giskard.me:resource_1.0:18");

	MindToken TOKEN_RESOURCE_ZIPREADER = Dust.access(null, "giskard.me:resource_1.0:19");

	MindToken TOKEN_RESOURCE_XMLDOM = Dust.access(null, "giskard.me:resource_1.0:20");

	MindToken TOKEN_RESOURCE_JSONDOM = Dust.access(null, "giskard.me:resource_1.0:21");

	MindToken TOKEN_RESOURCE_CSVSAX = Dust.access(null, "giskard.me:resource_1.0:22");
}

