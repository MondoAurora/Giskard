package me.giskard.dust.runtime.knowledge;

import me.giskard.dust.runtime.DustRuntimeUtils;

public class DustKnowledgeUtils implements DustKnowledgeConsts {
	public static MiNDResultType notifyAgent(MiNDAgent agent, DustKnowledgeContext ctx, Object val) throws Exception {
		if ( val instanceof DustKnowledgeLink ) {
			ctx.entities.put(MTMEMBER_ACTION_PARAM, ((DustKnowledgeLink) val).to);
		}

		return agent.process(MiNDAgentAction.Process);
	}

	public static void optSyncToken(DustToken token) {
		if ( DustRuntimeUtils.isBootDone() ) {
			DustKnowledgeBlock e = token.getEntity();

			e.access(MiNDAccessCommand.Set, token.getName(), (DustTokenMember) MTMEMBER_PLAIN_STRING, null);

			DustToken p = token.getParent();
			if ( null != p ) {
				e.access(MiNDAccessCommand.Set, p, (DustTokenMember) MTMEMBER_CONN_OWNER, null);
			}

			MiNDToken t;
			switch ( token.getType() ) {
			case AGENT:
				t = MTTYPE_AGENT;
				break;
			case MEMBER:
				t = MTTYPE_MEMBER;
				break;
			case TAG:
				t = MTTYPE_TAG;
				break;
			case TYPE:
				t = MTTYPE_TYPE;
				break;
			case UNIT:
				t = MTTYPE_UNIT;
				break;
			default:
				t = null;
			}
			if ( null != t ) {
				e.access(MiNDAccessCommand.Set, t, (DustTokenMember) MTMEMBER_ENTITY_PRIMARYTYPE, null);
			}

			e.access(MiNDAccessCommand.Set, token.getId(), (DustTokenMember) MTMEMBER_ENTITY_STOREID, null);
			e.access(MiNDAccessCommand.Set, token.getRoot(), (DustTokenMember) MTMEMBER_ENTITY_STOREUNIT, null);
			
//			Giskard.log(MiNDEventLevel.TRACE, "SyncToken", e);
		}
	}
}
