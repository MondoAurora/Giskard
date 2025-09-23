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

	public static Map<MindHandle, Object> storeHandle(DustHandle h, Map units, Map dialogIdeas) {
		Map<MindHandle, Object> ret = storeHandle(h, dialogIdeas);
		if (h == h.unit) {
			units.put(h.id, h);
		}

		return ret;
	}

	static Map<MindHandle, Object> storeHandle(DustHandle h, Map machineIdea) {
		DustHandle hUnit = h.isUnit() ? h : h.unit;
		
		Map<MindHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(machineIdea, UNIT_IDEAS);
		HashMap<String, MindHandle> unitHandles = DustUtils.simpleGet(machineIdea, UNIT_HANDLES);

		if (!h.isUnit()) {
			Object unit = DustUtils.simpleGet(unitIdeas, hUnit);
			unitIdeas = DustUtils.simpleGet(unit, UNIT_IDEAS);
			unitHandles = DustUtils.simpleGet(unit, UNIT_HANDLES);
		}

		Map<MindHandle, Object> data = new DustIdea(h);
		unitIdeas.put(h, data);
		unitHandles.put(h.id, h);

		return data;
	}

	static void storeToken(DustHandle h, Map dialogIdeas, Map vocabulary, String lang, String token) {
		String tkey = DUST_SEP_TOKEN + h.id;
		DustHandle hToken = new DustHandle(h.unit, tkey);

		Map<MindHandle, Object> dToken = storeHandle(hToken, dialogIdeas);

		dToken.put(MISC_TARGET, h);
		dToken.put(MISC_EXTID, lang + DUST_SEP_TOKEN + token);

		Map tokenMap = DustUtils.safeGet(vocabulary, lang, MAP_CREATOR);
		tokenMap = DustUtils.safeGet(tokenMap, h.unit, MAP_CREATOR);
		tokenMap.put(token, hToken);
		tokenMap.put(hToken, token);
		tokenMap.put(h, token);
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
