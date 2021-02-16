package me.giskard.dust.runtime.data;

import me.giskard.Mind;
import me.giskard.MindConsts;
import me.giskard.dust.runtime.DustCollections;
import me.giskard.dust.runtime.DustMeta;

public class DustDataContext implements DustDataConsts, DustMeta, DustCollections, MindConsts.MiNDContext {
	DustDataContext parentCtx;
	
	DustMap<Object, DustToken> tokens = new DustMap<>(true);
	
	DustFactory<MiNDToken, DustDataBlock> entityBlocks = new DustFactory<>(false, DustDataBlock.class);
	
	DustToken getToken(Object id) {
		DustToken ret = tokens.get(id);
		if ( null != parentCtx ) {
			ret = parentCtx.getToken(id);
		}
		
		return ret;
	}

	@Override
	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		DustToken parent = (MiNDTokenType.UNIT == type) ? null : (DustToken) params[0];
		String id = DustToken.buildId(name, parent);
		
		DustToken ret = tokens.get(id);
		
		if ( null == ret ) {
			ret = getToken(id);
			
			if ( null == ret ) {
				ret = DustToken.createToken(type, name, params);
			}
			
			tokens.put(id, ret);
		}
		
		return ret;
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
		switch ( cmd ) {
		case SET:
			entityBlocks.get(target).access(cmd, val, valPath);
			break;

		default:
			Mind.log(MiNDEventLevel.TRACE, cmd, val, target, valPath);
			break;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entityBlocks.toString();
	}
}
