package me.giskard.dust.mod.brain;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import me.giskard.mind.GiskardConsts;
import me.giskard.mind.GiskardException;
import me.giskard.mind.GiskardMind;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings("rawtypes")
public abstract class DustBrainBase implements DustBrainConsts, DustBrainBootstrap, GiskardConsts.MindBrain {

	protected KnowledgeItem brainRoot;

	protected LogicKnowledge logKnowledge;
	protected LogicContext logContext;
	protected LogicDialog logDialog;
	
	protected BootConn bootConn;

	protected abstract void setRoot(File root) throws Exception;

	protected abstract Map loadContext(String token) throws Exception;

	protected void testSomething() throws Exception {
		GiskardMind.dump("Vocabulary", bootConn.bootVoc);
		GiskardMind.dump("Boot context", bootConn.bootCtx);
	}

	public KnowledgeItem createKnowledgeItem() throws Exception {
		KnowledgeItem ki = GiskardUtils.createInstance(CN_KNOWLEDGE_ITEM);
		return ki;
	}

	public KnowledgeItem createKnowledgeItem(KnowledgeItem ctx, BrainHandle handle) throws Exception {
		KnowledgeItem ki = createKnowledgeItem();

		if ( null == handle ) {
			handle = new BrainHandle();
		}

		ki.access(MindAccess.Insert, BootToken.memKnowledgeHandle.getHandle(), MindColl.One, null, handle, null);

		ctx.access(MindAccess.Insert, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, handle, ki, null);

		return ki;
	}

	protected void initBrain() throws Exception {
		brainRoot = createKnowledgeItem();

		logKnowledge = GiskardUtils.createInstance(CN_KNOWLEDGE);
		logContext = GiskardUtils.createInstance(CN_CONTEXT);
		logDialog = GiskardUtils.createInstance(CN_DIALOG);

		for (BootToken bt : BootToken.values()) {
			bt.setHandle(new BrainHandle());
		}

		KnowledgeItem bootCtx = createKnowledgeItem();

		KnowledgeItem langCtx = createKnowledgeItem(bootCtx, null);
		KnowledgeItem bootMed = createKnowledgeItem(bootCtx, null);

		KnowledgeItem bootVoc = createKnowledgeItem(langCtx, null);

		for (BootToken bt : BootToken.values()) {
			String key = bt.name();

			createKnowledgeItem(bootCtx, bt.getHandle());
			KnowledgeItem token = createKnowledgeItem(langCtx, null);
			BrainHandle th = token.access(MindAccess.Peek, BootToken.memKnowledgeHandle.getHandle(), MindColl.One, null, null, null);

			token.access(MindAccess.Set, BootToken.memTextString.getHandle(), MindColl.One, null, key, null);
			bootVoc.access(MindAccess.Insert, BootToken.memVocabularyWords.getHandle(), MindColl.Map, key, th, null);

			bootMed.access(MindAccess.Insert, BootToken.memMediatorRemoteToLocal.getHandle(), MindColl.Map, th, bt.getHandle(), null);
			bootMed.access(MindAccess.Insert, BootToken.memMediatorLocalToRemote.getHandle(), MindColl.Map, bt.getHandle(), th, null);
		}

		BrainHandle lh = langCtx.access(MindAccess.Peek, BootToken.memKnowledgeHandle.getHandle(), MindColl.One, null, null, null);
		bootMed.access(MindAccess.Set, BootToken.memMediatorRemote.getHandle(), MindColl.One, null, lh, null);

		bootConn = new BootConn(bootCtx, langCtx, bootVoc, bootMed);
		setRoot(new File("../../../../Store/Units"));

		Map<String, BrainHandle> bootType = new TreeMap<>();

		bootType.put("typ", BootToken.typType.getHandle());
		bootType.put("mem", BootToken.typMember.getHandle());
		bootType.put("tag", BootToken.typTag.getHandle());

		BootToken tagParent = null;
		Map<BootToken, BootToken> tags = new HashMap<>();

		for (BootToken bt : BootToken.values()) {
			String name = bt.name();
			KnowledgeItem ki = bootConn.getByToken(name);

			String pfx = name.substring(0, 3);
			BrainHandle th = bootType.get(pfx);

			if ( null != th ) {
				ki.access(MindAccess.Set, BootToken.memKnowledgeType.getHandle(), MindColl.One, null, th, null);

				switch ( pfx ) {
				case "mem": {
					tags.clear();
					switch ( bt ) {
					case memContextLocalKnowledge:
						tags.put(BootToken.tagColl, BootToken.tagCollMap);
						break;
					case memKnowledgeOwner:
					case memKnowledgePublisher:
					case memKnowledgeType:
					case memMediatorRemote:
					case memMemberHandleType:
					case memMemberKeyType:
						tags.put(BootToken.tagValtype, BootToken.tagValtypeHandle);
						break;
					case memKnowledgeTags:
						ki.access(MindAccess.Set, BootToken.memMemberKeyType.getHandle(), MindColl.One, null, BootToken.typTag.getHandle(), null);
					case memMediatorLocalToRemote:
					case memMediatorRemoteToLocal:
					case memVocabularyWords:
						tags.put(BootToken.tagColl, BootToken.tagCollMap);
						tags.put(BootToken.tagValtype, BootToken.tagValtypeHandle);
						break;
					default:
						break;

					}

					for (Map.Entry<BootToken, BootToken> tt : tags.entrySet()) {
						ki.access(MindAccess.Insert, BootToken.memKnowledgeTags.getHandle(), MindColl.Map, tt.getKey().getHandle(), tt.getValue().getHandle(), null);
					}
				}
					break;
				case "tag":
					if ( (null == tagParent) || !name.startsWith(tagParent.name()) ) {
						tagParent = bt;
					} else {
						ki.access(MindAccess.Set, BootToken.memKnowledgeOwner.getHandle(), MindColl.One, null, tagParent.getHandle(), null);
					}
					break;
				}
			}
		}

		bootConn.loadCtx("Dust", "BrainJava", 0);

		bootConn.wrapUp();

		testSomething();
	}
	
