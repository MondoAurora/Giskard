package me.giskard.dust;

import java.nio.charset.StandardCharsets;

public interface DustConsts {
	String DUST_CHARSET_UTF8 = StandardCharsets.UTF_8.name();// "UTF-8";
	String DUST_FMT_TIMESTAMP = "yyyy-MM-dd'T'HH_mm_ss";
	String DUST_FMT_DATE = "yyyy-MM-dd";

	String DUST_EXT_JAR = ".jar";
	String FN_MODULE_INIT = "dustModuleInit";

	String DUST_EXT_JSON = ".json";
	String DUST_EXT_CSV = ".csv";
	String DUST_EXT_XML = ".xml";

	String DUST_SEP = "_";
	String DUST_SEP_ID = ":";

	public static class MindToken {
	};

	MindToken TOKEN_ADD = new MindToken();
	MindToken TOKEN_SIZE = new MindToken();

	MindToken TOKEN_ACCEPT = new MindToken();

	abstract class MindLogic {
		public MindToken agentInit() throws Exception {
			return TOKEN_ACCEPT;
		}

		public MindToken agentBegin() throws Exception {
			return TOKEN_ACCEPT;
		}

		public MindToken agentProcess() throws Exception {
			return TOKEN_ACCEPT;
		}

		public MindToken agentEnd() throws Exception {
			return TOKEN_ACCEPT;
		}

		public MindToken agentRelease() throws Exception {
			return TOKEN_ACCEPT;
		}
	}

	public abstract class MindDialog extends MindLogic {
		public abstract <RetType> RetType access(MindToken cmd, Object val, Object... path);

		public abstract void broadcast(MindToken event, Object... params);
	}

}
