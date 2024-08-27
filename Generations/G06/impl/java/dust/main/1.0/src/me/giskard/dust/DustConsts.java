package me.giskard.dust;

import java.nio.charset.StandardCharsets;

public interface DustConsts {
	String DUST_SEP = "_";
	String DUST_SEP_ID = ":";

	String DUST_CHARSET_UTF8 = StandardCharsets.UTF_8.name();// "UTF-8";
	String DUST_FMT_TIMESTAMP = "yyyy-MM-dd'T'HH_mm_ss";
	String DUST_FMT_DATE = "yyyy-MM-dd";
	
	String DUST_EXT_JSON = ".json";
	String DUST_EXT_CSV = ".csv";
	String DUST_EXT_XML = ".xml";

	String DUST_EXT_JS = ".js";

	String DUST_EXT_JAR = ".jar";
	String DUST_EXT_JAVA = ".java";
	String DUST_EXT_CLASS = ".class";
	
	String DUST_EXT_DUT = ".dut";
	String DUST_DUT_SIGNATURE = "DustUnitText";

	
	int KEY_ADD = -1;
	int KEY_SIZE = -2;


	
	enum MindValType {
		Int, Real, Handle, Bin
	};

	enum MindCollType {
		One, Set, Arr, Map
	};
	
	enum MindAction {
		Init, Begin, Process, End, Release,
	};
	
	enum MindResponse {
		Reject, Pass, Read, ReadAccept, Accept, Error,
	};

	enum MindAccess {
		Check, Peek, Get, Set, Insert, Delete, Reset, Commit, Broadcast, Lookup, Visit, 
	};

	public static abstract class MindHandle {
		protected MindHandle() {
		}
		
		public abstract String getId();
	};
	
	interface MindNarrative {
		MindResponse execute(MindAction action) throws Exception;
	}

	public interface MindMachine extends MindNarrative {
		<RetType> RetType access(MindAccess cmd, Object val, Object... path);
	}

}
