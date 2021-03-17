package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.gui.swing.DustSwingRenderer;
import me.giskard.tokens.DustTokens;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensMachine;

public class DustGuiSwingModule implements GiskardConsts.MiNDAgent, DustTokensGui, DustTokensMachine, DustTokensGeneric {
	void initModule() throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "GUI Swing module initializing");
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_PARAM);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_RENDERER, MTMEMBER_ACTION_PARAM, MTMEMBER_ENTITY_PRIMARYTYPE);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_WORLD, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTMEMBER_ACTION_THIS, MTMEMBER_WORLD_RENDERER);

		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_GUIOWNER_WORLD);
		
		DustTokens.registerNewModule();
		
		DustTokens.addModuleImpInfo(MTAGENT_RENDERER, DustSwingRenderer.class);

	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
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
