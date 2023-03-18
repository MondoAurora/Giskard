package me.giskard.dust.mod.brain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.giskard.mind.GiskardException;
import me.giskard.mind.GiskardUtils;

public class DustBrainLangConn implements DustBrainConsts, DustBrainBootstrap, DustBrainConsts.KnowledgeConnector {
	
	private static class CtxInfo {
		final String token;
		final KnowledgeItem voc;
		final KnowledgeItem ctx;
		
		public CtxInfo(String token, KnowledgeItem voc, KnowledgeItem ctx) {
			this.token = token;
			this.voc = voc;
			this.ctx = ctx;
		}
	}

	final KnowledgeItem brain;

	String lang;
	KnowledgeItem langCtx;
	String defContext;

	Map<String, CtxInfo> ctxInfos = new HashMap<>();

	public DustBrainLangConn(KnowledgeItem brain, String lang, String defContext) {
		this.brain = brain;
		this.lang = lang;
		this.defContext = defContext;

		ctxInfos.clear();
		DustBrainHandle handle = brain.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memBrainLanguages), MindColl.Map, LANG_BOOT, null, this);
		langCtx = DustBrainBootUtils.resolveHandle(brain, handle, null);
		
		addRefCtx(null, defContext);
	}

	public boolean addRefCtx(String refId, String ctxId) {
		if ( !ctxInfos.containsKey(refId) ) {
			DustBrainHandle hCtx = brain.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memBrainContexts), MindColl.Map, ctxId, null, this);
			KnowledgeItem ctx = DustBrainBootUtils.resolveHandle(brain, hCtx, this);
			
			DustBrainHandle hVoc = langCtx.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memLanguageVocabularies), MindColl.Map, ctxId, null, this, hCtx);
			KnowledgeItem voc = DustBrainBootUtils.resolveHandle(langCtx, hVoc, this);

			ctxInfos.put(refId, new CtxInfo(ctxId, voc, ctx));
			
			return true;
		}
		
		return false;
	}

	@Override
	public void notifyChange(MindAccess cmd, DustBrainHandle hMember, Object key, Object old, Object curr) {
	}

	@Override
	public Object create(KnowledgeItem ctx, DustBrainHandle hMember, Object key, Object... params) {
		Object ret = null;

		BootToken bt = GiskardUtils.getEnum(hMember);
		KnowledgeItem item;
		DustBrainHandle hItem;

		try {
			switch ( bt ) {
			case memContextLocalKnowledge:
				ret = DustBrainBootUtils.createKnowledgeItem(ctx, (DustBrainHandle) key);
				break;
			case memBrainLanguages:
			case memBrainContexts:
			case memLanguageVocabularies:
				ret = hItem = DustBrainBootUtils.createKnowledgeItem(ctx);
				item = DustBrainBootUtils.resolveHandle(ctx, hItem, null);
				item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memKnowledgeIdentifier), MindColl.One, null, key, null);
				
				if ( bt == BootToken.memLanguageVocabularies ) {
					item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memMediatorRemote), MindColl.One, null, params[0], null);					
				}
				break;
			case memLanguageWords:
				ret = hItem = DustBrainBootUtils.createKnowledgeItem(ctx);
				item = DustBrainBootUtils.resolveHandle(ctx, hItem, null);
				item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memTextString), MindColl.One, null, key, null);
				break;
			case memMediatorLocalToRemote:
				ret = params[1];
				if ( null == ret ) {
					ret = hItem = new DustBrainHandle();
					hItem.setCtxToken(params[0]);
				}
				ctx.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memMediatorRemoteToLocal), MindColl.Map, ret, key, null);
				break;
			default:
				break;

			}
		} catch (Throwable t) {
			GiskardException.wrap(t);
		}

		return ret;
	}

	public MindHandle getHandleByToken(String id) {
		KnowledgeItem item = getByToken( id);
		return DustBrainBootUtils.getHandle(item);
	}

	public KnowledgeItem getByToken(String id) {
		KnowledgeItem item;
		
		if ( GiskardUtils.isEmpty(id)) {
			item = getByToken(null, null, null);
		} else if ( ctxInfos.containsKey(id)) {
			item = getByToken(id, null, null);
		} else {
			int sep = id.indexOf('.');
			
			if ( -1 == sep ) {
				item = getByToken(null, id, null);
			} else {
				item = getByToken(id.substring(0, sep), id.substring(sep + 1), null);					
			}
		}

		return item;
	}

	public KnowledgeItem getByToken(String ctxRef, String name, MindHandle handle) {
		if ( GiskardUtils.isEmpty(name) ) {
			return ctxInfos.get(ctxRef).ctx;
		}
		
		CtxInfo ci = ctxInfos.get(ctxRef);
		
		MindHandle hWord = langCtx.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memLanguageWords), MindColl.Map, name, null, this);

		MindHandle hItem = ci.voc.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memMediatorLocalToRemote), MindColl.Map, hWord, null, this, ci.token, handle);
		
		return DustBrainBootUtils.resolveHandle(ci.ctx, hItem, this);
	}

	public KnowledgeItem getCtxByToken(String id) {
		return ctxInfos.get(id).ctx;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean loadMember(KnowledgeItem item, KnowledgeItem member, Object value) throws Exception {
		boolean knownMember = member.access(MindAccess.Check, GiskardUtils.getHandle(BootToken.memKnowledgeType), MindColl.One, null, GiskardUtils.getHandle(BootToken.typMember), null);

		if ( knownMember ) {
			boolean valHandle = member.access(MindAccess.Check, GiskardUtils.getHandle(BootToken.memKnowledgeTags), MindColl.Map, GiskardUtils.getHandle(BootToken.tagValtype),
					GiskardUtils.getHandle(BootToken.tagValtypeHandle), null);
			DustBrainHandle hMember = member.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeHandle), MindColl.One, null, null, null);

			if ( value instanceof Map ) {
				boolean keyNotHandle = member.access(MindAccess.Check, GiskardUtils.getHandle(BootToken.memMemberKeyType), MindColl.One, null, null, null);

				for (Object v : ((Map) value).entrySet()) {
					Map.Entry ev = (Map.Entry) v;
					Object val = valHandle ? getHandleByToken((String) ev.getValue()) : ev.getValue();
					Object key = keyNotHandle ? ev.getKey() : getHandleByToken((String) ev.getKey());
					item.access(MindAccess.Insert, hMember, MindColl.Map, key, val, null);
				}
			} else if ( value instanceof Collection ) {
				for (Object v : (Collection) value) {
					if ( valHandle ) {
						v = getHandleByToken((String) v);
					}
					item.access(MindAccess.Insert, hMember, MindColl.Arr, KEY_ADD, v, null);
				}
			} else {
				Object v = valHandle ? getHandleByToken((String) value) : value;
				item.access(MindAccess.Set, hMember, MindColl.One, null, v, null);
			}
		}
		
		return knownMember;
	}	
}