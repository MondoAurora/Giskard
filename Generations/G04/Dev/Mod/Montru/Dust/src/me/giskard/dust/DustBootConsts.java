package me.giskard.dust;

import me.giskard.GiskardConsts;
import me.giskard.coll.GisCollFactory;

public interface DustBootConsts extends GiskardConsts {

	class DustEntity implements MiNDEntity, Comparable<DustEntity> {
		public final Long id;

		public DustEntity(long id) {
			this.id = id;
		}

		@Override
		public int compareTo(DustEntity o) {
			return id.compareTo(o.id);
		}
		
		@Override
		public String toString() {
			return "Entity(" + id + ")";
		}
	}

	interface DustStore {
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath);
	}

	interface DustRuntime extends DustStore {
		void initBootHandles(GisCollFactory<Long, DustEntity> bootFact);
	}

}
