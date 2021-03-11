package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeneric extends GiskardConsts {
	MiNDToken MTUNIT_UTILS = Giskard.defineToken(MiNDTokenType.UNIT, "Generic_Utils");

	MiNDToken MTTYPE_ACTION = Giskard.defineToken(MiNDTokenType.TYPE, "Action", MTUNIT_UTILS);
	MiNDToken MTMEMBER_ACTION_THIS = Giskard.defineToken(MiNDTokenType.MEMBER, "This", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_PARAM = Giskard.defineToken(MiNDTokenType.MEMBER, "Param", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_LOCAL = Giskard.defineToken(MiNDTokenType.MEMBER, "Local", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_TARGET = Giskard.defineToken(MiNDTokenType.MEMBER, "Target", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTTYPE_TAGGED = Giskard.defineToken(MiNDTokenType.TYPE, "Tagged", MTUNIT_UTILS);
	MiNDToken MTMEMBER_TAGGED_TAGS = Giskard.defineToken(MiNDTokenType.MEMBER, "Tags", MTTYPE_TAGGED, MiNDValType.Link, MiNDCollType.Set);
	
	MiNDToken MTTYPE_VARIANT = Giskard.defineToken(MiNDTokenType.TYPE, "Variant", MTUNIT_UTILS);
	MiNDToken MTMEMBER_VARIANT_TYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "Type", MTTYPE_VARIANT, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VARIANT_VALUE = Giskard.defineToken(MiNDTokenType.MEMBER, "Value", MTTYPE_VARIANT, MiNDValType.Raw, MiNDCollType.One);

	MiNDToken MTTYPE_CONN = Giskard.defineToken(MiNDTokenType.TYPE, "Conn", MTUNIT_UTILS);
	MiNDToken MTMEMBER_CONN_OWNER = Giskard.defineToken(MiNDTokenType.MEMBER, "Owner", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_REQUIRES = Giskard.defineToken(MiNDTokenType.MEMBER, "Requires", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_EXTENDS = Giskard.defineToken(MiNDTokenType.MEMBER, "Extends", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_PROVIDES = Giskard.defineToken(MiNDTokenType.MEMBER, "Provides", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	
	MiNDToken MTTYPE_ITERATOR = Giskard.defineToken(MiNDTokenType.TYPE, "Iterator", MTUNIT_UTILS);
	MiNDToken MTMEMBER_ITERATOR_INDEX = Giskard.defineToken(MiNDTokenType.MEMBER, "Index", MTTYPE_ITERATOR, MiNDValType.Int, MiNDCollType.One);
	
	MiNDToken MTTYPE_RANGE = Giskard.defineToken(MiNDTokenType.TYPE, "Range", MTUNIT_UTILS);
	MiNDToken MTMEMBER_RANGE_INT_MIN = Giskard.defineToken(MiNDTokenType.MEMBER, "IntMin", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_RANGE_INT_MAX = Giskard.defineToken(MiNDTokenType.MEMBER, "IntMax", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	
	MiNDToken MTTYPE_LINK = Giskard.defineToken(MiNDTokenType.TYPE, "Link", MTUNIT_UTILS);
	MiNDToken MTMEMBER_LINK_TYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "Type", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_ONE = Giskard.defineToken(MiNDTokenType.MEMBER, "One", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_ARR = Giskard.defineToken(MiNDTokenType.MEMBER, "Arr", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_LINK_SET = Giskard.defineToken(MiNDTokenType.MEMBER, "Set", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_LINK_MAP = Giskard.defineToken(MiNDTokenType.MEMBER, "Map", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Map);
}
