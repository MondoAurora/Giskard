package me.giskard.dust.runtime.model;

import me.giskard.MindConsts;
import me.giskard.coll.MindCollConsts;
import me.giskard.coll.MindCollFactory;
import me.giskard.coll.MindCollMap;
import me.giskard.dust.runtime.DustMeta;
import me.giskard.utils.MindUtils;

public class DustModelContext implements DustModelConsts, DustMeta, MindCollConsts, MindConsts.MiNDContext {
	DustModelContext parentCtx;

	MindCollMap<Object, DustToken> tokens = new MindCollMap<>(true);

	MindCollFactory<MiNDToken, DustModelBlock> entityBlocks = new MindCollFactory<>(false, DustModelBlock.class);

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

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		Object ret = null;

		DustModelBlock eb = MindUtils.isAccessCreator(cmd) ? entityBlocks.get(target) : entityBlocks.peek(target);

		if ( null == eb ) {
			switch ( cmd ) {
			case Add:
			case Set:
				// can't be here
				break;
			case Chk:
			case Del:
				ret = Boolean.FALSE;
				break;
			case Get:
				ret = val;
				break;
			case Use:
				ret = MiNDResultType.REJECT;
				break;
			}
		} else {
			ret = eb.access(cmd, val, valPath);
		}

		return (RetType) ret;
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entityBlocks.toString();
	}
}
