package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeneric extends GiskardConsts {
	MiNDToken MTUNIT_UTILS = Giskard.defineToken(MiNDTokenType.Unit, "Generic_Utils");

	MiNDToken MTTYPE_TAG = Giskard.defineToken(MiNDTokenType.Type, "Tag", MTUNIT_UTILS);

	MiNDToken MTTYPE_CALL = Giskard.defineToken(MiNDTokenType.Type, "Call", MTUNIT_UTILS);
	MiNDToken MTMEMBER_CALL_TARGET = Giskard.defineToken(MiNDTokenType.Member, "Target", MTTYPE_CALL, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_CALL_PARAM = Giskard.defineToken(MiNDTokenType.Member, "Param", MTTYPE_CALL, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTTYPE_ACTION = Giskard.defineToken(MiNDTokenType.Type, "Action", MTUNIT_UTILS);
	MiNDToken MTMEMBER_ACTION_THIS = Giskard.defineToken(MiNDTokenType.Member, "This", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_PARAM = Giskard.defineToken(MiNDTokenType.Member, "Param", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_DIALOG = Giskard.defineToken(MiNDTokenType.Member, "Dialog", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTMEMBER_ACTION_GPR01 = Giskard.defineToken(MiNDTokenType.Member, "GPR01", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR02 = Giskard.defineToken(MiNDTokenType.Member, "GPR02", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR03 = Giskard.defineToken(MiNDTokenType.Member, "GPR03", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR04 = Giskard.defineToken(MiNDTokenType.Member, "GPR04", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR05 = Giskard.defineToken(MiNDTokenType.Member, "GPR05", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR06 = Giskard.defineToken(MiNDTokenType.Member, "GPR06", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR07 = Giskard.defineToken(MiNDTokenType.Member, "GPR07", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR08 = Giskard.defineToken(MiNDTokenType.Member, "GPR08", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ACTION_GPR09 = Giskard.defineToken(MiNDTokenType.Member, "GPR09", MTTYPE_ACTION, MiNDValType.Link, MiNDCollType.One);
	
	MiNDToken MTTYPE_VALUE = Giskard.defineToken(MiNDTokenType.Type, "Value", MTUNIT_UTILS);
	MiNDToken MTMEMBER_VALUE_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYPE_VALUE, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_INT = Giskard.defineToken(MiNDTokenType.Member, "Int", MTTYPE_VALUE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_REAL = Giskard.defineToken(MiNDTokenType.Member, "Real", MTTYPE_VALUE, MiNDValType.Real, MiNDCollType.One);
	MiNDToken MTMEMBER_VALUE_RAW = Giskard.defineToken(MiNDTokenType.Member, "Raw", MTTYPE_VALUE, MiNDValType.Raw, MiNDCollType.One);

	MiNDToken MTTYPE_VERSIONED = Giskard.defineToken(MiNDTokenType.Type, "Versioned", MTUNIT_UTILS);
	MiNDToken MTMEMBER_VERSIONED_SIGNATURE = Giskard.defineToken(MiNDTokenType.Member, "Signature", MTTYPE_VERSIONED, MiNDValType.Raw, MiNDCollType.One);

	MiNDToken MTTYPE_CONN = Giskard.defineToken(MiNDTokenType.Type, "Conn", MTUNIT_UTILS);
	MiNDToken MTMEMBER_CONN_OWNER = Giskard.defineToken(MiNDTokenType.Member, "Owner", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_REQUIRES = Giskard.defineToken(MiNDTokenType.Member, "Requires", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_EXTENDS = Giskard.defineToken(MiNDTokenType.Member, "Extends", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_PROVIDES = Giskard.defineToken(MiNDTokenType.Member, "Provides", MTTYPE_CONN, MiNDValType.Link, MiNDCollType.Set);
	
	MiNDToken MTTYPE_ITERATOR = Giskard.defineToken(MiNDTokenType.Type, "Iterator", MTUNIT_UTILS);
	MiNDToken MTMEMBER_ITERATOR_INDEX = Giskard.defineToken(MiNDTokenType.Member, "Index", MTTYPE_ITERATOR, MiNDValType.Int, MiNDCollType.One);
	
	MiNDToken MTTYPE_RANGE = Giskard.defineToken(MiNDTokenType.Type, "Range", MTUNIT_UTILS);
	MiNDToken MTMEMBER_RANGE_INT_MIN = Giskard.defineToken(MiNDTokenType.Member, "IntMin", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_RANGE_INT_MAX = Giskard.defineToken(MiNDTokenType.Member, "IntMax", MTTYPE_RANGE, MiNDValType.Int, MiNDCollType.One);
	
	MiNDToken MTTYPE_SPLIT = Giskard.defineToken(MiNDTokenType.Type, "Split", MTUNIT_UTILS);
	MiNDToken MTMEMBER_SPLIT_WEIGHT = Giskard.defineToken(MiNDTokenType.Member, "Weight", MTTYPE_SPLIT, MiNDValType.Real, MiNDCollType.Arr);
	MiNDToken MTMEMBER_SPLIT_ABSOLUTE = Giskard.defineToken(MiNDTokenType.Member, "Absolute", MTTYPE_SPLIT, MiNDValType.Int, MiNDCollType.Arr);
	
	MiNDToken MTTYPE_LINK = Giskard.defineToken(MiNDTokenType.Type, "Link", MTUNIT_UTILS);
	MiNDToken MTMEMBER_LINK_TYPE = Giskard.defineToken(MiNDTokenType.Member, "Type", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_ONE = Giskard.defineToken(MiNDTokenType.Member, "One", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_LINK_ARR = Giskard.defineToken(MiNDTokenType.Member, "Arr", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_LINK_SET = Giskard.defineToken(MiNDTokenType.Member, "Set", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Set);
	MiNDToken MTMEMBER_LINK_MAP = Giskard.defineToken(MiNDTokenType.Member, "Map", MTTYPE_LINK, MiNDValType.Link, MiNDCollType.Map);
	
	MiNDToken MTAGENT_DUMP = Giskard.defineToken(MiNDTokenType.Agent, "Dump", MTUNIT_UTILS);

}
