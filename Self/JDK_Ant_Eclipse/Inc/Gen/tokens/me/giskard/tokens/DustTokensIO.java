package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensIO extends MindConsts {
	MiNDToken MT_JSON_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "IO_Json");
	
	MiNDToken MT_JSON_EVENT = Mind.defineToken(MiNDTokenType.TYPE, "JsonEvent", MT_JSON_UNIT);
	MiNDToken MT_JSONEVENT_ACTION = Mind.defineToken(MiNDTokenType.MEMBER, "Action", MT_JSON_EVENT, MiNDValType.REF, MiNDCollType.ONE);
	MiNDToken MT_JSONEVENT_BLOCK = Mind.defineToken(MiNDTokenType.MEMBER, "Block", MT_JSON_EVENT, MiNDValType.REF, MiNDCollType.ONE);

	MiNDToken MT_JSON_BLOCKTYPE = Mind.defineToken(MiNDTokenType.TAG, "JsonBlockType", MT_JSON_UNIT);
	MiNDToken MT_JSON_BLOCKTYPE_ENTRY = Mind.defineToken(MiNDTokenType.TAG, "Entry", MT_JSON_BLOCKTYPE);
	MiNDToken MT_JSON_BLOCKTYPE_OBJECT = Mind.defineToken(MiNDTokenType.TAG, "Object", MT_JSON_BLOCKTYPE);
	MiNDToken MT_JSON_BLOCKTYPE_ARRAY = Mind.defineToken(MiNDTokenType.TAG, "Array", MT_JSON_BLOCKTYPE);
	MiNDToken MT_JSON_BLOCKTYPE_PRIMITIVE = Mind.defineToken(MiNDTokenType.TAG, "Primitive", MT_JSON_BLOCKTYPE);

}
