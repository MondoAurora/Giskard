package me.giskard.dust.runtime.knowledge;

import java.util.HashSet;
import java.util.Set;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.MindCollConsts;
import me.giskard.coll.MindCollFactory;
import me.giskard.coll.MindCollMap;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeMeta;

public class DustKnowledgeContext
		implements DustKnowledgeConsts, DustRuntimeMeta, MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustContext {
	DustKnowledgeContext parentCtx;

	MindCollMap<Object, DustToken> tokens = new MindCollMap<>(true);

	MindCollFactory<MiNDToken, DustKnowledgeBlock> entities = new MindCollFactory<>(false,
			new MiNDCreator<MiNDToken, DustKnowledgeBlock>() {
				@Override
				public DustKnowledgeBlock create(MiNDToken key) {
					return new DustKnowledgeBlock(DustKnowledgeContext.this);
				}
			});

	Set<DustKnowledgeLink> allLinks = new HashSet<>();
	
	public DustKnowledgeContext(DustKnowledgeContext parentCtx_) {
		this.parentCtx = parentCtx_;
	}

	public DustKnowledgeContext() {
		this(null);
	}

	DustToken getToken(Object id) {
		DustToken ret = tokens.get(id);
		if ( null != parentCtx ) {
			ret = parentCtx.getToken(id);
		}

		return ret;
	}

	DustKnowledgeLink setLink(DustKnowledgeBlock from, DustTokenMember def, DustKnowledgeBlock to) {
		DustKnowledgeLink link = new DustKnowledgeLink(from, def, to);
		allLinks.add(link);
		return link;
	}

	void delLink(DustKnowledgeLink link) {
		allLinks.remove(link);
		link.from.access(MiNDAccessCommand.Del, link, link.def);
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
				ret.setEntity(entities.get(ret));
			}

			tokens.put(id, ret);
		}

		return ret;
	}

	@Override
	public boolean selectByPath(MiNDToken target, Object... path) throws Exception {
		Giskard.log(MiNDEventLevel.TRACE, "selectByPath", target, path);
		if ( 0 == path.length ) {
			entities.put(target, new DustKnowledgeBlock(this));
			return true;
		} else {
			Object b = resolvePath(target, path);
			if ( b instanceof DustKnowledgeBlock) {
				entities.put(target, (DustKnowledgeBlock) b);
				return true;
			} else {
				return false;
			}
		}
	}

	public Object resolvePath(MiNDToken target, Object... path) throws Exception {
		Object b = null;
		for (Object o : path) {
			if ( null == b ) {
				b = entities.peek((MiNDToken) o);
			} else if ( o instanceof DustTokenMember ) {
				b = ((DustKnowledgeBlock) b).localData.get((DustTokenMember) o);
			} else if ( b instanceof DustKnowledgeCollection.ValArr) {
				b = ((DustKnowledgeCollection<?>) b).access(MiNDAccessCommand.Get, null, (Integer) o);
			}
			
			if ( null == b ) {
				return false;
			} else if ( b  instanceof DustKnowledgeLink ) {
				b = ((DustKnowledgeLink)b).to;
			}
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		Object ret = null;

		DustKnowledgeBlock eb = GiskardUtils.isAccessCreator(cmd) ? entities.get(target) : entities.peek(target);

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
				case Get:
					ret = eb.access(cmd, val, null);
					break;
				case Chk:
					ret = Boolean.TRUE;
					break;
				case Del:
					ret = Boolean.FALSE;
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
	public void put(MiNDToken token, Object block) {
		entities.put(token, (DustKnowledgeBlock) block);
	}

	@Override
	public String toString() {
		return "Tokens: \n" + tokens.toString() + "\n\nEntities: \n" + entities.toString();
	}
}
