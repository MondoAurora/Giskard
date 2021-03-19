package me.giskard.dust.runtime.knowledge;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.MindCollMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustKnowledgeBlock implements DustKnowledgeConsts {
	DustKnowledgeContext ctx;
	DustKnowledgeBlock orig;

	final MindCollMap<DustTokenMember, Object> localData;

	public DustKnowledgeBlock(DustKnowledgeContext ctx) {
		this.ctx = ctx;
		localData = new MindCollMap<DustTokenMember, Object>(false);
	}

	public DustKnowledgeBlock(DustKnowledgeBlock source) {
		this.ctx = source.ctx;
		localData = new MindCollMap<DustTokenMember, Object>(source.localData);
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustTokenMember tMember, Object key) {
		try {
			if ( null == tMember ) {
				return (RetType) this;
			}

			DustKnowledgeBlock tBlock = ((cmd != MiNDAccessCommand.Use) && (tMember.getValType() == MiNDValType.Link)) 
					? ctx.entities.peek((DustToken) val) : null;

			Object current = localData.get(tMember);
			Object data = (null == tBlock) ? val : tBlock;
			
			if ( (null == current) && ( MTMEMBER_ENTITY_TAGS == tMember) && GiskardUtils.isAccessCreator(cmd)) {
				current = DustKnowledgeCollection.create(this, tMember);
				localData.put(tMember, current);
			}

			switch ( cmd ) {
			case Get:
				if ( null != current ) {
					if ( current instanceof DustKnowledgeCollection<?> ) {
						current = (RetType) ((DustKnowledgeCollection) current).access(cmd, val, key);
					}
					
					if ( current instanceof DustKnowledgeLink ) {
						val = (RetType) ((DustKnowledgeLink) current).to;
					} else {
						val = (RetType) current;
					}
				} else if (val instanceof MiNDToken) {
					val = null;
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
					current = DustKnowledgeCollection.create(this, tMember);
					localData.put(tMember, current);
				}
				val = (RetType) ((DustKnowledgeCollection) current).access(cmd, data, null);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin, tMember);

						if ( current instanceof DustKnowledgeCollection ) {
							((DustKnowledgeCollection) current).access(cmd, agent, null);
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
