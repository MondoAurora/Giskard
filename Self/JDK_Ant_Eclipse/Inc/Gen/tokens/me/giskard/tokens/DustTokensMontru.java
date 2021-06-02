package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensMontru extends DustTokensMind {
	MiNDToken MTUNIT_MONTRU = Giskard.defineToken(MiNDTokenType.Unit, "Montru");
	
	MiNDToken MTSERVICE_GUIMAIN = Giskard.defineToken(MiNDTokenType.Service, "GuiMain", MTUNIT_MONTRU);

	MiNDToken MTAGENT_MAINPANEL = Giskard.defineToken(MiNDTokenType.Agent, "MainPanel", MTUNIT_MONTRU);

}
