package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMontru extends GiskardConsts { 
	MiNDToken MTUNIT_MONTRU = Giskard.defineToken(MiNDTokenType.Unit, "Montru"); 
	MiNDToken MTAGENT_MAINPANEL = Giskard.defineToken(MiNDTokenType.Agent, "MainPanel", MTUNIT_MONTRU);
}