	class BootConn implements KnowledgeConnector {
		KnowledgeItem bootCtx;

		KnowledgeItem langCtx;
		KnowledgeItem bootVoc;
		KnowledgeItem bootMed;

		Map<String, Map> bootFiles = new HashMap<>();
		Set<String> toBoot = new HashSet<>();
		Set<String> lateItems = new TreeSet<>();

		HandleFormatter hfmt = new HandleFormatter() {
			@Override
			public String formatLabel(BrainHandle h) {
				String ret = null;
				
				BrainHandle hToken = bootMed.access(MindAccess.Peek, BootToken.memMediatorLocalToRemote.getHandle(), MindColl.Map, h, null, null);

				if ( null != hToken ) {
					KnowledgeItem token = langCtx.access(MindAccess.Peek, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, hToken, null, null);
					if ( null != token ) {
						ret = token.access(MindAccess.Peek, BootToken.memTextString.getHandle(), MindColl.One, null, null, null);
					}
				}

				return (null == ret) ?  DEF_FORMATTER.formatLabel(h) : ret;
			}
		};

		public BootConn(KnowledgeItem bootCtx, KnowledgeItem langCtx, KnowledgeItem bootVoc, KnowledgeItem bootMed) {
			this.bootCtx = bootCtx;
			this.langCtx = langCtx;
			this.bootVoc = bootVoc;
			this.bootMed = bootMed;
		}
		
		public void wrapUp() throws Exception {
			for (String memId : lateItems) {
				KnowledgeItem member = getByToken(memId);
				boolean knownMember = member.access(MindAccess.Check, BootToken.memKnowledgeType.getHandle(), MindColl.One, null, BootToken.typMember.getHandle(), null);
				GiskardMind.dump("Late member", memId, knownMember);
			}

			for (Map content : bootFiles.values()) {
				for (Object o : content.entrySet()) {
					Map.Entry e = (Map.Entry) o;
					String id = (String) e.getKey();
					Map data = (Map) e.getValue();

					KnowledgeItem item = null;

					for (Object o1 : data.entrySet()) {
						Map.Entry e1 = (Map.Entry) o1;
						String memId = (String) e1.getKey();

						if ( lateItems.contains(memId) ) {
							if ( null == item ) {
								item = getByToken(id);
							}
							loadMember(item, memId, e1.getValue());
							GiskardMind.dump("Post loading", id, e1, item);
						}
					}
				}
			}
			
			BrainHandle.FORMATTER = hfmt;
		}

		protected BrainHandle getHandleByToken(String tokenStr) throws Exception {
			BrainHandle th = bootVoc.access(MindAccess.Get, BootToken.memVocabularyWords.getHandle(), MindColl.Map, tokenStr, null, this);
			BrainHandle h = bootMed.access(MindAccess.Get, BootToken.memMediatorRemoteToLocal.getHandle(), MindColl.Map, th, null, this);
			return h;
		}

		protected KnowledgeItem getByToken(String tokenStr) throws Exception {
			BrainHandle h = getHandleByToken(tokenStr);

			KnowledgeItem ki = bootCtx.access(MindAccess.Get, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, h, null, this);

			return ki;
		};

