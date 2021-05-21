package me.giskard.dust.text;

import java.text.MessageFormat;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextFormat implements DustTextConsts, GiskardConsts.MiNDAgent {

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		String fmt = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);

		int paramCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);
		Object[] p = new Object[paramCount];

		for (int idx = 0; idx < paramCount; ++idx) {
			Object handle = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);
			p[idx] = handle;
		}

		String result = MessageFormat.format(fmt, p);
		Giskard.log(MiNDEventLevel.Info, "Formatted string:", result);

		Giskard.access(MiNDAccessCommand.Set, result, MTMEMBER_ACTION_DIALOG, MTMEMBER_PLAIN_STRING);

		return MiNDResultType.Accept;
	}

}
