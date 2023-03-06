package me.giskard.dust.mod.brain.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import me.giskard.dust.mod.brain.DustBrainConsts;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustBrainKnowledge implements DustBrainConsts, DustBrainConsts.KnowledgeItem {

	Map<BrainHandle, Object> data = new TreeMap<>();
	
	@Override
	public String toString() {
		return data.toString();
	}

	public <RetType> RetType access(MindAccess cmd, BrainHandle hMember, MindColl coll, Object key, Object val, KnowledgeConnector kc) {
		Object ret = data.get(hMember);
		Object old = ret;
		boolean changed = false;

		if ( null == ret ) {
			switch ( cmd ) {
			case Peek:
				ret = val;
				break;
			case Check:
				ret = (null == val);
				break;
			case Commit:
				break;
			case Delete:
				// do nothing
				break;
			case Get:
				ret = kc.create(hMember, key);
				break;
			case Insert:
			case Set:
				ret = val;
				break;
			}

			if ( cmd.add && (null != ret) ) {
				Object o;

				switch ( coll ) {
				case Arr:
					ArrayList a = new ArrayList();
					old = null;
					extendArr(cmd, a, false, (Integer) key, ret);
					o = a;
					break;
				case Map:
					Map m = new HashMap();
					m.put(key, ret);
					o = m;
					break;
				default:
					o = ret;
					break;
				}

				data.put(hMember, o);
				changed = true;
			}
		} else {
			switch ( coll ) {
			case One: {
				switch ( cmd ) {
				case Peek:
				case Get:
					// do nothing
					break;
				case Check:
					ret = GiskardUtils.isEqual(val, ret);
					break;
				case Delete:
					data.remove(hMember);
					changed = null != old;
					break;
				case Insert:
				case Set:
					if ( !GiskardUtils.isEqual(val, ret) ) {
						ret = data.put(hMember, val);
						changed = true;
					}
					break;
				case Commit:
					break;
				}
			}
				break;
			case Set: {
				Set s = (ret instanceof Set) ? (Set) ret : null;

				switch ( cmd ) {
				case Peek:
				case Get:
					if ( (null != s) && !s.isEmpty() ) {
						ret = s.iterator().next();
					} else {
						ret = val;
					}
					break;
				case Check:
					ret = (null == s) ? GiskardUtils.isEqual(val, ret) : s.contains(val);
					break;
				case Commit:
					break;
				case Delete:
					if ( null == val ) {
						changed = (null == s) ? (null != ret) : !s.isEmpty();
						data.remove(hMember);
					} else {
						if ( null != s ) {
							changed = s.remove(val);
						} else if ( GiskardUtils.isEqual(val, ret) ) {
							changed = true;
							data.remove(hMember);
						}
					}
					break;
				case Insert:
				case Set:
					if ( !GiskardUtils.isEqual(val, ret) && ((null == s) || !s.contains(val)) ) {
						changed = true;
						if ( null == s ) {
							s = new HashSet<>();
							s.add(ret);
							data.put(hMember, s);
						}
						s.add(val);
					}
					break;
				}
			}
				break;

			case Map: {
				Map m = (Map) ret;

				switch ( cmd ) {
				case Peek:
					ret = m.getOrDefault(key, val);
					break;
				case Check:
					ret = GiskardUtils.isEqual(val, m.get(key));
					break;
				case Commit:
					break;
				case Delete:
					if ( null == key ) {
						data.remove(hMember);
						changed = !m.isEmpty();
					} else {
						changed = (null != m.remove(key));
					}
					break;
				case Get:
					ret = m.get(key);
					if ( null == ret ) {
						ret = kc.create(hMember, key);
						if ( null != ret ) {
							m.put(key, ret);
							old = null;
							changed = true;
						}
					}
					break;
				case Insert:
				case Set:
					old = m.put(key, val);
					changed = !GiskardUtils.isEqual(val, old);
					break;
				}
			}
				break;
			case Arr: {
				ArrayList a = (ArrayList) ret;
				Integer idx = (Integer) key;
				boolean idxValid = (0 <= idx) && (idx < a.size());

				switch ( cmd ) {
				case Peek:
					if ( idxValid ) {
						ret = a.get(idx);
					}
					break;
				case Check:
					ret = (null == key) ? a.contains(val) : idxValid ? GiskardUtils.isEqual(val, a.get(idx)) : false;
					break;
				case Commit:
					break;
				case Delete:
					if ( null == key ) {
						changed = !a.isEmpty();
						data.remove(hMember);
					} else {
						if ( null != a ) {
							if ( idxValid ) {
								a.remove(idx);
								changed = true;
							}
						}
					}
					break;
				case Get:
					if ( null != a ) {
						if ( idxValid ) {
							ret = a.get(idx);
						} else {
							ret = kc.create(hMember, key);
							if ( null != ret ) {
								old = null;
								changed = extendArr(cmd, a, idxValid, idx, ret);
							}
						}
					}
					break;
				case Insert:
					old = null;
					changed = extendArr(cmd, a, idxValid, idx, ret);
					break;
				case Set:
					old = idxValid ? a.get(idx) : null;
					changed = extendArr(cmd, a, idxValid, idx, ret);
					break;
				}
			}
				break;
			}

			if ( changed && (null != kc) ) {
				kc.notifyChange(cmd, hMember, key, old, ret);
			}
		}

		return (RetType) ret;
	}

	boolean extendArr(MindAccess cmd, ArrayList a, boolean idxValid, Integer idx, Object val) {
		boolean change = false;

		if ( KEY_ADD == idx ) {
			a.add(val);
			change = true;
		} else {
			if ( ! idxValid ) {
				for ( int i = a.size(); i < idx; ++i ) {
					a.add(null);
				}
				a.add(val);
				change = true;
			} else {
				if ( cmd == MindAccess.Insert ) {
					a.add(idx, val);
					change = true;
				} else {
					if ( !GiskardUtils.isEqual(val, a.get(idx)) ) {
						a.set(idx, val);
						change = true;
					}
				}
			}
		}
		
		return change;
	}
}