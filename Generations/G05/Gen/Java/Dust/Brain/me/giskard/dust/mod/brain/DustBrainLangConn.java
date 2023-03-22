package me.giskard.dust.mod.brain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.giskard.mind.GiskardException;
import me.giskard.mind.GiskardUtils;

public class DustBrainLangConn implements DustBrainConsts, DustBrainBootstrap, DustBrainConsts.KnowledgeConnector {

	private static class UnitInfo {
		final String token;
		final KnowledgeItem voc;
		final KnowledgeItem unit;

		public UnitInfo(String token, KnowledgeItem voc, KnowledgeItem unit) {
			this.token = token;
			this.voc = voc;
			this.unit = unit;
		}
	}

	final KnowledgeItem brain;
	MindHandle hBrain;

	String lang;
	KnowledgeItem langUnit;
	String defUnit;
	KnowledgeItem unit;

	Map<String, UnitInfo> unitInfos = new HashMap<>();

	public DustBrainLangConn(KnowledgeItem brain, String lang, String defUnit) {
		this.brain = brain;
		this.lang = lang;
		this.defUnit = defUnit;

		hBrain = GiskardUtils.getHandle(BootToken.theBrain);

		unitInfos.clear();
		DustBrainHandle handle = brain.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memBrainLanguages), MindColl.Map, lang, null, this);
		langUnit = DustBrainBootUtils.resolveHandle(brain, handle, null);

		addRefUnit(null, defUnit);
		
		unit = unitInfos.get(null).unit;
	}

	public boolean addRefUnit(String refId, String unitId) {
		if ( !unitInfos.containsKey(refId) ) {
			DustBrainHandle hUnit = brain.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memBrainUnits), MindColl.Map, unitId, null, this);
			KnowledgeItem unit = DustBrainBootUtils.resolveHandle(brain, hUnit, this);

			DustBrainHandle hVoc = langUnit.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memLanguageVocabularies), MindColl.Map, unitId, null, this, hUnit);
			KnowledgeItem voc = DustBrainBootUtils.resolveHandle(langUnit, hVoc, this);

			unitInfos.put(refId, new UnitInfo(unitId, voc, unit));

			return true;
		}

		return false;
	}

	@Override
	public void notifyChange(MindAccess cmd, DustBrainHandle hMember, Object key, Object old, Object curr) {
	}

	@Override
	public Object create(KnowledgeItem unit, DustBrainHandle hMember, Object key, Object... params) {
		Object ret = null;

		BootToken bt = GiskardUtils.getEnum(hMember);
		KnowledgeItem item;
		DustBrainHandle hItem;

		try {
			switch ( bt ) {
			case memUnitLocalKnowledge:
				if ( key == hBrain ) {
					ret = brain;
				} else {
					ret = DustBrainBootUtils.createKnowledgeItem(unit, (DustBrainHandle) key);
				}
				break;
			case memBrainLanguages:
			case memBrainUnits:
			case memLanguageVocabularies:
				ret = hItem = DustBrainBootUtils.createKnowledgeItem(unit);
				item = DustBrainBootUtils.resolveHandle(unit, hItem, null);
				item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memKnowledgeToken), MindColl.One, null, key, null);
				DustBrainBootUtils.optAssignID(unit, item);
				if ( bt == BootToken.memLanguageVocabularies ) {
					item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memMediatorRemote), MindColl.One, null, params[0], null);
				}
				break;
			case memLanguageWords:
				ret = hItem = DustBrainBootUtils.createKnowledgeItem(unit);
				item = DustBrainBootUtils.resolveHandle(unit, hItem, null);
				item.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memTextString), MindColl.One, null, key, null);
				DustBrainBootUtils.optAssignID(unit, item);
				break;
			case memMediatorLocalToRemote:
				ret = params[1];
				if ( null == ret ) {
					ret = hItem = new DustBrainHandle();
					hItem.setUnitToken(params[0]);
				}
				unit.access(MindAccess.Set, GiskardUtils.getHandle(BootToken.memMediatorRemoteToLocal), MindColl.Map, ret, key, null);
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
		KnowledgeItem item = getByToken(id);
		return DustBrainBootUtils.getHandle(item);
	}

	public KnowledgeItem getByToken(String id) {
		KnowledgeItem item;

		if ( GiskardUtils.isEmpty(id) ) {
			item = getByToken(null, null, null);
//		} else if ( unitInfos.containsKey(id) ) {
//			item = getByToken(id, null, null);
		} else {
			int sep = id.indexOf(SEP);

			if ( -1 == sep ) {
				item = getByToken(null, id, null);
			} else {
				item = getByToken(id.substring(0, sep), id.substring(sep + 1), null);
			}
		}

		return item;
	}

	public KnowledgeItem getByToken(String unitRef, String name, MindHandle handle) {
		if ( GiskardUtils.isEmpty(name) ) {
			return unitInfos.get(unitRef).unit;
		}

		UnitInfo ui = unitInfos.get(unitRef);

		MindHandle hWord = langUnit.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memLanguageWords), MindColl.Map, name, null, this);

		MindHandle hItem = ui.voc.access(MindAccess.Get, GiskardUtils.getHandle(BootToken.memMediatorLocalToRemote), MindColl.Map, hWord, null, this, ui.token, handle);

		return DustBrainBootUtils.resolveHandle(ui.unit, hItem, this);
	}

	public KnowledgeItem getUnitByToken(String id) {
		return unitInfos.get(id).unit;
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

	public KnowledgeItem getUnit() {
		return unit;
	}
}