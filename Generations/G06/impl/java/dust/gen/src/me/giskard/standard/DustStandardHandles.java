package me.giskard.standard;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustStandardHandles extends DustConsts {


	MindHandle DH_STANDARD_JSONAPIDOM = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:0");

	MindHandle DH_STANDARD_JSONAPIFETCH = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:1");
	MindHandle DH_STANDARD_JSONAPIFETCH_INCLUDE = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:2");
	MindHandle DH_STANDARD_JSONAPIFETCH_FIELDS = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:3");
	MindHandle DH_STANDARD_JSONAPIFETCH_FILTER = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:4");
	MindHandle DH_STANDARD_JSONAPIFETCH_SORT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:5");
	MindHandle DH_STANDARD_JSONAPIFETCH_PAGELIMIT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:6");
	MindHandle DH_STANDARD_JSONAPIFETCH_PAGEOFFSET = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:7");

	MindHandle DH_STANDARD_XMLELEMENT = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:8");
	MindHandle DH_STANDARD_XMLELEMENT_ATTRIBUTES = Dust.access(MindAccess.Lookup, "giskard.me:standard_1.0:9");
// No text assigned to handle giskard.me:standard_1.0:11
}

