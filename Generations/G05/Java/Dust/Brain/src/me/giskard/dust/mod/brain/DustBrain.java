package me.giskard.dust.mod.brain;

import java.util.HashMap;
import java.util.Map;

import me.giskard.mind.GiskardConsts;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings("rawtypes")
public class DustBrain implements DustBrainConsts, DustBrainImpl, GiskardConsts.MindBrain {
	
	Map brainRoot = new HashMap();
	
	BrainKnowledge agtKnowledge;
	BrainContext agtContext;
	BrainDialog agtDialog;

	@Override
	public MindStatus agentExecAction(MindAction action) throws Exception {
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
	
	public void initBrain() throws Exception {
		agtKnowledge = (BrainKnowledge) Class.forName(CN_KNOWLEDGE).getConstructor().newInstance();
		agtContext = (BrainContext) Class.forName(CN_CONTEXT).getConstructor().newInstance();
		agtDialog = (BrainDialog) Class.forName(CN_DIALOG).getConstructor().newInstance();
	}

}
