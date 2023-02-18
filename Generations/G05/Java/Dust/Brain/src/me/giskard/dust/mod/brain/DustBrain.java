package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;
import me.giskard.mind.GiskardUtils;

public class DustBrain implements DustConsts, GiskardConsts.MindBrain {

	@Override
	public MindStatus agentExecAction(MindAction action) {
		log(action);
		
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			initBrain();
			break;
		case Process:
			break;
		case Release:
			break;
		}
		
		return MindStatus.Accept;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path) {
		
		Object ret = val;
		
		return (RetType) ret;
	}

	@Override
	public void log(Object event, Object... params) {
		GiskardUtils.dump(", ", false, event, params);
	}
	
	public void initBrain() {

	}

}
