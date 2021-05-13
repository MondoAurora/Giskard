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

	public static class ListAll extends DustRuntimeAgentData {
		DustRuntimeDataContext ctx;
		Iterator<Integer> it;
		Set<Integer> visited = new TreeSet<>();
		MiNDResultType procRet;

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Init:
				visited.clear();
				ret = optSelectContext(getInvocation().runningActor.ctx) ? MiNDResultType.Accept : MiNDResultType.Reject;
				break;
			case Begin:
				break;
			case Process:
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
						Giskard.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
						ret = MiNDResultType.Reject;
						break;
					}
				}

				procRet = ret;
				Giskard.log(MiNDEventLevel.Info, "List returning", h, ret);

				break;
			case End:
				ret = procRet;
				break;
			case Release:
				visited.clear();
				ctx = null;
				it = null;
				break;
			}
			return ret;
		}

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
//				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
//				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
//				MiNDToken t = MTMEMBER_ACTION_GPR01;
//				Integer h = (Integer) getInvocation().runningActor.ctx.rootBlock.localData.get(t);
				
				Integer h = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
				if ( null == h ) {
					h = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
				}

//				Integer h = ((DustRuntimeToken)t).getEntityHandle();
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
							key = null;

							if ( tok.getCollType() == MiNDCollType.One ) {
								val = block.getValue(tok);
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
		MiNDResultType procRet;

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Begin:
				break;
			case Process:
				if ( currStep.step() ) {
					currStep.store(MiNDEventLevel.Info);
					ret = MiNDResultType.Accept;
				} else {
					ret = MiNDResultType.Reject;
				}
				procRet = ret;
				break;
			case End:
				ret = procRet;
			default:
				break;
			}
			return ret;
		}
	}

	public static class Read extends DustRuntimeAgentData {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Begin:
				Giskard.access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				break;
			case Process:
				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				ret = MiNDResultType.AcceptRead;
				break;
			default:
				break;
			}
			return ret;
		}
	}

	public static class Collect extends DustRuntimeAgentData {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Begin:
				Giskard.access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				break;
			case Process:
				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				ret = MiNDResultType.AcceptRead;
				break;
			default:
				break;
			}
			return ret;
		}
	}
}
