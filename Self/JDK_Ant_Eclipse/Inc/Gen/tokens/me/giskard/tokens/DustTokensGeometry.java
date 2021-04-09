package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGeometry extends DustTokensMind {
	MiNDToken MTUNIT_GEOMETRY = Giskard.defineToken(MiNDTokenType.Unit, "Geometry");
	
	
	MiNDToken MTTAG_LOCDATA = Giskard.defineToken(MiNDTokenType.Tag, "LocData", MTUNIT_GEOMETRY);
	
	MiNDToken MTTAG_LOCDATA_X = Giskard.defineToken(MiNDTokenType.Tag, "X", MTTAG_LOCDATA);
	MiNDToken MTTAG_LOCDATA_Y = Giskard.defineToken(MiNDTokenType.Tag, "Y", MTTAG_LOCDATA);
	MiNDToken MTTAG_LOCDATA_Z = Giskard.defineToken(MiNDTokenType.Tag, "Z", MTTAG_LOCDATA);

	MiNDToken MTTYPE_ENVIRONMENT = Giskard.defineToken(MiNDTokenType.Type, "Environment", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_ENVIRONMENT_COORDSYS = Giskard.defineToken(MiNDTokenType.Member, "Coordsys", MTTYPE_ENVIRONMENT, MiNDValType.Link, MiNDCollType.Arr);

	MiNDToken MTTYPE_GEODATA = Giskard.defineToken(MiNDTokenType.Type, "GeoData", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_GEODATA_COORDS = Giskard.defineToken(MiNDTokenType.Member, "Coords", MTTYPE_GEODATA, MiNDValType.Real, MiNDCollType.Arr);

	MiNDToken MTTYPE_AREA = Giskard.defineToken(MiNDTokenType.Type, "Area", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_AREA_CENTER = Giskard.defineToken(MiNDTokenType.Member, "Center", MTTYPE_AREA, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_AREA_SPAN = Giskard.defineToken(MiNDTokenType.Member, "Span", MTTYPE_AREA, MiNDValType.Link, MiNDCollType.One);

}
