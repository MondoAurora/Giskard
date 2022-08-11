package me.giskard.dust.brain;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.DustBootConsts;

public class DustBrainEntity implements DustBrainConsts, DustBootConsts.DustStore {
	
	private final Map<MiNDEntity, Object> data = new HashMap<>();
	
	public DustBrainEntity(DustEntity handle) {
		data.put(TOKEN_ATT_ENTITY_HANDLE, handle);
	}

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		// TODO Auto-generated method stub
		return null;
	}
}
