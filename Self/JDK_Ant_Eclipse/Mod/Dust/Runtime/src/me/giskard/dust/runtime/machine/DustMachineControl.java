package me.giskard.dust.runtime.machine;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.machine.DustMachineAgora.Invocation;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tools.GisToolsTokenTranslator;

public abstract class DustMachineControl implements DustMachineConsts, GiskardConsts.MiNDAgent, DustTokensMachine, DustTokensGeneric, DustTokensMind {

	DustMachineAgora.Dialog dialog;
	DustMachineAgora.Invocation currChild;
	
	public static class Iteration extends DustMachineControl {
		int repMin;
		int repMax;

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.ACCEPT;
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
					ret = MiNDResultType.ACCEPT_PASS;
				} else {
					Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
					Giskard.log(MiNDEventLevel.INFO, "Repeat called", c);
					
					if ( null == currChild ) {
						Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
						currChild = dialog.relay();
					} else {
						dialog.push(currChild);
					}
					ret = MiNDResultType.READ;
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

	static abstract class Multi extends DustMachineControl {
		ArrayList<Invocation> children = new ArrayList<>();
		
		boolean relayChild() throws Exception {
			boolean ok = false;
			int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
			
			++c;
			Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);

			if ( c < children.size() ) {
				dialog.push(currChild = children.get(c));
				ok = true;
			} else if ( null != Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, c) ) {
				children.add(currChild = dialog.relay());
				ok = true;
			}
			
			return ok;
		}
	}

	public static class Sequence extends Multi {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.ACCEPT;
			
			switch ( action ) {
			case Init:
				break;
			case Begin:
				break;
			case Process:
				if ( null != currChild ) {
					ret = getCurrResp(MiNDResultType.ACCEPT);
					if ( !GiskardUtils.isAgentAccept(ret) ) {
						break;
					}
				}
				
				ret = relayChild() ? MiNDResultType.READ : MiNDResultType.ACCEPT_PASS;
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
			MiNDResultType ret = MiNDResultType.ACCEPT;
			
			switch ( action ) {
			case Init:
				break;
			case Begin:
				break;
			case Process:
				if ( null != currChild ) {
					ret = getCurrResp(MiNDResultType.READ);
					if ( GiskardUtils.isAgentAccept(ret) ) {
						break;
					}
				}
				
				ret = relayChild() ? MiNDResultType.READ : MiNDResultType.REJECT;
				break;
				
			case End:
				break;
			case Release:
				break;
			}
			
			return ret;
		}
	}
	
	public MiNDResultType getCurrResp(MiNDResultType defVal) {
		MiNDResultType ret = defVal;
		MiNDToken resp = Giskard.access(MiNDAccessCommand.Get, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
		if ( null != resp ) {
			ret = (MiNDResultType) GisToolsTokenTranslator.toEnum(resp);
		}
		return ret;
	}
}
