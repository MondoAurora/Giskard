package me.giskard.dust.brain;

import java.util.HashMap;
import java.util.Map;

public class DustBrainEntity implements DustBrainConsts {
	
	private final Map<MiNDHandle, Object> data = new HashMap<>();
	
	public DustBrainEntity(MiNDHandle handle) {
		data.put(HANDLE_ATT_ENTITY_HANDLE, handle);
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle) {
		return access(cmd, val, handle, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle, Object key) {
		Object attVal = data.get(handle);
		Object ret = null;
		
		switch ( cmd ) {
		case Insert:
			if ( null == attVal ) {
				attVal = new HashMap<>();
				data.put(handle, attVal);
			}
			
			ret = ((Map)attVal).put(key, val);
			
			break;
		case Broadcast:
			break;
		case Check:
			break;
		case Delete:
			break;
		case Get:
			break;
		case Peek:
			if ( attVal instanceof Map ) {
				ret = ((Map)attVal).get(key);
			}
			break;
		case Set:
			ret = data.put(handle, val);
			break;
		case Visit:
			break;
		}
		
		return (RetType) ret;
	}
}
