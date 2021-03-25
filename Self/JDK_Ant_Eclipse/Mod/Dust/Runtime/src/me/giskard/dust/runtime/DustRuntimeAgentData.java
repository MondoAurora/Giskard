package me.giskard.dust.runtime;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import me.giskard.Giskard;

public abstract class DustRuntimeAgentData extends DustRuntimeConsts.RuntimeAgent {

	public static class FindAll extends DustRuntimeAgentData {
		DustRuntimeContext ctx;
		Iterator<Integer> it;
		Set<Integer> visited = new TreeSet<>();

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.ACCEPT;

			switch ( action ) {
			case Init:
				break;
			case Begin:
				visited.clear();
				ret = optSelectContext(getInvocation().ctx) ? MiNDResultType.ACCEPT : MiNDResultType.REJECT;
				break;
			case Process:
				while ( (null != it) && it.hasNext()) {
					Integer h = it.next();

					if ( (HANDLE_NULL < h) && visited.add(h) ) {
						Giskard.access(MiNDAccessCommand.Set, h, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
						ret = MiNDResultType.ACCEPT_PASS;
						break;
					}

					if ( !it.hasNext() && !optSelectContext(ctx.parentCtx) ) {
						Giskard.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
						ret = MiNDResultType.REJECT;
						break;
					}
				}
				break;
			case End:
			case Release:
				visited.clear();
				ctx = null;
				it = null;
				break;
			}
			return ret;
		}

		boolean optSelectContext(DustRuntimeContext ctx_) {
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

	public static class Collect extends DustRuntimeAgentData {
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.ACCEPT;

			switch ( action ) {
			case Begin:
				Giskard.access(MiNDAccessCommand.Del, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				break;
			case Process:
				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ONE);
				Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_DIALOG, MTMEMBER_LINK_ARR);
				ret = MiNDResultType.ACCEPT_READ;
				break;
			default:
				break;
			}
			return ret;
		}

	}
}
