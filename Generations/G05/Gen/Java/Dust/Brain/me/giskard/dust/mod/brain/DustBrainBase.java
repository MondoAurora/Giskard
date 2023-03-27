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

	protected abstract Map loadUnitContent(String token, String lang) throws Exception;
	protected abstract void saveUnitContent(String token) throws Exception;
	protected abstract void saveLanguage(String lang) throws Exception;

	Set<String> unitToRead = new TreeSet<>();
	Map<String, Map> bootContent = new HashMap<>();
	Map<String, DustBrainLangConn> loadConn = new HashMap<>();

	protected void initBrain() throws Exception {
		brain = GiskardUtils.createInstance(CN_KNOWLEDGE_ITEM);

		for (BootToken bt : BootToken.values()) {
			DustBrainHandle bh = new DustBrainHandle();
			bh.setUnitToken(bt.unit.getToken());
			GiskardUtils.setEnumHandle(bt, bh);
		}
		
		for (MindHandleEnum mhe : MindValType.values()) {
			DustBrainHandle bh = new DustBrainHandle();
			bh.setUnitToken(mhe.getUnitToken());
			GiskardUtils.setEnumHandle((Enum) mhe, bh);
		}

		DustBrainBootUtils.initKnowledgeItem(brain, brain, GiskardUtils.getHandle(BootToken.theBrain));

		setRoot(new File(PATH_UNITS));

		Map<String, BootToken> bootType = new TreeMap<>();

		bootType.put("typ", BootToken.typType);
		bootType.put("mem", BootToken.typMember);
		bootType.put("tag", BootToken.typTag);
		bootType.put("log", BootToken.typLogic);

		BootToken tagParent = null;
		Map<BootToken, BootToken> tags = new HashMap<>();

		BootToken hMemType = BootToken.memKnowledgeType;
		BootToken hMemTags = BootToken.memKnowledgeTags;
		BootToken hMemOwner = BootToken.memKnowledgeOwner;

		DustBrainLangConn langConn = new DustBrainLangConn(brain, LANG_BOOT, BootToken.theBrain.unit.getToken());

		for (BootToken bt : BootToken.values()) {
			String unitToken = bt.unit.getToken();
			if ( langConn.addRefUnit(bt.unit.id, unitToken) ) {
				optAddUnit(unitToken);
			}

			String name = bt.name();
			KnowledgeItem ki = langConn.getByToken(bt.unit.id, name, GiskardUtils.getHandle(bt));

			String pfx = name.substring(0, 3);
			MindHandle th = bootType.get(pfx);

			if ( null != th ) {
				ki.access(MindAccess.Set, hMemType, MindColl.One, null, th, null);

				switch ( pfx ) {
				case "mem": {
					tags.clear();
					switch ( bt ) {
					case memUnitLocalKnowledge:
						tags.put(BootToken.tagColl, BootToken.tagCollMap);
						break;
					case memKnowledgeOwner:
					case memUnitAuthor:
					case memProxyRemote:
					case memKnowledgeType:
					case memMediatorRemote:
					case memMemberHandleType:
					case memKnowledgeUnit:
					case memMemberKeyType:
						tags.put(BootToken.tagValtype, BootToken.tagValtypeHandle);
						break;
					case memKnowledgeTags:
						ki.access(MindAccess.Set, BootToken.memMemberKeyType, MindColl.One, null, BootToken.typTag, null);
					case memKnowledgeRequires:
					case memKnowledgeExtends:
					case memMediatorLocalToRemote:
					case memMediatorRemoteToLocal:
					case memLanguageWords:
					case memBrainLanguages:
					case memLanguageVocabularies:
						tags.put(BootToken.tagColl, BootToken.tagCollMap);
						tags.put(BootToken.tagValtype, BootToken.tagValtypeHandle);
						break;
					default:
						break;

					}

					for (Map.Entry<BootToken, BootToken> tt : tags.entrySet()) {
						ki.access(MindAccess.Insert, hMemTags, MindColl.Map, tt.getKey(), tt.getValue(), null);
					}
				}
					break;
				case "tag":
					if ( (null == tagParent) || !name.startsWith(tagParent.name()) ) {
						tagParent = bt;
					} else {
						ki.access(MindAccess.Set, hMemOwner, MindColl.One, null, tagParent, null);
					}
					break;
				}
			}
		}

		DustBrainHandle.FORMATTER = new DustBrainLangFormatter(brain, LANG_BOOT);

		while (!unitToRead.isEmpty()) {
			Iterator<String> it = unitToRead.iterator();
			String unitToken = it.next();
			it.remove();

			loadUnit(unitToken);
		}

		while (!bootContent.isEmpty()) {
			for (Iterator<String> itContent = bootContent.keySet().iterator(); itContent.hasNext();) {
				String unitToken = itContent.next();
				if ( loadUnit(unitToken) ) {
					itContent.remove();
//					saveUnitContent(unitToken);
				}
			}
		}
		
		saveLanguage(LANG_BOOT);
	}

	public void optAddUnit(String unitToken) throws Exception {
		if ( !bootContent.containsKey(unitToken) ) {
			unitToRead.add(unitToken);
		}
	}

	public boolean loadUnit(String unitToken) throws Exception {
		GiskardMind.dump("Loading unit", unitToken);

		DustBrainLangConn langConn;

		Map content = bootContent.get(unitToken);
		if ( null == content ) {
			langConn = new DustBrainLangConn(brain, LANG_BOOT, unitToken);
			loadConn.put(unitToken, langConn);
			
			content = loadUnitContent(unitToken, LANG_BOOT);
			bootContent.put(unitToken, content);

			Map data = (Map) content.get("");

			Collection<String> refs = (Collection<String>) data.get(BootToken.memKnowledgeRequires.toString());
			if ( null != refs ) {
				for (String refId : refs) {
					data = (Map) content.get(refId);
					Map author = (Map) content.get(data.get(BootToken.memUnitAuthor.toString()));

					if ( null == author ) {
						continue; // this is an author ref
					}
					String refUnitId = ModuleUnit.getToken(author.get(BootToken.memKnowledgeToken.toString()), data.get(BootToken.memKnowledgeToken.toString()),
							data.get(BootToken.memUnitVersionMajor.toString()));

					langConn.addRefUnit(refId, refUnitId);
					optAddUnit(refUnitId);
					
				}
			}
		} else {
			langConn = loadConn.get(unitToken);
		}
		
		KnowledgeItem unit = langConn.getUnit();
		KnowledgeItem item = null;

		for (Iterator itData = content.entrySet().iterator(); itData.hasNext();) {
			boolean processed = true;

			Map.Entry e = (Map.Entry) itData.next();
			String id = (String) e.getKey();
			Object ob = e.getValue();

			if ( !(ob instanceof Map) ) {
				GiskardMind.dump("Comment", id, ob);
			} else {
				item = langConn.getByToken(id);

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
				if ( -1 == id.indexOf(SEP)) {
					DustBrainBootUtils.optAssignID(unit, item);
				}
				
				KnowledgeItem refUnit = langConn.getUnitByToken(id);
				if ( null != refUnit ) {
					MindHandle hU = DustBrainBootUtils.getHandle(refUnit);
					item.access(MindAccess.Set, BootToken.memProxyRemote, MindColl.One, null, hU, null);
				}
				
				itData.remove();
			}
		}
		
		if ( content.isEmpty() ) {
			GiskardMind.dump("Load complete", unitToken);
		} else {
			GiskardMind.dump("Load pending", unitToken, content);
		}

		return content.isEmpty();
	}
}
