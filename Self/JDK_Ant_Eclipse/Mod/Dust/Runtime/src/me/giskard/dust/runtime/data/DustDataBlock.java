package me.giskard.dust.runtime.data;

import me.giskard.coll.MindCollMap;

public class DustDataBlock implements DustDataConsts {
	DustDataBlock parent;
	MiNDCollMap<DustToken, Object> localData = new MindCollMap<DustToken, Object>(false);
	
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

		switch ( cmd ) {
		case Set:
			localData.put((DustToken)valPath[0], val);
			break;

		default:
//			Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);
			break;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return localData.toString();
	}
}
