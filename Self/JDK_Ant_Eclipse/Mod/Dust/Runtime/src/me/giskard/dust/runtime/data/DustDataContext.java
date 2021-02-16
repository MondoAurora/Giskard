package me.giskard.dust.runtime.data;

import me.giskard.MindConsts;
import me.giskard.dust.runtime.DustCollections.DustFactory;

public class DustDataContext implements DustDataConsts, MindConsts.MiNDContext {
	DustDataContext parent;
	
	
	DustFactory<DustToken, DustDataBlock> entityBlocks;


	@Override
	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selectById(MiNDToken target, String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectByPath(MiNDToken target, Object... path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
