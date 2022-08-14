package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustGiskard {

	final DustBrainEntity eBrain;
	final DustBrainEntity eRootStore;

	final GisToolsTranslator<MiNDHandle, CollType> H2COLLTYPE = new GisToolsTranslator<>();
	final GisToolsTranslator<MiNDHandle, ValType> H2VALTYPE = new GisToolsTranslator<>();

	public boolean isLearning(MiNDHandle hMember) {
		Boolean learning = eBrain.access(MiNDAccessCommand.Check, HANDLE_TAG_LENIENT, HANDLE_ATT_ENTITY_TAGS, CollType.Set, null);
		
		if ( Boolean.TRUE.equals(learning) ){
			return true;
		}
		
		return false;
	}

	public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {
		DustBrainEntity eMember = eRootStore.access(MiNDAccessCommand.Peek, null, HANDLE_ATT_CONTEXT_ENTITIES, CollType.Map, hMember);
		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, HANDLE_ATT_ENTITY_TAGS, CollType.Set, HANDLE_TAG_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(hMember) ) {
				h = H2COLLTYPE.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, HANDLE_ATT_ENTITY_TAGS, CollType.Set, HANDLE_TAG_COLLTYPE);
			}
		} else {
			ret = H2COLLTYPE.getRight(h);
		}

		return ret;
	}

	public DustBrainGiskard() {
		eBrain = new DustBrainEntity(null);
		eRootStore = new DustBrainEntity(HANDLE_STORE_ROOT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = null;
		if ( cmd != MiNDAccessCommand.Broadcast ) {
			DustBrainEntity e = eRootStore.access(MiNDAccessCommand.Peek, null, HANDLE_ATT_CONTEXT_ENTITIES, CollType.Map, valPath[0]);
			DustHandle hMember = null;
			Object key = null;

			for (int i = 1; i < valPath.length; ++i) {
				hMember = (DustHandle) valPath[i];
			}

			ret = e.access(cmd, val, hMember, getCollType(hMember, cmd, key), key);
		}

		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", cmd, val, valPath);

		return (RetType) ret;
	}

	@Override
	public void initBootHandles(GisCollFactory<Long, DustHandle> bootFact) {

		for (Long k : bootFact.keys()) {
			DustHandle dh = (DustHandle) bootFact.peek(k);
			DustBrainEntity be = (HANDLE_STORE_ROOT == dh) ? eRootStore : new DustBrainEntity(dh);
			eRootStore.access(MiNDAccessCommand.Insert, dh, HANDLE_ATT_STORE_HANDLES, CollType.Map, k);
			eRootStore.access(MiNDAccessCommand.Insert, be, HANDLE_ATT_CONTEXT_ENTITIES, CollType.Map, dh);

			be.access(MiNDAccessCommand.Set, HANDLE_STORE_ROOT, HANDLE_ATT_ENTITY_STORE, CollType.One, null);
			be.access(MiNDAccessCommand.Set, HANDLE_STORE_ROOT, HANDLE_ATT_ENTITY_CONTEXT, CollType.One, null);
		}

		Giskard.log(null, this.getClass().getName(), "BELLO...", HANDLE_UNIT_IDEA);
	}

	@Override
	public void initBrain() {
		H2COLLTYPE.add(HANDLE_TAG_COLLTYPE_ARR, CollType.Arr);
		H2COLLTYPE.add(HANDLE_TAG_COLLTYPE_ONE, CollType.One);
		H2COLLTYPE.add(HANDLE_TAG_COLLTYPE_SET, CollType.Set);
		H2COLLTYPE.add(HANDLE_TAG_COLLTYPE_MAP, CollType.Map);

		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_COLLTYPE, HANDLE_TAG_COLLTYPE_ONE, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_COLLTYPE, HANDLE_TAG_COLLTYPE_ARR, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_COLLTYPE, HANDLE_TAG_COLLTYPE_MAP, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_COLLTYPE, HANDLE_TAG_COLLTYPE_SET, HANDLE_ATT_ENTITY_OWNER);

		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_VALTYPE, HANDLE_TAG_VALTYPE_INT, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_VALTYPE, HANDLE_TAG_VALTYPE_LINK, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_VALTYPE, HANDLE_TAG_VALTYPE_RAW, HANDLE_ATT_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TAG_VALTYPE, HANDLE_TAG_VALTYPE_REAL, HANDLE_ATT_ENTITY_OWNER);

		eBrain.access(MiNDAccessCommand.Insert, HANDLE_TAG_LENIENT, HANDLE_ATT_ENTITY_TAGS, CollType.Set, null);

		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_TYPE, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_MEMBER, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_TAG, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_UNIT, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_STORE, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_ENTITY, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_HANDLE, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_BRAIN, HANDLE_ATT_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_TYPE, HANDLE_TYPE_CONTEXT, HANDLE_ATT_ENTITY_PRIMARYTYPE);


//		Giskard.access(MiNDAccessCommand.Set, HANDLE_TYPE_HANDLE, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_MEMBER_TYPE);
//		Giskard.access(MiNDAccessCommand.Insert, HANDLE_TAG_VALTYPE_LINK, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_ENTITY_TAGS);
//		Giskard.access(MiNDAccessCommand.Insert, HANDLE_TAG_COLLTYPE_ONE, HANDLE_ATT_ENTITY_HANDLE, HANDLE_ATT_ENTITY_TAGS);

	}
}
