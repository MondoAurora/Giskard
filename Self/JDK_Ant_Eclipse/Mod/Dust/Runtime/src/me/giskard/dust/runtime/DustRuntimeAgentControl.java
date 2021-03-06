package me.giskard.dust.runtime;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeMachine.Invocation;

public abstract class DustRuntimeAgentControl extends DustRuntimeConsts.RuntimeAgent {

	DustRuntimeMachine.Activity activity;
	DustRuntimeMachine.Invocation currChild;
	MiNDResultType lastState;
	boolean relayCalled;

	@Override
	public void mindAgentEnd() throws Exception {
	}

	public static class Iteration extends DustRuntimeAgentControl {
		int repMin;
		int repMax;
		
		@Override
		public void mindAgentBegin() throws Exception {
			repMin = Giskard.access(MiNDAccessCommand.Get, 0, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_RANGE_INTMIN);
			repMax = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_RANGE_INTMAX);

			Giskard.access(MiNDAccessCommand.Set, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
			relayCalled = false;
		}

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;
//			int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
			int c;
			
			if ( MiNDResultType.Read == lastState ) {
				c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
			} else {
				Giskard.access(MiNDAccessCommand.Set, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
				relayCalled = false;
				c = -1;
			}

			++c;

			if ( c == repMax ) {
				ret = MiNDResultType.Accept;
			} else {
				ret = MiNDResultType.Read;

				Giskard.access(MiNDAccessCommand.Set, c, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
				Giskard.log(MiNDEventLevel.Info, "Repeat called", c);

				if ( null == currChild ) {
					Object act = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ONE);
					currChild = activity.relay(act);
				} else {
					if ( relayCalled && GiskardUtils.isAgentReject(currChild.state) ) {
						ret = ((repMin <= c) && ((-1 == repMax) || (c <= repMax))) ? MiNDResultType.Accept : MiNDResultType.Reject;
					} else {
						activity.push(currChild);
					}
				}

				relayCalled = true;
			}
			lastState = ret;

			return ret;
		}
	}

	static abstract class Multi extends DustRuntimeAgentControl {
		ArrayList<DustRuntimeMachine.Invocation> children = new ArrayList<>();
		int count;

		void setCount() {
			count = Giskard.access(MiNDAccessCommand.Get, 0, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ARR, KEY_SIZE);
		}

		boolean relayChild() throws Exception {
			boolean ok = false;
			int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);

			++c;
			Giskard.access(MiNDAccessCommand.Set, c, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);

			if ( c < children.size() ) {
				activity.push(currChild = children.get(c));
				ok = true;
			} else if ( c < count ) {
				Object act = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ARR, c);
				children.add(currChild = activity.relay(act));
				ok = true;
			}

			return ok;
		}

		@Override
		public void mindAgentBegin() throws Exception {
			setCount();
			Giskard.access(MiNDAccessCommand.Set, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
			currChild = null;
		}
	}

	public static class Sequence extends Multi {
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			ret = (null == currChild) ? MiNDResultType.Accept : currChild.state;

			switch ( ret ) {
			case Accept:
				// the member reported done, start from beginning
//					Giskard.access(MiNDAccessCommand.Set, -1, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_ITERATOR_INDEX);
//					break;
//				case AcceptPass:
				// child done its job, next member please...
				break;
			case Wait:
			case Read:
			case AcceptRead:
				// the child invocation should loop in these cases
				GiskardException.wrap(null, "How can I get here?");
				break;
			case Notimplemented:
			case Reject:
				// do nothing, the sequence should report the child failure
				break;
			}

			if ( GiskardUtils.isAgentAccept(ret) ) {
				lastState = ret = relayChild() ? MiNDResultType.Read : MiNDResultType.Accept;
			} else {
				lastState = ret;
			}

			return ret;
		}
	}

	public static class Selection extends Multi {
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			if ( null != currChild ) {
				ret = currChild.state;
				if ( GiskardUtils.isAgentAccept(ret) ) {
					lastState = ret;
					return ret;
				}
			}

			ret = relayChild() ? MiNDResultType.Read : MiNDResultType.Reject;
			lastState = ret;

			return ret;
		}
	}

	@Override
	public void setInvocation(Invocation invocation_) {
		super.setInvocation(invocation_);
		activity = invocation_.activity;
	}
}
