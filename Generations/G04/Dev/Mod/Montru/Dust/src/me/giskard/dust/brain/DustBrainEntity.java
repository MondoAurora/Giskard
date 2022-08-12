package me.giskard.dust.brain;

import java.util.HashMap;
import java.util.Map;

public class DustBrainEntity implements DustBrainConsts {
	
	private final Map<MiNDEntity, Object> data = new HashMap<>();
	
	public DustBrainEntity(MiNDEntity handle) {
		data.put(TOKEN_ATT_ENTITY_HANDLE, handle);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDEntity token, Object key) {
		Object attVal = data.get(token);
		Object ret = null;
		
		switch ( cmd ) {
		case Insert:
			if ( null == attVal ) {
				attVal = new HashMap<>();
				data.put(token, attVal);
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
			break;
		case Set:
			break;
		case Visit:
			break;
		}
		
		return (RetType) ret;
	}
}
