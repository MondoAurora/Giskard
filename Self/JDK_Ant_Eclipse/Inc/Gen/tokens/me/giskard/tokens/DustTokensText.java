package me.giskard.tokens;

import me.giskard.Mind;

public interface DustTokensText extends DustTokensMind {
	MiNDToken MT_TEXT_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "Text");
	
	MiNDToken MT_TEXT_LANGUAGE = Mind.defineToken(MiNDTokenType.TYPE, "Language", MT_TEXT_UNIT);
	
	MiNDToken MT_TEXT_PLAIN = Mind.defineToken(MiNDTokenType.TYPE, "PlainText", MT_TEXT_UNIT);
	
	MiNDToken MT_TEXT_AGENT_MATCHCONST = Mind.defineToken(MiNDTokenType.TYPE, "MatchConst", MT_TEXT_UNIT);
	MiNDToken MT_TEXT_AGENT_MATCHRANGE = Mind.defineToken(MiNDTokenType.TYPE, "MatchRange", MT_TEXT_UNIT);
	
//	MiNDToken MT_IO_SERIALIZEEVENT_TYPE = Mind.defineToken(MiNDTokenType.MEMBER, "Type", MT_IO_SERIALIZEEVENT, MiNDValType.Ref, MiNDCollType.One);
}
