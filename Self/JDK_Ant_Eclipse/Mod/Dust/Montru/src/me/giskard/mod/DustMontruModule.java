package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.montru.gui.MontruGuiMainPanel;
import me.giskard.tokens.DustTokensMontru;
import me.giskard.tokens.DustTokens;

public class DustMontruModule implements GiskardConsts.MiNDAgent, DustTokensMontru {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Montru Gui module initializing");

		DustTokens.addModuleImpInfo(MTAGN_MONTRU_MAINPANEL, MontruGuiMainPanel.class);

		// TODO Temp removal, should put back...
//		Giskard.access(MiNDAccessCommand.Add, MTUNI_MONTRU, MTMEM_GENERIC_ACTION_THIS, MTMEM_CONN_PROVIDES);

		return MiNDResultType.Accept;
	}
}
