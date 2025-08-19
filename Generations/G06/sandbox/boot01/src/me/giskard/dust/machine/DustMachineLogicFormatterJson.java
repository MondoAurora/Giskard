package me.giskard.dust.machine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogicFormatterJson implements DustMachineBootConsts, DustMachineConsts, DustUtilsConsts {
	
	DustMachineLogic machine;
	
	public DustMachineLogicFormatterJson(DustMachineLogic machine) {
		this.machine = machine;
	}

	public void optLoadUnit(Map<String, ArrayList<Object>> queue, String key) throws Exception {
		boolean first = (null == queue);

		if (first) {
			queue = new TreeMap<>();
		}

		Map units = DustUtils.simpleGet(machine.data, MACHINE_UNITS);
		Map dialogIdeas = DustUtils.simpleGet(machine.data, DIALOG_IDEAS);
		Map vocabulary = DustUtils.simpleGet(machine.data, DIALOG_VOCABULARY);

		if (!queue.containsKey(key)) {
			Dust.log(null, "Queueing", key);
			File f = new File("units/" + key + DUST_EXT_JSON);
			ArrayList<Object> j = DustUtilsJson.readJson(f);
			queue.put(key, j);

			String lang = DustUtils.getPostfix(DustUtils.simpleGet(j, 0, LOAD_TOKEN_LANG), DUST_REF_PREFIX);
			String token = DustUtils.simpleGet(j, 1, LOAD_TOKEN_EXT_ID);

			DustHandle unitHandle = DustUtils.safeGet(units, key, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object k, Object... hints) {
					DustHandle ret = new DustHandle(key);
					DustMachineUtils.storeHandle(ret, units, dialogIdeas, vocabulary, lang, token);
					return ret;
				}
			});

			Map<MindHandle, Object> unitData = DustUtils.simpleGet(dialogIdeas, unitHandle, unitHandle);
			Map<String, MindHandle> unitHandles = DustUtils.simpleGet(unitData, UNIT_HANDLES);
			Map<String, String> refs = DustUtils.safeGet(unitData, MISC_CONN_REQUIRED, MAP_CREATOR);
			refs.put(token, key);

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
						DustMachineUtils.storeHandle(ret, units, dialogIdeas, vocabulary, lang, token);
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

				String uKey = DustMachineUtils.buildUnitKey(author, uid);
				optLoadUnit(queue, uKey);
				refs.put(ure.getKey(), uKey);
			}
		}

		if (first) {
			for (Map.Entry<String, ArrayList<Object>> eq : queue.entrySet()) {
				String qKey = eq.getKey();
				ArrayList<Object> qData = eq.getValue();

				MindHandle unitHandle = DustUtils.simpleGet(machine.data, MACHINE_UNITS, qKey);
				Map<MindHandle, Object> unit = DustUtils.simpleGet(machine.data, DIALOG_IDEAS, unitHandle, unitHandle);

				Map<String, DustHandle> unitHandles = DustUtils.simpleGet(unit, UNIT_HANDLES);
				Map<String, String> unitMap = DustUtils.simpleGet(unit, MISC_CONN_REQUIRED);

				Dust.log(null, "Load unit", qKey, unitMap, qData, unitHandles);

				optLoadIdea(unitMap, qKey, (DustHandle) unit.get(IDEA_HANDLE), unit, DustUtils.simpleGet(qData, 1));

				for (DustHandle ideaHandle : unitHandles.values()) {
					Map<MindHandle, Object> ideaData = DustUtils.simpleGet(machine.data, DIALOG_IDEAS, unitHandle, ideaHandle);
					Map<String, Object> input = DustUtils.simpleGet(qData, 2, ideaHandle.getId());
					ideaData.put(IDEA_HANDLE, ideaHandle);

					optLoadIdea(unitMap, qKey, ideaHandle, ideaData, input);
				}
			}
		}
	}

	private void optLoadIdea(Map<String, String> unitMap, String unit, DustHandle ideaHandle, Map<MindHandle, Object> ideaData, Map<String, Object> input) {
		if (null == input) {
			return;
		}

		Dust.log(null, "   Load item", ideaHandle.getId(), input);

		for (Map.Entry<String, Object> ev : input.entrySet()) {

			String t = ev.getKey();
			Object v = ev.getValue();

			MindHandle hVal = machine.resolveHandle(unitMap, unit, t);

			if (null == hVal) {
				Dust.log(null, "ERROR - missing referred handle for key", t);
			} else {
				v = loadValue(unitMap, unit, hVal, v);
				ideaData.put(hVal, v);
				Dust.log(null, "       Load value", hVal, v);
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
				ret = machine.resolveHandle(unitMap, unit, s.substring(DUST_REF_PREFIX.length()));
			}
		}

		return ret;
	}
}
