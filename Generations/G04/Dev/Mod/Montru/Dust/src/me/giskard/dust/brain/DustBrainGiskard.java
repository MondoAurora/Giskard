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

	DustHandleFormatter HFMT = new DustHandleFormatter() {

		@Override
		public String toString(DustHandle h) {
			String hn = access_(MiNDAccessCommand.Peek, null, GIS_LANG_DUST_BOOT, GIS_MEM_LANG_LANG_TERMINOLOGY, h);
			DustHandle hpt = access_(MiNDAccessCommand.Peek, null, h, GIS_MEM_MODEL_ENTITY_PRIMARYTYPE);
			String ptn = (null == hpt) ? "???"
					: access_(MiNDAccessCommand.Peek, "???", GIS_LANG_DUST_BOOT, GIS_MEM_LANG_LANG_TERMINOLOGY, hpt);

			return (null == hn) ? DEF_FMT.toString(h) : ptn + ":" + hn;
		}
	};

	public boolean isLearning(MiNDHandle hMember) {
		Boolean learning = eBrain.access(MiNDAccessCommand.Check, GIS_TAG_GENERIC_LENIENT, GIS_MEM_MODEL_ENTITY_TAGS,
				CollType.Set, null);

		if ( Boolean.TRUE.equals(learning) ) {
			return true;
		}

		return false;
	}

	public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {
		DustBrainEntity eMember = eRootStore.access(MiNDAccessCommand.Peek, null, GIS_MEM_DIALOG_CONTEXT_ENTITIES,
				CollType.Map, hMember);
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

	public DustBrainEntity resolveHandle(MiNDHandle h) {
		return eRootStore.access(MiNDAccessCommand.Peek, null, GIS_MEM_DIALOG_CONTEXT_ENTITIES, CollType.Map, h);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		Object ret = null;
		int last = valPath.length - 1;
		int i = -1;

		if ( cmd != MiNDAccessCommand.Broadcast ) {
			if ( 0 > last ) {
				// probably create a temporary entity?
			} else {
				ret = valPath[0];

				for (i = 1; i <= last;) {
					DustBrainEntity e = resolveHandle((MiNDHandle) ret);
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
		} else {
			GiskardUtils.dump(" ", false, "DustBrainGiskard.access", cmd, val, valPath);
		}

		if ( null == ret ) {
			switch ( cmd ) {
			case Broadcast:
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
			case Visit:
				ret = null;
				break;
			}
		}

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

		eBrain.access(MiNDAccessCommand.Insert, GIS_TAG_GENERIC_LENIENT, GIS_MEM_MODEL_ENTITY_TAGS, CollType.Set, null);

		eBrain.access(MiNDAccessCommand.Set, GIS_LANG_DUST_BOOT, GIS_MEM_DUST_BRAIN_DEF_LANG, CollType.One, null);

		DustBrainUtilsDev.loadHandles(DustConsts.class, GIS_STO_ROOT, GIS_LANG_DUST_BOOT);

		DustHandle.FMT = HFMT;

		Giskard.log(null, "initBrain complete", eRootStore);
	}
}
