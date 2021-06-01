package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensIO extends GiskardConsts { 
	MiNDToken MTUNIT_IO = Giskard.defineToken(MiNDTokenType.Unit, "IO"); 
	MiNDToken MTTYPE_SERIALIZEEVENT = Giskard.defineToken(MiNDTokenType.Type, "SerializeEvent", MTUNIT_IO);
	MiNDToken MTTYPE_STREAM = Giskard.defineToken(MiNDTokenType.Type, "Stream", MTUNIT_IO);
	MiNDToken MTAGENT_TEST01 = Giskard.defineToken(MiNDTokenType.Agent, "Test01", MTUNIT_IO);
	MiNDToken MTMEMBER_STREAM_URL = Giskard.defineToken(MiNDTokenType.Member, "Url", MTTYPE_STREAM, MiNDValType.Raw, MiNDCollType.One);
}
