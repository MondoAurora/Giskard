package me.giskard.dust.runtime.data;

import me.giskard.Mind;
import me.giskard.dust.runtime.DustCollections.DustMap;

public class DustDataBlock implements DustDataConsts {
	DustDataBlock parent;
	DustCollMap<DustToken, Object> localData = new DustMap<DustToken, Object>(false);
	
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		switch ( cmd ) {
		case SET:
			localData.put((DustToken)valPath[0], val);
			break;

		default:
			Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);
			break;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return localData.toString();
	}
}
