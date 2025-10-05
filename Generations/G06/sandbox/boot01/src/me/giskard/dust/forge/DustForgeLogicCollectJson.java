package me.giskard.dust.forge;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineBootConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;
import me.giskard.dust.utils.DustUtilsJson;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustForgeLogicCollectJson implements DustConsts.MindLogic, DustForgeConsts, DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		Object handle = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_HANDLE);
		Map data = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MISC_GEN_PAYLOAD);

		switch (a) {
		case Begin:
			if (null == handle) {
				Dust.access(MIND_TAG_ACCESS_SET, new TreeMap(), null, DUST_AGENT_PARAM, MISC_GEN_PAYLOAD);
			}
			break;
		case Process:

			Object att = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_ATT);
			Object key = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_KEY);
			Object val = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE);

			if (null != val) {
				Map m = DustUtils.safeGet(data, handle.toString(), SORTEDMAP_CREATOR);
				if (null != key) {
					Object currVal = DustUtils.safeGet(m, att.toString(), SORTEDMAP_CREATOR);
					if (currVal instanceof Map) {
						m = (Map) currVal;
					} else {
						Map newMap = new TreeMap();
						newMap.put("", currVal);
						m.put(att.toString(), newMap);
						m = newMap;
					}
					key = key.toString();
				} else {
					key = att.toString();
				}

				m.put(key, val.toString());
			}
			
			break;
		case End:
			if (null == handle) {
				Dust.log(null, a, "Collection complete", data);
				DustUtilsJson.writeJson("dump.json", data);
			}
			break;
		default:
			break;
		}

		return MIND_TAG_RESULT_ACCEPT;
	}
}