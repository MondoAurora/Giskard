package me.giskard.dust.runtime.narrative;

import java.util.Stack;

import me.giskard.GiskardConsts;
import me.giskard.utils.MindUtils;

public class DustNarrativeBlock implements DustNarrativeConsts, GiskardConsts.MiNDAgent {
	
	Stack<MiNDAgent> agentStack;
	MiNDAgent current;
	MiNDResultType lastResult;
	
	public DustNarrativeBlock(MiNDAgent root) {
		current = root;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Process:
			lastResult = current.process(action, params);
			if ( !MindUtils.isAgentRead(lastResult) ) {
				
			}
			return MiNDResultType.REJECT;
		default:
			return MiNDResultType.ACCEPT;
		}
	}

}
