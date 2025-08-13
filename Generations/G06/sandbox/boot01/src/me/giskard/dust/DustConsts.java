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
	String DUST_SEP_TOKEN = "#";

	public static class MindHandle {
//		public static MindFormatter FORMATTER = FMT_DEFAULT;
//		
//		@Override
//		public String toString() {
//			return FORMATTER.toString(this);
//		}
	};

//	public static class MindToken {
////		public static MindFormatter FORMATTER = FMT_DEFAULT;
//		
//		private MindHandle target;
//		
//		public MindToken(MindHandle target) {
//			this.target = target;
//		}
//		
//		public void setTarget(MindHandle t) {
//			if ( null == target ) {
//				this.target = t;				
//			} else {
//				DustException.wrap(null, "Token already has target", target, "when setting", t);
//			}
//		}
//		
//		public MindHandle getTarget() {
//			return target;
//		}
//		
////		@Override
////		public String toString() {
////			return FORMATTER.toString(this);
////		}
//	};

//	public static interface MindFormatter {
//		public String toString(Object o);
//	};
//
//	public static MindFormatter FMT_DEFAULT = new MindFormatter() {
//		public String toString(Object o) {
//			return DustUtils.toString(o);
//		}
//	};

//	MindToken TOKEN_ADD = new MindToken(null);
//	MindToken TOKEN_SIZE = new MindToken(null);
//
//	MindToken TOKEN_ACCEPT = new MindToken(null);
	
	interface MindLogic {
		MindHandle logicProcess(MindHandle action) throws Exception;
	}

//	abstract class MindLogic {
//		public MindToken agentInit() throws Exception {
//			return TOKEN_ACCEPT;
//		}
//
//		public MindToken agentBegin() throws Exception {
//			return TOKEN_ACCEPT;
//		}
//
//		public MindToken agentProcess() throws Exception {
//			return TOKEN_ACCEPT;
//		}
//
//		public MindToken agentEnd() throws Exception {
//			return TOKEN_ACCEPT;
//		}
//
//		public MindToken agentRelease() throws Exception {
//			return TOKEN_ACCEPT;
//		}
//	}

	public abstract class MindDialog implements MindLogic {
		public abstract <RetType> RetType access(MindHandle cmd, Object val, Object... path);

		public abstract void broadcast(MindHandle event, Object... params);
	}

}
