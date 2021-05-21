package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.montru.gui.MontruGuiMainPanel;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensMontru;

public class DustMontruModule implements GiskardConsts.MiNDAgent, DustTokensMontru {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Montru Gui module initializing");

		DustTokens.addModuleImpInfo(MTAGENT_MAINPANEL, MontruGuiMainPanel.class);

		// TODO Temp removal, should put back...
//		Giskard.access(MiNDAccessCommand.Add, MTUNIT_MONTRU, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);

		return MiNDResultType.Accept;
	}
}
