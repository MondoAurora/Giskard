package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineUtils implements DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	public static String buildUnitKey(Object... path) {
		return DustUtils.sbAppend(null, "/", true, path).toString();
	}

	public static void storeHandle(DustHandle h, Map units, Map dialogIdeas, Map vocabulary, String lang, String token) {
		storeHandle(h, units, dialogIdeas);
		if (!DustUtils.isEmpty(token)) {
			storeToken(h, dialogIdeas, vocabulary, lang, token);
		}
	}

	private static void storeHandle(DustHandle h, Map units, Map dialogIdeas) {
		if (h == h.unit) {
			units.put(h.id, h);

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

	private static void storeToken(DustHandle h, Map dialogIdeas, Map vocabulary, String lang, String token) {
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
}
