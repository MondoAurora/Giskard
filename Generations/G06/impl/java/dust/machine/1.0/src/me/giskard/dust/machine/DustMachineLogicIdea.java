package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.utils.DustUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogicIdea implements DustMachineConsts {

	public <RetType> RetType peek(Map<MindToken, Object> data, MindToken hAtt) {
		return peek(data, hAtt, MindCollType.One, null);
	}

	public <RetType> RetType peek(Map<MindToken, Object> data, MindToken hAtt, MindCollType ct, Object key) {
		Object ob = data.get(hAtt);

		if (null != ob) {
			switch (ct) {
			case Arr:
				ArrayList al = (ArrayList) ob;
				int ik = (int) key;
				ob = ((0 <= ik) && (ik < al.size())) ? al.get(ik) : null;
				break;
			case Map:
				Map map = (Map) ob;
				ob = map.get(key);
				break;
			case Set:
				Set set = (Set) ob;
				ob = set.isEmpty() ? null : set.iterator().next();
				break;
			case One:
				break;
			}
		}

		return (RetType) ob;
	}

	public <RetType> RetType get(Map<MindToken, Object> data, MindToken hAtt, MindCollType ct, Object key, DustCreator<RetType> creator,
			Object... hints) {
		synchronized (data) {
			Object ob = data.get(hAtt);

			if (null != ob) {
				switch (ct) {
				case Arr:
					ArrayList al = (ArrayList) ob;
					int ik = (int) key;
					ob = ((0 <= ik) && (ik < al.size())) ? al.get(ik) : null;
					break;
				case Map:
					Map map = (Map) ob;
					ob = map.get(key);
					break;
				case Set:
					Set set = (Set) ob;
					ob = set.isEmpty() ? null : set.iterator().next();
					break;
				case One:
					break;
				}
			}

			if ((null == ob) && (null != creator)) {
				ob = creator.create(key, hints);
				set(data, hAtt, ob, ct, key);
			}

			return (RetType) ob;
		}
	}

	public boolean set(Map<MindToken, Object> data, MindToken hAtt, Object val) {
		return set(data, hAtt, val, MindCollType.One, null);
	}

	public boolean set(Map<MindToken, Object> data, MindToken hAtt, Object val, MindCollType ct, Object key) {
		boolean changed;

		synchronized (data) {
			Object orig = data.get(hAtt);
			ArrayList al = null;
			Map map = null;
			Set set = null;

			int ik = (ct == MindCollType.Arr) ? (int) key : -1;

			if (null == orig) {
				switch (ct) {
				case Arr:
					al = new ArrayList();
					data.put(hAtt, al);
					break;
				case Map:
					map = new HashMap();
					data.put(hAtt, map);
					break;
				case Set:
					set = new HashSet();
					data.put(hAtt, set);
					break;
				case One:
					break;
				}
			} else {
				switch (ct) {
				case Arr:
					al = (ArrayList) orig;
					orig = ((0 <= ik) && (ik < al.size())) ? al.get(ik) : null;
					break;
				case Map:
					map = (Map) orig;
					orig = map.get(key);
					break;
				case Set:
					set = (Set) orig;
					orig = set.isEmpty() ? null : set.iterator().next();
					break;
				case One:
					break;
				}
			}

			changed = !DustUtils.isEqual(orig, val);

			if (changed) {
				switch (ct) {
				case Arr:
					if (ik == KEY_ADD) {
						al.add(val);
					} else {
						if (ik < al.size()) {
							al.set(ik, val);
						} else {
							for (int i = al.size(); i < ik; ++i) {
								al.add(null);
							}
							al.add(val);
						}
					}
					break;
				case Map:
					map.put(key, val);
					break;
				case Set:
					changed = set.add(val);
					break;
				case One:
					data.put(hAtt, val);
					break;
				}
			}

			return changed;
		}
	}

}
