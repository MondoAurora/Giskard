package me.giskard.dust.machine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogicFormatterJson implements DustConsts.MindLogic, DustMachineConstsInt.UnitLoader, DustMachineBootConsts, DustMachineConstsInt, DustUtilsConsts {

	private static ThreadLocal<Map<String, ArrayList<Object>>> READ_QUEUE = new ThreadLocal<Map<String, ArrayList<Object>>>() {
		protected java.util.Map<String, java.util.ArrayList<Object>> initialValue() {
			return new TreeMap<>();
		};
	};
	
	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		return null;
	}

	@Override
	public void optLoadUnit(String key) throws Exception {
		Map<String, ArrayList<Object>> queue = READ_QUEUE.get();
		boolean first = queue.isEmpty();

		try {
			if (!queue.containsKey(key)) {
				File f = new File("units/" + key + DUST_EXT_JSON);				
				if ( !f.isFile() ) {
//					Dust.log(null, "No stored file for unit", key, f.getCanonicalPath());
					return;
				}

				Dust.log(null, "Queueing", key);
				ArrayList<Object> j = DustUtilsJson.readJson(f);
				queue.put(key, j);

				Map meta = DustUtils.simpleGet(j, 0);

				String lang = DustUtils.getPostfix(DustUtils.simpleGet(meta, LOAD_TOKEN_LANG), DUST_REF_PREFIX);

				String token = DustUtils.simpleGet(j, 1, LOAD_TOKEN_EXT_ID);
				Map<String, String> refs = DustUtils.safeGet(meta, LOAD_TOKEN_UNITMAP, MAP_CREATOR);
				refs.put(token, key);

				MindHandle unitHandle = Dust.lookup(null, key);
				Dust.access(MIND_TAG_ACCESS_SET, f.getAbsolutePath(), unitHandle, DUST_PERS_ID);
				
				meta.put(MIND_IDEA_HANDLE, unitHandle);

				Map<String, Object> content = DustUtils.simpleGet(j, 2);
				for (Map.Entry<String, Object> ec : content.entrySet()) {
					String kIdea = ec.getKey();

					if (DustMachineUtils.isIdRemote(kIdea)) {
						// remote idea
						continue;
					}

					token = DustUtils.simpleGet(ec.getValue(), LOAD_TOKEN_IDEA_TOKEN);
					Dust.lookup(unitHandle, kIdea, lang, token);
				}

				Map<String, String> unitRefs = DustUtils.simpleGet(j, 1, LOAD_TOKEN_CONN_REQUIRED);

				for (Map.Entry<String, String> ure : unitRefs.entrySet()) {
					String uRef = ure.getValue();
					Map<String, Object> ud = (Map<String, Object>) content.get(uRef.substring(2));

					String author = DustUtils.simpleGet(ud, LOAD_TOKEN_UNIT_AUTHOR);
					author = (DUST_REF_PREFIX.equals(author)) ? DustUtils.simpleGet(j, 1, LOAD_TOKEN_EXT_ID)
							: DustUtils.simpleGet(content, author.substring(2), LOAD_TOKEN_EXT_ID);

					String uid = DustUtils.simpleGet(ud, LOAD_TOKEN_EXT_ID);
					String cid = DustUtils.simpleGet(ud, LOAD_TOKEN_COMMIT_ID);

					String uKey = DustMachineUtils.buildUnitKey(author, uid, cid);
					Dust.lookup(null, uKey);
//					optLoadUnit(uKey);
					refs.put(ure.getKey(), uKey);
				}
			}

			if (first) {
				for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
					String qKey = eq.getKey();
					ArrayList<Object> qData = eq.getValue();

					Map<String, String> unitMap = DustUtils.simpleGet(qData, 0, LOAD_TOKEN_UNITMAP);
					DustHandle unitHandle = DustUtils.simpleGet(qData, 0, MIND_IDEA_HANDLE);

					Dust.log(null, "Load unit", qKey, unitMap, qData);
					optLoadIdea(unitMap, qKey, null, unitHandle.getId(), DustUtils.simpleGet(qData, 1));
//					optLoadIdea(unitMap, qKey, unitHandle, "", DustUtils.simpleGet(qData, 1));

					Map<String, Map<String, Object>> content = DustUtils.simpleGet(qData, 2);
					for (Map.Entry<String, Map<String, Object>> ec : content.entrySet()) {
						String kIdea = ec.getKey();

						if (!DustMachineUtils.isIdRemote(kIdea)) {
							optLoadIdea(unitMap, qKey, unitHandle, kIdea, ec.getValue());
						}
					}
				}
			}
		} finally {
			if (first) {
				queue.clear();
			}
		}
	}

	private void optLoadIdea(Map<String, String> unitMap, String unit, DustHandle unitHandle, String itemId, Map<String, Object> input) {
		Dust.log(null, "   Load item", DustUtils.toString(unitHandle), itemId, input);

		MindHandle hItem = Dust.lookup(unitHandle, itemId);

		for (Map.Entry<String, Object> ev : input.entrySet()) {

			String t = ev.getKey();
			t = DustUtils.getPrefix(t, DUST_SEP_TOKEN);
			Object v = ev.getValue();

			MindHandle hVal = DustMachineUtils.resolveHandle(unitMap, unit, t);

			if (null == hVal) {
				Dust.log(null, "ERROR - missing referred handle for key", t);
			} else {
				v = loadValue(unitMap, unit, hVal, v);
				Dust.access(MIND_TAG_ACCESS_SET, v, hItem, hVal);
			}
		}
	}

	private Object loadValue(Map<String, String> unitMap, String unit, MindHandle hVal, Object v) {
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
				ret = DustMachineUtils.resolveHandle(unitMap, unit, s.substring(DUST_REF_PREFIX.length()));
			}
		}

		return ret;
	}
}
