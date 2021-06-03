package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensTemp extends GiskardConsts { 
	MiNDToken MTUNI_TEMP = Giskard.defineToken(MiNDTokenType.Unit, "Temp"); 
	MiNDToken MTTYP_TEMP_TEMP = Giskard.defineToken(MiNDTokenType.Type, "Temp", MTUNI_TEMP);
	MiNDToken MTMEM_TEMP_TEMP_SERIALIZER = Giskard.defineToken(MiNDTokenType.Member, "Serializer", MTTYP_TEMP_TEMP, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTTAG_TEMP_SERIALIZEMODE = Giskard.defineToken(MiNDTokenType.Tag, "SerializeMode", MTUNI_TEMP);
	MiNDToken MTTAG_TEMP_SERIALIZEMODE_LOAD = Giskard.defineToken(MiNDTokenType.Tag, "Load", MTTAG_TEMP_SERIALIZEMODE);
	MiNDToken MTTAG_TEMP_SERIALIZEMODE_COLLECT = Giskard.defineToken(MiNDTokenType.Tag, "Collect", MTTAG_TEMP_SERIALIZEMODE);
	MiNDToken MTTAG_TEMP_SERIALIZEMODE_SAVE = Giskard.defineToken(MiNDTokenType.Tag, "Save", MTTAG_TEMP_SERIALIZEMODE);
}
