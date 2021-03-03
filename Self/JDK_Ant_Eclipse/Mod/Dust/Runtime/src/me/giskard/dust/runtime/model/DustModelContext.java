package me.giskard.dust.runtime.model;

import java.util.HashSet;
import java.util.Set;

import me.giskard.Mind;
import me.giskard.MindConsts;
import me.giskard.coll.MindCollConsts;
import me.giskard.coll.MindCollFactory;
import me.giskard.coll.MindCollMap;
import me.giskard.dust.runtime.DustMeta;
import me.giskard.dust.runtime.DustMind;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.tokens.DustTokens;
import me.giskard.utils.MindTokenTranslator;
import me.giskard.utils.MindUtils;

public class DustModelContext implements DustModelConsts, DustMeta, MindCollConsts, DustRuntimeConsts, MindConsts.MiNDContext {
	DustModelContext parentCtx;

	MindCollMap<Object, DustToken> tokens = new MindCollMap<>(true);

	MindCollFactory<MiNDToken, DustModelBlock> entityBlocks = new MindCollFactory<>(false, new MiNDCreator<MiNDToken, DustModelBlock>() {
		@Override
		public DustModelBlock create(MiNDToken key) {
			return new DustModelBlock(DustModelContext.this);
		}
	});
	
	Set<DustModelRef> refs = new HashSet<>();

	DustToken getToken(Object id) {
		DustToken ret = tokens.get(id);
		if ( null != parentCtx ) {
			ret = parentCtx.getToken(id);
		}

		return ret;
	}
	
	DustModelRef setRef(DustModelBlock from, DustTokenMember def, DustModelBlock to) {
		DustModelRef ref = new DustModelRef(from, def, to);
		
		refs.add(ref);
		if ( null == to.incomingRefs ) {
			to.incomingRefs = new HashSet<>();
		}
		to.incomingRefs.add(ref);
		
		return ref;
	}

	void delRef(DustModelRef ref) {
		refs.remove(ref);
		if ( null == ref.to.incomingRefs ) {
			ref.to.incomingRefs.remove(ref);
		}
	}

	@Override
	public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
		DustToken parent = ((MiNDTokenType.UNIT == type) || (MiNDTokenType.LOCAL == type)) ? null : (DustToken) params[0];
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

//	@Override
//	public void selectById(MiNDToken target, String id) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void selectByPath(MiNDToken target, Object... path) {
		Mind.log(MiNDEventLevel.TRACE, "selectByPath", target, path);
		if ( 0 == path.length ) {
			entityBlocks.put(target, new DustModelBlock(this));
		}
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
	
	public static void boot() {
		Mind.log(MiNDEventLevel.TRACE, "would boot now...");
		
		MindTokenTranslator.setTokenMember(MTMEMBER_TAGGED_TAGS);
		
		// Machine
		Mind.selectByPath(MTSHARED_MACHINE);
		
		// Application
		Mind.selectByPath(MTMEMBER_ACTION_PARAM);
		Mind.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_PARAM, MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP);
		
		// Runtime module
		DustTokens.registerNewModule();

		// Agent implementation data
		DustTokens.addModuleImpInfo(MTAGENT_MIND, DustMind.class);
		DustTokens.addModuleImpInfo(MTAGENT_CONTEXT, DustModelContext.class);
		
	}

}
