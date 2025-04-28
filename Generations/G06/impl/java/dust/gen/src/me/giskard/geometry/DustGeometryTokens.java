package me.giskard.geometry;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustGeometryTokens extends DustConsts {


	MindToken TOKEN_GEOMETRY_NODE = Dust.access(null, "giskard.me:geometry_1.0:0");

	MindToken TOKEN_GEOMETRY_EDGE = Dust.access(null, "giskard.me:geometry_1.0:1");

	MindToken TOKEN_GEOMETRY_GRAPH = Dust.access(null, "giskard.me:geometry_1.0:2");
	MindToken TOKEN_GEOMETRY_GRAPH_NODES = Dust.access(null, "giskard.me:geometry_1.0:3");
	MindToken TOKEN_GEOMETRY_GRAPH_EDGES = Dust.access(null, "giskard.me:geometry_1.0:4");

	MindToken TOKEN_GEOMETRY_VECTOR_LOCATION = Dust.access(null, "giskard.me:geometry_1.0:5");

	MindToken TOKEN_GEOMETRY_VECTOR_SIZE = Dust.access(null, "giskard.me:geometry_1.0:6");

	MindToken TOKEN_GEOMETRY_VECTOR_WEIGHT = Dust.access(null, "giskard.me:geometry_1.0:7");

	MindToken TOKEN_GEOMETRY_VECTOR_SCALE = Dust.access(null, "giskard.me:geometry_1.0:8");

	MindToken TOKEN_GEOMETRY_VECTOR_ROTATE = Dust.access(null, "giskard.me:geometry_1.0:9");

	MindToken TOKEN_GEOMETRY_VALTYPE = Dust.access(null, "giskard.me:geometry_1.0:10");
	MindToken TOKEN_GEOMETRY_VALTYPE_CARTESIAN_X = Dust.access(null, "giskard.me:geometry_1.0:11");
	MindToken TOKEN_GEOMETRY_VALTYPE_CARTESIAN_Y = Dust.access(null, "giskard.me:geometry_1.0:12");
	MindToken TOKEN_GEOMETRY_VALTYPE_CARTESIAN_Z = Dust.access(null, "giskard.me:geometry_1.0:13");
}

