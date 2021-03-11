package me.giskard.dust.runtime.knowledge;

public class DustKnowledgeUtils implements DustKnowledgeConsts {
	public static MiNDResultType notifyAgent(MiNDAgent agent, DustKnowledgeContext ctx, Object val) throws Exception {
		if ( val instanceof DustKnowledgeLink ) {
			ctx.entities.put(MTMEMBER_ACTION_PARAM, ((DustKnowledgeLink)val).to);
		}
		
		return agent.process(MiNDAgentAction.Process);
	}
}
