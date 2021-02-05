package me.giskard.dust.data;

public class DustDataBlock implements DustDataConsts {
	DustDataBlock parent;
	DustCollMap<DustToken, Object> localData;
	
	public <RetType> RetType access(MiNDAccessCommand cmd, MiNDToken tMember, RetType val, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
}
