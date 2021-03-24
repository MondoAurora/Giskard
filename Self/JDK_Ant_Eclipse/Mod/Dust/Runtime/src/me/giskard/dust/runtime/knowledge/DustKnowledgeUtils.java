package me.giskard.dust.runtime.knowledge;

public class DustKnowledgeUtils implements DustKnowledgeConsts {
	public static MiNDResultType notifyAgent(MiNDAgent agent, DustKnowledgeContext ctx, Object val) throws Exception {
		ctx.rootBlock.access(MiNDAccessCommand.Set, val, (DustTokenMember) MTMEMBER_ACTION_PARAM, null);
		return agent.process(MiNDAgentAction.Process);
	}
}
