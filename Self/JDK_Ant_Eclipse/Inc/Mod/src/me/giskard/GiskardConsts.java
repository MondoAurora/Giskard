package me.giskard;

public interface GiskardConsts {
	String SEP_ID = "_";

	String DEF_ENCODING = "UTF-8";
	String DEF_FORMAT_DATE = "YYYYMMdd";
	String DEF_FORMAT_TIMESTAMP = "YYYYMMdd_HHmmss_SSS";

	String GISKARD_PATH_BRAIN = "/Brain/JRE";
	String GISKARD_SEGMENT_MOD = "/Mod";
	String GISKARD_SEGMENT_EXT = "/Ext";
	
	String EXT_JAR = ".jar";

	String GISKARD_PACKAGE = Giskard.class.getPackage().getName();
	String GISKARD_CLASS_APP = GISKARD_PACKAGE + ".app.GiskardApp";
	String GISKARD_PACKAGE_MOD = GISKARD_PACKAGE + ".mod.";

	final int KEY_APPEND = -1;
	final int KEY_SIZE = -2;

	enum MiNDTokenType {
		UNIT, AGENT, TYPE, MEMBER, TAG, LOCAL
	}

	enum MiNDValType {
		Int, Real, Link, Raw
	}

	enum MiNDCollType {
		One, Arr, Set, Map
	}

	enum MiNDAccessCommand {
		Chk, Get, Set, Add, Del, Use
	};

	enum MiNDEventLevel {
		CRITICAL, ERROR, WARNING, INFO, TRACE, DEBUG
	};

	enum MiNDAgentAction {
		Init, Begin, Process, End, Release
	};

	enum MiNDResultType {
		NOTIMPLEMENTED, REJECT, ACCEPT_PASS, ACCEPT, ACCEPT_READ, READ
	};

	public interface MiNDNamed {
		String getMiNDName();
	}

	public interface MiNDToken extends MiNDNamed {
	}

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType process(MiNDAgentAction action) throws Exception;
	}

}
