package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.gui.swing.DustSwingAgent;
import me.giskard.dust.gui.swing.DustSwingRenderer;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensMachine;

public class DustGuiSwingModule implements GiskardConsts.MiNDAgent, DustTokensGui, DustTokensMachine, DustTokensGeneric {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "GUI Swing module initializing");
		
		DustTokens.addModuleImpInfo(MTAGENT_MANAGER, DustSwingRenderer.class);
		
		DustTokens.addModuleImpInfo(MTAGENT_FRAME, DustSwingAgent.SwingFrame.class);
		DustTokens.addModuleImpInfo(MTAGENT_PANEL, DustSwingAgent.SwingPanel.class);
		DustTokens.addModuleImpInfo(MTAGENT_LABEL, DustSwingAgent.SwingLabel.class);
		
		Object mgr = Giskard.access(MiNDAccessCommand.Get, MTAGENT_MANAGER);
		Object plt = Giskard.access(MiNDAccessCommand.Get, MTTYPE_PLATFORM);
		
		Giskard.access(MiNDAccessCommand.Set, mgr, plt, MTMEMBER_PLATFORM_MANAGER);
		Giskard.access(MiNDAccessCommand.Set, plt, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_PLATFORMS, MTTAG_PLATFORM_SWING);

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
