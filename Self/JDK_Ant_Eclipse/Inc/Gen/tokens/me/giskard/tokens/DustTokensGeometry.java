package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGeometry extends DustTokensMind {
	MiNDToken MTUNIT_GEOMETRY = Giskard.defineToken(MiNDTokenType.UNIT, "Geometry");
	
	
	MiNDToken MTTAG_LOCDATA_X = Giskard.defineToken(MiNDTokenType.TAG, "X");
	MiNDToken MTTAG_LOCDATA_Y = Giskard.defineToken(MiNDTokenType.TAG, "Y");
	MiNDToken MTTAG_LOCDATA_Z = Giskard.defineToken(MiNDTokenType.TAG, "Z");

	MiNDToken MTTYPE_ENVIRONMENT = Giskard.defineToken(MiNDTokenType.TYPE, "Environment", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_ENVIRONMENT_COORDSYS = Giskard.defineToken(MiNDTokenType.MEMBER, "Coordsys", MTTYPE_ENVIRONMENT, MiNDValType.Link, MiNDCollType.Arr);

	MiNDToken MTTYPE_GEODATA = Giskard.defineToken(MiNDTokenType.TYPE, "GeoData", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_GEODATA_COORDS = Giskard.defineToken(MiNDTokenType.MEMBER, "Coords", MTTYPE_GEODATA, MiNDValType.Real, MiNDCollType.Arr);

	MiNDToken MTTYPE_AREA = Giskard.defineToken(MiNDTokenType.TYPE, "Area", MTUNIT_GEOMETRY);
	MiNDToken MTMEMBER_AREA_CENTER = Giskard.defineToken(MiNDTokenType.MEMBER, "Center", MTTYPE_AREA, MiNDValType.Real, MiNDCollType.One);
	MiNDToken MTMEMBER_AREA_SPAN = Giskard.defineToken(MiNDTokenType.MEMBER, "Span", MTTYPE_AREA, MiNDValType.Real, MiNDCollType.One);

}
