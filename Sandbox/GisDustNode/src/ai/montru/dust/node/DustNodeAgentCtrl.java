package ai.montru.dust.node;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts.GiskardAgent;
import ai.montru.utils.MontruUtils;

public abstract class DustNodeAgentCtrl implements DustNodeConsts, GiskardAgent {

	public static class DustVisitor extends DustNodeAgentCtrl {

		enum CutMode {
			Once, Recursion, Free
		}

		@SuppressWarnings({"unchecked", "rawtypes"})
		class VisitCursor {
			DustNodeEntityRef ref;

			Map<Object, Object> entityData;
			Iterator<Object> itKey;
			DustNodeEntityRef refKey;
			boolean keyChange;

			Iterator<Object> itVal;
			boolean isMap;
			Object key;
			Object val;

			public VisitCursor(DustNodeEntityRef ref) {
				this.ref = ref;

				entityData = DustNodeAgentRuntime.getRuntime().getEntity(ref, null, false);
				itKey = entityData.keySet().iterator();
			}

			boolean next() {
				if ( optNextVal() ) {
					return true;
				}

				keyChange = true;

				while (itKey.hasNext()) {
					refKey = (DustNodeEntityRef) itKey.next();
					val = entityData.get(refKey);

					if ( val instanceof Map ) {
						isMap = true;
						itVal = ((Map) val).entrySet().iterator();
					} else if ( val instanceof Iterable ) {
						isMap = false;
						itVal = ((Iterable) val).iterator();
					} else {
						itVal = null;
						return true;
					}

					if ( optNextVal() ) {
						return true;
					}
				}

				return false;
			}

			boolean isColl() {
				return null != itVal;
			}

			private boolean optNextVal() {
				if ( (null == itVal) || !itVal.hasNext() ) {
					return false;
				}

				keyChange = false;
				if ( isMap ) {
					Map.Entry<Object, Object> e = (Entry<Object, Object>) itVal.next();
					key = e.getKey();
					val = e.getValue();
				} else {
					val = itVal.next();
				}

				return true;
			}
		}

		CutMode cutMode;

		Set<DustNodeEntityRef> seen;
		Stack<VisitCursor> callStack;

		@Override
		public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
			GiskardResponse ret = GiskardResponse.Read;
			DustNodeEntityRef ref;
			VisitCursor vc;

			switch ( action ) {
			case Init:
				break;
			case Begin:
				seen = new HashSet<>();
				callStack = new Stack<>();

				ref = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_TARGET);
				if ( null != ref ) {
					seen.add(ref);
					callStack.push(new VisitCursor(ref));
				} else {
					ret = GiskardResponse.Reject;
				}
				break;
			case Process:
				vc = callStack.peek();
				ref = vc.refKey;
				boolean isColl = vc.isColl();

				StringBuilder prefix = new StringBuilder();
				for (int i = callStack.size(); i-- > 0;) {
					prefix.append("__");
				}

				if ( vc.next() ) {
					if ( vc.keyChange ) {
						if ( null != ref ) {
							if ( isColl ) {
								MontruUtils.dumpLine(prefix, "collClose");
							}
							MontruUtils.dumpLine(prefix, "keyClose", ref);
						}
						MontruUtils.dumpLine(prefix, "keyStart", vc.refKey);

						if ( vc.isColl() ) {
							MontruUtils.dumpLine(prefix, "collStart");
						}
					}

					if ( vc.isMap ) {
						MontruUtils.dumpLine(prefix, "__", vc.key, "->", vc.val);
					} else {
						MontruUtils.dumpLine(prefix, "__", vc.val);
					}
					
					if ( (vc.val instanceof DustNodeEntityRef) && (seen.add((DustNodeEntityRef) vc.val)) ) {
						callStack.push(new VisitCursor((DustNodeEntityRef) vc.val));
					}
				} else {
					if ( null != ref ) {
						if ( isColl ) {
							MontruUtils.dumpLine(prefix, "collClose");
						}
						MontruUtils.dumpLine(prefix, "keyClose", ref);
					}

					callStack.pop();
					if ( callStack.isEmpty() ) {
						ret = GiskardResponse.Accept;
					}
				}
				break;
			case End:
				seen.clear();
				break;
			case Release:
				break;
			}

			return ret;
		}

	}

	public static class DustIterator extends DustNodeAgentCtrl {

		@Override
		public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
			GiskardResponse ret = GiskardResponse.Read;

			Integer min;
			Integer max;
			Integer idx;
			Integer cnt;

			switch ( action ) {
			case Init:
				break;
			case Begin:
				Giskard.access(GiskardAccess.Set, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);

				idx = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);
				if ( null != idx ) {
					max = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMAX);
					if ( (null != max) && (0 > max.compareTo(idx)) ) {
						return GiskardResponse.Reject;
					}
				}

				min = Giskard.access(GiskardAccess.Peek, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMIN);
				Giskard.access(GiskardAccess.Set, min, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);

				break;
			case Process:
				idx = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);

				Giskard.broadcastEvent(null, "Iterator",
						Giskard.access(GiskardAccess.Peek, "huh?", null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_ID), "index", idx);

				cnt = Giskard.access(GiskardAccess.Peek, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);
				Giskard.access(GiskardAccess.Set, ++cnt, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);

				Giskard.access(GiskardAccess.Set, ++idx, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);
				max = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMAX);
				ret = (0 <= max.compareTo(idx)) ? GiskardResponse.Read : GiskardResponse.Accept;

				break;
			case End:
				break;
			case Release:
				break;
			}

			return ret;
		}

	}

}
