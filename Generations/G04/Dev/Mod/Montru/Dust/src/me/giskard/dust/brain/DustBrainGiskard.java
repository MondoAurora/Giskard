package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.DustBootConsts;
import me.giskard.dust.DustTokens;

public class DustBrainGiskard implements DustBrainConsts, DustBootConsts.DustGiskard {

	final DustBrainKnowledge eBrain;
	final DustBrainKnowledge eRootStore;

	DustHandleFormatter HFMT = new DustHandleFormatter() {

		@Override
		public String toString(DustHandle h) {
			String hn = access_(MiNDAccessCommand.Peek, null, DUST_LANG_BOOT, LANG_MEM_LANG_TERMINOLOGY, h);
			DustHandle hpt = access_(MiNDAccessCommand.Peek, null, h, MODEL_MEM_ENTITY_PRIMARYTYPE);
			String ptn = (null == hpt) ? "???"
					: access_(MiNDAccessCommand.Peek, "???", DUST_LANG_BOOT, LANG_MEM_LANG_TERMINOLOGY, hpt);

			return (null == hn) ? DEF_FMT.toString(h) : ptn + ":" + hn;
		}
	};

	public boolean isLearning(MiNDHandle hMember) {
		Boolean learning = eBrain.access(MiNDAccessCommand.Check, GENERIC_TAG_LENIENT, MODEL_MEM_ENTITY_TAGS,
				CollType.Set, null);

		if ( Boolean.TRUE.equals(learning) ) {
			return true;
		}

		return false;
	}

	public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {
		
		if ((null == key) && (cmd == MiNDAccessCommand.Peek) ) {
			return CollType.One;
		}
		
		DustBrainKnowledge eMember = eRootStore.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_ENTITIES,
				CollType.Map, hMember);
		MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, MODEL_MEM_ENTITY_TAGS, CollType.Set,
				IDEA_TAG_COLLTYPE);

		CollType ret = null;

		if ( null == h ) {
			ret = DustBrainUtils.guessCollType(cmd, key);

			if ( (null != ret) && isLearning(hMember) ) {
				h = HANDLE2ENUM.getLeft(ret);
				eMember.access(MiNDAccessCommand.Insert, h, MODEL_MEM_ENTITY_TAGS, CollType.Set, IDEA_TAG_COLLTYPE);
			}
		} else {
			ret = HANDLE2ENUM.getRight(h);
		}

		return ret;
	}

	public DustBrainGiskard() {
		eBrain = new DustBrainKnowledge(null);
		eRootStore = new DustBrainKnowledge(DUST_SRC_ROOT);
	}

	public DustBrainKnowledge resolveHandle(MiNDHandle h) {
		return eRootStore.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_ENTITIES, CollType.Map, h);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = null;
		int last = valPath.length - 1;
		int i = -1;

			if ( 0 > last ) {
				// probably create a temporary entity?
			} else {
				ret = valPath[0];

				for (i = 1; i <= last;) {
					DustBrainKnowledge e = resolveHandle((MiNDHandle) ret);
					MiNDHandle h = (DustHandle) valPath[i++];
					Object key = (i <= last) ? valPath[i] : null;

					CollType ct = getCollType(h, MiNDAccessCommand.Peek, key);
					if ( (null != ct) && ct.hasKey ) {
						++i;
					}

					if ( null == e ) {
						break;
					} else {
						ret = (i < last) ? e.access(MiNDAccessCommand.Peek, null, h, ct, key)
								: e.access(cmd, val, h, getCollType(h, cmd, key), key);
					}
				}
			}

		if ( null == ret ) {
			switch ( cmd ) {
			case Check:
			case Delete:
				ret = false;
				break;
			case Get:
				if ( i == last ) {
					// also, put?
				} else {
					ret = val;
				}
				break;
			case Insert:
				ret = null;
				break;
			case Peek:
				ret = val;
				break;
			case Set:
				ret = null;
				break;
			}
		}

		return (RetType) ret;
	}
	
	@Override
	public void broadcast_(MiNDHandle event, Object... params) {
		GiskardUtils.dump(" ", false, "DustBrainGiskard.access", event, params);	
	}

	@Override
	public void initBootHandles(GisCollFactory<Long, DustHandle> bootFact) {

		for (Long k : bootFact.keys()) {
			DustHandle dh = (DustHandle) bootFact.peek(k);
			DustBrainKnowledge be = (DUST_SRC_ROOT == dh) ? eRootStore : new DustBrainKnowledge(dh);
			eRootStore.access(MiNDAccessCommand.Insert, dh, MODEL_MEM_SOURCE_HANDLES, CollType.Map, k);
			eRootStore.access(MiNDAccessCommand.Insert, be, NARRATIVE_MEM_JOURNEY_ENTITIES, CollType.Map, dh);

			be.access(MiNDAccessCommand.Set, DUST_SRC_ROOT, MODEL_MEM_ENTITY_STORE, CollType.One, null);
			be.access(MiNDAccessCommand.Set, DUST_SRC_ROOT, MODEL_MEM_ENTITY_CONTEXT, CollType.One, null);
		}

		Giskard.log(null, this.getClass().getName(), "BELLO...", IDEA_UNI);
	}

	@Override
	public void initBrain() throws Exception {
//		HANDLE2ENUM.add(IDEA_TAG_COLLTYPE_ARR, CollType.Arr);
//		HANDLE2ENUM.add(IDEA_TAG_COLLTYPE_ONE, CollType.One);
//		HANDLE2ENUM.add(IDEA_TAG_COLLTYPE_SET, CollType.Set);
//		HANDLE2ENUM.add(IDEA_TAG_COLLTYPE_MAP, CollType.Map);
//
//		HANDLE2ENUM.add(IDEA_TAG_VALTYPE_INT, ValType.Int);
//		HANDLE2ENUM.add(IDEA_TAG_VALTYPE_LINK, ValType.Link);
//		HANDLE2ENUM.add(IDEA_TAG_VALTYPE_REAL, ValType.Real);
//		HANDLE2ENUM.add(IDEA_TAG_VALTYPE_RAW, ValType.Raw);
		
		DustBrainUtilsDev.loadEnums(DustTokens.class, ValType.class, "IDEA_TAG_VALTYPE");
		DustBrainUtilsDev.loadEnums(DustTokens.class, CollType.class, "IDEA_TAG_COLLTYPE");
		
		DustBrainUtilsDev.loadEnums(DustTokens.class, MiNDAccessCommand.class, "NARRATIVE_TAG_ACCESS");
		DustBrainUtilsDev.loadEnums(DustTokens.class, MiNDAction.class, "NARRATIVE_TAG_ACTION");
		DustBrainUtilsDev.loadEnums(DustTokens.class, MiNDResultType.class, "NARRATIVE_TAG_RESULT");

		eBrain.access(MiNDAccessCommand.Insert, GENERIC_TAG_LENIENT, MODEL_MEM_ENTITY_TAGS, CollType.Set, null);

		eBrain.access(MiNDAccessCommand.Set, DUST_LANG_BOOT, DUST_MEM_BRAIN_DEF_LANG, CollType.One, null);

		DustBrainUtilsDev.loadHandles(DustTokens.class, DUST_SRC_ROOT, DUST_LANG_BOOT);

		DustHandle.FMT = HFMT;

		Giskard.log(null, "initBrain complete", eRootStore);
	}
}
