package me.giskard.dust.runtime;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeMachine.Invocation;

public abstract class DustRuntimeAgentControl extends DustRuntimeConsts.RuntimeAgent {

	DustRuntimeMachine.Activity activity;
	DustRuntimeMachine.Invocation currChild;
	MiNDResultType lastProc;
	
	public static class Iteration extends DustRuntimeAgentControl {
		int repMin;
		int repMax;

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
				boolean callBegin = false;
				
				if ( -1 == c ) {
					c = repMin;
					callBegin = true;
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
						currChild = activity.relay(callBegin);
					} else {
						activity.push(currChild);
					}
					ret = MiNDResultType.Read;
				}
				
				lastProc = ret;
				break;
			case End:
				ret = lastProc;
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
				children.add(currChild = activity.relay(true));
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
					ret = getCurrResp(MiNDResultType.Accept);
					if ( !GiskardUtils.isAgentAccept(ret) ) {
						lastProc = ret;
						break;
					}
				}
				
				lastProc = ret = relayChild() ? MiNDResultType.Read : MiNDResultType.AcceptPass;
				break;
				
			case End:
				ret = lastProc;
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
					ret = getCurrResp(MiNDResultType.Read);
					if ( GiskardUtils.isAgentAccept(ret) ) {
						lastProc = ret;
						break;
					}
				}
				
				lastProc = ret = relayChild() ? MiNDResultType.Read : MiNDResultType.Reject;
				break;
				
			case End:
				ret = lastProc;
				break;
			case Release:
				break;
			}
			
			return ret;
		}
	}
	
	public MiNDResultType getCurrResp(MiNDResultType defVal) {
		MiNDResultType ret = currChild.state;
//		MiNDToken resp = Giskard.access(MiNDAccessCommand.Get, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
//		if ( null != resp ) {
//			ret = (MiNDResultType) GisToolsTokenTranslator.toEnum(resp);
//		}
		return ret;
	}
	
	@Override
	public void setInvocation(Invocation invocation_) {
		super.setInvocation(invocation_);
		activity = invocation_.activity;
	}
}
