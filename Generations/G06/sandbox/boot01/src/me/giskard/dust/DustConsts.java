package me.giskard.dust;

import java.nio.charset.StandardCharsets;

public interface DustConsts {
	String DUST_CHARSET_UTF8 = StandardCharsets.UTF_8.name();// "UTF-8";
	String DUST_FMT_TIMESTAMP = "yyyyMMdd'T'HHmmss'Z'";

	String DUST_EXT_JAR = ".jar";
	String FN_MACHINE_INIT = "dustMachineInit";
	String FN_MODULE_INIT = "dustModuleInit";

	String DUST_EXT_JSON = ".json";
	String DUST_EXT_CSV = ".csv";
	String DUST_EXT_XML = ".xml";

	String DUST_SEP = "_";
	String DUST_SEP_ID = ":";
	String DUST_SEP_TOKEN = "#";
	String DUST_REF_PREFIX = "->";

	public static class MindHandle {};
	
	interface MindLogic {
		MindHandle logicProcess(MindHandle action) throws Exception;
	}

	public abstract class MindDialog implements MindLogic {
		public abstract MindHandle lookup(MindHandle unit, String id, String lang, String token);
		public abstract <RetType> RetType access(MindHandle cmd, Object val, Object... path);
		public abstract void broadcast(MindHandle event, Object... params);
	}

}
