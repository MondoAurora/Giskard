package me.giskard.java.dust;

import me.giskard.java.Mind;
import me.giskard.java.MindConsts;

public interface DustConsts extends MindConsts {
	String SEP_ID = "_";

	String ENCODING_UTF8 = "UTF-8";
	String FORMAT_DATE_DEFAULT = "YYYYMMdd";
	String FORMAT_TIMESTAMP_DEFAULT = "YYYYMMdd_HHmmss_SSS";
	
	String FMT_ENTITY_ID = "de_{0}_{1}";

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
				return Mind.wrapException(e);
			}
		}
	}

	class DustVisitStatus<Key, Val> {
		public MiNDEntity action;

		public Key key;
		public Val val;
	}

	interface DustVisitor<Key, Val> {
		MiNDEntity visit(DustVisitStatus<Key, Val> status) throws Exception;
	}

	interface DustColl<Key, Val> {
		boolean isEmpty();

		int getCount();

		void clear();

		MiNDEntity visit(DustVisitor<Key, Val> visitor);
	}

	interface DustCollMap<Key, Val> {
		Val get(Key key);
	}
}
