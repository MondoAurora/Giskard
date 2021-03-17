package me.giskard.dust.runtime.knowledge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.MindCollMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustKnowledgeBlock implements DustKnowledgeConsts {
	DustKnowledgeContext ctx;
	DustKnowledgeBlock orig;

	final MindCollMap<DustTokenMember, Object> localData;
	Set<DustToken> tags;

	public DustKnowledgeBlock(DustKnowledgeContext ctx) {
		this.ctx = ctx;
		localData = new MindCollMap<DustTokenMember, Object>(false);
	}

	public DustKnowledgeBlock(DustKnowledgeBlock source) {
		this.ctx = source.ctx;
		localData = new MindCollMap<DustTokenMember, Object>(source.localData);

		if ( (null != source.tags) && !source.tags.isEmpty() ) {
			tags = new HashSet<>(source.tags);
		}
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustTokenMember tMember, Object key) {
		try {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

			DustKnowledgeBlock tBlock = null;

			if ( val instanceof DustToken ) {
				DustToken tok = (DustToken) val;

				if ( tok.getType() == MiNDTokenType.TAG ) {
					Object ret = val;
					DustToken p = tok.getParent();

					if ( (null == tags) && GiskardUtils.isAccessCreator(cmd) ) {
						tags = new HashSet<>();
					}

					switch ( cmd ) {
					case Add:
						tags.add(tok);
						break;
					case Set:
						if ( !tags.contains(tok) ) {
							if ( null != p ) {
								for (Iterator<DustToken> it = tags.iterator(); it.hasNext();) {
									DustToken t = it.next();
									if ( p == t.getParent() ) {
										it.remove();
									}
								}
							}

							tags.add(tok);
						}
						break;
					case Chk:
						ret = (null != tags) && tags.contains(tok);
						break;
					case Del:
						ret = (null != tags) && tags.remove(tok);
						break;
					case Get:
						ret = tok;

						if ( null != tags ) {
							if ( null != p ) {
								for (DustToken t : tags) {
									if ( p == t.getParent() ) {
										ret = t;
										break;
									}
								}
							}
						}
						break;
					case Use:
						break;
					}

					return (RetType) ret;
				}

				tBlock = ctx.entities.peek(tok);
			}
			
			if ( null == tMember ) {
				return (RetType) this;
			}

			Object current = localData.get(tMember);
			Object data = (null == tBlock) ? val : tBlock;

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
