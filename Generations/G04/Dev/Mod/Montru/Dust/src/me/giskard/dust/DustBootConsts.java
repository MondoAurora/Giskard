package me.giskard.dust;

import me.giskard.GiskardConsts;
import me.giskard.coll.GisCollFactory;

public interface DustBootConsts extends GiskardConsts {

	class DustHandle implements MiNDHandle, Comparable<DustHandle> {
		public final Long id;

		public DustHandle(long id) {
			this.id = id;
		}

		@Override
		public int compareTo(DustHandle o) {
			return id.compareTo(o.id);
		}
		
		@Override
		public String toString() {
			return "Entity(" + id + ")";
		}
	}

	interface DustGiskard extends GiskardImpl {
		void initBootHandles(GisCollFactory<Long, DustHandle> bootFact);
		void initBrain() throws Exception;
	}

}
