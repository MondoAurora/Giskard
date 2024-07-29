package me.giskard.dust.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustSandboxBrain implements DustSandboxConsts {

	private DustCreator crHandle = new DustCreator() {
		@Override
		public DustSandboxHandle create(Object key, Object... hints) {
			return new DustSandboxHandle(hints[0] + ":" + key);
		}
	};

	private DustCreator crUnit = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(crHandle);
		}
	};

	private DustCreator crAuthor = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(crUnit);
		}
	};

	DustUtilsFactory handleCatalog = new DustUtilsFactory(crAuthor);
	DustUtilsFactory<DustSandboxHandle, Map> knowledge = new DustUtilsFactory(MAP_CREATOR);

	public DustSandboxHandle resolve(String author, String unit, String id) {
		DustUtilsFactory<String, DustSandboxHandle> hf = (DustUtilsFactory<String, DustSandboxHandle>) ((DustUtilsFactory) handleCatalog
				.get(author)).get(unit);
		String uref = author + ":" + unit;

		DustSandboxHandle h = hf.get(id, uref);

		return h;
	}

	public void set(DustSandboxHandle hTarget, DustSandboxHandle hAtt, char attType, Object key, Object val) {
		Map m = knowledge.get(hTarget);
		Object v = m.get(hAtt);

		if (null == v) {
			switch (attType) {
			case 's':
				v = new HashSet();
				break;
			case 'a':
				v = new ArrayList();
				break;
			case 'm':
				v = new HashMap();
				break;
			}

			if (null != v) {
				m.put(hAtt, v);
			}
		}

		switch (attType) {
		case 'o':
			m.put(hAtt, val);
			break;
		case 's':
			((Set) v).add(val);
			break;
		case 'a':
			ArrayList al = (ArrayList) v;
			int idx = (Integer) key;
			DustUtils.ensureSize(al, idx);
			al.set(idx, val);
			break;
		case 'm':
			((Map) v).put(key, val);
			break;
		}

		Dust.log(null, "Set", hTarget, hAtt, key, val);
	}
}
