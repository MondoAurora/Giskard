package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.montru.gui.MontruGuiMainPanel;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensMontru;

public class DustMontruModule implements GiskardConsts.MiNDAgent, DustTokensMontru {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Montru Gui module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_MAINPANEL, MontruGuiMainPanel.class);
		
		Giskard.access(MiNDAccessCommand.Add, MTUNIT_MONTRU, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);

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
		return MiNDResultType.Accept;
	}
}
