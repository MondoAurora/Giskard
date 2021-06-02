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
				Object chg = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_DIALOG_CONTEXT_CHANGE);
				
				if ( null == chg ) {
					// TODO replace with createIfMissing Tag on MTMEM_DIALOG_CHANGE
					chg = Giskard.access(MiNDAccessCommand.Get, MTTYP_DIALOG_VISITINFO);
					Giskard.access(MiNDAccessCommand.Set, chg, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_DIALOG_CONTEXT_CHANGE);
				}

				Giskard.access(MiNDAccessCommand.Set, block, chg, MTMEM_GENERIC_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Set, member, chg, MTMEM_DIALOG_VISITINFO_TOKEN);
				
				switch ( ((DustRuntimeToken) member).getCollType() ) {
				case Arr:
					Giskard.access(MiNDAccessCommand.Set, key, chg, MTMEM_DIALOG_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEM_DIALOG_VISITINFO_KEYMAP);
					break;
				case Map:
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEM_DIALOG_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, key, chg, MTMEM_DIALOG_VISITINFO_KEYMAP);
					break;
				default:
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEM_DIALOG_VISITINFO_KEYARR);
					Giskard.access(MiNDAccessCommand.Set, null, chg, MTMEM_DIALOG_VISITINFO_KEYMAP);
					break;
				}

				if ( ((DustRuntimeToken) member).getValType() == MiNDValType.Link ) {
					Giskard.access(MiNDAccessCommand.Set, valOld, chg, MTMEM_DIALOG_VISITINFO_LINKOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, chg, MTMEM_DIALOG_VISITINFO_LINKNEW);
				} else {
					Giskard.access(MiNDAccessCommand.Set, valOld, chg, MTMEM_DIALOG_VISITINFO_VALOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, chg, MTMEM_DIALOG_VISITINFO_VALNEW);
				}
				
				for (MiNDAgent a : listeners) {
					try {
						a.mindAgentProcess();
					} catch (Throwable e) {
						GiskardException.swallow(e, "Change notification");
					}
				}
			}

			return ret;
		}
	}
}
