package me.giskard.dust.runtime.model;

import java.util.HashSet;
import java.util.Set;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.MindCollConsts;
import me.giskard.coll.MindCollFactory;
import me.giskard.coll.MindCollMap;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeMeta;

public class DustModelContext
		implements DustModelConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustContext {
	DustModelContext parentCtx;

	MindCollMap<Object, DustToken> tokens = new MindCollMap<>(true);

	MindCollFactory<MiNDToken, DustModelBlock> entityBlocks = new MindCollFactory<>(false,
			new MiNDCreator<MiNDToken, DustModelBlock>() {
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
		ref.to.incomingRefs.remove(ref);
		ref.from.access(MiNDAccessCommand.Del, ref, ref.def);
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
				ret.setEntity(entityBlocks.get(ret));
			}

			tokens.put(id, ret);
		}

		return ret;
	}

	@Override
	public void selectByPath(MiNDToken target, Object... path) {
		Giskard.log(MiNDEventLevel.TRACE, "selectByPath", target, path);
		if ( 0 == path.length ) {
			entityBlocks.put(target, new DustModelBlock(this));
		} else {
			Object b = null;
			for (Object o : path) {
				DustTokenMember mt = (DustTokenMember) o;
				b = (null == b) ? entityBlocks.peek(mt) : ((DustModelBlock) b).localData.get(mt);
				if ( null == b ) {
					break;
				} else if ( b  instanceof DustModelRef ) {
					b = ((DustModelRef)b).to;
				}
			}
			entityBlocks.put(target, (DustModelBlock) b);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		Object ret = null;

		DustModelBlock eb = GiskardUtils.isAccessCreator(cmd) ? entityBlocks.get(target) : entityBlocks.peek(target);

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
			if ( 0 < valPath.length ) {
				ret = eb.access(cmd, val, (DustTokenMember) valPath[0]);
			} else {
				switch ( cmd ) {
				case Add:
				case Set:
					// can't be here
					break;
				case Chk:
					ret = Boolean.TRUE;
					break;
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
			}
		}

		return (RetType) ret;
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entityBlocks.toString();
	}
}
