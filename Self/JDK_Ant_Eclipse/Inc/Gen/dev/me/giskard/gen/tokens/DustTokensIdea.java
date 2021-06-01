package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensIdea extends GiskardConsts { 
	MiNDToken MTUNIT_IDEA = Giskard.defineToken(MiNDTokenType.Unit, "Idea"); 
	MiNDToken MTTAG_COLLTYPE = Giskard.defineToken(MiNDTokenType.Tag, "CollType", MTUNIT_IDEA);
	MiNDToken MTTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.Type, "Member", MTUNIT_IDEA);
	MiNDToken MTTAG_TOKENTYPE = Giskard.defineToken(MiNDTokenType.Tag, "TokenType", MTUNIT_IDEA);
	MiNDToken MTTYPE_TYPE = Giskard.defineToken(MiNDTokenType.Type, "Type", MTUNIT_IDEA);
	MiNDToken MTTAG_VALTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ValType", MTUNIT_IDEA);
	MiNDToken MTTAG_VALTYPE_INT = Giskard.defineToken(MiNDTokenType.Tag, "Int", MTTAG_VALTYPE);
	MiNDToken MTTAG_VALTYPE_LINK = Giskard.defineToken(MiNDTokenType.Tag, "Link", MTTAG_VALTYPE);
	MiNDToken MTTAG_VALTYPE_RAW = Giskard.defineToken(MiNDTokenType.Tag, "Raw", MTTAG_VALTYPE);
	MiNDToken MTTAG_VALTYPE_REAL = Giskard.defineToken(MiNDTokenType.Tag, "Real", MTTAG_VALTYPE);
	MiNDToken MTTAG_TOKENTYPE_AGENT = Giskard.defineToken(MiNDTokenType.Tag, "Agent", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_LOCAL = Giskard.defineToken(MiNDTokenType.Tag, "Local", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.Tag, "Member", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_SERVICE = Giskard.defineToken(MiNDTokenType.Tag, "Service", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_TAG = Giskard.defineToken(MiNDTokenType.Tag, "Tag", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_TYPE = Giskard.defineToken(MiNDTokenType.Tag, "Type", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_TOKENTYPE_UNIT = Giskard.defineToken(MiNDTokenType.Tag, "Unit", MTTAG_TOKENTYPE);
	MiNDToken MTTAG_COLLTYPE_ARR = Giskard.defineToken(MiNDTokenType.Tag, "Arr", MTTAG_COLLTYPE);
	MiNDToken MTTAG_COLLTYPE_MAP = Giskard.defineToken(MiNDTokenType.Tag, "Map", MTTAG_COLLTYPE);
	MiNDToken MTTAG_COLLTYPE_ONE = Giskard.defineToken(MiNDTokenType.Tag, "One", MTTAG_COLLTYPE);
	MiNDToken MTTAG_COLLTYPE_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_COLLTYPE);
}
