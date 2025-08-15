package me.giskard.dust.boot;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsFile;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustBootTest01 implements DustBootConsts, DustUtilsConsts {
	static String LOCAL_UNIT_AUTHOR = "author";
	static String LOCAL_UNIT_ID = "token";

	public static void test01() {

		File root = new File("units");
		int pl = root.getAbsolutePath().length();

		FileFilter ff = new DustUtilsFile.ExtFilter(DUST_EXT_JSON);

		DustUtilsFile.FileProcessor fp = new DustUtilsFile.FileProcessor() {
			@Override
			public boolean processFile(File f) {
				String id = f.getAbsolutePath().substring(pl + 1);

				try {
					ArrayList<Object> j = DustUtilsJson.readJson(f);

					Object meta = j.get(0);
					Dust.log(null, id, meta);

					Map<String, Object> data = (Map<String, Object>) j.get(2);

					int idx = 0;
					for (String o : data.keySet()) {
						Dust.log(null, "data", idx++, o, data.get(o));

					}
				} catch (Exception e) {
					DustException.swallow(e, "reading unit file", id);
				}
				return true;
			}
		};

		DustUtilsFile.procRecursive(root, fp, ff);
	}

	public static void test02() throws Exception {
		Map<String, Object> app = initUnitData(null, "giskard.me", "test01");

		enqueueModule(app);

		loadQueue();
	}

	public static Map<String, Object> initUnitData(Map<String, Object> ud, String author, String id) throws Exception {
		if (null == ud) {
			ud = new TreeMap<String, Object>();
		}

		ud.put(LOCAL_UNIT_AUTHOR, author);
		ud.put(LOCAL_UNIT_ID, id);

//		ud.put(LOCAL_UNIT_MAP, new TreeMap<>());

		return ud;
	}

	static Map<String, Map<MindHandle, Object>> units = new TreeMap<>();
	static Map<String, ArrayList<Object>> queue = new TreeMap<>();
	static Map<String, Map<String, Object>> vocabulary = new TreeMap<>();

	private static String enqueueModule(Map<String, Object> unitData) throws Exception {
		String key = buildKey(unitData);

		if (!units.containsKey(key) && !queue.containsKey(key)) {
			File f = new File(key + DUST_EXT_JSON);
			ArrayList<Object> j = DustUtilsJson.readJson(f);
			queue.put(key, j);

			String lang = DustUtils.simpleGet(j, 0, "lang");
			lang = DustUtils.getPostfix(lang, DUST_REF_PREFIX);
			Map<String, DustHandle> tokenMap = DustUtils.safeGet(vocabulary, lang, SORTEDMAP_CREATOR);
			tokenMap = DustUtils.safeGet(tokenMap, key, MAP_CREATOR);

			DustHandle hUnit = new DustHandle(null, key);
			Map<MindHandle, Object> unit = new HashMap<>();
			units.put(key, unit);
			unit.put(IDEA_HANDLE, hUnit);

			HashMap<DustHandle, Map<MindHandle, Object>> unitIdeas = new HashMap();
			unit.put(UNIT_IDEAS, unitIdeas);

			HashMap<String, MindHandle> unitHandles = new HashMap();
			unit.put(UNIT_HANDLES, unitHandles);
			unitHandles.put("", hUnit);

			Map<String, String> refs = DustUtils.safeGet(unit, MISC_CONN_REQUIRED, MAP_CREATOR); // DustUtils.simpleGet(unitData, LOCAL_UNIT_MAP);
			refs.put(DustUtils.simpleGet(j, 1, "misc#ideaToken"), key);

			Map<String, Object> content = DustUtils.simpleGet(j, 2);
			for (Map.Entry<String, Object> ec : content.entrySet()) {
				String kIdea = ec.getKey();

				if (kIdea.contains(DUST_SEP_ID)) {
					// remote idea
					continue;
				}

				DustHandle hIdea = new DustHandle(hUnit, kIdea);
				Map<MindHandle, Object> dIdea = new HashMap<>();
				dIdea.put(IDEA_HANDLE, hIdea);
				unitIdeas.put(hIdea, dIdea);
				unitHandles.put(kIdea, hIdea);

				String token = DustUtils.simpleGet(ec.getValue(), "misc#ideaToken");
				if (!DustUtils.isEmpty(token)) {
					String tkey = DUST_SEP_TOKEN + token;
					DustHandle hToken = new DustHandle(hUnit, tkey);
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
				author = (DUST_REF_PREFIX.equals(author)) ? DustUtils.simpleGet(j, 1, "misc#ideaToken")
						: DustUtils.simpleGet(content, author.substring(2), "misc#ideaToken");

				String uid = DustUtils.simpleGet(ud, "misc#ideaToken");
				initUnitData(ud, author, uid);

				String uKey = enqueueModule(ud);
				refs.put(ure.getKey(), uKey);

			}
		}

		return key;
	}

	public static String buildKey(Map<String, Object> unitData) {
		return DustUtils.sbAppend(null, "/", true, "units", unitData.get("author"), unitData.get("token")).toString();
	}

	private static void loadQueue() {
		for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
			String key = eq.getKey();
			ArrayList<Object> inputData = eq.getValue();
			Map<MindHandle, Object> unit = DustUtils.simpleGet(units, key);
			Map<DustHandle, Map<MindHandle, Object>> unitIdeas = DustUtils.simpleGet(unit, UNIT_IDEAS);

			Map<String, String> unitMap = DustUtils.simpleGet(unit, MISC_CONN_REQUIRED);

			Dust.log(null, "Load unit", key, unitMap, inputData, unitIdeas);

			optLoadIdea(unitMap, key, (DustHandle) unit.get(IDEA_HANDLE), unit, DustUtils.simpleGet(inputData, 1));

			for (Map.Entry<DustHandle, Map<MindHandle, Object>> eu : unitIdeas.entrySet()) {
				DustHandle ideaHandle = eu.getKey();
				Map<MindHandle, Object> ideaData = eu.getValue();
				Map<String, Object> input = DustUtils.simpleGet(inputData, 2, ideaHandle.id);
				ideaData.put(IDEA_HANDLE, ideaHandle);

				optLoadIdea(unitMap, key, ideaHandle, ideaData, input);
			}
		}

		Dust.log(null, "Vocabulary", vocabulary);
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
		int sep = t.indexOf(DUST_SEP_TOKEN);
		if (-1 != sep) {
			ur = unitMap.get(t.substring(0, sep));
			t = t.substring(sep);
		} else {
			sep = t.indexOf(DUST_SEP_ID);
			if (-1 != sep) {
				ur = unitMap.get(t.substring(0, sep));
				t = t.substring(sep + DUST_SEP_ID.length());
			}
		}

		return DustUtils.isEmpty(t) ? DustUtils.simpleGet(units, ur, IDEA_HANDLE) : DustUtils.simpleGet(units, ur, UNIT_HANDLES, t);
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
