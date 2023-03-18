package me.giskard.dust.mod.brain;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import me.giskard.mind.GiskardConsts;
import me.giskard.mind.GiskardMind;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class DustBrainBase implements DustBrainConsts, DustBrainBootstrap, GiskardConsts.MindBrain {

	protected KnowledgeItem brain;

	protected abstract void setRoot(File root) throws Exception;

	protected abstract Map loadContextContent(String token, String lang) throws Exception;

	Set<String> ctxToRead = new TreeSet<>();
	Map<String, Map> bootContent = new HashMap<>();
	Map<String, DustBrainLangConn> loadConn = new HashMap<>();

	protected void initBrain() throws Exception {
		brain = GiskardUtils.createInstance(CN_KNOWLEDGE_ITEM);

		for (BootToken bt : BootToken.values()) {
			DustBrainHandle bh = new DustBrainHandle();
			bh.setCtxToken(bt.context.getToken());
			GiskardUtils.setEnumHandle(bt, bh);
		}

		DustBrainBootUtils.initKnowledgeItem(brain, brain, GiskardUtils.getHandle(BootToken.theBrain));

		setRoot(new File(PATH_UNITS));

		Map<String, DustBrainHandle> bootType = new TreeMap<>();

		bootType.put("typ", GiskardUtils.getHandle(BootToken.typType));
		bootType.put("mem", GiskardUtils.getHandle(BootToken.typMember));
		bootType.put("tag", GiskardUtils.getHandle(BootToken.typTag));

		BootToken tagParent = null;
		Map<BootToken, BootToken> tags = new HashMap<>();

		DustBrainHandle hMemType = GiskardUtils.getHandle(BootToken.memKnowledgeType);
		DustBrainHandle hMemTags = GiskardUtils.getHandle(BootToken.memKnowledgeTags);
		DustBrainHandle hMemOwner = GiskardUtils.getHandle(BootToken.memKnowledgeOwner);

		DustBrainLangConn langConn = new DustBrainLangConn(brain, LANG_BOOT, BootToken.theBrain.context.getToken());

		for (BootToken bt : BootToken.values()) {
			String ctxToken = bt.context.getToken();
			if ( langConn.addRefCtx(bt.context.id, ctxToken) ) {
				optAddCtx(ctxToken);
			}

			String name = bt.name();
			KnowledgeItem ki = langConn.getByToken(bt.context.id, name, GiskardUtils.getHandle(bt));

			String pfx = name.substring(0, 3);
			DustBrainHandle th = bootType.get(pfx);

			if ( null != th ) {
				ki.access(MindAccess.Set, hMemType, MindColl.One, null, th, null);

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
						ki.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memMemberKeyType), MindColl.One, null, GiskardUtils.getHandle(BootToken.typTag), null);
					case memMediatorLocalToRemote:
					case memMediatorRemoteToLocal:
					case memLanguageWords:
						tags.put(BootToken.tagColl, BootToken.tagCollMap);
						tags.put(BootToken.tagValtype, BootToken.tagValtypeHandle);
						break;
					default:
						break;

					}

					for (Map.Entry<BootToken, BootToken> tt : tags.entrySet()) {
						ki.access(MindAccess.Insert, hMemTags, MindColl.Map, GiskardUtils.getHandle(tt.getKey()), GiskardUtils.getHandle(tt.getValue()), null);
					}
				}
					break;
				case "tag":
					if ( (null == tagParent) || !name.startsWith(tagParent.name()) ) {
						tagParent = bt;
					} else {
						ki.access(MindAccess.Set, hMemOwner, MindColl.One, null, GiskardUtils.getHandle(tagParent), null);
					}
					break;
				}
			}
		}

		DustBrainHandle.FORMATTER = new DustBrainLangFormatter(brain, LANG_BOOT);

		while (!ctxToRead.isEmpty()) {
			Iterator<String> it = ctxToRead.iterator();
			String ctxToken = it.next();
			it.remove();

			loadCtx(ctxToken);
		}

		while (!bootContent.isEmpty()) {
			for (Iterator<String> itContent = bootContent.keySet().iterator(); itContent.hasNext();) {
				if ( loadCtx(itContent.next()) ) {
					itContent.remove();
				}
			}
		}
	}

	public void optAddCtx(String ctxToken) throws Exception {
		if ( !bootContent.containsKey(ctxToken) ) {
			ctxToRead.add(ctxToken);
		}
	}

	public boolean loadCtx(String ctxToken) throws Exception {
		GiskardMind.dump("Loading context", ctxToken);

		DustBrainLangConn langConn;

		Map content = bootContent.get(ctxToken);
		if ( null == content ) {
			langConn = new DustBrainLangConn(brain, LANG_BOOT, ctxToken);
			loadConn.put(ctxToken, langConn);
			
			content = loadContextContent(ctxToken, LANG_BOOT);
			bootContent.put(ctxToken, content);

			Map data = (Map) content.get("");

			Collection<String> refs = (Collection<String>) data.get(BootToken.memKnowledgeRequires.toString());
			if ( null != refs ) {
				for (String refId : refs) {
					data = (Map) content.get(refId);
					Map pub = (Map) content.get(data.get(BootToken.memKnowledgePublisher.toString()));

					String refCtxId = BootContext.getToken(pub.get(BootToken.memKnowledgeIdentifier.toString()), data.get(BootToken.memKnowledgeIdentifier.toString()),
							data.get(BootToken.memPublishedVersionMajor.toString()));

					langConn.addRefCtx(refId, refCtxId);
					optAddCtx(refCtxId);
				}
			}
		} else {
			langConn = loadConn.get(ctxToken);
		}

		for (Iterator itData = content.entrySet().iterator(); itData.hasNext();) {
			boolean processed = true;

			Map.Entry e = (Map.Entry) itData.next();
			String id = (String) e.getKey();
			Object ob = e.getValue();

			if ( !(ob instanceof Map) ) {
				GiskardMind.dump("Comment", id, ob);
			} else {
				KnowledgeItem item = langConn.getByToken(id);

				for (Iterator itValues = ((Map) ob).entrySet().iterator(); itValues.hasNext();) {
					Map.Entry e1 = (Map.Entry) itValues.next();
					if ( langConn.loadMember(item, langConn.getByToken((String) e1.getKey()), e1.getValue()) ) {
						itValues.remove();
					} else {
						processed = false;
					}
				}
			}

			if ( processed ) {
				itData.remove();
			}
		}

		return content.isEmpty();
	}
}
