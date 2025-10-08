package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineUtils implements DustMachineConstsInt, DustMachineBootConsts, DustUtilsConsts {

	public static String buildUnitKey(Object author, Object unitId, Object commitId) {
		return author + "/" + unitId + "." + commitId;
	}

	public static boolean isIdRemote(String kIdea) {
		return kIdea.contains(DUST_SEP_ID);
	}

	public static String nextId(Map ideaData) {
		return nextId(ideaData, MIND_UNIT_NEXT_ID);
	}

	public static String nextId(Map ideaData, MindHandle key) {
		Long l = (Long) ideaData.getOrDefault(key, 0L);
		ideaData.put(key, l + 1);
		return l.toString();
	}

	public static boolean isCreator(MindAccess acc) {
		switch (acc) {
		case Get:
		case Insert:
		case Set:
			return true;
		default:
			return false;
		}
	}

	public static boolean isContinue(MindResult res) {
		if (null == res) {
			return false;
		}

		switch (res) {
		case Read:
		case ReadAccept:
			return true;
		default:
			return false;
		}
	}

	public static MindCollType getCollType(Object coll) {
		MindCollType ret = (null == coll) ? MindCollType.One
				: (coll instanceof ArrayList) ? MindCollType.Arr
						: (coll instanceof Map) ? MindCollType.Map : (coll instanceof Set) ? MindCollType.Set : MindCollType.One;

		return ret;
	}

	static Map<MindHandle, Object> safeGetIdea(DustHandle h, Map machineIdea) {
		DustHandle hUnit = h.isUnit() ? h : h.unit;

		Map<MindHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(machineIdea, MIND_UNIT_IDEAS);
		HashMap<String, MindHandle> unitHandles = DustUtils.simpleGet(machineIdea, MIND_UNIT_HANDLES);

		if (!h.isUnit()) {
			Object unit = DustUtils.simpleGet(unitIdeas, hUnit);
			unitIdeas = DustUtils.simpleGet(unit, MIND_UNIT_IDEAS);
			unitHandles = DustUtils.simpleGet(unit, MIND_UNIT_HANDLES);
		} else {
			((Map) DustUtils.simpleGet(machineIdea, DUST_MACHINE_UNITS)).put(h.getId(), h);
		}

		Map<MindHandle, Object> data = unitIdeas.get(h);
		if (null == data) {
			data = new DustIdea(h);
			unitIdeas.put(h, data);
			unitHandles.put(h.id, h);
//		} else {
//			Dust.log(null, "hmm");
		}

		return data;
	}

	public static String getTokenStr(MindHandle h, String lang) {
		String resUnit = ((DustHandle) h).getResUnitId(lang);
		MindHandle hResUnit = Dust.lookup(null, resUnit);
		String id;

		if (((DustHandle) h).isUnit()) {
			id = h.toString();
		} else {
			id = h.toString();
		}
		DustHandle hResToken = (DustHandle) Dust.lookup(hResUnit, id);

		String ret = Dust.access(MIND_TAG_ACCESS_PEEK, "???", hResToken, MISC_GEN_PAYLOAD);

		return ret;
	}

	public static void storeToken(DustHandle h, Map dialogIdeas, String lang, String token) {
		String tkey = DUST_SEP_TOKEN + h.id;

		DustHandle hUnit = h.getUnit();
		DustHandle hToken = (DustHandle) Dust.lookup(hUnit, tkey);

		Map<MindHandle, Object> dToken = safeGetIdea(hToken, dialogIdeas);
		dToken.put(MISC_GEN_TARGET, h);
		dToken.put(MIND_IDEA_PRIMARYASPECT, TEXT_TAG_TOKEN);

		String resUnit = hUnit.getResUnitId(lang);
		DustHandle hResUnit = (DustHandle) Dust.lookup(null, resUnit);
		DustHandle hResToken = (DustHandle) Dust.lookup(hResUnit, token);

		Map<MindHandle, Object> dRes = safeGetIdea(hResToken, dialogIdeas);
		dRes.put(MISC_GEN_TARGET, h);
		dRes.put(MISC_GEN_PAYLOAD, token);

		Map tokenMap = safeGetIdea(hResUnit, dialogIdeas);
		tokenMap = DustUtils.safeGet(tokenMap, MIND_UNIT_HANDLES, MAP_CREATOR);
		tokenMap.put(h.toString(), hResToken);
		tokenMap.put(hToken.toString(), hResToken);
	}

	public static MindHandle resolveHandle(Map<String, String> unitRefMap, String unit, String t) {
		String ur = unit;
		String k = DustUtils.getPrefix(t, DUST_SEP_TOKEN);

		int sep = t.indexOf(DUST_SEP_ID);
		if (-1 != sep) {
			ur = unitRefMap.get(t.substring(0, sep));
			k = t.substring(sep + DUST_SEP_ID.length());
		}

		MindHandle hUnit = Dust.lookup(null, ur);
		return DustUtils.isEmpty(k) ? hUnit : Dust.lookup(hUnit, k);
	}
}
