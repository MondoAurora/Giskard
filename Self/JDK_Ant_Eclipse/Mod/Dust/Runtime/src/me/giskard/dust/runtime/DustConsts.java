package me.giskard.dust.runtime;

import me.giskard.MindConsts;
import me.giskard.utils.MindUtils;

public interface DustConsts extends MindConsts {
	String SEP_ID = "_";

	String ENCODING_UTF8 = "UTF-8";
	String FORMAT_DATE_DEFAULT = "YYYYMMdd";
	String FORMAT_TIMESTAMP_DEFAULT = "YYYYMMdd_HHmmss_SSS";
	
//	String FMT_ENTITY_ID = "de_{0}_{1}";

	interface DustCreator<Key, Val> {
		Val create(Key key);
	}

	class DustCreatorSimple<Key, Val> implements DustCreator<Key, Val> {
		Class<Val> cVal;

		public DustCreatorSimple(Class<Val> cVal) {
			this.cVal = cVal;
		}

		@Override
		public Val create(Key key) {
			try {
				return cVal.newInstance();
			} catch (Throwable e) {
				return MindUtils.wrapException(e);
			}
		}
	}
	
//	class DustToken implements MiNDToken {
//		public String token;
//
//		public DustToken(String token) {
//			this.token = token;
//		}
//		
//		// may be needed for refactor to keep the instance but change the key
//		void setToken(String token) {
//			this.token = token;
//		}
//		
//		@Override
//		public String toString() {
//			return token;
//		}
//	}

//	class DustVisitStatus<Key, Val> {
//		public MiNDAgentAction action;
//
//		public Key key;
//		public Val val;
//	}
//
//	interface DustVisitor<Key, Val> {
//		MiNDResultType visit(DustVisitStatus<Key, Val> status) throws Exception;
//	}

	interface DustColl<Key, Val> {
		boolean isEmpty();

		int getCount();

		void clear();

//		MiNDResultType visit(DustVisitor<Key, Val> visitor);
	}

	interface DustCollMap<Key, Val> {
		Val get(Key key);
		Val put(Key key, Val val);
	}
}
