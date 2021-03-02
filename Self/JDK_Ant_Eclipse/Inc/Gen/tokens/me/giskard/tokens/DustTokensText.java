package me.giskard.tokens;

import me.giskard.Mind;

public interface DustTokensText extends DustTokensMind {
	MiNDToken MTUNIT_TEXT = Mind.defineToken(MiNDTokenType.UNIT, "Text");
	
	MiNDToken MTTYPE_LANGUAGE = Mind.defineToken(MiNDTokenType.TYPE, "Language", MTUNIT_TEXT);
	
	MiNDToken MTTYPE_PLAIN = Mind.defineToken(MiNDTokenType.TYPE, "PlainText", MTUNIT_TEXT);
	
	MiNDToken MTAGENT_MATCHCONST = Mind.defineToken(MiNDTokenType.AGENT, "MatchConst", MTUNIT_TEXT);
	MiNDToken MTAGENT_MATCHRANGE = Mind.defineToken(MiNDTokenType.AGENT, "MatchRange", MTUNIT_TEXT);
}
