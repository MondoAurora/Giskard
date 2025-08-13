package me.giskard.dust.boot;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
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
		Map<String, Object> app = new TreeMap<String, Object>();

		app.put("author", "giskard.me");
		app.put("token", "test01");

		enqueueModule(app);

		loadQueue();
	}

	static Map<String, Map<MindHandle, Object>> units = new TreeMap<>();
	static Map<String, ArrayList<Object>> queue = new TreeMap<>();
	static Map<String, Map<String, Object>> vocabulary = new TreeMap<>();

	private static void enqueueModule(Map<String, Object> unitData) throws Exception {
		String key = buildKey(unitData);

		if (!units.containsKey(key) && !queue.containsKey(key)) {
			File f = new File(key + DUST_EXT_JSON);
			ArrayList<Object> j = DustUtilsJson.readJson(f);
			queue.put(key, j);

			Map<MindHandle, Object> unit = new HashMap<>();
			units.put(key, unit);
			DustHandle hUnit = new DustHandle(null, key);
			unit.put(IDEA_HANDLE, hUnit);

			HashMap<DustHandle, Map<MindHandle, Object>> iUnit = new HashMap();
			unit.put(UNIT_HANDLES, iUnit);

			String lang = DustUtils.simpleGet(j, 0, "lang");
			lang = DustUtils.getPostfix(lang, REF_PREFIX);

			Map<String, DustHandle> tokenMap = DustUtils.safeGet(vocabulary, lang, SORTEDMAP_CREATOR);
			tokenMap = DustUtils.safeGet(tokenMap, key, SORTEDMAP_CREATOR);
			tokenMap.put("", hUnit);

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
				iUnit.put(hIdea, dIdea);

				String token = DustUtils.simpleGet(ec.getValue(), "mind#ideaToken");
				if (!DustUtils.isEmpty(token)) {
					String tkey = DUST_SEP_TOKEN + token;
					DustHandle hToken = new DustHandle(hUnit, tkey);
					Map<MindHandle, Object> dToken = new HashMap<>();
					dToken.put(IDEA_HANDLE, hToken);
					dToken.put(MISC_TARGET, hIdea);
					iUnit.put(hToken, dToken);

					tokenMap.put(tkey, hToken);
				}
			}

			Map<String, String> unitRefs = DustUtils.simpleGet(j, 1, "misc#connRequired");

			for (String uRef : unitRefs.values()) {
				Map<String, Object> ud = (Map<String, Object>) content.get(uRef.substring(2));

				String author = DustUtils.simpleGet(ud, "mind#unitAuthor");
				author = (REF_PREFIX.equals(author)) ? DustUtils.simpleGet(j, 1, "mind#ideaToken")
						: DustUtils.simpleGet(content, author.substring(2), "mind#ideaToken");
				ud.put("author", author);
				ud.put("token", DustUtils.simpleGet(ud, "mind#ideaToken"));

				enqueueModule(ud);
			}

		}
	}

	public static String buildKey(Map<String, Object> unitData) {
		return DustUtils.sbAppend(null, "/", true, "units", unitData.get("author"), unitData.get("token")).toString();
	}

	private static void loadQueue() {
		for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
			String key = eq.getKey();
			ArrayList<Object> inputData = eq.getValue();
			Map<DustHandle, Map<MindHandle, Object>> iUnit = DustUtils.simpleGet(units, key, UNIT_HANDLES);

			Dust.log(null, "Load unit", key, inputData, iUnit);

			for (Map.Entry<DustHandle, Map<MindHandle, Object>> eu : iUnit.entrySet()) {
				DustHandle h = eu.getKey();
				Map<MindHandle, Object> idea = eu.getValue();
				idea.put(IDEA_HANDLE, h);

				Map<String, Object> input = DustUtils.simpleGet(inputData, 2, h.id);

				if (null != input) {
					Dust.log(null, "   Load item", h.id, input);

					for (Map.Entry<String, Object> ev : input.entrySet()) {
						String t = ev.getKey();
						Object v = ev.getValue();

						Dust.log(null, "       Load value", t, v);

					}
				}
			}
		}

		Dust.log(null, "Vocabulary", vocabulary);
	}
}
