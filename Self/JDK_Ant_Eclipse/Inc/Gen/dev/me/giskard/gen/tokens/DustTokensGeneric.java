package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeneric extends GiskardConsts { 
	MiNDToken MTUNIT_GENERIC = Giskard.defineToken(MiNDTokenType.Unit, "Generic"); 
	MiNDToken MTTYPE_CONN = Giskard.defineToken(MiNDTokenType.Type, "Conn", MTUNIT_GENERIC);
	MiNDToken MTTYPE_ACTION = Giskard.defineToken(MiNDTokenType.Type, "Action", MTUNIT_GENERIC);
	MiNDToken MTTYPE_ACTIVITY = Giskard.defineToken(MiNDTokenType.Type, "Activity", MTUNIT_GENERIC);
	MiNDToken MTAGENT_DUMP = Giskard.defineToken(MiNDTokenType.Agent, "Dump", MTUNIT_GENERIC);
	MiNDToken MTTYPE_ITERATOR = Giskard.defineToken(MiNDTokenType.Type, "Iterator", MTUNIT_GENERIC);
	MiNDToken MTTYPE_LINK = Giskard.defineToken(MiNDTokenType.Type, "Link", MTUNIT_GENERIC);
	MiNDToken MTTYPE_RANGE = Giskard.defineToken(MiNDTokenType.Type, "Range", MTUNIT_GENERIC);
	MiNDToken MTTYPE_SPLIT = Giskard.defineToken(MiNDTokenType.Type, "Split", MTUNIT_GENERIC);
	MiNDToken MTTYPE_TAG = Giskard.defineToken(MiNDTokenType.Type, "Tag", MTUNIT_GENERIC);
	MiNDToken MTTYPE_VALUE = Giskard.defineToken(MiNDTokenType.Type, "Value", MTUNIT_GENERIC);
	MiNDToken MTTYPE_VERSIONED = Giskard.defineToken(MiNDTokenType.Type, "Versioned", MTUNIT_GENERIC);
	MiNDToken MTMEMBER_VERSIONED_SIGNATURE = Giskard.defineToken(MiNDTokenType.Member, "Signature", MTTYPE_VERSIONED, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_COLLTYPE = Giskard.defineToken(MiNDTokenType.Member, "CollType", MTTYPE_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_INT = Giskard.defineToken(MiNDTokenType.Member, "Int", MTTYPE_VALUE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_LINK = Giskard.defineToken(MiNDTokenType.Member, "Link", MTTYPE_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_RAW = Giskard.defineToken(MiNDTokenType.Member, "Raw", MTTYPE_VALUE, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_REAL = Giskard.defineToken(MiNDTokenType.Member, "Real", MTTYPE_VALUE, MiNDValType.Real, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYPE_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_SPLIT_ABSOLUTE = Giskard.defineToken(MiNDTokenType.Member, "Absolute", MTTYPE_SPLIT, MiNDValType.Int, MiNDCollType.Arr);
	MiNDToken MTMEMBER_SPLIT_WEIGHT = Giskard.defineToken(MiNDTokenType.Member, "Weight", MTTYPE_SPLIT, MiNDValType.Real, MiNDCollType.Arr);
	MiNDToken MTMEMBER_RANGE_INTMAX = Giskard.defineToken(MiNDTokenType.Member, "IntMax", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_RANGE_INTMIN = Giskard.defineToken(MiNDTokenType.Member, "IntMin", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_ARR = Giskard.defineToken(MiNDTokenType.Member, "Arr", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_LINK_MAP = Giskard.defineToken(MiNDTokenType.Member, "Map", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTMEMBER_LINK_ONE = Giskard.defineToken(MiNDTokenType.Member, "One", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_SET = Giskard.defineToken(MiNDTokenType.Member, "Set", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_LINK_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ITERATOR_INDEX = Giskard.defineToken(MiNDTokenType.Member, "Index", MTTYPE_ITERATOR, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTIVITY_INSTANCE = Giskard.defineToken(MiNDTokenType.Member, "Instance", MTTYPE_ACTIVITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_DIALOG = Giskard.defineToken(MiNDTokenType.Member, "Dialog", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_THIS = Giskard.defineToken(MiNDTokenType.Member, "This", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_OWNER = Giskard.defineToken(MiNDTokenType.Member, "Owner", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_EXTENDS = Giskard.defineToken(MiNDTokenType.Member, "Extends", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_PROVIDES = Giskard.defineToken(MiNDTokenType.Member, "Provides", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_REQUIRES = Giskard.defineToken(MiNDTokenType.Member, "Requires", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
}
