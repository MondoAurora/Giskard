package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDev extends GiskardConsts { 
	MiNDToken MTUNIT_DEV = Giskard.defineToken(MiNDTokenType.Unit, "Dev"); 
	MiNDToken MTAGENT_GENTOKENS = Giskard.defineToken(MiNDTokenType.Agent, "GenTokens", MTUNIT_DEV);
}
