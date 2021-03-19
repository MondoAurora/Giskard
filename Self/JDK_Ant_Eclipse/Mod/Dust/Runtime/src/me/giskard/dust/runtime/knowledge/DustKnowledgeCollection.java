package me.giskard.dust.runtime.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import me.giskard.GiskardException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustKnowledgeCollection<ContainerType> implements DustKnowledgeConsts {
	protected final ContainerType container;
	protected final DustKnowledgeBlock owner;
	protected final DustTokenMember token;

	public abstract Object access(MiNDAccessCommand cmd, Object val, Object key);

	public static DustKnowledgeCollection create(DustKnowledgeBlock owner, DustTokenMember token) {
		switch ( token.getCollType() ) {
		case Arr:
			return new ValArr(owner, token);
		case Map:
			return new ValMap(owner, token);
		case Set:
			return (MTMEMBER_ENTITY_TAGS == token) ? new TagManager(owner, token) : new ValSet(owner, token);
		default:
			return null;
		}
	}

	public DustKnowledgeCollection(DustKnowledgeBlock owner, DustTokenMember token, ContainerType container) {
		this.owner = owner;
		this.token = token;
		this.container = container;
	}

	protected void notify(Object val, Iterable container) {
		for (Object ob : container) {
			try {
				DustKnowledgeUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
			} catch (Exception e) {
				GiskardException.swallow(e);
			}
		}
	}

	public static class ValSet extends DustKnowledgeCollection<Set> {
		public ValSet(DustKnowledgeBlock owner, DustTokenMember token) {
			super( owner,  token, new HashSet());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
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
				notify(val, container);
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
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
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
				notify(val, container);
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
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class TagManager extends ValSet {
		public TagManager(DustKnowledgeBlock owner, DustTokenMember token) {
			super(owner, token);
		}
		
		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			DustToken tok = (DustToken) val;
			Object ret = val;
			DustToken p = tok.getParent();

			switch ( cmd ) {
			case Add:
				container.add(tok);
				break;
			case Set:
				if ( !container.contains(tok) ) {
					if ( null != p ) {
						for (Iterator<DustToken> it = container.iterator(); it.hasNext();) {
							DustToken t = it.next();
							if ( p == t.getParent() ) {
								it.remove();
							}
						}
					}

					container.add(tok);
				}
				break;
			case Chk:
				ret = container.contains(tok);
				break;
			case Del:
				ret = container.remove(tok);
				break;
			case Get:
				ret = tok;

					if ( null != p ) {
						for (DustToken t : (Iterable<DustToken>) container) {
							if ( p == t.getParent() ) {
								ret = t;
								break;
							}
						}
				}
				break;
			case Use:
				break;
			}

			return ret;
		}
	}
}
