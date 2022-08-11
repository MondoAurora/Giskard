package me.giskard.dust.boot;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;

public class DustBootGiskard extends Giskard implements DustBootConsts, GisCollConsts {

	class BootStore implements DustStore {
		long lastId = -1;
		
		GisCollFactory<Long, DustEntity> bootFact = new GisCollFactory<Long, DustEntity>(true,
				new MiNDCreator<Long, DustEntity>() {
					@Override
					public DustEntity create(Long key) {
						return new DustEntity(key);
					}
				});

		@SuppressWarnings("unchecked")
		@Override
		public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
			if (cmd == MiNDAccessCommand.Get ) {
				long eid = (valPath.length > 1 ) ? (long) (int) valPath[1] : ++lastId;
				return (RetType) bootFact.get(eid);
			}
			
			GiskardUtils.dump(" ", false, "DustBootGiskard.access", cmd, val, valPath);

			return null;
		}
	};

	DustStore mainStore;

	public DustBootGiskard() throws Exception {
		super();
		
		BootStore bs = new BootStore();
		
		mainStore = bs;
		
		DustRuntime rt = (DustRuntime) Class.forName("me.giskard.dust.brain.DustBrainGiskard").newInstance();
		
		rt.initBootHandles(bs.bootFact);
		
		mainStore = rt;
	}

	@Override
	protected <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return mainStore.access(cmd, val, valPath);
	}

}
