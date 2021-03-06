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
	String GISKARD_PREFIX_MOD = GISKARD_PACKAGE + ".mod.";
	String GISKARD_PREFIX_UNIT = GISKARD_PACKAGE + ".units.Unit";

	final int KEY_APPEND = -1;
	final int KEY_SIZE = -2; // return the item count of a member
	final int KEY_FORMAT_STRING = -3; // "toString" of the current member (formatters should be sent into the runtime)

	enum MiNDTokenType {
		Unit("UNI"), Type("TYP"), Member("MEM"), Tag("TAG"), Service("SVC"), Agent("AGN")/* , Local("LOC") */;
		
		public final String prefix;
		
		private MiNDTokenType(String prefix_) {
			this.prefix = prefix_;
		}
	}

	enum MiNDValType {
		Int, Real, Link, Raw
	}

	enum MiNDCollType {
		One, Arr, Set, Map
	}

	enum MiNDAccessCommand {
		Chk, Get, Set, Add, Del
	};

//	enum MiNDVisitState {
//		Entity, Token, Value
//	};

	enum MiNDEventLevel {
		Critical, Error, Warning, Info, Trace, Debug
	};

//	enum MiNDAgentAction {
//		Init, Begin, Process, End, Release
//	};

	enum MiNDResultType {
		Notimplemented, Reject, Accept, AcceptRead, Read, Wait
	};

	public interface MiNDNamed {
		String getMiNDName();
	}

	public interface MiNDToken extends MiNDNamed {
		MiNDCollType getCollType();
		MiNDValType getValType();
		Object getEntity();
	}

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType mindAgentProcess() throws Exception;
	}

	public interface MiNDAgentBlock extends MiNDAgent {
		void mindAgentBegin() throws Exception;
		void mindAgentEnd() throws Exception;
	}

	public interface MiNDAgentResource extends MiNDAgent {
		void mindAgentInit() throws Exception;
		void mindAgentRelease() throws Exception;
	}
}
