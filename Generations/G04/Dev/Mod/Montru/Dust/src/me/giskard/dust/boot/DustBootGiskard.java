package me.giskard.dust.boot;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;

public class DustBootGiskard extends Giskard implements DustBootConsts, GisCollConsts, GiskardConsts.GiskardImpl {

	class BootRuntime implements GiskardImpl {
		long lastId = -1;

		GisCollFactory<Long, DustHandle> bootFact = new GisCollFactory<Long, DustHandle>(true,
				new MiNDCreator<Long, DustHandle>() {
					@Override
					public DustHandle create(Long key) {
						return new DustHandle(key);
					}
				});

		@SuppressWarnings("unchecked")
		@Override
		public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
			if ( cmd == MiNDAccessCommand.Get ) {
				int l = valPath.length;
				Object key = (1 < l) ? valPath[1] : null;

				long eid = (key instanceof Integer) ? (long) (int) key : ++lastId;
				return (RetType) bootFact.get(eid);
			}

			GiskardUtils.dump(" ", false, "DustBootGiskard.access", cmd, val, valPath);

			return null;
		}
		
		@Override
		public void broadcast_(MiNDHandle event, Object... params) {
			GiskardUtils.dump(" ", false, "DustBootGiskard.access", event, params);			
		}
	};

	GiskardImpl runtime;

	public DustBootGiskard() throws Exception {
		setImpl(this);

		BootRuntime bs = new BootRuntime();
		runtime = bs;

		DustGiskard rt = (DustGiskard) Class.forName("me.giskard.dust.brain.DustBrainGiskard").newInstance();
		rt.initBootHandles(bs.bootFact);

		runtime = rt;
		
		rt.initBrain();
	}
	
	@Override
	public void broadcast_(MiNDHandle event, Object... params) {
		runtime.broadcast_(event, params);
	}

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return runtime.access_(cmd, val, valPath);
	}

}
