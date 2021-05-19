package me.giskard.dust.runtime;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardException;

public interface DustRuntimeNotifier extends DustRuntimeConsts {
	class NotifDispatcher implements DustRuntimeBootConsts.DustNotifier {

		ArrayList<MiNDAgent> listeners = new ArrayList<>();

		public void setListener(boolean add, MiNDAgent agent) {
			if ( add ) {
				if ( !listeners.contains(agent) ) {
					listeners.add(agent);
				}
			} else {
				listeners.remove(agent);
			}
		}

		@Override
		public boolean notify(MiNDAccessCommand cmd, Object block, Object valOld, Object valNew, MiNDToken member,
				Object key) {
			boolean ret = false;

			if ( !listeners.isEmpty() ) {
				Object chg = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_DIALOG_CHANGE);
				
				if ( null == chg ) {
					// TODO replace with createIfMissing Tag on MTMEMBER_DIALOG_CHANGE
					chg = Giskard.access(MiNDAccessCommand.Get, MTTYPE_VISITINFO);
					Giskard.access(MiNDAccessCommand.Set, chg, MTMEMBER_ACTION_DIALOG, MTMEMBER_DIALOG_CHANGE);
				}

//				Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_DIALOG, MTMEMBER_ACTION_GPR04);
//				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_DIALOG);
				
				Giskard.access(MiNDAccessCommand.Set, block, chg, MTMEMBER_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Set, member, chg, MTMEMBER_VISITINFO_TOKEN);
				
				switch ( ((DustRuntimeToken) member).getCollType() ) {
				case Arr:
					Giskard.access(MiNDAccessCommand.Set, key, chg, MTMEMBER_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEMBER_VISITINFO_KEYMAP);
					break;
				case Map:
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEMBER_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, key, chg, MTMEMBER_VISITINFO_KEYMAP);
					break;
				default:
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEMBER_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEMBER_VISITINFO_KEYMAP);
					break;
				}

				if ( ((DustRuntimeToken) member).getValType() == MiNDValType.Link ) {
					Giskard.access(MiNDAccessCommand.Set, valOld, chg, MTMEMBER_VISITINFO_LINKOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, chg, MTMEMBER_VISITINFO_LINKNEW);
				} else {
					Giskard.access(MiNDAccessCommand.Set, valOld, chg, MTMEMBER_VISITINFO_VALOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, chg, MTMEMBER_VISITINFO_VALNEW);
				}
				
				for (MiNDAgent a : listeners) {
					try {
						a.process(MiNDAgentAction.Process);
					} catch (Throwable e) {
						GiskardException.swallow(e, "Change notification");
					}
				}

//				Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR04, MTMEMBER_ACTION_DIALOG);
			}

			return ret;
		}
	}
}
