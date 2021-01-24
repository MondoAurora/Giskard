package me.giskard.java.dust;

import me.giskard.java.Mind;
import me.giskard.java.MindConsts;

public interface DustConsts extends MindConsts {
	String SEP_ID = "::";
	
	String ENCODING_UTF8 = "UTF-8";
	String FORMAT_DATE_DEFAULT = "YYYYMMdd";
	String FORMAT_TIMESTAMP_DEFAULT = "YYYYMMdd_HHmmss_SSS";

	
	interface DustCreator<Key, Val> {
		Val create(Key key, Object... hints);
	}
	
	class DustCreatorSimple<Key, Val> implements DustCreator<Key, Val> {
		Class<Val> cVal;
		
		public DustCreatorSimple(Class<Val> cVal) {
			this.cVal = cVal;
		}
		
		@Override
		public Val create(Key key, Object... hints) {
			try {
				return cVal.newInstance();
			} catch (Throwable e) {
				return Mind.throwException(e);
			}
		}
	}
	
	class DustVisitStatus<Key, Val> {
		public MiNDToken action;
		
		public Key key;
		public Val val;
	}
	
	interface DustVisitor<Key, Val> {
		MiNDToken visit(DustVisitStatus<Key, Val> status) throws Exception;
	}
	
	interface DustColl<Key, Val> {
		boolean isEmpty();
		int getCount();
		void clear();
		MiNDToken visit(DustVisitor<Key, Val> visitor);
	}
	
	interface DustCollMap<Key, Val> {
		Val get(Key key);
	}	
}
