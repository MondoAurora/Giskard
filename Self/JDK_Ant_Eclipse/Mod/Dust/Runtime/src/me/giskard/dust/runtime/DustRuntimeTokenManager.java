package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;

class DustRuntimeTokenManager implements DustRuntimeConsts {
	DustRuntimeDataContext ctx;
	DustRuntimeTokenManager parent;
	
	private Map<String, DustRuntimeToken> tokensById = new TreeMap<>();
	private Map<Integer, DustRuntimeToken> tokensByHandle = new TreeMap<>();

	public DustRuntimeTokenManager(DustRuntimeDataContext ctx_) {
		this.ctx = ctx_;
		parent = (null == ctx.parentCtx) ? null : ctx.parentCtx.getTokenManager();
	}

	DustRuntimeToken getTokenById(Object id) {
		DustRuntimeToken ret = tokensById.get(id);
		if ( null != parent ) {
			ret = parent.getTokenById(id);
		}

		return ret;
	}

	DustRuntimeToken getTokenByHandle(Integer handle) {
		DustRuntimeToken ret = tokensByHandle.get(handle);
		if ( null != parent ) {
			ret = parent.tokensByHandle.get(handle);
		}

		return ret;
	}

	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		String id = DustRuntimeToken.buildId(type, name, params);

		DustRuntimeToken ret = getTokenById(id);

		if ( null == ret ) {
			ret = DustRuntimeToken.createToken(type, name, params);
			registerToken(id, ret);
		}

		return ret;
	}

	public int getTokenHandle(DustRuntimeToken token) {
		int th = token.entityHandle;
		
		if ( HANDLE_NULL == th ) {
			DustRuntimeDataBlock te = ctx.createEntity();
			th = te.getHandle();
			token.entityHandle = th;
			tokensByHandle.put(th, token);
		}
		
		return th;
	}

	public void registerToken(String id, DustRuntimeToken token) {
		tokensById.put(id, token);
		
		int th = token.getEntityHandle(this);
		DustRuntimeDataBlock te = ctx.getEntity(th);
		
		Giskard.log(MiNDEventLevel.Info, "Registering token", id, "->", th);
		
		ctx.rootBlock.access(MiNDAccessCommand.Add, th, MTMEMBER_CONTEXT_TOKENS, token);

		te.access(MiNDAccessCommand.Set, token.getName(), MTMEMBER_PLAIN_STRING, null);

		DustRuntimeToken p = token.getParent();
		if ( null != p ) {
			te.access(MiNDAccessCommand.Set, p.getEntityHandle(this), MTMEMBER_CONN_OWNER, null);
		}

		DustRuntimeToken t = DustRuntimeUtils.getTypeToken(token);

		if ( null != t ) {
			te.access(MiNDAccessCommand.Set, t, MTMEMBER_ENTITY_PRIMARYTYPE, null);
		}

		te.access(MiNDAccessCommand.Set, token.getId(), MTMEMBER_ENTITY_STOREID, null);
		te.access(MiNDAccessCommand.Set, token.getRoot().getEntityHandle(this), MTMEMBER_ENTITY_STOREUNIT, null);
	}

	@Override
	public String toString() {
		return tokensById.toString();
	}
}