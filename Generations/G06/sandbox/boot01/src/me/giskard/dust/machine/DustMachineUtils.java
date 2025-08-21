package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineUtils implements DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	public static String buildUnitKey(Object... path) {
		return DustUtils.sbAppend(null, "/", true, path).toString();
	}
	
	public static boolean isIdRemote(String kIdea) {
		return kIdea.contains(DUST_SEP_ID);
	}

	public static void storeHandle(DustHandle h, Map units, Map dialogIdeas, Map vocabulary, String lang, String token) {
		if (h == h.unit) {
			units.put(h.id, h);
		}
		storeHandle(h, dialogIdeas);
		if (!DustUtils.isEmpty(token)) {
			storeToken(h, dialogIdeas, vocabulary, lang, token);
		}
	}

	static void storeHandle(DustHandle h, Map dialogIdeas) {
		if (h == h.unit) {
			Map<MindHandle, Object> unitData = new HashMap<>();
			unitData.put(IDEA_HANDLE, h);

			HashMap<String, MindHandle> unitHandles = new HashMap();
			unitData.put(UNIT_HANDLES, unitHandles);
			unitHandles.put("", h);

			Map<MindHandle, Map<MindHandle, Object>> unitIdeas = new HashMap();
			unitIdeas.put(h, unitData);

			dialogIdeas.put(h, unitIdeas);
		} else {
			Map<MindHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(dialogIdeas, h.unit);
			HashMap<String, MindHandle> unitHandles = DustUtils.simpleGet(unitIdeas, h.unit, UNIT_HANDLES);

			Map<MindHandle, Object> dIdea = new HashMap<>();
			dIdea.put(IDEA_HANDLE, h);
			unitIdeas.put(h, dIdea);
			unitHandles.put(h.id, h);
		}
	}

	static void storeToken(DustHandle h, Map dialogIdeas, Map vocabulary, String lang, String token) {
		Map<MindHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(dialogIdeas, h.unit);
		HashMap<String, MindHandle> unitHandles = DustUtils.simpleGet(unitIdeas, h.unit, UNIT_HANDLES);
		String tkey = DUST_SEP_TOKEN + token;

		DustHandle hToken = new DustHandle(h.unit, tkey);
		Map<MindHandle, Object> dToken = new HashMap<>();
		dToken.put(IDEA_HANDLE, hToken);
		dToken.put(MISC_TARGET, h);
		unitIdeas.put(hToken, dToken);
		unitHandles.put(tkey, hToken);

		Map<String, DustHandle> tokenMap = DustUtils.safeGet(vocabulary, lang, SORTEDMAP_CREATOR);
		tokenMap = DustUtils.safeGet(tokenMap, h.unit.id, MAP_CREATOR);
		tokenMap.put(tkey, hToken);
	}
	
	public static MindHandle resolveHandle(Map<String, String> unitRefMap, String unit, String t) {
		String ur = unit;
		String k = t;
		
		int sep = t.indexOf(DUST_SEP_TOKEN);
		if (-1 != sep) {
			ur = unitRefMap.get(t.substring(0, sep));
			k = t.substring(sep);
		} else {
			sep = t.indexOf(DUST_SEP_ID);
			if (-1 != sep) {
				ur = unitRefMap.get(t.substring(0, sep));
				k = t.substring(sep + DUST_SEP_ID.length());
			}
		}

		MindHandle hUnit = Dust.lookup(null, ur);
		return DustUtils.isEmpty(k) ? hUnit : Dust.lookup(hUnit, k);
	}

}
