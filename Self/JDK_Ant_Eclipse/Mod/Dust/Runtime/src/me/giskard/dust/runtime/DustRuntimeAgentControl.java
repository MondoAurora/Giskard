package me.giskard.dust.runtime;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeMachine.Invocation;

public abstract class DustRuntimeAgentControl extends DustRuntimeConsts.RuntimeAgent {

	DustRuntimeMachine.Activity activity;
	DustRuntimeMachine.Invocation currChild;

	public static class Iteration extends DustRuntimeAgentControl {
		int repMin;
		int repMax;
		MiNDResultType repState;

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;
			switch ( action ) {
			case Init:
				repMin = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_RANGE_INT_MIN);
				repMax = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_RANGE_INT_MAX);
				break;
			case Begin:
				Giskard.access(MiNDAccessCommand.Set, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
				break;
			case Process:
				int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);

				if ( -1 == c ) {
					c = repMin;
				} else {
					++c;
				}

				if ( c == repMax ) {
					ret = MiNDResultType.AcceptPass;
				} else {
					Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
					Giskard.log(MiNDEventLevel.Info, "Repeat called", c);

					if ( null == currChild ) {
						Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
						currChild = activity.relay();
					} else {
						if ( GiskardUtils.isAgentReject(currChild.state) ) {
							ret = currChild.state;
							repState = MiNDResultType.Reject;
							break;
						}
						activity.push(currChild);
					}
					
					ret = MiNDResultType.Read;
					repState = MiNDResultType.Accept;
				}

				break;
			case End:
				ret = repState;
				break;
			case Release:
				break;
			}
			return ret;
		}
	}

	static abstract class Multi extends DustRuntimeAgentControl {
		ArrayList<DustRuntimeMachine.Invocation> children = new ArrayList<>();
		int count;

		void setCount() {
			count = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);
		}

		boolean relayChild() throws Exception {
			boolean ok = false;
			int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);

			++c;
			Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);

			if ( c < children.size() ) {
				activity.push(currChild = children.get(c));
				ok = true;
			} else if ( c < count ) {
				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, c);
				children.add(currChild = activity.relay());
				ok = true;
			}

			return ok;
		}
	}

	public static class Sequence extends Multi {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Init:
				setCount();
				break;
			case Begin:
				break;
			case Process:
				if ( null != currChild ) {
					ret = currChild.state;
					if ( !GiskardUtils.isAgentAccept(ret) ) {
						Giskard.access(MiNDAccessCommand.Set, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
						break;
					}
				}

				ret = relayChild() ? MiNDResultType.Read : MiNDResultType.AcceptPass;
				break;

			case End:
				break;
			case Release:
				break;
			}

			return ret;
		}
	}

	public static class Selection extends Multi {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Init:
				setCount();
				break;
			case Begin:
				break;
			case Process:
				if ( null != currChild ) {
					ret = currChild.state;
					if ( GiskardUtils.isAgentAccept(ret) ) {
						break;
					}
				}

				ret = relayChild() ? MiNDResultType.Read : MiNDResultType.Reject;
				break;

			case End:
				break;
			case Release:
				break;
			}

			return ret;
		}
	}

	@Override
	public void setInvocation(Invocation invocation_) {
		super.setInvocation(invocation_);
		activity = invocation_.activity;
	}
}
