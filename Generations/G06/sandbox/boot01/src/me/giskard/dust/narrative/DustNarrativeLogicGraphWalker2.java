package me.giskard.dust.narrative;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings("rawtypes")
public class DustNarrativeLogicGraphWalker2 implements DustConsts.MindLogic, DustNarrativeConsts, DustMachineConsts, DustUtilsConsts {

	private static final Object END = new Object();

	class WalkIterator {
		Object[] items;
		int idx;

		WalkIterator(Collection src) {
			items = src.toArray();
		}

		WalkIterator(Map map) {
			items = map.entrySet().toArray();
		}

		public Object next() {
			return (idx < items.length) ? items[idx++] : END;
		}

		Object getHandle() {
			return null;
		}

		Object getKeyField() {
			return MIND_VISIT_KEY;
		}

		Object getCurrentKey() {
			return idx - 1;
		}
	}

	class WalkIteratorIdea extends WalkIterator {
		MindHandle h;

		WalkIteratorIdea(MindHandle h) {
			super((Collection) Dust.access(MIND_TAG_ACCESS_PEEK, Collections.EMPTY_LIST, h, MIND_IDEA_ATTS));

			this.h = h;
		}

		@Override
		public Object next() {
			Object key = super.next();

			return (END == key) ? key : Dust.access(MIND_TAG_ACCESS_PEEK, null, h, key);
		}

		Object getHandle() {
			return h;
		}

		Object getKeyField() {
			return MIND_VISIT_ATT;
		}

		Object getCurrentKey() {
			return items[idx - 1];
		}
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		MindHandle ret = MIND_TAG_RESULT_READ;
		WalkIterator wi;

		switch (a) {
		case Init:
			break;
		case Begin:
			break;
		case Process:
			wi = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE);
			int depth = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_PARAM, MISC_STACK, KEY_SIZE);
//			Dust.access(MIND_TAG_ACCESS_SET, depth, null, DUST_SELF, MISC_ATT_TARGET, MISC_DEPTH);

			if (null == wi) {
				MindHandle hItem = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE);
				MindHandle hAtt = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_ATT);

				Map initVal = Dust.access(MIND_TAG_ACCESS_PEEK, null, hItem, hAtt);

				wi = new WalkIterator(initVal);

				Dust.access(MIND_TAG_ACCESS_SET, wi, null, DUST_PARAM, MIND_VISIT_VALUE);

				Dust.log(null, a, "Initialising walk", wi.items);
				Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_BEGIN, null, DUST_SELF, MISC_ATT_TARGET);
			} else {

				Object s = wi.next();
				
				Object h = wi.getHandle();
				if ( null != h ) {
					Dust.access(MIND_TAG_ACCESS_SET, h, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_HANDLE);							
				}
				
				if (END == s) {
					if (0 == depth) {
						ret = MIND_TAG_RESULT_ACCEPT;
						Dust.access(MIND_TAG_ACCESS_RESET, null, null, DUST_PARAM, MISC_SEEN);
					} else {
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, wi.getKeyField());
						wi = Dust.access(MIND_TAG_ACCESS_DELETE, null, null, DUST_PARAM, MISC_STACK, 0);
						Dust.access(MIND_TAG_ACCESS_SET, wi, null, DUST_PARAM, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_END, null, DUST_SELF, MISC_ATT_TARGET);
					}
				} else {
					
					Object val;
					Object key;
					
					if (s instanceof Map.Entry) {
						Map.Entry e = (Map.Entry) s;
						val = e.getValue();
						key = e.getKey();
					} else {
						val = s;
						key = wi.getCurrentKey();
					}
					Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_KEY);
					Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_VALUE);
					Dust.access(MIND_TAG_ACCESS_SET, key, null, DUST_SELF, MISC_ATT_TARGET, wi.getKeyField());

					WalkIterator newIt = null;

					if (val instanceof Map) {
						newIt = new WalkIterator((Map) val);
					} else if (val instanceof Collection) {
						newIt = new WalkIterator((Collection) val);
					} else if (val instanceof MindHandle) {
						boolean newHandle = Dust.access(MIND_TAG_ACCESS_INSERT, val, null, DUST_PARAM, MISC_SEEN);
						if (newHandle) {
							newIt = new WalkIteratorIdea((MindHandle) val);
							Dust.access(MIND_TAG_ACCESS_SET, val, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_HANDLE);
							Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_ATT);
						}
					}

					if (null == newIt) {
						Dust.access(MIND_TAG_ACCESS_SET, val, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_PROCESS, null, DUST_SELF, MISC_ATT_TARGET);
					} else {
						Dust.access(MIND_TAG_ACCESS_INSERT, wi, null, DUST_PARAM, MISC_STACK, 0);
						Dust.access(MIND_TAG_ACCESS_SET, newIt, null, DUST_PARAM, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_SET, null, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_VALUE);
						Dust.access(MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACTION_BEGIN, null, DUST_SELF, MISC_ATT_TARGET);
					}
				}

				depth = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_PARAM, MISC_STACK, KEY_SIZE);
				Dust.access(MIND_TAG_ACCESS_SET, depth, null, DUST_SELF, MISC_ATT_TARGET, MISC_DEPTH);
			}
			break;
		case End:
			break;
		case Release:
			break;
		}

		return ret;
	}
}