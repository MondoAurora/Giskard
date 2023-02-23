package me.giskard.dust.mod.brain.agents;

import me.giskard.dust.mod.brain.DustBrainConsts;
import me.giskard.mind.GiskardConsts;

public class DustBrainAgentContext implements DustBrainAgentConsts, GiskardConsts.MindAgent, DustBrainConsts.BrainContext {

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

	@Override
	public MindColl getMemberColl(MindHandle hMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MindHandle getTagParent(MindHandle hTag) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
