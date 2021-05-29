package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensTemp extends DustTokensMind {
	MiNDToken MTUNIT_TEMP = Giskard.defineToken(MiNDTokenType.Unit, "Temp");
	
	MiNDToken MTTYPE_TEMP = Giskard.defineToken(MiNDTokenType.Type, "Temp", MTUNIT_TEMP);
	MiNDToken MTMEMBER_TEMP_SERIALIZER = Giskard.defineToken(MiNDTokenType.Member, "Serializer", MTTYPE_TEMP, MiNDValType.Raw,
			MiNDCollType.One);

	MiNDToken MTTAG_SERIALIZEMODE = Giskard.defineToken(MiNDTokenType.Tag, "SerializeMode", MTUNIT_TEMP);
	MiNDToken MTTAG_SERIALIZEMODE_LOAD = Giskard.defineToken(MiNDTokenType.Tag, "Load", MTTAG_SERIALIZEMODE);
	MiNDToken MTTAG_SERIALIZEMODE_COLLECT = Giskard.defineToken(MiNDTokenType.Tag, "Collect", MTTAG_SERIALIZEMODE);
	MiNDToken MTTAG_SERIALIZEMODE_SAVE = Giskard.defineToken(MiNDTokenType.Tag, "Save", MTTAG_SERIALIZEMODE);

}
