package me.giskard.dust.boot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustBootTest02 implements DustBootConsts, DustUtilsConsts {

	static Map<MindHandle, Object> DIALOG_DATA = new HashMap<>();

	public static void test01() throws Exception {
		DIALOG_DATA.put(DIALOG_UNITS, new TreeMap());
		DIALOG_DATA.put(DIALOG_IDEAS, new HashMap());
		DIALOG_DATA.put(DIALOG_VOCABULARY, new TreeMap());

		Map<String, ArrayList<Object>> queue = new TreeMap<>();
		Map<String, Object> app = new TreeMap<>();
		initUnitData(app, "giskard.me", "test01");

		optRegisterUnit(queue, app);

		loadQueue(queue);
	}

	public static String buildKey(Map<String, Object> unitData) {
		return DustUtils.sbAppend(null, "/", true, "units", unitData.get(LOCAL_UNIT_AUTHOR), unitData.get(LOCAL_UNIT_ID)).toString();
	}

	public static Map<String, Object> initUnitData(Map<String, Object> ud, String author, String id) throws Exception {
		ud.put(LOCAL_UNIT_AUTHOR, author);
		ud.put(LOCAL_UNIT_ID, id);
		return ud;
	}

	private static String optRegisterUnit(Map<String, ArrayList<Object>> queue, Map<String, Object> inputData) throws Exception {
		String key = buildKey(inputData);

		Map units = DustUtils.simpleGet(DIALOG_DATA, DIALOG_UNITS);
		Map dialogIdeas = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS);
		Object u = DustUtils.simpleGet(units, key);

		if ((null == u) && !queue.containsKey(key)) {
			Dust.log(null, "Queueing", key);
			File f = new File(key + DUST_EXT_JSON);
			ArrayList<Object> j = DustUtilsJson.readJson(f);
			queue.put(key, j);

			String lang = DustUtils.simpleGet(j, 0, "lang");
			lang = DustUtils.getPostfix(lang, DUST_REF_PREFIX);
			Map<String, DustHandle> tokenMap = DustUtils.safeGet(DIALOG_DATA.get(DIALOG_VOCABULARY), lang, SORTEDMAP_CREATOR);
			tokenMap = DustUtils.safeGet(tokenMap, key, MAP_CREATOR);

			DustHandle unitHandle = new DustHandle(key);
			units.put(key, unitHandle);
			
			Map<MindHandle, Object> unitData = new HashMap<>();
			unitData.put(IDEA_HANDLE, unitHandle);
			
			HashMap<String, MindHandle> unitHandles = new HashMap();
			unitData.put(UNIT_HANDLES, unitHandles);
			unitHandles.put("", unitHandle);

			Map<MindHandle, Map<MindHandle, Object>> unitIdeas = new HashMap();
			dialogIdeas.put(unitHandle, unitIdeas);
			unitIdeas.put(unitHandle, unitData);

			Map<String, String> refs = DustUtils.safeGet(unitData, MISC_CONN_REQUIRES, MAP_CREATOR);
			refs.put(DustUtils.simpleGet(j, 1, "mind#ideaToken"), key);

			Map<String, Object> content = DustUtils.simpleGet(j, 2);
			for (Map.Entry<String, Object> ec : content.entrySet()) {
				String kIdea = ec.getKey();

				if (kIdea.contains(DUST_SEP_ID)) {
					// remote idea
					continue;
				}

				DustHandle hIdea = new DustHandle(unitHandle, kIdea);
				Map<MindHandle, Object> dIdea = new HashMap<>();
				dIdea.put(IDEA_HANDLE, hIdea);
				unitIdeas.put(hIdea, dIdea);
				unitHandles.put(kIdea, hIdea);

				String token = DustUtils.simpleGet(ec.getValue(), "mind#ideaToken");
				if (!DustUtils.isEmpty(token)) {
					String tkey = DUST_SEP_TOKEN + token;
					DustHandle hToken = new DustHandle(unitHandle, tkey);
					Map<MindHandle, Object> dToken = new HashMap<>();
					dToken.put(IDEA_HANDLE, hToken);
					dToken.put(MISC_TARGET, hIdea);
					unitIdeas.put(hToken, dToken);
					unitHandles.put(tkey, hToken);

					tokenMap.put(tkey, hToken);
				}
			}

			Map<String, String> unitRefs = DustUtils.simpleGet(j, 1, "misc#connRequired");

			for (Map.Entry<String, String> ure : unitRefs.entrySet()) {
				String uRef = ure.getValue();
				Map<String, Object> ud = (Map<String, Object>) content.get(uRef.substring(2));

				String author = DustUtils.simpleGet(ud, "mind#unitAuthor");
				author = (DUST_REF_PREFIX.equals(author)) ? DustUtils.simpleGet(j, 1, "mind#ideaToken")
						: DustUtils.simpleGet(content, author.substring(2), "mind#ideaToken");

				String uid = DustUtils.simpleGet(ud, "mind#ideaToken");
				initUnitData(ud, author, uid);

				String uKey = optRegisterUnit(queue, ud);
				refs.put(ure.getKey(), uKey);
			}
		}

		return key;
	}

	private static void loadQueue(Map<String, ArrayList<Object>> queue) {
		for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
			String key = eq.getKey();
			ArrayList<Object> inputData = eq.getValue();
			
			MindHandle unitHandle = DustUtils.simpleGet(DIALOG_DATA, DIALOG_UNITS, key);
			Map<MindHandle, Object> unit = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, unitHandle, unitHandle);
			
			Map<String, DustHandle> unitHandles = DustUtils.simpleGet(unit, UNIT_HANDLES);
			Map<String, String> unitMap = DustUtils.simpleGet(unit, MISC_CONN_REQUIRES);

			Dust.log(null, "Load unit", key, unitMap, inputData, unitHandles);

			optLoadIdea(unitMap, key, (DustHandle) unit.get(IDEA_HANDLE), unit, DustUtils.simpleGet(inputData, 1));

			for (DustHandle ideaHandle : unitHandles.values()) {
				Map<MindHandle, Object> ideaData = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, unitHandle, ideaHandle);
				Map<String, Object> input = DustUtils.simpleGet(inputData, 2, ideaHandle.id);
				ideaData.put(IDEA_HANDLE, ideaHandle);

				optLoadIdea(unitMap, key, ideaHandle, ideaData, input);
			}
		}
	}

	public static void optLoadIdea(Map<String, String> unitMap, String unit, DustHandle ideaHandle, Map<MindHandle, Object> ideaData, Map<String, Object> input) {
		if (null == input) {
			return;
		}

		Dust.log(null, "   Load item", ideaHandle.id, input);

		for (Map.Entry<String, Object> ev : input.entrySet()) {

			String t = ev.getKey();
			Object v = ev.getValue();

			MindHandle hVal = resolveHandle(unitMap, unit, t);

			if (null == hVal) {
				Dust.log(null, "ERROR - missing referred handle for key", t);
			} else {
				v = resolveValue(unitMap, unit, hVal, v);
				ideaData.put(hVal, v);
				Dust.log(null, "       Load value", t, v, hVal);
			}
		}
	}

	private static MindHandle resolveHandle(Map<String, String> unitMap, String unit, String t) {
		String ur = unit;
		String k = t;
		int sep = t.indexOf(DUST_SEP_TOKEN);
		if (-1 != sep) {
			ur = unitMap.get(t.substring(0, sep));
			k = t.substring(sep);
		} else {
			sep = t.indexOf(DUST_SEP_ID);
			if (-1 != sep) {
				ur = unitMap.get(t.substring(0, sep));
				k = t.substring(sep + DUST_SEP_ID.length());
			}
		}

		DustHandle hUnit = DustUtils.simpleGet(DIALOG_DATA, DIALOG_UNITS, ur);
		return DustUtils.isEmpty(k) ? hUnit : DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, hUnit, hUnit, UNIT_HANDLES, k);
	}

	private static Object resolveValue(Map<String, String> unitMap, String unit, MindHandle hVal, Object v) {
		Object ret = v;

		if (v instanceof Map) {
			Map<String, Object> m = new TreeMap<>();

			for (Map.Entry<String, Object> ev : ((Map<String, Object>) v).entrySet()) {
				String k = ev.getKey();
				m.put(k, resolveValue(unitMap, unit, hVal, ev.getValue()));
			}

			ret = m;
		} else if (v instanceof Collection) {
			ArrayList<Object> a = new ArrayList<>();

			for (Object o : (Collection) v) {
				a.add(resolveValue(unitMap, unit, hVal, o));
			}

			ret = a;

		} else if (v instanceof String) {
			String s = (String) v;

			if (s.startsWith(DUST_REF_PREFIX)) {
				ret = resolveHandle(unitMap, unit, s.substring(DUST_REF_PREFIX.length()));
			}
		}

		return ret;
	}
}
