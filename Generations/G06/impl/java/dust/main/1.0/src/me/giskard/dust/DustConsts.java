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

	public static abstract class MindToken {
		protected MindToken() {
		}

		public abstract String getId();
	};

	MindToken UNIMPLEMENTED_ACTION = new MindToken() {
		@Override
		public String getId() {
			return "Unimplemented action";
		}
	};

	interface MindAgent {
		default MindToken agentInit() throws Exception {
			return UNIMPLEMENTED_ACTION;
		}

		default MindToken agentBegin() throws Exception {
			return UNIMPLEMENTED_ACTION;
		}

		default MindToken agentProcess() throws Exception {
			return UNIMPLEMENTED_ACTION;
		}

		default MindToken agentEnd() throws Exception {
			return UNIMPLEMENTED_ACTION;
		}

		default MindToken agentRelease() throws Exception {
			return UNIMPLEMENTED_ACTION;
		}
	}

	public interface MindDialog extends MindAgent {
		<RetType> RetType access(MindToken cmd, Object val, Object... path);
		void broadcast(MindToken event, Object... params);
	}

}
