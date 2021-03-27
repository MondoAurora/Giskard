package me.giskard.dust.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.giskard.GiskardUtils;

public class DustRuntimeUtils extends GiskardUtils implements DustRuntimeConsts {
	public static String strTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date());
	}

	public static String strDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_DATE);
		return sdf.format(new Date());
	}

	public static String strTimestamp(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEF_FORMAT_TIMESTAMP);
		return sdf.format(new Date(time));
	}

	public static MiNDResultType notifyAgent(MiNDAgent agent, DustRuntimeContext ctx, Object val) throws Exception {
		ctx.rootBlock.access(MiNDAccessCommand.Set, val, MTMEMBER_ACTION_PARAM, null);
		return agent.process(MiNDAgentAction.Process);
	}
}
