package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;
import me.giskard.dust.DustConsts;
import me.giskard.tools.GisToolsTranslator;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustGiskard {

	final DustBrainEntity eBrain;
	final DustBrainEntity eRootStore;

	final GisToolsTranslator<MiNDHandle, CollType> H2COLLTYPE = new GisToolsTranslator<>();
	final GisToolsTranslator<MiNDHandle, ValType> H2VALTYPE = new GisToolsTranslator<>();

	public boolean isLearning(MiNDHandle hMember) {
		Boolean learning = eBrain.access(MiNDAccessCommand.Check, GIS_TAG_GENERIC_LENIENT, GIS_MEM_MODEL_ENTITY_TAGS, CollType.Set,
				null);

		if ( Boolean.TRUE.equals(learning) ) {
			return true;
		}

		return false;
	}

	public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {
		DustBrainEntity eMember = eRootStore.access(MiNDAccessCommand.Peek, null, GIS_MEM_DIALOG_CONTEXT_ENTITIES, CollType.Map,
				hMember);
		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, GIS_MEM_MODEL_ENTITY_TAGS, CollType.Set,
				GIS_TAG_IDEA_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(hMember) ) {
				h = H2COLLTYPE.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, GIS_MEM_MODEL_ENTITY_TAGS, CollType.Set, GIS_TAG_IDEA_COLLTYPE);
			}
		} else {
			ret = H2COLLTYPE.getRight(h);
		}

		return ret;
	}

	public DustBrainGiskard() {
		eBrain = new DustBrainEntity(null);
		eRootStore = new DustBrainEntity(GIS_STO_ROOT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = null;
		if ( cmd != MiNDAccessCommand.Broadcast ) {
			DustBrainEntity e = eRootStore.access(MiNDAccessCommand.Peek, null, GIS_MEM_DIALOG_CONTEXT_ENTITIES, CollType.Map,
					valPath[0]);
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
			DustBrainEntity be = (GIS_STO_ROOT == dh) ? eRootStore : new DustBrainEntity(dh);
			eRootStore.access(MiNDAccessCommand.Insert, dh, GIS_MEM_MODEL_STORE_HANDLES, CollType.Map, k);
			eRootStore.access(MiNDAccessCommand.Insert, be, GIS_MEM_DIALOG_CONTEXT_ENTITIES, CollType.Map, dh);

			be.access(MiNDAccessCommand.Set, GIS_STO_ROOT, GIS_MEM_MODEL_ENTITY_STORE, CollType.One, null);
			be.access(MiNDAccessCommand.Set, GIS_STO_ROOT, GIS_MEM_MODEL_ENTITY_CONTEXT, CollType.One, null);
		}

		Giskard.log(null, this.getClass().getName(), "BELLO...", GIS_UNI_IDEA);
	}

	@Override
	public void initBrain() throws Exception {
		H2COLLTYPE.add(GIS_TAG_IDEA_COLLTYPE_ARR, CollType.Arr);
		H2COLLTYPE.add(GIS_TAG_IDEA_COLLTYPE_ONE, CollType.One);
		H2COLLTYPE.add(GIS_TAG_IDEA_COLLTYPE_SET, CollType.Set);
		H2COLLTYPE.add(GIS_TAG_IDEA_COLLTYPE_MAP, CollType.Map);

		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_COLLTYPE, GIS_TAG_IDEA_COLLTYPE_ONE, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_COLLTYPE, GIS_TAG_IDEA_COLLTYPE_ARR, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_COLLTYPE, GIS_TAG_IDEA_COLLTYPE_MAP, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_COLLTYPE, GIS_TAG_IDEA_COLLTYPE_SET, GIS_MEM_MODEL_ENTITY_OWNER);

		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_VALTYPE, GIS_TAG_IDEA_VALTYPE_INT, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_VALTYPE, GIS_TAG_IDEA_VALTYPE_LINK, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_VALTYPE, GIS_TAG_IDEA_VALTYPE_RAW, GIS_MEM_MODEL_ENTITY_OWNER);
		Giskard.access(MiNDAccessCommand.Set, GIS_TAG_IDEA_VALTYPE, GIS_TAG_IDEA_VALTYPE_REAL, GIS_MEM_MODEL_ENTITY_OWNER);

		eBrain.access(MiNDAccessCommand.Insert, GIS_TAG_GENERIC_LENIENT, GIS_MEM_MODEL_ENTITY_TAGS, CollType.Set, null);

//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_IDEA_TYPE, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_IDEA_MEMBER, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_IDEA_TAG, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_MODEL_UNIT, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_MODEL_STORE, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_MODEL_ENTITY, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_MODEL_HANDLE, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_DUST_BRAIN, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_IDEA_TYPE, GIS_TYP_DIALOG_CONTEXT, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);

		DustBrainUtilsDev.loadHandles(DustConsts.class, GIS_STO_ROOT);

//		Giskard.access(MiNDAccessCommand.Set, GIS_TYP_HANDLE, GIS_MEM_MODEL_ENTITY_HANDLE, HANDLE_ATT_MEMBER_TYPE);
//		Giskard.access(MiNDAccessCommand.Insert, GIS_TAG_IDEA_VALTYPE_LINK, GIS_MEM_MODEL_ENTITY_HANDLE, GIS_MEM_MODEL_ENTITY_TAGS);
//		Giskard.access(MiNDAccessCommand.Insert, GIS_TAG_IDEA_COLLTYPE_ONE, GIS_MEM_MODEL_ENTITY_HANDLE, GIS_MEM_MODEL_ENTITY_TAGS);

		Giskard.log(null, "initBrain complete", eBrain);
	}
}
