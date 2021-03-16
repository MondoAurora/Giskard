package me.giskard.dust.gui.swing;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustSwingRenderer implements DustSwingConsts, GiskardConsts.MiNDAgent {

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT;
		
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			break;
		case Process:
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_PARAM, MTMEMBER_STRING);
			
//			Giskard.selectByPath(MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_PARAM, MTMEMBER_AREA_CENTER);
			
			Giskard.log(MiNDEventLevel.INFO, "Would create frame", label);
			break;
		case Release:
			break;
		}
		
		return ret;
	}
	

}
