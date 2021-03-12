package me.giskard.dust.runtime.knowledge;

import java.util.HashSet;
import java.util.Set;

import me.giskard.GiskardException;
import me.giskard.coll.MindCollMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustKnowledgeBlock implements DustKnowledgeConsts {
	DustKnowledgeContext ctx;
	DustKnowledgeBlock orig;
//	Set<DustKnowledgeLink> incomingLinks;

	final MindCollMap<DustTokenMember, Object> localData;
	Set<DustToken> tags;

	public DustKnowledgeBlock(DustKnowledgeContext ctx) {
		this.ctx = ctx;
		localData = new MindCollMap<DustTokenMember, Object>(false);
	}

	public DustKnowledgeBlock(DustKnowledgeBlock source) {
		this.ctx = source.ctx;
		localData = new MindCollMap<DustTokenMember, Object>(source.localData);
		
		if ( (null != source.tags ) && !source.tags.isEmpty() ) {
			tags = new HashSet<>(source.tags);
		}
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustTokenMember tMember) {
		try {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

			DustKnowledgeBlock tBlock = null;
			
			if (val instanceof DustToken) {
				DustToken tok = (DustToken) val;
				
				if ( tok.getType() == MiNDTokenType.TAG ) {
					Object ret = val;
					
					switch ( cmd ) {
					case Add:
					case Set:
						if ( null == tags ) {
							tags = new HashSet<>();
						}
						tags.add(tok);
						break;
					case Chk:
						ret = (null != tags) && tags.contains(tok);
						break;
					case Del:
						ret = (null != tags) && tags.remove(tok);
						break;
					case Get:
						ret = ((null != tags) && tags.contains(tok)) ? tok : null;
						break;
					case Use:
						break;
					}
					
					return (RetType) ret;
				}
				
				tBlock = ctx.entities.peek(tok);
			}
			
			Object current = localData.get(tMember);
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
