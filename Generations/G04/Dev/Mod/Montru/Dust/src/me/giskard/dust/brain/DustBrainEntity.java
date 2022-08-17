package me.giskard.dust.brain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class DustBrainEntity implements DustBrainConsts {

	private final Map<MiNDHandle, Object> data = new HashMap<>();
	private Map<MiNDHandle, MiNDHandle> singleTags;

	public DustBrainEntity(MiNDHandle handle) {
		data.put(GIS_MEM_MODEL_ENTITY_HANDLE, handle);
	}

	boolean inToString = false;

	@Override
	public String toString() {
		try {
			if ( inToString ) {
				return GiskardUtils.toString(data.get(GIS_MEM_MODEL_ENTITY_HANDLE));
			} else {
				inToString = true;
				return data.toString();
			}
		} finally {
			inToString = false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle, CollType ct, Object key) {
		Object attVal = data.get(handle);
		Object ret = null;
		boolean changed = false;

		switch ( cmd ) {
		case Insert:
			if ( null == attVal ) {
				switch ( ct ) {
				case Arr:
					attVal = new ArrayList<>();
					break;
				case Map:
					attVal = new HashMap<>();
					break;
				case One:
				case Set:
					attVal = new HashSet<>();
					break;
				}
				data.put(handle, attVal);
			}

			switch ( ct ) {
			case Arr:
				attVal = new ArrayList<>();
				break;
			case Map:
				ret = ((Map) attVal).put(key, val);
				changed = !GiskardUtils.isEqual(ret, val);
				break;
			case One:
			case Set:
				ret = changed = ((Set) attVal).add(val);
				break;
			}

			break;
		case Broadcast:
			break;
		case Check:
			if ( null == attVal ) {
				ret = false;
			} else
				switch ( ct ) {
				case Arr:
				case Set:
					ret = ((Collection) attVal).contains(val);
					break;
				case Map:
					ret = ((Map) attVal).containsValue(val);
					break;
				case One:
					ret = GiskardUtils.isEqual(attVal, val);
					break;
				}

			break;
		case Delete:
			break;
		case Get:
			break;
		case Peek:
			if ( GIS_MEM_MODEL_ENTITY_TAGS == handle ) {
				ret = (null == singleTags) ? val : singleTags.getOrDefault(key, (MiNDHandle) val);
			} else if ( attVal instanceof Map ) {
				ret = ((Map) attVal).get(key);
			} else {
				ret = attVal;
			}
			break;
		case Set:
			ret = data.put(handle, val);
			changed = !GiskardUtils.isEqual(ret, val);

			break;
		case Visit:
			break;
		}

		if ( changed ) {

			// single tag management
			if ( GIS_MEM_MODEL_ENTITY_TAGS == handle ) {
				MiNDHandle tagClass = Giskard.access(MiNDAccessCommand.Peek, null, val, GIS_MEM_MODEL_ENTITY_OWNER);
				if ( null != tagClass ) {
					if ( null == singleTags ) {
						singleTags = new HashMap<>();
					} else {
						MiNDHandle prev = singleTags.get(tagClass);
						((Set) attVal).remove(prev);
					}
					singleTags.put(tagClass, (MiNDHandle) val);
				}
			}

			// listener notification
		}

		return (RetType) ret;
	}

}
