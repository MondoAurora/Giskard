package me.giskard.dust.runtime.model;

public class DustModelUtils implements DustModelConsts {
	public static MiNDResultType notifyAgent(MiNDAgent agent, DustModelContext ctx, Object val) throws Exception {
		if ( val instanceof DustModelRef ) {
			ctx.entityBlocks.put(MTMEMBER_ACTION_PARAM, ((DustModelRef)val).to);
		}
		
		return agent.process(MiNDAgentAction.Process);
	}
}
