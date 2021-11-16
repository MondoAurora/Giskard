package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensIO extends GiskardConsts { 
	MiNDToken MTUNI_IO = Giskard.defineToken(MiNDTokenType.Unit, "IO"); 
	MiNDToken MTTYP_IO_SERIALIZEEVENT = Giskard.defineToken(MiNDTokenType.Type, "SerializeEvent", MTUNI_IO);
	MiNDToken MTTYP_IO_STREAM = Giskard.defineToken(MiNDTokenType.Type, "Stream", MTUNI_IO);
	MiNDToken MTMEM_IO_STREAM_URL = Giskard.defineToken(MiNDTokenType.Member, "Url", MTTYP_IO_STREAM, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTAGN_IO_TEST01 = Giskard.defineToken(MiNDTokenType.Agent, "Test01", MTUNI_IO);
	MiNDToken MTAGN_IO_PDF = Giskard.defineToken(MiNDTokenType.Agent, "Pdf", MTUNI_IO);
}
