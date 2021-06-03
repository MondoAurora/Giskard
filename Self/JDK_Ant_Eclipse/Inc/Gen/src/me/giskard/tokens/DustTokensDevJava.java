package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDevJava extends GiskardConsts { 
	MiNDToken MTUNI_DEVJAVA = Giskard.defineToken(MiNDTokenType.Unit, "DevJava"); 
	MiNDToken MTTYP_DEVJAVA_RUNTIME = Giskard.defineToken(MiNDTokenType.Type, "Runtime", MTUNI_DEVJAVA);
}
