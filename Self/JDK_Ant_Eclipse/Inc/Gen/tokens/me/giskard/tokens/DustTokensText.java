package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensText extends DustTokensMind {
	MiNDToken MTUNIT_TEXT = Giskard.defineToken(MiNDTokenType.Unit, "Text");
	
	MiNDToken MTTYPE_LANGUAGE = Giskard.defineToken(MiNDTokenType.Type, "Language", MTUNIT_TEXT);
	
	MiNDToken MTTYPE_PLAIN = Giskard.defineToken(MiNDTokenType.Type, "PlainText", MTUNIT_TEXT);
	MiNDToken MTMEMBER_PLAIN_STRING = Giskard.defineToken(MiNDTokenType.Member, "String", MTTYPE_PLAIN, MiNDValType.Raw,
			MiNDCollType.One);

	MiNDToken MTAGENT_MATCHCONST = Giskard.defineToken(MiNDTokenType.Agent, "MatchConst", MTUNIT_TEXT);
	MiNDToken MTAGENT_MATCHRANGE = Giskard.defineToken(MiNDTokenType.Agent, "MatchRange", MTUNIT_TEXT);
}
