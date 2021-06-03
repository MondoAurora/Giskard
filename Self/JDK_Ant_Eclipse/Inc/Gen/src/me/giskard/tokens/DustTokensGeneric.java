package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeneric extends GiskardConsts { 
	MiNDToken MTUNI_GENERIC = Giskard.defineToken(MiNDTokenType.Unit, "Generic"); 
	MiNDToken MTTYP_GENERIC_VERSIONED = Giskard.defineToken(MiNDTokenType.Type, "Versioned", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_VERSIONED_SIGNATURE = Giskard.defineToken(MiNDTokenType.Member, "Signature", MTTYP_GENERIC_VERSIONED, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTTYP_GENERIC_VALUE = Giskard.defineToken(MiNDTokenType.Type, "Value", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_VALUE_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYP_GENERIC_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_VALUE_REAL = Giskard.defineToken(MiNDTokenType.Member, "Real", MTTYP_GENERIC_VALUE, MiNDValType.Real, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_VALUE_RAW = Giskard.defineToken(MiNDTokenType.Member, "Raw", MTTYP_GENERIC_VALUE, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_VALUE_LINK = Giskard.defineToken(MiNDTokenType.Member, "Link", MTTYP_GENERIC_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_VALUE_INT = Giskard.defineToken(MiNDTokenType.Member, "Int", MTTYP_GENERIC_VALUE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_VALUE_COLLTYPE = Giskard.defineToken(MiNDTokenType.Member, "CollType", MTTYP_GENERIC_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_GENERIC_TAG = Giskard.defineToken(MiNDTokenType.Type, "Tag", MTUNI_GENERIC);
	MiNDToken MTTYP_GENERIC_SPLIT = Giskard.defineToken(MiNDTokenType.Type, "Split", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_SPLIT_WEIGHT = Giskard.defineToken(MiNDTokenType.Member, "Weight", MTTYP_GENERIC_SPLIT, MiNDValType.Real, MiNDCollType.Arr);
	MiNDToken MTMEM_GENERIC_SPLIT_ABSOLUTE = Giskard.defineToken(MiNDTokenType.Member, "Absolute", MTTYP_GENERIC_SPLIT, MiNDValType.Int, MiNDCollType.Arr);
	MiNDToken MTTYP_GENERIC_RANGE = Giskard.defineToken(MiNDTokenType.Type, "Range", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_RANGE_INTMIN = Giskard.defineToken(MiNDTokenType.Member, "IntMin", MTTYP_GENERIC_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_RANGE_INTMAX = Giskard.defineToken(MiNDTokenType.Member, "IntMax", MTTYP_GENERIC_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTTYP_GENERIC_LINK = Giskard.defineToken(MiNDTokenType.Type, "Link", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_LINK_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYP_GENERIC_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_LINK_SET = Giskard.defineToken(MiNDTokenType.Member, "Set", MTTYP_GENERIC_LINK, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEM_GENERIC_LINK_ONE = Giskard.defineToken(MiNDTokenType.Member, "One", MTTYP_GENERIC_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_LINK_MAP = Giskard.defineToken(MiNDTokenType.Member, "Map", MTTYP_GENERIC_LINK, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTMEM_GENERIC_LINK_ARR = Giskard.defineToken(MiNDTokenType.Member, "Arr", MTTYP_GENERIC_LINK, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTTYP_GENERIC_ITERATOR = Giskard.defineToken(MiNDTokenType.Type, "Iterator", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_ITERATOR_INDEX = Giskard.defineToken(MiNDTokenType.Member, "Index", MTTYP_GENERIC_ITERATOR, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTAGN_GENERIC_DUMP = Giskard.defineToken(MiNDTokenType.Agent, "Dump", MTUNI_GENERIC);
	MiNDToken MTTYP_GENERIC_ACTIVITY = Giskard.defineToken(MiNDTokenType.Type, "Activity", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_ACTIVITY_INSTANCE = Giskard.defineToken(MiNDTokenType.Member, "Instance", MTTYP_GENERIC_ACTIVITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_GENERIC_ACTION = Giskard.defineToken(MiNDTokenType.Type, "Action", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_ACTION_THIS = Giskard.defineToken(MiNDTokenType.Member, "This", MTTYP_GENERIC_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_GENERIC_ACTION_DIALOG = Giskard.defineToken(MiNDTokenType.Member, "Dialog", MTTYP_GENERIC_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_GENERIC_CONN = Giskard.defineToken(MiNDTokenType.Type, "Conn", MTUNI_GENERIC);
	MiNDToken MTMEM_GENERIC_CONN_REQUIRES = Giskard.defineToken(MiNDTokenType.Member, "Requires", MTTYP_GENERIC_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEM_GENERIC_CONN_PROVIDES = Giskard.defineToken(MiNDTokenType.Member, "Provides", MTTYP_GENERIC_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEM_GENERIC_CONN_EXTENDS = Giskard.defineToken(MiNDTokenType.Member, "Extends", MTTYP_GENERIC_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEM_GENERIC_CONN_OWNER = Giskard.defineToken(MiNDTokenType.Member, "Owner", MTTYP_GENERIC_CONN, MiNDValType.Link, MiNDCollType.One);
}
