package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensText extends DustTokensMind {
	MiNDToken MTUNIT_TEXT = Giskard.defineToken(MiNDTokenType.UNIT, "Text");
	
	MiNDToken MTTYPE_LANGUAGE = Giskard.defineToken(MiNDTokenType.TYPE, "Language", MTUNIT_TEXT);
	
	MiNDToken MTTYPE_PLAIN = Giskard.defineToken(MiNDTokenType.TYPE, "PlainText", MTUNIT_TEXT);
	MiNDToken MTMEMBER_STRING = Giskard.defineToken(MiNDTokenType.MEMBER, "String", MTTYPE_PLAIN, MiNDValType.Raw,
			MiNDCollType.One);

	MiNDToken MTAGENT_MATCHCONST = Giskard.defineToken(MiNDTokenType.AGENT, "MatchConst", MTUNIT_TEXT);
	MiNDToken MTAGENT_MATCHRANGE = Giskard.defineToken(MiNDTokenType.AGENT, "MatchRange", MTUNIT_TEXT);
}
