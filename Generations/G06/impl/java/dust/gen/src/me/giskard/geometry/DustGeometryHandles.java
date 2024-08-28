package me.giskard.geometry;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustGeometryHandles extends DustConsts {


	MindHandle DH_GEOMETRY_NODE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:0");

	MindHandle DH_GEOMETRY_EDGE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:1");

	MindHandle DH_GEOMETRY_GRAPH = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:2");
	MindHandle DH_GEOMETRY_GRAPH_NODES = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:3");
	MindHandle DH_GEOMETRY_GRAPH_EDGES = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:4");

	MindHandle DH_GEOMETRY_VECTOR_LOCATION = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:5");

	MindHandle DH_GEOMETRY_VECTOR_SIZE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:6");

	MindHandle DH_GEOMETRY_VECTOR_WEIGHT = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:7");

	MindHandle DH_GEOMETRY_VECTOR_SCALE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:8");

	MindHandle DH_GEOMETRY_VECTOR_ROTATE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:9");

	MindHandle DH_GEOMETRY_VALTYPE = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:10");
	MindHandle DH_GEOMETRY_VALTYPE_CARTESIAN_X = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:11");
	MindHandle DH_GEOMETRY_VALTYPE_CARTESIAN_Y = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:12");
	MindHandle DH_GEOMETRY_VALTYPE_CARTESIAN_Z = Dust.access(MindAccess.Lookup, "giskard.me:geometry_1.0:13");
}

