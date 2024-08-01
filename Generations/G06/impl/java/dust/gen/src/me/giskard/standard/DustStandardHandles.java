package me.giskard.standard;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustStandardHandles extends DustConsts {


	MindHandle STANDARD_ASP_JSONAPIDOM = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:0");

	MindHandle STANDARD_ASP_JSONAPIFETCH = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:1");
	MindHandle STANDARD_ATT_JSONAPIFETCH_INCLUDE = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:2");
	MindHandle STANDARD_ATT_JSONAPIFETCH_FIELDS = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:3");
	MindHandle STANDARD_ATT_JSONAPIFETCH_FILTER = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:4");
	MindHandle STANDARD_ATT_JSONAPIFETCH_SORT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:5");
	MindHandle STANDARD_ATT_JSONAPIFETCH_PAGELIMIT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:6");
	MindHandle STANDARD_ATT_JSONAPIFETCH_PAGEOFFSET = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:7");

	MindHandle STANDARD_ASP_XMLELEMENT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:8");
// No text assigned to handle giskard.me:standard_1.0:9
// No text assigned to handle giskard.me:standard_1.0:11
}

