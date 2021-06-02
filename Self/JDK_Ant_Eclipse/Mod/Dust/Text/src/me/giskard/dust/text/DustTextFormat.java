package me.giskard.dust.text;

import java.text.MessageFormat;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextFormat implements DustTextConsts, GiskardConsts.MiNDAgent {

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		String fmt = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_THIS, MTMEM_TEXT_PLAINTEXT_STRING);

		int paramCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ARR, KEY_SIZE);
		Object[] p = new Object[paramCount];

		for (int idx = 0; idx < paramCount; ++idx) {
			Object handle = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_THIS, MTMEM_GENERIC_LINK_ARR, idx);
			p[idx] = handle;
		}

		String result = MessageFormat.format(fmt, p);
		Giskard.log(MiNDEventLevel.Info, "Formatted string:", result);

		Giskard.access(MiNDAccessCommand.Set, result, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_TEXT_PLAINTEXT_STRING);

		return MiNDResultType.Accept;
	}

}
