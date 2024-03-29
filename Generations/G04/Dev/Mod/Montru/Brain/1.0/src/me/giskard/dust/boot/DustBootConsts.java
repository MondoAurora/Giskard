package me.giskard.dust.boot;

import me.giskard.GiskardConsts;
import me.giskard.coll.GisCollFactory;

public interface DustBootConsts extends GiskardConsts {
	
	interface DustHandleFormatter {
		public String toString(DustHandle h);
	}
	
	DustHandleFormatter DEF_FMT = new DustHandleFormatter() {
		@Override
		public String toString(DustHandle h) {
			return "Entity(" + h.id + ")";
		}
	};

	class DustHandle implements MiNDHandle, Comparable<DustHandle> {
		public static DustHandleFormatter FMT = DEF_FMT;
		
		public final Long id;

		public DustHandle(long id) {
			this.id = id;
		}
		
		@Override
		public Object getId() {
			return id;
		}

		@Override
		public int compareTo(DustHandle o) {
			return id.compareTo(o.id);
		}
		
		@Override
		public String toString() {
			return FMT.toString(this);
		}
	}

	interface DustGiskard extends GiskardImpl {
		void initBootJourney(GisCollFactory<Long, DustHandle> bootFact) throws Exception;
	}

}
