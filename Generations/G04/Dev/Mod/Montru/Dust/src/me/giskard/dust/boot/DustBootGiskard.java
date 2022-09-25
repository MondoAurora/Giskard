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
		public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
			if ( cmd == MiNDAccessCommand.Get ) {
				long eid = (key instanceof Integer) ? (long) (int) key : ++lastId;
				return (RetType) bootFact.get(eid);
			}

			GiskardUtils.dump(" ", false, "DustBootGiskard.access", cmd, val, ref, att, key);

			return null;
		}
		
		@Override
		public void broadcast_(MiNDHandle event, Object... params) {
			GiskardUtils.dump(" ", false, "DustBootGiskard.access", event, params);			
		}
		
		@Override
		public MiNDResultType mindAgentStep() throws Exception {
			return null;
		}
	};

	GiskardImpl runtime;

	public DustBootGiskard() throws Exception {
		setImpl(this);

		BootRuntime bs = new BootRuntime();
		runtime = bs;

		DustGiskard rt = (DustGiskard) Class.forName("me.giskard.dust.brain.DustBrainGiskard").newInstance();

		runtime = rt;
		
		rt.initBootJourney(bs.bootFact);
	}
	
	@Override
	public void broadcast_(MiNDHandle event, Object... params) {
		runtime.broadcast_(event, params);
	}

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key) {
		return runtime.access_(cmd, val, ref, att, key);
	}
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		return runtime.mindAgentStep();
	}

}
