package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensText extends GiskardConsts { 
	MiNDToken MTUNI_TEXT = Giskard.defineToken(MiNDTokenType.Unit, "Text"); 
	MiNDToken MTAGN_TEXT_MATCHRANGE = Giskard.defineToken(MiNDTokenType.Agent, "MatchRange", MTUNI_TEXT);
	MiNDToken MTAGN_TEXT_MATCHCONST = Giskard.defineToken(MiNDTokenType.Agent, "MatchConst", MTUNI_TEXT);
	MiNDToken MTTYP_TEXT_LANGUAGE = Giskard.defineToken(MiNDTokenType.Type, "Language", MTUNI_TEXT);
	MiNDToken MTAGN_TEXT_FORMAT = Giskard.defineToken(MiNDTokenType.Agent, "Format", MTUNI_TEXT);
	MiNDToken MTTYP_TEXT_PLAINTEXT = Giskard.defineToken(MiNDTokenType.Type, "PlainText", MTUNI_TEXT);
	MiNDToken MTMEM_TEXT_PLAINTEXT_STRING = Giskard.defineToken(MiNDTokenType.Member, "String", MTTYP_TEXT_PLAINTEXT, MiNDValType.Raw, MiNDCollType.One);
}
