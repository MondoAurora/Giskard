package me.giskard.dust.runtime.knowledge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.GiskardException;
import me.giskard.coll.MindCollConsts;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeMeta;

public class DustKnowledgeContext
		implements DustKnowledgeConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustContext {

	class PathResolver {
		Object[] path;
		int plen;

		DustKnowledgeBlock lastBlock;
		DustToken lastMember;
		Object lastKey;
		Object lastOb;

		PathResolver(Object... path_) {
			this.path = path_;
			plen = path_.length;
			lastOb = lastBlock = null;
			boolean coll = false;

			for (int i = 0; i < plen; ++i) {
				Object o = path[i];
				lastKey = null;

				if ( coll ) {
					lastOb = ((DustKnowledgeCollection<?>) lastOb).access(MiNDAccessCommand.Get, null, o);
					lastKey = o;
				} else if ( o instanceof DustToken ) {
					lastMember = (DustToken) o;
					if ( null == lastBlock ) {
						lastOb = rootBlock.localData.get(lastMember);
					} else {
						lastOb = lastBlock.localData.get(lastMember);
					}
				}

				if ( null != lastOb ) {
					if ( lastOb instanceof DustKnowledgeCollection ) {
						coll = true;
					} else if ( lastMember.getValType() == MiNDValType.Link ) {
						lastBlock = getEntity((Integer) lastOb);
						lastMember = null;
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType access(MiNDAccessCommand cmd, RetType val) {
			if ( (cmd == MiNDAccessCommand.Get) && (null == lastMember) ) {
				if ( val instanceof DustToken ) {
					rootBlock.access(MiNDAccessCommand.Set, lastOb, (DustToken) val, null);
				}
				return (RetType) lastOb;
			} else {
				return lastBlock.access(cmd, val, lastMember, lastKey);
			}
		}
	}

	DustKnowledgeContext parentCtx;

	Map<String, DustToken> tokens = new TreeMap<>();

	Map<Integer, DustKnowledgeBlock> entities = new HashMap<>();
	DustKnowledgeBlock rootBlock;

	public DustKnowledgeContext(DustKnowledgeContext parentCtx_, Integer rootHandle) {
		this.parentCtx = parentCtx_;
		rootBlock = (null == parentCtx) ? new DustKnowledgeBlock(this) : new DustKnowledgeBlock(this, parentCtx_.rootBlock);
		entities.put(rootHandle, rootBlock);
	}

	public DustKnowledgeContext() {
		this(null, null);
	}

	DustKnowledgeBlock createEntity() {
		DustKnowledgeBlock e = new DustKnowledgeBlock(this);
		entities.put(e.getHandle(), e);
		return e;
	}

	public DustKnowledgeBlock getEntity(Integer handle) {
		DustKnowledgeBlock e = null;

		for (DustKnowledgeContext ctx = this; (null == e) && (null != ctx); ctx = ctx.parentCtx) {
			e = ctx.entities.get(handle);
		}

		return e;
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

		DustToken ret = getToken(id);

		if ( null == ret ) {
			ret = DustToken.createToken(type, name, params);
			DustKnowledgeBlock te = createEntity();
			ret.setEntityHandle(te.getHandle());
			rootBlock.access(MiNDAccessCommand.Set, te.getHandle(), ret, null);
			DustKnowledgeUtils.optSyncToken(this, ret);

			tokens.put(id, ret);
		}

		return ret;
	}

	@Override
	public Iterator<DustToken> iterator() {
		return tokens.values().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = val;

		if ( 0 == valPath.length ) {
			switch ( cmd ) {
			case Get:
				if ( val instanceof DustToken ) {
					rootBlock.access(MiNDAccessCommand.Set, createEntity().getHandle(), (DustToken) val, null);
				}
				break;
			case Add:
			case Set:
			case Chk:
			case Del:
				ret = Boolean.FALSE;
				break;
			case Use:
				if ( val instanceof MiNDAgent ) {
					MiNDAgent a = (MiNDAgent) val;
					DustKnowledgeContext c = this;
					while (null != c.parentCtx) {
						c = c.parentCtx;
					}
					access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
					for (DustToken t : c) {
						access(MiNDAccessCommand.Add, t.getEntityHandle(), MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
						try {
							ret = a.process(MiNDAgentAction.Process);
						} catch (Exception e) {
							GiskardException.swallow(e);
						}
					}
				} else {
					ret = MiNDResultType.REJECT;
				}
				break;
			}
		} else {
			PathResolver pr = new PathResolver(valPath);

			ret = pr.lastOb;
			DustKnowledgeBlock eb = pr.lastBlock;
			if ( (cmd != MiNDAccessCommand.Get) && (val instanceof DustToken) && (((DustToken)val).getType() != MiNDTokenType.TAG) ) {
				val = rootBlock.access(MiNDAccessCommand.Get, null, (DustToken) val, null);
			}

			if ( null == eb ) {
				switch ( cmd ) {
				case Add:
				case Set:
				case Chk:
				case Del:
					ret = Boolean.FALSE;
					break;
				case Get:
					ret = val;
					break;
				case Use:
					ret = MiNDResultType.REJECT;
					break;
				}
			} else {
				ret = pr.access(cmd, val);
				
				if ( (null == ret ) && (cmd == MiNDAccessCommand.Get) ) {
					ret = val;
				}
			}
		}

		return (RetType) ret;
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entities.toString();
	}

	public DustKnowledgeBlock getRootBlock() {
		return rootBlock;
	}
}
