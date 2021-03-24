package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.GiskardException;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeDataContext
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
			lastOb = lastBlock = null;
			boolean coll = false;

			for (int i = 0; i < plen; ++i) {
				Object o = path[i];
				lastKey = null;

				if ( coll ) {
					lastOb = ((DustRuntimeDataCollection<?>) lastOb).access(MiNDAccessCommand.Get, null, o);
					lastKey = o;
				} else if ( o instanceof DustRuntimeToken ) {
					lastMember = (DustRuntimeToken) o;
					if ( null == lastBlock ) {
						lastOb = rootBlock.localData.get(lastMember);
					} else {
						lastOb = lastBlock.localData.get(lastMember);
					}
				}

				if ( null != lastOb ) {
					if ( lastOb instanceof DustRuntimeDataCollection ) {
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
				if ( val instanceof DustRuntimeToken ) {
					rootBlock.access(MiNDAccessCommand.Set, lastOb, (DustRuntimeToken) val, null);
				}
				return (RetType) lastOb;
			} else {
				return lastBlock.access(cmd, val, lastMember, lastKey);
			}
		}
	}

	DustRuntimeDataContext parentCtx;

	Map<String, DustRuntimeToken> tokens = new TreeMap<>();

	Map<Integer, DustRuntimeDataBlock> entities = new HashMap<>();
	DustRuntimeDataBlock rootBlock;

	public DustRuntimeDataContext(DustRuntimeDataContext parentCtx_, Integer rootHandle) {
		this.parentCtx = parentCtx_;
		rootBlock = (null == parentCtx) ? new DustRuntimeDataBlock(this) : new DustRuntimeDataBlock(this, parentCtx_.rootBlock);
		entities.put(rootHandle, rootBlock);
	}

	public DustRuntimeDataContext() {
		this(null, null);
	}

	DustRuntimeDataBlock createEntity() {
		DustRuntimeDataBlock e = new DustRuntimeDataBlock(this);
		entities.put(e.getHandle(), e);
		return e;
	}

	public DustRuntimeDataBlock getEntity(Integer handle) {
		DustRuntimeDataBlock e = null;

		for (DustRuntimeDataContext ctx = this; (null == e) && (null != ctx); ctx = ctx.parentCtx) {
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
		rootBlock.localData.put(token, te.getHandle());

		te.access(MiNDAccessCommand.Set, token.getName(), (DustRuntimeToken.Member) MTMEMBER_PLAIN_STRING, null);

		DustRuntimeToken p = token.getParent();
		if ( null != p ) {
			te.access(MiNDAccessCommand.Set, p, (DustRuntimeToken.Member) MTMEMBER_CONN_OWNER, null);
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
			te.access(MiNDAccessCommand.Set, t, (DustRuntimeToken.Member) MTMEMBER_ENTITY_PRIMARYTYPE, null);
		}

		te.access(MiNDAccessCommand.Set, token.getId(), (DustRuntimeToken.Member) MTMEMBER_ENTITY_STOREID, null);
		te.access(MiNDAccessCommand.Set, token.getRoot(), (DustRuntimeToken.Member) MTMEMBER_ENTITY_STOREUNIT, null);	
	}

	public Iterator<DustRuntimeToken> iterator() {
		return tokens.values().iterator();
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = val;

		if ( 0 == valPath.length ) {
			switch ( cmd ) {
			case Get:
				if ( val instanceof DustRuntimeToken ) {
					rootBlock.access(MiNDAccessCommand.Set, createEntity().getHandle(), (DustRuntimeToken) val, null);
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
					DustRuntimeDataContext c = this;
					while (null != c.parentCtx) {
						c = c.parentCtx;
					}
					access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR);
					for (DustRuntimeToken t : c) {
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
			DustRuntimeDataBlock eb = pr.lastBlock;
			if ( (cmd != MiNDAccessCommand.Get) && (val instanceof DustRuntimeToken) && (((DustRuntimeToken)val).getType() != MiNDTokenType.TAG) ) {
				val = rootBlock.access(MiNDAccessCommand.Get, null, (DustRuntimeToken) val, null);
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

	public DustRuntimeDataBlock getRootBlock() {
		return rootBlock;
	}
}
