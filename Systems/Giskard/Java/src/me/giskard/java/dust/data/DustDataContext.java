package me.giskard.java.dust.data;

import me.giskard.java.dust.DustCollections.DustFactory;
import me.giskard.java.dust.DustConsts;

public class DustDataContext implements DustDataConsts, DustConsts.DustContext {
	DustDataContext parent;
	
	
	DustFactory<DustEntity, DustDataBlock> entities;
	
	
	@Override
	public void select_(MiNDEntity target, MiNDEntity... path) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <RetType> RetType access_(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
}
