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
				Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTMEMBER_ACTION_GPR04);

				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_PARAM);
				Giskard.access(MiNDAccessCommand.Set, block, MTMEMBER_ACTION_PARAM, MTMEMBER_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Set, member, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_TOKEN);
				Giskard.access(MiNDAccessCommand.Set, key, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_KEY);

				if ( ((DustRuntimeToken) member).getValType() == MiNDValType.Link ) {
					Giskard.access(MiNDAccessCommand.Set, valOld, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW);
				} else {
					Giskard.access(MiNDAccessCommand.Set, valOld, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_VALOLD);
					Giskard.access(MiNDAccessCommand.Set, valNew, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_VALNEW);
				}
				for (MiNDAgent a : listeners) {
					try {
						a.process(MiNDAgentAction.Process);
					} catch (Throwable e) {
						GiskardException.swallow(e, "Change notification");
					}
				}

				Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR04, MTMEMBER_ACTION_PARAM);
			}

			return ret;
		}
	}
}
