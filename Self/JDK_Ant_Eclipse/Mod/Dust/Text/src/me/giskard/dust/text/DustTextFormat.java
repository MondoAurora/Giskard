package me.giskard.dust.text;

import java.text.MessageFormat;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextFormat implements DustTextConsts, GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		switch ( action ) {
		case Process:
			String fmt = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);

			int paramCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);
			Object[] p = new Object[paramCount];

			for (int idx = 0; idx < paramCount; ++idx) {
//				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);
//				Object handle = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_HANDLE);
				Object handle = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);

				p[idx] = handle;
//				p[idx] = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, handle);
			}
			
			String result = MessageFormat.format(fmt, p);
			Giskard.log(MiNDEventLevel.Info, "Formatted string:", result);
			
			Giskard.access(MiNDAccessCommand.Set, result, MTMEMBER_ACTION_DIALOG, MTMEMBER_PLAIN_STRING);
			break;
		default:
			break;
		}
		
		return MiNDResultType.Accept;
	}

}
