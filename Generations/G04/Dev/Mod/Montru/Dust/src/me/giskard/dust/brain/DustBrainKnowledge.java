package me.giskard.dust.brain;

import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked" })
public class DustBrainKnowledge implements DustBrainConsts {

	private final Map<MiNDHandle, DustBrainInformation> mappedInfo = new HashMap<>();

	public DustBrainKnowledge(MiNDHandle handle) {
		mappedInfo.put(MODEL_MEM_KNOWLEDGE_HANDLE, new DustBrainInformation.Single(handle, null));
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle, CollType ct, Object key) {
		DustBrainInformation info = mappedInfo.get(handle);
		Object ret = null;
		boolean changed = false;

		if ( MODEL_MEM_KNOWLEDGE_TAGS == handle ) {
			MiNDHandle tagClass = Giskard.access(MiNDAccessCommand.Peek, null, (MiNDHandle) val, GENERIC_MEM_GEN_OWNER);
			key = (null == tagClass) ? val : tagClass;
		}

		if ( null == info ) {
			if ( cmd.creator && (null != val) ) {
				mappedInfo.put(handle, new DustBrainInformation.Single(val, key));
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
				break;
			}
		} else {
			DustBrainInformation i2 = info.optExtend(cmd, val, key, ct);
			if ( null != i2 ) {
				mappedInfo.put(handle, i2);
				info = i2;
			}

			ret = info.access(cmd, val, key);
			changed = info.changed;
		}

		if ( changed ) {
			// listener notification
		}

		return (RetType) ret;
	}

	boolean inToString = false;

	@Override
	public String toString() {
		try {
			if ( inToString ) {
				return GiskardUtils.toString(mappedInfo.get(MODEL_MEM_KNOWLEDGE_HANDLE));
			} else {
				inToString = true;
				return mappedInfo.toString();
			}
		} finally {
			inToString = false;
		}
	}
}
