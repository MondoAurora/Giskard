package me.giskard.dust.narrative;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineBootConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustNarrativeLogicGraphWalker implements DustConsts.MindLogic, DustNarrativeConsts, DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	private static final Object END = new Object();

	class WalkIterator {
		MindHandle h;
		Object[] items;
		int idx;

		WalkIterator(Object src) {
			if (src instanceof Collection) {
				items = ((Collection) src).toArray();
			} else if (src instanceof Map) {
				items = ((Map) src).entrySet().toArray();
			} else if (src instanceof MindHandle) {
				h = (MindHandle) src;
				items = ((Collection) Dust.access(MIND_TAG_ACCESS_PEEK, Collections.EMPTY_LIST, h, MIND_IDEA_ATTS)).toArray();
			}
		}

		public Object next() {
			Object ret = END;

			if (idx < items.length) {
				ret = items[idx++];
				if (null != h) {
					ret = Dust.access(MIND_TAG_ACCESS_PEEK, null, h, ret);
				}
			}

			return ret;
		}

		Object getHandle() {
			return h;
		}

		Object getKeyField() {
			return (null == h) ? MIND_VISIT_KEY : MIND_VISIT_ATT;
		}

		Object getCurrentKey() {
			return (null == h) ? idx - 1 : items[idx - 1];
		}
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		MindHandle ret = MIND_TAG_RESULT_READ;
		WalkIterator wi = null;

		switch (a) {
		case Init:
			break;
		case Begin:
			break;
		case Process:

			Collection queue = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_SELF, MISC_PROC_QUEUE);

			if (null == queue) {
				MindHandle hItem = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_HANDLE);
				MindHandle hAtt = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_ATT);

				Object initVal = Dust.access(MIND_TAG_ACCESS_PEEK, null, hItem, hAtt);

				if (initVal instanceof Collection) {
					queue = new ArrayList((Collection) initVal);
				} else if (initVal instanceof Map) {
					queue = new ArrayList(((Map) initVal).values());
				} else if (initVal instanceof MindHandle) {
					queue = new ArrayList();
					queue.add(initVal);
				}

				if (DustUtils.isEmpty(queue)) {
					return MIND_TAG_RESULT_PASS;
				} else {
					Dust.access(MIND_TAG_ACCESS_SET, queue, null, DUST_AGENT_SELF, MISC_PROC_QUEUE);
					Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_BEGIN, null, DUST_AGENT_SELF, MISC_GEN_TARGET);
					return ret;
				}
			}

			Object key;
			Object val = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_VALUE);
			WalkIterator newIt = null;

			if (val instanceof MindHandle) {
				boolean seen = Dust.access(MIND_TAG_ACCESS_CHECK, val, null, DUST_AGENT_SELF, MISC_PROC_SEEN);
				boolean queued = Dust.access(MIND_TAG_ACCESS_CHECK, val, null, DUST_AGENT_SELF, MISC_PROC_QUEUE);
				if (!seen && !queued) {
					boolean dfs = Dust.access(MIND_TAG_ACCESS_CHECK, MIND_TAG_SEARCH_DEPTHFIRST, null, DUST_AGENT_SELF, MIND_TAG_SEARCH);
					if (dfs) {
						newIt = new WalkIterator(val);
						Dust.access(MIND_TAG_ACCESS_SET, val, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_HANDLE);
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_ATT);
					} else {
						Dust.access(MIND_TAG_ACCESS_INSERT, val, null, DUST_AGENT_SELF, MISC_PROC_QUEUE, KEY_ADD);
					}
				}
			}

			Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_KEY);
			Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_VALUE);

			wi = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE);

			if (null == newIt) {

				if (null == wi) {
					MindHandle hItem = null;
					boolean repeat;
					do {
						hItem = Dust.access(MIND_TAG_ACCESS_DELETE, null, null, DUST_AGENT_SELF, MISC_PROC_QUEUE, 0);
						repeat = (null != hItem) && !(boolean) Dust.access(MIND_TAG_ACCESS_INSERT, hItem, null, DUST_AGENT_SELF, MISC_PROC_SEEN);
					} while (repeat);

					if (null == hItem) {
						if (null == Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_HANDLE)) {
							ret = MIND_TAG_RESULT_ACCEPT;
						} else {
							Dust.access(MIND_TAG_ACCESS_RESET, null, null, DUST_AGENT_PARAM, MISC_PROC_SEEN);
							Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_END, null, DUST_AGENT_SELF, MISC_GEN_TARGET);
						}
						
						return ret;
					} else {
						newIt = new WalkIterator(hItem);
						Dust.access(MIND_TAG_ACCESS_SET, hItem, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_HANDLE);
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_ATT);
					}
				} else {

					Object s = wi.next();

					Object h = wi.getHandle();
					if (null != h) {
						Dust.access(MIND_TAG_ACCESS_SET, h, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_HANDLE);
					}

					if (END == s) {
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_AGENT_SELF, MISC_GEN_TARGET, wi.getKeyField());
						wi = Dust.access(MIND_TAG_ACCESS_DELETE, null, null, DUST_AGENT_PARAM, MISC_PROC_STACK, 0);
						Dust.access(MIND_TAG_ACCESS_SET, wi, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_END, null, DUST_AGENT_SELF, MISC_GEN_TARGET);
						int depth = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_AGENT_PARAM, MISC_PROC_STACK, KEY_SIZE);
						Dust.access(MIND_TAG_ACCESS_SET, depth, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MISC_PROC_DEPTH);

						return ret;
					} else {
						if (s instanceof Map.Entry) {
							Map.Entry e = (Map.Entry) s;
							val = e.getValue();
							key = e.getKey();
						} else {
							val = s;
							key = wi.getCurrentKey();
						}
						Dust.access(MIND_TAG_ACCESS_SET, key, null, DUST_AGENT_SELF, MISC_GEN_TARGET, wi.getKeyField());
						
						if ( key == MIND_UNIT_IDEAS ) {
							Dust.log(null, "handling ideas", h, val);
						}
						
						if ( val instanceof MindIdea ) {
							Object ih = ((Map)val).get(MIND_IDEA_HANDLE);
							if ( null != ih ) {
								val = ih;
							}
						}

						if ((val instanceof Map) || (val instanceof Collection)) {
							newIt = new WalkIterator(val);
						}
					}
				}
			}

			if (null == newIt) {
				Dust.access(MIND_TAG_ACCESS_SET, val, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MIND_VISIT_VALUE);
				Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_PROCESS, null, DUST_AGENT_SELF, MISC_GEN_TARGET);
			} else {
				if (null != wi) {
					Dust.access(MIND_TAG_ACCESS_INSERT, wi, null, DUST_AGENT_PARAM, MISC_PROC_STACK, 0);
				}
				Dust.access(MIND_TAG_ACCESS_SET, newIt, null, DUST_AGENT_PARAM, MIND_VISIT_VALUE);
				Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_BEGIN, null, DUST_AGENT_SELF, MISC_GEN_TARGET);
			}

			int depth = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_AGENT_PARAM, MISC_PROC_STACK, KEY_SIZE);
			Dust.access(MIND_TAG_ACCESS_SET, depth, null, DUST_AGENT_SELF, MISC_GEN_TARGET, MISC_PROC_DEPTH);

			break;
		case End:
			break;
		case Release:
			break;
		}

		return ret;
	}
}