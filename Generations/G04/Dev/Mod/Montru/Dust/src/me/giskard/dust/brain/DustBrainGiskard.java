package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustGiskard {

	DustBrainEntity eBrain;
	DustBrainEntity eRootStore;

	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		if ( cmd != MiNDAccessCommand.Broadcast ) {
			DustBrainEntity e = eRootStore.access(MiNDAccessCommand.Peek, null, HANDLE_ATT_CONTEXT_ENTITIES, valPath[0]);
			DustHandle hMember = null;
			DustBrainEntity eMember = null;
			Object key = null;

			for (int i = 1; i < valPath.length; ++i) {
				hMember = (DustHandle) valPath[i];
				eMember = eRootStore.access(MiNDAccessCommand.Peek, null, HANDLE_ATT_CONTEXT_ENTITIES, hMember);
			}

			e.access(cmd, val, hMember, key);
		}

		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", cmd, val, valPath);

		return null;
	}

	@Override
	public void initBootHandles(GisCollFactory<Long, DustHandle> bootFact) {
		eBrain = new DustBrainEntity(null);
		eRootStore = new DustBrainEntity(HANDLE_STORE_ROOT);

		for (Long k : bootFact.keys()) {
			DustHandle dh = (DustHandle) bootFact.peek(k);
			DustBrainEntity be = (HANDLE_STORE_ROOT == dh) ? eRootStore : new DustBrainEntity(dh);
			eRootStore.access(MiNDAccessCommand.Insert, dh, HANDLE_ATT_STORE_HANDLES, k);
			eRootStore.access(MiNDAccessCommand.Insert, be, HANDLE_ATT_CONTEXT_ENTITIES, dh);

			be.access(MiNDAccessCommand.Set, HANDLE_STORE_ROOT, HANDLE_ATT_ENTITY_STORE);
			be.access(MiNDAccessCommand.Set, HANDLE_STORE_ROOT, HANDLE_ATT_ENTITY_CONTEXT);
		}

		Giskard.log(null, this.getClass().getName(), "BELLO...", HANDLE_UNIT_IDEA);
	}

	@Override
	public void initBrain() {
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_TYPE, HANDLE_ATT_ENTITY_PRIMARYTYPE);

//		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_HANDLE, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_MEMBER_TYPE);
//		Giskard.access(MiNDAccessCommand.Insert, HANDLE_TAG_VALTYPE_LINK, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_ENTITY_TAGS);
//		Giskard.access(MiNDAccessCommand.Insert, HANDLE_TAG_COLLTYPE_ONE, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_ENTITY_TAGS);

	}
}
