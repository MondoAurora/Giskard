package me.giskard.tokens;

import me.giskard.Mind;

public interface DustTokensIO extends DustTokensMind {
	MiNDToken MT_IO_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "IO");
	
	MiNDToken MT_IO_SERIALIZEEVENT = Mind.defineToken(MiNDTokenType.TYPE, "SerializeEvent", MT_IO_UNIT);
	MiNDToken MT_IO_SERIALIZEEVENT_TYPE = Mind.defineToken(MiNDTokenType.MEMBER, "Type", MT_IO_SERIALIZEEVENT, MiNDValType.Ref, MiNDCollType.One);

//	MiNDToken MT_IO_SERIALIZEITEM = Mind.defineToken(MiNDTokenType.TAG, "SerializeItem", MT_IO_UNIT, SerializeItem.class);
//	MiNDToken MT_IO_SERIALIZEITEM_KEY = Mind.defineToken(MiNDTokenType.TAG, "Key", MT_IO_SERIALIZEITEM, SerializeItem.Key);
//	MiNDToken MT_IO_SERIALIZEITEM_MAP = Mind.defineToken(MiNDTokenType.TAG, "Map", MT_IO_SERIALIZEITEM, SerializeItem.Map);
//	MiNDToken MT_IO_SERIALIZEITEM_ARRAY = Mind.defineToken(MiNDTokenType.TAG, "Array", MT_IO_SERIALIZEITEM, SerializeItem.Array);
//	MiNDToken MT_IO_SERIALIZEITEM_VALUE = Mind.defineToken(MiNDTokenType.TAG, "Value", MT_IO_SERIALIZEITEM, SerializeItem.Value);

}
