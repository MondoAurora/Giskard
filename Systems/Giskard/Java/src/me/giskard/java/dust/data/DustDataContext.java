package me.giskard.java.dust.data;

import me.giskard.java.MindConsts;
import me.giskard.java.dust.DustCollections.DustFactory;

public class DustDataContext implements DustDataConsts, MindConsts.MiNDContext {
	DustDataContext parent;
	
	
	DustFactory<MiNDEntity, DustDataBlock> entityBlocks;
	
	@Override
	public void select(MiNDEntity target, Object... path) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public <RetType> RetType access(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
}
