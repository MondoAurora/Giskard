package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensMontru extends DustTokensMind {
	MiNDToken MTUNIT_MONTRUGUI = Giskard.defineToken(MiNDTokenType.UNIT, "MontruGui");
	
	MiNDToken MTAGENT_MAINPANEL = Giskard.defineToken(MiNDTokenType.AGENT, "MainPanel", MTUNIT_MONTRUGUI);

}
