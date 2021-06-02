package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.tools.GisToolsTokenTranslator;

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
		
		ctx.rootBlock.access(MiNDAccessCommand.Add, th, MTMEM_DIALOG_CONTEXT_TOKENS, token);

		te.access(MiNDAccessCommand.Set, token.getName(), MTMEM_TEXT_PLAINTEXT_STRING, null);

		DustRuntimeToken p = token.getParent();
		if ( null != p ) {
			te.access(MiNDAccessCommand.Set, p.getEntityHandle(this), MTMEM_GENERIC_CONN_OWNER, null);
		}

		DustRuntimeToken t = DustRuntimeUtils.getTypeToken(token);

		if ( null != t ) {
			te.access(MiNDAccessCommand.Set, t, MTMEM_MODEL_ENTITY_PRIMARYTYPE, null);			
		}
		
		optUpdateTokenEntity(token);

		te.access(MiNDAccessCommand.Set, token.getId(), MTMEM_MODEL_ENTITY_IDGLOBAL, null);
		te.access(MiNDAccessCommand.Set, token.getRoot().getEntityHandle(this), MTMEM_MODEL_ENTITY_UNIT, null);
	}

	public void updateTokenEntities() {
		for ( DustRuntimeToken drt : tokensByHandle.values() ) {
			optUpdateTokenEntity(drt);
		}
	}

	public void optUpdateTokenEntity(DustRuntimeToken token) {
		if ( token.getType() == MiNDTokenType.Member ) {
			int th = token.getEntityHandle(this);
			DustRuntimeDataBlock te = ctx.getEntity(th);

			MiNDToken o = GisToolsTokenTranslator.toToken(token.getCollType());
			if ( null != o ) {
				te.access(MiNDAccessCommand.Add, o, MTMEM_MODEL_ENTITY_TAGS, null);
			}
			o = GisToolsTokenTranslator.toToken(token.getValType());
			if ( null != o ) {
				te.access(MiNDAccessCommand.Add, o, MTMEM_MODEL_ENTITY_TAGS, null);
			}			
		}
	}

	@Override
	public String toString() {
		return tokensById.toString();
	}
}