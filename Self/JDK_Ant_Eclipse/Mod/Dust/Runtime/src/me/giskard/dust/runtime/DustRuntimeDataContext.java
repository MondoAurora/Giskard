package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Map;

import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeDataContext
		implements DustRuntimeConsts, GisCollConsts, DustRuntimeBootConsts {

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

				if ( !coll && (lastOb instanceof Integer) && !GiskardUtils.isEqual(KEY_SIZE, lastKey) && (lastMember.getValType() == MiNDValType.Link) ) {
					lastBlock = getEntity((Integer) lastOb);
					lastMember = null;
				}
				
				if ( (0 == i) && (o instanceof Integer) ) {
					lastOb = lastBlock = getEntity((Integer) o);
				} else if ( coll ) {
					lastOb = (null == lastOb) ? null
							: ((DustRuntimeDataCollection<?>) lastOb).access(NULL_NOTIF, MiNDAccessCommand.Get, null, o);
					lastKey = o;
					coll = false;
				} else if ( o instanceof DustRuntimeToken ) {
					lastMember = (DustRuntimeToken) o;
					coll = MiNDCollType.One != lastMember.getCollType();

					if ( null == lastBlock ) {
						for (DustRuntimeDataContext ctx = DustRuntimeDataContext.this; (null != ctx)
								&& (null == lastOb); ctx = ctx.parentCtx) {
							lastOb = ctx.rootBlock.getValue(lastMember);
						}
						if ( null == lastOb ) {
							lastOb = lastMember.entityHandle;
						}
					} else {
						lastOb = lastBlock.getValue(lastMember);
					}
				}

//				if ( !coll && (lastOb instanceof Integer) && !GiskardUtils.isEqual(KEY_SIZE, lastKey) && (lastMember.getValType() == MiNDValType.Link) ) {
//					lastBlock = getEntity((Integer) lastOb);
//					lastMember = null;
//				}
			}
		}

		@SuppressWarnings("unchecked")
		public <RetType> RetType access(DustNotifier notif, MiNDAccessCommand cmd, RetType val) {
			if ( (cmd == MiNDAccessCommand.Get) && (null == lastMember) ) {
//			if ( (cmd == MiNDAccessCommand.Get) && ((null == lastMember) || (lastMember.getCollType() != MiNDCollType.One)) ) {
				if ( val instanceof DustRuntimeToken ) {
					rootBlock.access(notif, MiNDAccessCommand.Set, lastOb, (MiNDToken) val, null);
				}
				return (RetType) lastOb;
			} else {
				return lastBlock.access(notif, cmd, val, lastMember, lastKey);
			}
		}
	}

	DustRuntimeDataContext parentCtx;

	private DustRuntimeTokenManager tokenManager;

	Map<Integer, DustRuntimeDataBlock> entities = new HashMap<>();
	DustRuntimeDataBlock rootBlock;

	public DustRuntimeDataContext(DustRuntimeDataContext parentCtx_, Integer rootHandle) {
		this.parentCtx = parentCtx_;
		tokenManager = new DustRuntimeTokenManager(this);
		rootBlock = new DustRuntimeDataBlock(this);
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

	@SuppressWarnings("unchecked")
	public <RetType> RetType access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = val;

		if ( 0 == valPath.length ) {
			switch ( cmd ) {
			case Get:
				if ( val instanceof DustRuntimeToken ) {
					DustRuntimeDataBlock e = createEntity();
					e.access(MiNDAccessCommand.Set, val, MTMEMBER_ENTITY_PRIMARYTYPE, null);
					ret = e.getHandle();
//					rootBlock.access(MiNDAccessCommand.Set, createEntity().getHandle(), (MiNDToken) val, null);
				}
				break;
			case Add:
			case Set:
			case Chk:
			case Del:
				ret = Boolean.FALSE;
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
					if ( null == ret ) {
						ret = val;
					}
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
		return "Tokens: \n" + tokenManager.toString() + "\n\nEntities: \n" + entities.toString();
	}

	public DustRuntimeDataBlock getRootBlock() {
		return rootBlock;
	}
	
	public DustRuntimeTokenManager getTokenManager() {
		return tokenManager;
	}

	public void commit() {
		if ( null != parentCtx ) {
			// TODO update parent
		}
	}
}
