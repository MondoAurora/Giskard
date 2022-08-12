package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustRuntime {
	
	DustBrainEntity eRootStore;

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", cmd, val, valPath);

		return null;
	}

	@Override
	public void initBootHandles(GisCollFactory<Long, DustEntity> bootFact) {
		eRootStore = new DustBrainEntity(TOKEN_STORE_ROOT);
		
		for ( Long k : bootFact.keys() ) {
			DustEntity de = (DustEntity) bootFact.peek(k);
			DustBrainEntity be = (TOKEN_STORE_ROOT == de) ? eRootStore : new DustBrainEntity(de);
			eRootStore.access(MiNDAccessCommand.Insert, be, TOKEN_ATT_STORE_ENTITIES, k);
			eRootStore.access(MiNDAccessCommand.Insert, be, TOKEN_ATT_CONTEXT_ENTITIES, de);
		}
		
		Giskard.log(null, this.getClass().getName(), "BELLO...", TOKEN_UNIT_IDEA);		
	}

}
