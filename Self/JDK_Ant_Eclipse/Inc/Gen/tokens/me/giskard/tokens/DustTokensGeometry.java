package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeometry extends GiskardConsts { 
	MiNDToken MTUNI_GEOMETRY = Giskard.defineToken(MiNDTokenType.Unit, "Geometry"); 
	MiNDToken MTTAG_GEOMETRY_LOCDATA = Giskard.defineToken(MiNDTokenType.Tag, "LocData", MTUNI_GEOMETRY);
	MiNDToken MTTAG_GEOMETRY_LOCDATA_X = Giskard.defineToken(MiNDTokenType.Tag, "X", MTTAG_GEOMETRY_LOCDATA);
	MiNDToken MTTAG_GEOMETRY_LOCDATA_Y = Giskard.defineToken(MiNDTokenType.Tag, "Y", MTTAG_GEOMETRY_LOCDATA);
	MiNDToken MTTAG_GEOMETRY_LOCDATA_Z = Giskard.defineToken(MiNDTokenType.Tag, "Z", MTTAG_GEOMETRY_LOCDATA);
	MiNDToken MTTYP_GEOMETRY_ENVIRONMENT = Giskard.defineToken(MiNDTokenType.Type, "Environment", MTUNI_GEOMETRY);
	MiNDToken MTMEM_GEOMETRY_ENVIRONMENT_COORDSYS = Giskard.defineToken(MiNDTokenType.Member, "Coordsys", MTTYP_GEOMETRY_ENVIRONMENT, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTTYP_GEOMETRY_GEODATA = Giskard.defineToken(MiNDTokenType.Type, "GeoData", MTUNI_GEOMETRY);
	MiNDToken MTMEM_GEOMETRY_GEODATA_COORDS = Giskard.defineToken(MiNDTokenType.Member, "Coords", MTTYP_GEOMETRY_GEODATA, MiNDValType.Real, MiNDCollType.Arr);
	MiNDToken MTTYP_GEOMETRY_AREA = Giskard.defineToken(MiNDTokenType.Type, "Area", MTUNI_GEOMETRY);
	MiNDToken MTMEM_GEOMETRY_AREA_CENTER = Giskard.defineToken(MiNDTokenType.Member, "Center", MTTYP_GEOMETRY_AREA, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GEOMETRY_AREA_SPAN = Giskard.defineToken(MiNDTokenType.Member, "Span", MTTYP_GEOMETRY_AREA, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_GEOMETRY_PROJECTION = Giskard.defineToken(MiNDTokenType.Type, "Projection", MTUNI_GEOMETRY);
	MiNDToken MTMEM_GEOMETRY_PROJECTION_ENTITIES = Giskard.defineToken(MiNDTokenType.Member, "Entities", MTTYP_GEOMETRY_PROJECTION, MiNDValType.Link, MiNDCollType.Arr);
}
