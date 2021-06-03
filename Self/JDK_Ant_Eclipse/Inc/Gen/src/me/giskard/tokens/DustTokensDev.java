package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDev extends GiskardConsts { 
	MiNDToken MTUNI_DEV = Giskard.defineToken(MiNDTokenType.Unit, "Dev"); 
	MiNDToken MTAGN_DEV_GENTOKENS = Giskard.defineToken(MiNDTokenType.Agent, "GenTokens", MTUNI_DEV);
}
