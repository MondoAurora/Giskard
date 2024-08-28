package me.giskard.event;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustEventHandles extends DustConsts {


	MindHandle DH_EVENT_TIME = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:0");
	MindHandle DH_EVENT_TIME_YEAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:1");
	MindHandle DH_EVENT_TIME_DAYOFYEAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:2");
	MindHandle DH_EVENT_TIME_HOUR24 = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:3");
	MindHandle DH_EVENT_TIME_MIN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:4");
	MindHandle DH_EVENT_TIME_SEC = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:5");
	MindHandle DH_EVENT_TIME_MILLI = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:6");

	MindHandle DH_EVENT_EVENT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:7");
	MindHandle DH_EVENT_EVENT_START = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:8");
	MindHandle DH_EVENT_EVENT_END = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:9");
	MindHandle DH_EVENT_EVENT_DURATION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:10");
	MindHandle DH_EVENT_EVENT_REPETITION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:11");
	MindHandle DH_EVENT_EVENT_TIMEFORMAT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:12");

	MindHandle DH_EVENT_CALENDAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:13");

	MindHandle DH_EVENT_THROTTLE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:14");
	MindHandle DH_EVENT_LAST_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:15");

	MindHandle DH_EVENT_CAL = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:16");
	MindHandle DH_EVENT_CAL_GREGORIAN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:17");

	MindHandle DH_EVENT_TIMEZONE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:18");
	MindHandle DH_EVENT_TIMEZONE_GMT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:19");

	MindHandle DH_EVENT_DST = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:20");

	MindHandle DH_EVENT_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:21");
	MindHandle DH_EVENT_TYPE_EXCEPTIONTHROWN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:22");
	MindHandle DH_EVENT_TYPE_EXCEPTIONSWALLOWED = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:23");
	MindHandle DH_EVENT_TYPE_ERROR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:24");
	MindHandle DH_EVENT_TYPE_WARNING = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:25");
	MindHandle DH_EVENT_TYPE_INFO = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:26");
	MindHandle DH_EVENT_TYPE_TRACE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:27");
	MindHandle DH_EVENT_TYPE_BREAKPOINT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:28");
}

