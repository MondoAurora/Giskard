package me.giskard.dust.data;

import me.giskard.MindConsts;
import me.giskard.dust.DustCollections.DustFactory;

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
