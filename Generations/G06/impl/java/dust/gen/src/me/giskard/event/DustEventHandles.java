package me.giskard.event;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustEventHandles extends DustConsts {


	MindHandle EVENT_ASP_TIME = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:0");
	MindHandle EVENT_ATT_TIME_YEAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:1");
	MindHandle EVENT_ATT_TIME_DAYOFYEAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:2");
	MindHandle EVENT_ATT_TIME_HOUR24 = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:3");
	MindHandle EVENT_ATT_TIME_MIN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:4");
	MindHandle EVENT_ATT_TIME_SEC = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:5");
	MindHandle EVENT_ATT_TIME_MILLI = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:6");

	MindHandle EVENT_ASP_EVENT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:7");
	MindHandle EVENT_ATT_EVENT_START = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:8");
	MindHandle EVENT_ATT_EVENT_END = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:9");
	MindHandle EVENT_ATT_EVENT_DURATION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:10");
	MindHandle EVENT_ATT_EVENT_REPETITION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:11");
	MindHandle EVENT_ATT_EVENT_TIMEFORMAT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:12");

	MindHandle EVENT_ASP_CALENDAR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:13");
	MindHandle EVENT_ATT_LAST_ACTION = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:15");

	MindHandle EVENT_TAG_CAL = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:16");
	MindHandle EVENT_TAG_CAL_GREGORIAN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:17");

	MindHandle EVENT_TAG_TIMEZONE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:18");
	MindHandle EVENT_TAG_TIMEZONE_GMT = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:19");

	MindHandle EVENT_TAG_DST = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:20");

	MindHandle EVENT_TAG_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:21");
	MindHandle EVENT_TAG_TYPE_EXCEPTIONTHROWN = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:22");
	MindHandle EVENT_TAG_TYPE_EXCEPTIONSWALLOWED = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:23");
	MindHandle EVENT_TAG_TYPE_ERROR = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:24");
	MindHandle EVENT_TAG_TYPE_WARNING = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:25");
	MindHandle EVENT_TAG_TYPE_INFO = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:26");
	MindHandle EVENT_TAG_TYPE_TRACE = Dust.access(MindAccess.Lookup, "giskard.me:event_1.0:27");
// No text assigned to handle giskard.me:event_1.0:28
}

