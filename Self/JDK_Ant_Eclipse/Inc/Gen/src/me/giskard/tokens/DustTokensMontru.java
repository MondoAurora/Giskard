package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMontru extends GiskardConsts { 
	MiNDToken MTUNI_MONTRU = Giskard.defineToken(MiNDTokenType.Unit, "Montru"); 
	MiNDToken MTSVC_MONTRU_GUIMAIN = Giskard.defineToken(MiNDTokenType.Service, "GuiMain", MTUNI_MONTRU);
	MiNDToken MTAGN_MONTRU_MAINPANEL = Giskard.defineToken(MiNDTokenType.Agent, "MainPanel", MTUNI_MONTRU);
}
