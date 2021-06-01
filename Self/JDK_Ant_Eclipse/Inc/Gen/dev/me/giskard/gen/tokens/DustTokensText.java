package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensText extends GiskardConsts { 
	MiNDToken MTUNIT_TEXT = Giskard.defineToken(MiNDTokenType.Unit, "Text"); 
	MiNDToken MTTYPE_PLAINTEXT = Giskard.defineToken(MiNDTokenType.Type, "PlainText", MTUNIT_TEXT);
	MiNDToken MTAGENT_FORMAT = Giskard.defineToken(MiNDTokenType.Agent, "Format", MTUNIT_TEXT);
	MiNDToken MTTYPE_LANGUAGE = Giskard.defineToken(MiNDTokenType.Type, "Language", MTUNIT_TEXT);
	MiNDToken MTAGENT_MATCHCONST = Giskard.defineToken(MiNDTokenType.Agent, "MatchConst", MTUNIT_TEXT);
	MiNDToken MTAGENT_MATCHRANGE = Giskard.defineToken(MiNDTokenType.Agent, "MatchRange", MTUNIT_TEXT);
	MiNDToken MTMEMBER_PLAINTEXT_STRING = Giskard.defineToken(MiNDTokenType.Member, "String", MTTYPE_PLAINTEXT, MiNDValType.Raw, MiNDCollType.One);
}
