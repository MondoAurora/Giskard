package me.giskard.dust.runtime.knowledge;

import java.util.Set;

import me.giskard.GiskardException;
import me.giskard.coll.MindCollMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustKnowledgeBlock implements DustKnowledgeConsts {
	DustKnowledgeContext ctx;
	DustKnowledgeBlock orig;
	Set<DustKnowledgeLink> incomingLinks;

	final MindCollMap<DustTokenMember, Object> localData;

	public DustKnowledgeBlock(DustKnowledgeContext ctx) {
		this.ctx = ctx;
		localData = new MindCollMap<DustTokenMember, Object>(false);
	}

	public DustKnowledgeBlock(DustKnowledgeBlock source) {
		this.ctx = source.ctx;
		localData = new MindCollMap<DustTokenMember, Object>(source.localData);
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustTokenMember tMember) {
		try {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

			Object current = localData.get(tMember);

			DustKnowledgeBlock tBlock = (val instanceof DustToken) ? ctx.entities.peek((DustToken) val) : null;
			Object data = (null == tBlock) ? val : tBlock;
			
			switch ( cmd ) {
			case Get:
				if ( null != current ) {
					if ( current instanceof DustKnowledgeLink ) {
						val = (RetType) ((DustKnowledgeLink) current).to;
					} else {
						val = (RetType) current;
					}
				}
				break;
			case Set:
				if ( null != tBlock ) {
					data = ctx.setLink(this, tMember, tBlock);
				}
				localData.put(tMember, data);
				val = (RetType) current;
				break;
			case Add:
				if ( null == current ) {
					current = DustKnowledgeCollection.create(tMember);
					localData.put(tMember, current);
				}
				val = (RetType) ((DustKnowledgeCollection) current).access(this, tMember, cmd, data);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin, tMember);

						if ( current instanceof DustKnowledgeCollection ) {
							((DustKnowledgeCollection) current).access(this, tMember, cmd, agent);
						} else {
							DustKnowledgeUtils.notifyAgent(agent, ctx, current);
						}
					} finally {
						agent.process(MiNDAgentAction.End, tMember);
					}
				}
				break;
			default:
//			Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);
				break;
			}
			return val;
		} catch (Exception e) {
			return GiskardException.wrap(e);
		}
	}

	@Override
	public String toString() {
		return localData.toString();
	}
}
