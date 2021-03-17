package me.giskard.dust.runtime.knowledge;

import java.util.HashSet;
import java.util.Set;

import me.giskard.coll.MindCollConsts;
import me.giskard.coll.MindCollFactory;
import me.giskard.coll.MindCollMap;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeMeta;

public class DustKnowledgeContext
		implements DustKnowledgeConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustContext {

	class PathResolver {
		Object[] path;
		int plen;

		Object lastOb;
		DustKnowledgeBlock lastBlock;
		int lastBlockIdx;

		PathResolver(Object... path_) {
			this.path = path_;
			plen = path.length;

			for (int i = 0; i < plen; ++i) {
				Object o = path[i];

				if ( null == lastOb ) {
					lastOb = entities.peek((MiNDToken) o);
				} else if ( o instanceof DustTokenMember ) {
					lastOb = lastBlock.localData.get((DustTokenMember) o);
				} else if ( lastOb instanceof DustKnowledgeCollection.ValArr ) {
					lastOb = ((DustKnowledgeCollection<?>) lastOb).access(MiNDAccessCommand.Get, null, (Integer) o);
				}

				if ( lastOb instanceof DustKnowledgeLink ) {
					lastBlock = ((DustKnowledgeLink) lastOb).to;
					lastOb = lastBlock;
					lastBlockIdx = i;
				} else if ( lastOb instanceof DustKnowledgeBlock ) {
					lastBlock = (DustKnowledgeBlock) lastOb;
					lastBlockIdx = i;
				}
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType access(MiNDAccessCommand cmd, RetType val) {
			if ( (plen == lastBlockIdx + 1) && (cmd == MiNDAccessCommand.Get) && !((val instanceof DustToken) && (((DustToken)val).getType() == MiNDTokenType.TAG)) ) {
				entities.put((MiNDToken) val, lastBlock);
				return (RetType) lastBlock;
//				return val;
			} else {
				Object key = (lastBlockIdx < (plen - 2)) ? path[plen - 1] : null;
				DustTokenMember tMember = (DustTokenMember) ((null != key) ? path[plen - 2]
						: (lastBlockIdx < (plen - 1)) ? path[plen - 1] : null);
				return lastBlock.access(cmd, val, tMember, key);
			}
		}
	}

	DustKnowledgeContext parentCtx;

	MindCollMap<Object, DustToken> tokens = new MindCollMap<>(true);

	MindCollFactory<MiNDToken, DustKnowledgeBlock> entities = new MindCollFactory<>(false,
			new MiNDCreator<MiNDToken, DustKnowledgeBlock>() {
				@Override
				public DustKnowledgeBlock create(MiNDToken key) {
					return new DustKnowledgeBlock(DustKnowledgeContext.this);
				}
			});

	Set<DustKnowledgeLink> allLinks = new HashSet<>();

	public DustKnowledgeContext(DustKnowledgeContext parentCtx_) {
		this.parentCtx = parentCtx_;
	}

	public DustKnowledgeContext() {
		this(null);
	}

	DustToken getToken(Object id) {
		DustToken ret = tokens.get(id);
		if ( null != parentCtx ) {
			ret = parentCtx.getToken(id);
		}

		return ret;
	}

	@Override
	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		DustToken parent = ((MiNDTokenType.UNIT == type) || (MiNDTokenType.LOCAL == type)) ? null : (DustToken) params[0];
		String id = DustToken.buildId(name, parent);

		DustToken ret = tokens.get(id);

		if ( null == ret ) {
			ret = getToken(id);

			if ( null == ret ) {
				ret = DustToken.createToken(type, name, params);
				ret.setEntity(entities.get(ret));
			}

			tokens.put(id, ret);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = val;

		if ( 0 == valPath.length ) {
			switch ( cmd ) {
			case Get:
				if ( val instanceof MiNDToken ) {
					entities.put((MiNDToken) val, new DustKnowledgeBlock(this));
				}
				break;
			case Add:
			case Set:
			case Chk:
			case Del:
				ret = Boolean.FALSE;
				break;
			case Use:
				ret = MiNDResultType.REJECT;
				break;
			}
		} else {
			PathResolver pr = new PathResolver(valPath);

			ret = pr.lastOb;
			DustKnowledgeBlock eb = pr.lastBlock;

			if ( null == eb ) {
				switch ( cmd ) {
				case Add:
				case Set:
				case Chk:
				case Del:
					ret = Boolean.FALSE;
					break;
				case Get:
					// Get can be used to select the target entity. If the resolver did not find it,
					// return null!
					ret = (val instanceof MiNDToken) ? null : val;
					break;
				case Use:
					ret = MiNDResultType.REJECT;
					break;
				}
			} else {
				if ( 0 < valPath.length ) {
					ret = pr.access(cmd, val);
				} else {
					switch ( cmd ) {
					case Add:
					case Set:
					case Get:
						ret = eb.access(cmd, val, null, null);
						break;
					case Chk:
						ret = Boolean.TRUE;
						break;
					case Del:
						ret = Boolean.FALSE;
						break;
					case Use:
						ret = MiNDResultType.REJECT;
						break;
					}
				}
			}
		}

		return (RetType) ret;
	}

	@Override
	public void put(MiNDToken token, Object block) {
		entities.put(token, (DustKnowledgeBlock) block);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType peek(MiNDToken token) {
		return (RetType) entities.peek(token);
	}

	DustKnowledgeLink setLink(DustKnowledgeBlock from, DustTokenMember def, DustKnowledgeBlock to) {
		DustKnowledgeLink link = new DustKnowledgeLink(from, def, to);
		allLinks.add(link);
		return link;
	}

	void delLink(DustKnowledgeLink link) {
		allLinks.remove(link);
		link.from.access(MiNDAccessCommand.Del, link, link.def, null);
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entities.toString();
	}
}
