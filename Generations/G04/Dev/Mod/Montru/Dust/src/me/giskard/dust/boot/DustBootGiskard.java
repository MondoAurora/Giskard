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

		GisCollFactory<Long, DustEntity> bootFact = new GisCollFactory<Long, DustEntity>(true,
				new MiNDCreator<Long, DustEntity>() {
					@Override
					public DustEntity create(Long key) {
						return new DustEntity(key);
					}
				});

		@SuppressWarnings("unchecked")
		@Override
		public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
			if ( cmd == MiNDAccessCommand.Get ) {
				int l = valPath.length;
//				MiNDEntity token = (0 < l) ? (MiNDEntity) valPath[0] : null;
				Object key = (1 < l) ? valPath[1] : null;

				long eid = (key instanceof Integer) ? (long) (int) key : ++lastId;
				return (RetType) bootFact.get(eid);
			}

			GiskardUtils.dump(" ", false, "DustBootGiskard.access", cmd, val, valPath);

			return null;
		}
	};

	GiskardImpl runtime;

	public DustBootGiskard() throws Exception {
		setImpl(this);

		BootRuntime bs = new BootRuntime();
		runtime = bs;

		DustRuntime rt = (DustRuntime) Class.forName("me.giskard.dust.brain.DustBrainGiskard").newInstance();
		rt.initBootHandles(bs.bootFact);

		runtime = rt;
	}

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return runtime.access_(cmd, val, valPath);
	}

}
