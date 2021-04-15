package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

class DustRuntimeTokenManager implements DustRuntimeConsts {
	DustRuntimeContext ctx;
	DustRuntimeTokenManager parent;
	
	Map<String, DustRuntimeToken> tokens = new TreeMap<>();

	public DustRuntimeTokenManager(DustRuntimeContext ctx_) {
		this.ctx = ctx_;
		parent = (null == ctx.parentCtx) ? null : ctx.parentCtx.getTokenManager();
	}

	DustRuntimeToken getToken(Object id) {
		DustRuntimeToken ret = tokens.get(id);
		if ( null != parent ) {
			ret = parent.getToken(id);
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

		DustRuntimeDataBlock te = ctx.createEntity();
		token.setEntityHandle(te.getHandle());
		ctx.rootBlock.access(MiNDAccessCommand.Add, te.getHandle(), MTMEMBER_CONTEXT_TOKENS, token);

		te.access(MiNDAccessCommand.Set, token.getName(), MTMEMBER_PLAIN_STRING, null);

		DustRuntimeToken p = token.getParent();
		if ( null != p ) {
			te.access(MiNDAccessCommand.Set, p.getEntityHandle(), MTMEMBER_CONN_OWNER, null);
		}

		DustRuntimeToken t = DustRuntimeUtils.getTypeToken(token);

		if ( null != t ) {
			te.access(MiNDAccessCommand.Set, t, MTMEMBER_ENTITY_PRIMARYTYPE, null);
		}

		te.access(MiNDAccessCommand.Set, token.getId(), MTMEMBER_ENTITY_STOREID, null);
		te.access(MiNDAccessCommand.Set, token.getRoot().getEntityHandle(), MTMEMBER_ENTITY_STOREUNIT, null);
	}

	@Override
	public String toString() {
		return tokens.toString();
	}
}