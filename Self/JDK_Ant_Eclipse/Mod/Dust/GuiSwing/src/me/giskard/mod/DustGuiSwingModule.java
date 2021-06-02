package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.gui.swing.DustSwingAgent;
import me.giskard.dust.gui.swing.DustSwingRenderer;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensMind;

public class DustGuiSwingModule implements GiskardConsts.MiNDAgent, DustTokensGui, DustTokensMind {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "GUI Swing module initializing");
		
		DustTokens.addModuleImpInfo(MTAGN_GUI_MANAGER, DustSwingRenderer.class);
		
		DustTokens.addModuleImpInfo(MTAGN_GUI_FRAME, DustSwingAgent.SwingFrame.class);
		DustTokens.addModuleImpInfo(MTAGN_GUI_PANEL, DustSwingAgent.SwingPanel.class);
		DustTokens.addModuleImpInfo(MTAGN_GUI_LABEL, DustSwingAgent.SwingLabel.class);
		
		Object mgr = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_MANAGER);
		Object plt = Giskard.access(MiNDAccessCommand.Get, MTTYP_GUI_PLATFORM);
		
		Giskard.access(MiNDAccessCommand.Set, mgr, plt, MTMEM_GUI_PLATFORM_MANAGER);
		Giskard.access(MiNDAccessCommand.Set, plt, MTLOC_MACHINE_THEMACHINE, MTMEM_GUI_GUIOWNER_PLATFORMS, MTTAG_GUI_PLATFORMTYPE_SWING);

		return MiNDResultType.Accept;
	}
}