		public String getCtxId(String publisher, String ctx, Number commit) {
			return GiskardUtils.sbAppend(null, SEP, true, publisher, ctx, commit).toString();
		}

		public void loadCtx(String publisher, String ctx, Number commit) throws Exception {
			String token = getCtxId(publisher, ctx, commit);

			GiskardMind.dump("Loading context", publisher, ctx, commit, token);

			Map content = bootFiles.get(token);
			if ( null == content ) {
				content = loadContext(token);
				bootFiles.put(token, content);
			}

			Set<KnowledgeItem> refs = new HashSet<>();
			Set<String> comments = new HashSet<>();

			for (Object o : content.entrySet()) {
				Map.Entry e = (Map.Entry) o;
				String id = (String) e.getKey();
				Object ob = e.getValue();

				if ( !(ob instanceof Map) ) {
					GiskardMind.dump("Comment", id, ob);
					comments.add(id);
					continue;
				}
				Map data = (Map) ob;

				KnowledgeItem item = getByToken(id);

				for (Object o1 : data.entrySet()) {
					Map.Entry e1 = (Map.Entry) o1;
					loadMember(item, (String) e1.getKey(), e1.getValue());
				}

				boolean ctxref = item.access(MindAccess.Check, BootToken.memKnowledgeType.getHandle(), MindColl.One, null, BootToken.typContext.getHandle(), this);
				if ( ctxref ) {
					refs.add(item);
				}
			}

			for (String cid : comments) {
				content.remove(cid);
			}

			for (KnowledgeItem item : refs) {
				Object hPub = item.access(MindAccess.Peek, BootToken.memKnowledgePublisher.getHandle(), MindColl.One, null, null, null);

				if ( null != hPub ) {
					KnowledgeItem kPub = bootCtx.access(MindAccess.Peek, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, hPub, null, null);
					String refPub = kPub.access(MindAccess.Peek, BootToken.memTextIdentifier.getHandle(), MindColl.One, null, null, null);

					String refId = item.access(MindAccess.Peek, BootToken.memTextIdentifier.getHandle(), MindColl.One, null, null, null);
					Number refCommit = item.access(MindAccess.Peek, BootToken.memKnowledgeCommit.getHandle(), MindColl.One, null, null, null);

					String ctxId = getCtxId(refPub, refId, refCommit);

					if ( !bootFiles.containsKey(ctxId) ) {
						loadCtx(refPub, refId, refCommit);
					}
				}
			}
		}

		public void loadMember(KnowledgeItem item, String memId, Object value) throws Exception {
			KnowledgeItem member = getByToken(memId);
			boolean knownMember = member.access(MindAccess.Check, BootToken.memKnowledgeType.getHandle(), MindColl.One, null, BootToken.typMember.getHandle(), null);

			if ( knownMember ) {
				boolean valHandle = member.access(MindAccess.Check, BootToken.memKnowledgeTags.getHandle(), MindColl.Map, BootToken.tagValtype.getHandle(), BootToken.tagValtypeHandle.getHandle(), null);
				BrainHandle hMember = member.access(MindAccess.Peek, BootToken.memKnowledgeHandle.getHandle(), MindColl.One, null, null, null);

				if ( value instanceof Map ) {
					boolean keyNotHandle = member.access(MindAccess.Check, BootToken.memMemberKeyType.getHandle(), MindColl.One, null, null, null);

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
			} else {
				lateItems.add(memId);
			}
		}

		@Override
		public void notifyChange(MindAccess cmd, BrainHandle hMember, Object key, Object old, Object curr) {
		}

		@Override
		public Object create(BrainHandle hMember, Object key) {
			Object ret = null;

			try {
				if ( hMember == BootToken.memVocabularyWords.getHandle() ) {
					KnowledgeItem token = createKnowledgeItem(langCtx, null);
					BrainHandle th = token.access(MindAccess.Peek, BootToken.memKnowledgeHandle.getHandle(), MindColl.One, null, null, null);

					token.access(MindAccess.Set, BootToken.memTextString.getHandle(), MindColl.One, null, key, null);
					bootVoc.access(MindAccess.Insert, BootToken.memVocabularyWords.getHandle(), MindColl.Map, key, th, null);

					ret = th;
				} else if ( hMember == BootToken.memMediatorRemoteToLocal.getHandle() ) {
					ret = new BrainHandle();
					createKnowledgeItem(bootCtx, (BrainHandle) ret);
					bootMed.access(MindAccess.Insert, BootToken.memMediatorLocalToRemote.getHandle(), MindColl.Map, ret, key, null);
				}
			} catch (Throwable t) {
				GiskardException.wrap(t);
			}

			return ret;
		}
	};

}