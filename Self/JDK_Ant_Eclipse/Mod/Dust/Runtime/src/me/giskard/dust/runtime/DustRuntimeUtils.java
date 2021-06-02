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

	public static DustRuntimeToken getTypeToken(DustRuntimeToken token) {
		MiNDToken t;
		switch ( token.getType() ) {
		case Service:
			t = MTTYP_NARRATIVE_SERVICE;
			break;
		case Agent:
			t = MTTYP_NARRATIVE_AGENT;
			break;
		case Member:
			t = MTTYP_IDEA_MEMBER;
			break;
		case Tag:
			t = MTTYP_GENERIC_TAG;
			break;
		case Type:
			t = MTTYP_IDEA_TYPE;
			break;
		case Unit:
			t = MTTYP_MODEL_UNIT;
			break;
		default:
			t = null;
		}
		return (DustRuntimeToken) t;
	}

}
