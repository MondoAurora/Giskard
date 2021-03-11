package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensIO extends DustTokensMind {
	MiNDToken MTUNIT_IO = Giskard.defineToken(MiNDTokenType.UNIT, "IO");
	
	MiNDToken MTTYPE_SERIALIZEEVENT = Giskard.defineToken(MiNDTokenType.TYPE, "SerializeEvent", MTUNIT_IO);
	MiNDToken MTMEMBER_SERIALIZEEVENT_TYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "Type", MTTYPE_SERIALIZEEVENT, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTAGENT_TEST01 = Giskard.defineToken(MiNDTokenType.AGENT, "Test01", MTUNIT_IO);

//	MiNDToken MT_IO_SERIALIZEITEM = Mind.defineToken(MiNDTokenType.TAG, "SerializeItem", MT_IO_UNIT, SerializeItem.class);
//	MiNDToken MT_IO_SERIALIZEITEM_KEY = Mind.defineToken(MiNDTokenType.TAG, "Key", MT_IO_SERIALIZEITEM, SerializeItem.Key);
//	MiNDToken MT_IO_SERIALIZEITEM_MAP = Mind.defineToken(MiNDTokenType.TAG, "Map", MT_IO_SERIALIZEITEM, SerializeItem.Map);
//	MiNDToken MT_IO_SERIALIZEITEM_ARRAY = Mind.defineToken(MiNDTokenType.TAG, "Array", MT_IO_SERIALIZEITEM, SerializeItem.Array);
//	MiNDToken MT_IO_SERIALIZEITEM_VALUE = Mind.defineToken(MiNDTokenType.TAG, "Value", MT_IO_SERIALIZEITEM, SerializeItem.Value);

}
