package me.giskard.dust.runtime.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustKnowledgeCollection<ContainerType> implements DustKnowledgeConsts {
	protected final ContainerType container;
	protected final DustKnowledgeBlock owner;
	protected final DustTokenMember token;

	public abstract Object access(MiNDAccessCommand cmd, Object val, Object key) throws Exception;

	public static DustKnowledgeCollection create(DustKnowledgeBlock owner, DustTokenMember token) {
		switch ( token.getCollType() ) {
		case Arr:
			return new ValArr(owner, token);
		case Map:
			return new ValMap(owner, token);
		case Set:
			return new ValSet(owner, token);
		default:
			return null;
		}
	}

	public DustKnowledgeCollection(DustKnowledgeBlock owner, DustTokenMember token, ContainerType container) {
		this.owner = owner;
		this.token = token;
		this.container = container;
	}

	public static class ValSet extends DustKnowledgeCollection<Set> {
		public ValSet(DustKnowledgeBlock owner, DustTokenMember token) {
			super( owner,  token, new HashSet());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key)
				throws Exception {
			switch ( cmd ) {
			case Add:
				if ( val instanceof DustKnowledgeBlock ) {
					for (Object link : container) {
						if ( ((DustKnowledgeLink) link).to == val ) {
							return false;
						}
					}
					container.add(owner.ctx.setLink(owner, token, (DustKnowledgeBlock) val));
				} else {
					container.add(val);
				}
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				break;
			case Set:
				break;
			case Use:
				for (Object ob : container) {
					DustKnowledgeUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
				}
				break;
			}
			return null;
		}
	}

	public static class ValArr extends DustKnowledgeCollection<ArrayList> {
		public ValArr(DustKnowledgeBlock owner, DustTokenMember token) {
			super( owner,  token, new ArrayList());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key)
				throws Exception {
			Object ret = val;
			Integer idx = (Integer) key;
			
			switch ( cmd ) {
			case Add:
				if ( val instanceof DustKnowledgeBlock ) {
					val = owner.ctx.setLink(owner, token, (DustKnowledgeBlock) val);
				}
				container.add(val);
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				if ( idx < container.size() ) {
					ret = container.get(idx);
				}
				break;
			case Set:
				break;
			case Use:
				for (Object ob : container) {
					DustKnowledgeUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
				}
				break;
			}
			return ret;
		}
	}

	public static class ValMap extends DustKnowledgeCollection<Map> {
		public ValMap(DustKnowledgeBlock owner, DustTokenMember token) {
			super( owner,  token, new HashMap());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key)
				throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
