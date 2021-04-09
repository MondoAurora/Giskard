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
		
		DustTokens.addModuleImpInfo(MTAGENT_FRAME, DustSwingAgent.Frame.class);

		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_MANAGER, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_PLATFORM, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_PLATFORM_MANAGER);

		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_PLATFORMS, MTTAG_PLATFORM_SWING);

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
