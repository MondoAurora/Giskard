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
		Dust.access(null, null);

		Map units = new TreeMap();
		Map dialogIdeas = new HashMap();

		DIALOG_DATA.put(MACHINE_UNITS, units);
		DIALOG_DATA.put(DIALOG_IDEAS, dialogIdeas);
		DIALOG_DATA.put(DIALOG_VOCABULARY, new TreeMap());

		storeHandle(MISC, units, dialogIdeas, "iso:639-1:en", "misc");
		storeHandle(MISC_TARGET, units, dialogIdeas, "iso:639-1:en", "MISC_TARGET");
		storeHandle(MISC_PARENT, units, dialogIdeas, "iso:639-1:en", "MISC_PARENT");
		storeHandle(MISC_CONN_REQUIRED, units, dialogIdeas, "iso:639-1:en", "connRequired");

		storeHandle(MIND, units, dialogIdeas, "iso:639-1:en", "mind");
		storeHandle(DUST, units, dialogIdeas, "iso:639-1:en", "dust");

		optLoadUnit(null, buildUnitKey("giskard.me", "test01"));
	}

	public static String buildUnitKey(Object... path) {
		return DustUtils.sbAppend(null, "/", true, path).toString();
	}

	public static void storeHandle(DustHandle h, Map units, Map dialogIdeas, String lang, String token) {
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

			Map<String, String> refs = DustUtils.safeGet(unitData, MISC_CONN_REQUIRED, MAP_CREATOR);
			refs.put(token, h.id);

		} else {
			Map<MindHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(dialogIdeas, h.unit);
			HashMap<String, MindHandle> unitHandles = DustUtils.simpleGet(unitIdeas, h.unit, UNIT_HANDLES);

			Map<MindHandle, Object> dIdea = new HashMap<>();
			dIdea.put(IDEA_HANDLE, h);
			unitIdeas.put(h, dIdea);
			unitHandles.put(h.id, h);

			if (!DustUtils.isEmpty(token)) {
				String tkey = DUST_SEP_TOKEN + token;
				DustHandle hToken = new DustHandle(h.unit, tkey);
				Map<MindHandle, Object> dToken = new HashMap<>();
				dToken.put(IDEA_HANDLE, hToken);
				dToken.put(MISC_TARGET, h);
				unitIdeas.put(hToken, dToken);
				unitHandles.put(tkey, hToken);

				Map<String, DustHandle> tokenMap = DustUtils.safeGet(DIALOG_DATA.get(DIALOG_VOCABULARY), lang, SORTEDMAP_CREATOR);
				tokenMap = DustUtils.safeGet(tokenMap, h.unit.id, MAP_CREATOR);

				tokenMap.put(tkey, hToken);
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

		DustHandle hUnit = DustUtils.simpleGet(DIALOG_DATA, MACHINE_UNITS, ur);
		return DustUtils.isEmpty(k) ? hUnit : DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, hUnit, hUnit, UNIT_HANDLES, k);
	}

	private static void optLoadUnit(Map<String, ArrayList<Object>> queue, String key) throws Exception {
		boolean first = (null == queue);

		if (first) {
			queue = new TreeMap<>();
		}

		Map units = DustUtils.simpleGet(DIALOG_DATA, MACHINE_UNITS);
		Map dialogIdeas = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS);

		if (!queue.containsKey(key)) {
			Dust.log(null, "Queueing", key);
			File f = new File("units/" + key + DUST_EXT_JSON);
			ArrayList<Object> j = DustUtilsJson.readJson(f);
			queue.put(key, j);

			String lang = DustUtils.getPostfix(DustUtils.simpleGet(j, 0, LOAD_TOKEN_LANG), DUST_REF_PREFIX);

			DustHandle unitHandle = DustUtils.safeGet(units, key, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object k, Object... hints) {
					DustHandle ret = new DustHandle(key);
					String token = DustUtils.simpleGet(j, 1, LOAD_TOKEN_EXT_ID);
					storeHandle(ret, units, dialogIdeas, lang, token);
					return ret;
				}
			});

			Map<MindHandle, Object> unitData = DustUtils.simpleGet(dialogIdeas, unitHandle, unitHandle);
			Map<String, MindHandle> unitHandles = DustUtils.simpleGet(unitData, UNIT_HANDLES);
			Map<String, String> refs = DustUtils.simpleGet(unitData, MISC_CONN_REQUIRED);

			Map<String, Object> content = DustUtils.simpleGet(j, 2);
			for (Map.Entry<String, Object> ec : content.entrySet()) {
				String kIdea = ec.getKey();

				if (kIdea.contains(DUST_SEP_ID)) {
					// remote idea
					continue;
				}

				DustUtils.safeGet(unitHandles, kIdea, new DustCreator<DustHandle>() {
					@Override
					public DustHandle create(Object k, Object... hints) {
						DustHandle ret = new DustHandle(unitHandle, kIdea);
						String token = DustUtils.simpleGet(ec.getValue(), LOAD_TOKEN_IDEA_TOKEN);
						storeHandle(ret, units, dialogIdeas, lang, token);
						return ret;
					}
				});
			}

			Map<String, String> unitRefs = DustUtils.simpleGet(j, 1, LOAD_TOKEN_CONN_REQUIRED);

			for (Map.Entry<String, String> ure : unitRefs.entrySet()) {
				String uRef = ure.getValue();
				Map<String, Object> ud = (Map<String, Object>) content.get(uRef.substring(2));

				String author = DustUtils.simpleGet(ud, LOAD_TOKEN_UNIT_AUTHOR);
				author = (DUST_REF_PREFIX.equals(author)) ? DustUtils.simpleGet(j, 1, LOAD_TOKEN_EXT_ID)
						: DustUtils.simpleGet(content, author.substring(2), LOAD_TOKEN_EXT_ID);

				String uid = DustUtils.simpleGet(ud, LOAD_TOKEN_EXT_ID);

				String uKey = buildUnitKey(author, uid);
				optLoadUnit(queue, uKey);
				refs.put(ure.getKey(), uKey);
			}
		}

		if (first) {
			for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
				String qKey = eq.getKey();
				ArrayList<Object> qData = eq.getValue();

				MindHandle unitHandle = DustUtils.simpleGet(DIALOG_DATA, MACHINE_UNITS, qKey);
				Map<MindHandle, Object> unit = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, unitHandle, unitHandle);

				Map<String, DustHandle> unitHandles = DustUtils.simpleGet(unit, UNIT_HANDLES);
				Map<String, String> unitMap = DustUtils.simpleGet(unit, MISC_CONN_REQUIRED);

				Dust.log(null, "Load unit", qKey, unitMap, qData, unitHandles);

				optLoadIdea(unitMap, qKey, (DustHandle) unit.get(IDEA_HANDLE), unit, DustUtils.simpleGet(qData, 1));

				for (DustHandle ideaHandle : unitHandles.values()) {
					Map<MindHandle, Object> ideaData = DustUtils.simpleGet(DIALOG_DATA, DIALOG_IDEAS, unitHandle, ideaHandle);
					Map<String, Object> input = DustUtils.simpleGet(qData, 2, ideaHandle.id);
					ideaData.put(IDEA_HANDLE, ideaHandle);

					optLoadIdea(unitMap, qKey, ideaHandle, ideaData, input);
				}
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
				v = loadValue(unitMap, unit, hVal, v);
				ideaData.put(hVal, v);
				Dust.log(null, "       Load value", t, v, hVal);
			}
		}
	}

	private static Object loadValue(Map<String, String> unitMap, String unit, MindHandle hVal, Object v) {
		Object ret = v;

		if (v instanceof Map) {
			Map<String, Object> m = new TreeMap<>();

			for (Map.Entry<String, Object> ev : ((Map<String, Object>) v).entrySet()) {
				String k = ev.getKey();
				m.put(k, loadValue(unitMap, unit, hVal, ev.getValue()));
			}

			ret = m;
		} else if (v instanceof Collection) {
			ArrayList<Object> a = new ArrayList<>();

			for (Object o : (Collection) v) {
				a.add(loadValue(unitMap, unit, hVal, o));
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
