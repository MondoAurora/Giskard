package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensIdea extends GiskardConsts { 
	MiNDToken MTUNI_IDEA = Giskard.defineToken(MiNDTokenType.Unit, "Idea"); 
	MiNDToken MTTAG_IDEA_VALTYPE = Giskard.defineToken(MiNDTokenType.Tag, "ValType", MTUNI_IDEA);
	MiNDToken MTTAG_IDEA_VALTYPE_REAL = Giskard.defineToken(MiNDTokenType.Tag, "Real", MTTAG_IDEA_VALTYPE);
	MiNDToken MTTAG_IDEA_VALTYPE_RAW = Giskard.defineToken(MiNDTokenType.Tag, "Raw", MTTAG_IDEA_VALTYPE);
	MiNDToken MTTAG_IDEA_VALTYPE_LINK = Giskard.defineToken(MiNDTokenType.Tag, "Link", MTTAG_IDEA_VALTYPE);
	MiNDToken MTTAG_IDEA_VALTYPE_INT = Giskard.defineToken(MiNDTokenType.Tag, "Int", MTTAG_IDEA_VALTYPE);
	MiNDToken MTTYP_IDEA_TYPE = Giskard.defineToken(MiNDTokenType.Type, "Type", MTUNI_IDEA);
	MiNDToken MTTAG_IDEA_TOKENTYPE = Giskard.defineToken(MiNDTokenType.Tag, "TokenType", MTUNI_IDEA);
	MiNDToken MTTAG_IDEA_TOKENTYPE_UNIT = Giskard.defineToken(MiNDTokenType.Tag, "Unit", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_TYPE = Giskard.defineToken(MiNDTokenType.Tag, "Type", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_TAG = Giskard.defineToken(MiNDTokenType.Tag, "Tag", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_SERVICE = Giskard.defineToken(MiNDTokenType.Tag, "Service", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_MEMBER = Giskard.defineToken(MiNDTokenType.Tag, "Member", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_LOCAL = Giskard.defineToken(MiNDTokenType.Tag, "Local", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTAG_IDEA_TOKENTYPE_AGENT = Giskard.defineToken(MiNDTokenType.Tag, "Agent", MTTAG_IDEA_TOKENTYPE);
	MiNDToken MTTYP_IDEA_MEMBER = Giskard.defineToken(MiNDTokenType.Type, "Member", MTUNI_IDEA);
	MiNDToken MTTAG_IDEA_COLLTYPE = Giskard.defineToken(MiNDTokenType.Tag, "CollType", MTUNI_IDEA);
	MiNDToken MTTAG_IDEA_COLLTYPE_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_IDEA_COLLTYPE);
	MiNDToken MTTAG_IDEA_COLLTYPE_ONE = Giskard.defineToken(MiNDTokenType.Tag, "One", MTTAG_IDEA_COLLTYPE);
	MiNDToken MTTAG_IDEA_COLLTYPE_MAP = Giskard.defineToken(MiNDTokenType.Tag, "Map", MTTAG_IDEA_COLLTYPE);
	MiNDToken MTTAG_IDEA_COLLTYPE_ARR = Giskard.defineToken(MiNDTokenType.Tag, "Arr", MTTAG_IDEA_COLLTYPE);
}
