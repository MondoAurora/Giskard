package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustRuntime {

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", cmd, val, valPath);

		return null;
	}

	@Override
	public void initBootHandles(GisCollFactory<Long, DustEntity> bootFact) {
		Giskard.log(null, this.getClass().getName(), "BELLO...", TOKEN_UNIT_IDEA);		
	}

}
