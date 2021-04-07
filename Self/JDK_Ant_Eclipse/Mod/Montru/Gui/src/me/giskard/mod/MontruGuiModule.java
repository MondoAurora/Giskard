package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.montru.gui.MontruGuiMainPanel;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensMontru;

public class MontruGuiModule implements GiskardConsts.MiNDAgent, DustTokensMontru {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "Montru Gui module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_MAINPANEL, MontruGuiMainPanel.class);
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		switch ( action ) {
		case Init:
			initModule();
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
