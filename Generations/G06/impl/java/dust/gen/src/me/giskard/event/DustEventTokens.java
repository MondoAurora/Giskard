package me.giskard.event;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustEventTokens extends DustConsts {


	MindToken TOKEN_EVENT_TIME = Dust.access(null, "giskard.me:event_1.0:0");
	MindToken TOKEN_EVENT_TIME_YEAR = Dust.access(null, "giskard.me:event_1.0:1");
	MindToken TOKEN_EVENT_TIME_DAYOFYEAR = Dust.access(null, "giskard.me:event_1.0:2");
	MindToken TOKEN_EVENT_TIME_HOUR24 = Dust.access(null, "giskard.me:event_1.0:3");
	MindToken TOKEN_EVENT_TIME_MIN = Dust.access(null, "giskard.me:event_1.0:4");
	MindToken TOKEN_EVENT_TIME_SEC = Dust.access(null, "giskard.me:event_1.0:5");
	MindToken TOKEN_EVENT_TIME_MILLI = Dust.access(null, "giskard.me:event_1.0:6");

	MindToken TOKEN_EVENT_EVENT = Dust.access(null, "giskard.me:event_1.0:7");
	MindToken TOKEN_EVENT_EVENT_START = Dust.access(null, "giskard.me:event_1.0:8");
	MindToken TOKEN_EVENT_EVENT_END = Dust.access(null, "giskard.me:event_1.0:9");
	MindToken TOKEN_EVENT_EVENT_DURATION = Dust.access(null, "giskard.me:event_1.0:10");
	MindToken TOKEN_EVENT_EVENT_REPETITION = Dust.access(null, "giskard.me:event_1.0:11");
	MindToken TOKEN_EVENT_EVENT_TIMEFORMAT = Dust.access(null, "giskard.me:event_1.0:12");

	MindToken TOKEN_EVENT_CALENDAR = Dust.access(null, "giskard.me:event_1.0:13");

	MindToken TOKEN_EVENT_THROTTLE = Dust.access(null, "giskard.me:event_1.0:14");
	MindToken TOKEN_EVENT_LAST_ACTION = Dust.access(null, "giskard.me:event_1.0:15");

	MindToken TOKEN_EVENT_CAL = Dust.access(null, "giskard.me:event_1.0:16");
	MindToken TOKEN_EVENT_CAL_GREGORIAN = Dust.access(null, "giskard.me:event_1.0:17");

	MindToken TOKEN_EVENT_TIMEZONE = Dust.access(null, "giskard.me:event_1.0:18");
	MindToken TOKEN_EVENT_TIMEZONE_GMT = Dust.access(null, "giskard.me:event_1.0:19");

	MindToken TOKEN_EVENT_DST = Dust.access(null, "giskard.me:event_1.0:20");

	MindToken TOKEN_EVENT_TYPE = Dust.access(null, "giskard.me:event_1.0:21");
	MindToken TOKEN_EVENT_TYPE_EXCEPTIONTHROWN = Dust.access(null, "giskard.me:event_1.0:22");
	MindToken TOKEN_EVENT_TYPE_EXCEPTIONSWALLOWED = Dust.access(null, "giskard.me:event_1.0:23");
	MindToken TOKEN_EVENT_TYPE_ERROR = Dust.access(null, "giskard.me:event_1.0:24");
	MindToken TOKEN_EVENT_TYPE_WARNING = Dust.access(null, "giskard.me:event_1.0:25");
	MindToken TOKEN_EVENT_TYPE_INFO = Dust.access(null, "giskard.me:event_1.0:26");
	MindToken TOKEN_EVENT_TYPE_TRACE = Dust.access(null, "giskard.me:event_1.0:27");
	MindToken TOKEN_EVENT_TYPE_BREAKPOINT = Dust.access(null, "giskard.me:event_1.0:28");
}

