package me.giskard.dust.runtime;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.tokens.DustTokens;

public abstract class DustRuntimeAgentData extends DustRuntimeConsts.RuntimeAgent {

	@Override
	public void mindAgentBegin() throws Exception {
	}

	@Override
	public void mindAgentEnd() throws Exception {
	}

	public static class ListAll extends DustRuntimeAgentData {
		DustRuntimeDataContext ctx;
		Iterator<Integer> it;
		Set<Integer> visited;

		@Override
		public void mindAgentBegin() throws Exception {
			if ( null == visited ) {
				visited = new TreeSet<>();
				optSelectContext(getInvocation().runningActor.ctx);
			}
//			visited.clear();
		}

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			Integer h = null;
			ret = MiNDResultType.Reject;

			while ((null != it) && it.hasNext()) {
				h = it.next();

				if ( (HANDLE_NULL < h) && visited.add(h) ) {
					Giskard.access(MiNDAccessCommand.Set, h, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
					ret = MiNDResultType.Accept;
					break;
				}

				if ( !it.hasNext() && !optSelectContext(ctx.parentCtx) ) {
					ret = MiNDResultType.Reject;
					break;
				}
			}
			
			if ( MiNDResultType.Reject == ret ) {
				Giskard.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);				
				Giskard.log(MiNDEventLevel.Info, "List finished");				
			} else {
				Giskard.log(MiNDEventLevel.Info, "List returning", h, ret);				
			}

			return ret;
		}

//		@Override
//		public MiNDResultType mindAgentEnd() throws Exception {
//			visited.clear();
//			ctx = null;
//			it = null;
//			return null;
//		}

		boolean optSelectContext(DustRuntimeDataContext ctx_) {
			boolean ret = false;

			it = null;
			for (ctx = ctx_; null != ctx;) {
				it = ctx.entities.keySet().iterator();

				if ( it.hasNext() ) {
					return true;
				}

				ctx = ctx.parentCtx;
			}

			return ret;
		}
	}

	public static class Visit extends DustRuntimeAgentData {
		class VisitStep {
			Integer current;

			DustRuntimeDataBlock block;
			DustRuntimeTokenManager tm;
			Iterator<Integer> itToken;
			Iterator<?> itColl;

			DustRuntimeToken tok;
			Object val;
			Object key;

			public VisitStep() {
				current = null;
			}

			boolean stepColl() {
				if ( null != itColl ) {
					if ( itColl.hasNext() ) {
						val = itColl.next();

						switch ( tok.getCollType() ) {
						case Arr:
						case Set:
							key = (null == key) ? 0 : ((Integer) key) + 1;
							break;
						case Map:
							Map.Entry<?, ?> e = (Map.Entry<?, ?>) val;
							val = e.getValue();
							key = e.getKey();
							break;
						default:
							break;
						}

						return true;
					} else {
						itColl = null;
					}
				}

				return false;
			}

			boolean step() {
				Integer h = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
				if ( null == h ) {
					h = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
				}

				if ( !GiskardUtils.isEqual(current, h) ) {
					current = h;
					block = getInvocation().runningActor.ctx.getEntity(current);
					tm = block.ctx.getTokenManager();
					itToken = block.getKeyIter();
					itColl = null;
				}

				if ( !stepColl() ) {
					while (null != itToken) {
						if ( itToken.hasNext() ) {
							tok = tm.getTokenByHandle(itToken.next());
							val = block.getValue(tok);
							key = null;

							if ( tok.getCollType() == MiNDCollType.One ) {
								return true;
							} else {
								itColl = ((DustRuntimeDataCollection<?>) val).getIterator();
								if ( stepColl() ) {
									return true;
								}
							}
						} else {
							itToken = null;
							current = null;
							block = null;
							return false;
						}
					}
				}

				return true;
			}

			public void store(MiNDEventLevel optLogLevel) {
				DustTokens.setValue(MTMEMBER_ACTION_DIALOG, tok, val, key);

				if ( null != optLogLevel ) {
					Giskard.log(MiNDEventLevel.Info, "VisitStep", current, tok.getId(), val, key);
				}
			}
		}

		VisitStep currStep = new VisitStep();
		Stack<VisitStep> visitStack;

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			if ( currStep.step() ) {
				currStep.store(MiNDEventLevel.Info);
				ret = MiNDResultType.Accept;
			} else {
				ret = MiNDResultType.Reject;
			}
			return ret;
		}
	}

	public static class Read extends DustRuntimeAgentData {
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			return ret;
		}
	}

	public static class Collect extends DustRuntimeAgentData {

		@Override
		public void mindAgentBegin() throws Exception {
			Giskard.access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
		}

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			Object o = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
			Giskard.access(MiNDAccessCommand.Add, o, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
			ret = MiNDResultType.AcceptRead;

			return ret;
		}
	}
}
