package me.giskard.dust.mod.brain.logic;

import me.giskard.dust.mod.brain.DustBrainConsts;
import me.giskard.mind.GiskardConsts;

public class DustBrainLogicDialog implements DustBrainLogicConsts, GiskardConsts.MindAgent, DustBrainConsts.LogicDialog {

	@Override
	public MindStatus agentExecAction(MindAction action) {
		
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			break;
		case Process:
			break;
		case Release:
			break;
		}
		
		return MindStatus.Accept;
	}

	

}
