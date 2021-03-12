package me.giskard.dust.runtime.machine;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;

public abstract class DustMachineControl implements DustMachineConsts, GiskardConsts.MiNDAgent, DustTokensMachine, DustTokensGeneric {

	DustMachineAgora.Dialog dialog;
	DustMachineAgora.Dialog.Invocation currChild;
	
	public static class Iteration extends DustMachineControl {
		int repMin;
		int repMax;

		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
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
						Giskard.selectByPath(MTMEMBER_ACTION_TARGET, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ONE);
						currChild = dialog.relay(MTMEMBER_ACTION_TARGET, null);
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

	public static class Sequence extends DustMachineControl {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				break;
			case Process:
				break;
			case Release:
				break;
			}
			return null;
		}
	}

	public static class Selection extends DustMachineControl {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				break;
			case Process:
				break;
			case Release:
				break;
			}
			return null;
		}
	}
}
