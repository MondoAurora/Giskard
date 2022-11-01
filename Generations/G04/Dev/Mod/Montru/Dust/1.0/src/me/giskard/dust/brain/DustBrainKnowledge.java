package me.giskard.dust.brain;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked" })
public class DustBrainKnowledge implements DustBrainConsts {
	DustBrainInformation.ContMap infoMap = new DustBrainInformation.ContMap();

	public DustBrainKnowledge(MiNDHandle handle) {
		infoMap.setValType(ValType.Raw);
		infoMap.access(MiNDAccessCommand.Set, new DustBrainInformation.Single(handle, null, CollType.One), MODEL_MEM_KNOWLEDGE_HANDLE);
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle, CollType ct, Object key) {
		DustBrainInformation info = infoMap.access(MiNDAccessCommand.Peek, null, handle);
		Object ret = null;
		boolean changed = false;

		if ( null == handle ) {
			ret = (KEY_ITERATOR == key) ? infoMap.container.keySet().iterator() : infoMap.access(cmd, val, key);
		} else {
			if ( (MODEL_MEM_KNOWLEDGE_TAGS == handle) && !(key instanceof MiNDSpecKey) ) {
				MiNDHandle tagClass = Giskard.access(MiNDAccessCommand.Peek, null, (MiNDHandle) val, GENERIC_MEM_GEN_OWNER, null);
				key = (null == tagClass) ? val : tagClass;
			}

			if ( null == info ) {
				if ( cmd.creator && (null != val) ) {
					infoMap.access(MiNDAccessCommand.Set, new DustBrainInformation.Single(val, key, ct), handle);
					changed = true;
				}
				switch ( cmd ) {
				case Check:
				case Delete:
					ret = false;
					break;
				case Get:
				case Peek:
					ret = val;
					break;
				case Insert:
				case Set:
					ret = true;
					break;
				}
			} else {
				DustBrainInformation i2 = info.optExtend(cmd, val, key, ct);
				if ( null != i2 ) {
					infoMap.access(MiNDAccessCommand.Set, i2, handle);
					info = i2;
				}

				ret = info.access(cmd, val, key);
				changed = info.changed;
			}
		}

		if ( changed ) {
//			if ( cmd.creator ) {
//				access(MiNDAccessCommand.Insert, handle, MODEL_MEM_KNOWLEDGE_MEMBERS);
//				if ( changed ) {
//					
//				}
//			}
			// listener notification
		}

		return (RetType) ret;
	}

	boolean inToString = false;

	@Override
	public String toString() {
		try {
			if ( inToString ) {
				return GiskardUtils.toString(infoMap.access(MiNDAccessCommand.Peek, null, MODEL_MEM_KNOWLEDGE_HANDLE));
			} else {
				inToString = true;
				return infoMap.toString();
			}
		} finally {
			inToString = false;
		}
	}
}
