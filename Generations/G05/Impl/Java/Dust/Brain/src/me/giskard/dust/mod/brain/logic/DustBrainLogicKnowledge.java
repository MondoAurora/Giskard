package me.giskard.dust.mod.brain.logic;

import java.util.Map;

import me.giskard.dust.mod.brain.DustBrainConsts;
import me.giskard.dust.mod.brain.DustBrainHandle;
import me.giskard.mind.GiskardConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustBrainLogicKnowledge implements DustBrainLogicConsts, GiskardConsts.MindAgent, DustBrainConsts.LogicKnowledge {
	

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

	
	public <RetType> RetType access(MindAccess cmd, Object val, Map knowledge, DustBrainHandle member, MindColl coll, Object key) {
		Object ret = val;
		
		
		switch ( cmd ) {
		case Check:
			break;
		case Commit:
			break;
		case Delete:
			break;
		case Get:
			break;
		case Insert:
			break;
		case Peek:
			break;
		case Set:
			break;
		}
		
		return (RetType) ret;
	}


	@Override
	public <RetType> RetType access(LogicUnit unit, MindAccess cmd, Object val, Object root, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

}
