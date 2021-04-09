package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.GiskardException;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeContext
		implements DustRuntimeConsts, GisCollConsts, DustRuntimeBootConsts, Iterable<DustRuntimeToken> {

	class PathResolver {
		Object[] path;
		int plen;

		DustRuntimeDataBlock lastBlock;
		DustRuntimeToken lastMember;
		Object lastKey;
		Object lastOb;

		PathResolver(Object... path_) {
			this.path = path_;
			plen = path_.length;
			boolean coll = false;

			for (int i = 0; i < plen; ++i) {
				Object o = path[i];
				lastKey = null;

				if ( coll ) {
					lastOb = (null == lastOb) ? null : ((DustRuntimeValueCollection<?>)lastOb).access(NULL_NOTIF, MiNDAccessCommand.Get, null, o);
					lastKey = o;
					coll = false;
				} else if ( o instanceof DustRuntimeToken ) {
					lastMember = (DustRuntimeToken) o;
					coll = MiNDCollType.One != lastMember.getCollType();

					if ( null == lastBlock ) {
						for ( DustRuntimeContext ctx = DustRuntimeContext.this; (null != ctx) && (null == lastOb); ctx = ctx.parentCtx ) {
							lastOb = ctx.rootBlock.localData.get(lastMember);							
						}
					} else {
						lastOb = lastBlock.localData.get(lastMember);
					}
				}

				if ( !coll && (null != lastOb ) && ( lastMember.getValType() == MiNDValType.Link ) ) {
					lastBlock = getEntity((Integer) lastOb);
					lastMember = null;
				}
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType access(DustNotifier notif, MiNDAccessCommand cmd, RetType val) {
			if ( (cmd == MiNDAccessCommand.Get) && (null == lastMember) ) {
				if ( val instanceof DustRuntimeToken ) {
					rootBlock.access(notif, MiNDAccessCommand.Set, lastOb, (MiNDToken) val, null);
				}
				return (RetType) lastOb;
			} else {
				return lastBlock.access(notif, cmd, val, lastMember, lastKey);
			}
		}
	}

	DustRuntimeContext parentCtx;

	Map<String, DustRuntimeToken> tokens = new TreeMap<>();

	Map<Integer, DustRuntimeDataBlock> entities = new HashMap<>();
	DustRuntimeDataBlock rootBlock;

	public DustRuntimeContext(DustRuntimeContext parentCtx_, Integer rootHandle) {
		this.parentCtx = parentCtx_;
//		rootBlock = (null == parentCtx) ? new DustRuntimeDataBlock(this) : new DustRuntimeDataBlock(this, parentCtx_.rootBlock);
		rootBlock = new DustRuntimeDataBlock(this);
		entities.put(rootHandle, rootBlock);
	}

	public DustRuntimeContext() {
		this(null, null);
	}

	DustRuntimeDataBlock createEntity() {
		DustRuntimeDataBlock e = new DustRuntimeDataBlock(this);
		entities.put(e.getHandle(), e);
		return e;
	}

	public DustRuntimeDataBlock getEntity(Integer handle) {
		DustRuntimeDataBlock e = null;

		for (DustRuntimeContext ctx = this; (null == e) && (null != ctx); ctx = ctx.parentCtx) {
			e = ctx.entities.get(handle);
		}

		return e;
	}

	DustRuntimeToken getToken(Object id) {
		DustRuntimeToken ret = tokens.get(id);
		if ( null != parentCtx ) {
			ret = parentCtx.getToken(id);
		}

		return ret;
	}

	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		String id = DustRuntimeToken.buildId(type, name, params);

		DustRuntimeToken ret = getToken(id);

		if ( null == ret ) {
			ret = DustRuntimeToken.createToken(type, name, params);
			registerToken(id, ret);
		}

		return ret;
	}

	public void registerToken(String id, DustRuntimeToken token) {
		tokens.put(id, token);

		DustRuntimeDataBlock te = createEntity();
		token.setEntityHandle(te.getHandle());
		rootBlock.access(MiNDAccessCommand.Add, te.getHandle(), MTMEMBER_CONTEXT_TOKENS, token);

		te.access(MiNDAccessCommand.Set, token.getName(), MTMEMBER_PLAIN_STRING, null);

		DustRuntimeToken p = token.getParent();
		if ( null != p ) {
			te.access(MiNDAccessCommand.Set, p.getEntityHandle(), MTMEMBER_CONN_OWNER, null);
		}

		DustRuntimeToken t = getTypeToken(token);

		if ( null != t ) {
			te.access(MiNDAccessCommand.Set, t, MTMEMBER_ENTITY_PRIMARYTYPE, null);
		}

		te.access(MiNDAccessCommand.Set, token.getId(), MTMEMBER_ENTITY_STOREID, null);
		te.access(MiNDAccessCommand.Set, token.getRoot().getEntityHandle(), MTMEMBER_ENTITY_STOREUNIT, null);
	}

	public DustRuntimeToken getTypeToken(DustRuntimeToken token) {
		MiNDToken t;
		switch ( token.getType() ) {
		case Agent:
			t = MTTYPE_AGENT;
			break;
		case Member:
			t = MTTYPE_MEMBER;
			break;
		case Tag:
			t = MTTYPE_TAG;
			break;
		case Type:
			t = MTTYPE_TYPE;
			break;
		case Unit:
			t = MTTYPE_UNIT;
			break;
		default:
			t = null;
		}
		return (DustRuntimeToken) t;
	}

	public Iterator<DustRuntimeToken> iterator() {
		return tokens.values().iterator();
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = val;

		if ( 0 == valPath.length ) {
			switch ( cmd ) {
			case Get:
				if ( val instanceof DustRuntimeToken ) {
					rootBlock.access(MiNDAccessCommand.Set, createEntity().getHandle(), (MiNDToken) val, null);
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
					DustRuntimeContext c = this;
					while (null != c.parentCtx) {
						c = c.parentCtx;
					}
					access(notif, MiNDAccessCommand.Del, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
					for (DustRuntimeToken t : c) {
						access(notif, MiNDAccessCommand.Add, t.getEntityHandle(), MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
						try {
							ret = a.process(MiNDAgentAction.Process);
						} catch (Exception e) {
							GiskardException.swallow(e);
						}
					}
				} else {
					ret = MiNDResultType.Reject;
				}
				break;
			}
		} else {
			PathResolver pr = new PathResolver(valPath);

			ret = pr.lastOb;
			DustRuntimeDataBlock eb = pr.lastBlock;
			if ( (cmd != MiNDAccessCommand.Get) && (val instanceof DustRuntimeToken)
					&& (((DustRuntimeToken) val).getType() != MiNDTokenType.Tag) ) {
				Object block = rootBlock.access(MiNDAccessCommand.Get, null, (MiNDToken) val, null);
				if ( null == block ) {
					block = rootBlock.access(MiNDAccessCommand.Get, block, MTMEMBER_CONTEXT_TOKENS, val);
				}
				val = block;
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
					ret = MiNDResultType.Reject;
					break;
				}
			} else {
				ret = pr.access(notif, cmd, val);

				if ( (null == ret) && (cmd == MiNDAccessCommand.Get) ) {
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

	public DustRuntimeDataBlock getRootBlock() {
		return rootBlock;
	}
}
