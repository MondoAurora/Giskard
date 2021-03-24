package me.giskard.dust.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustRuntimeDataCollection<ContainerType> implements DustRuntimeConsts {
	protected final ContainerType container;
	protected final DustRuntimeDataBlock owner;
	protected final DustRuntimeToken token;

	public abstract Object access(MiNDAccessCommand cmd, Object val, Object key);

	public static DustRuntimeDataCollection create(DustRuntimeDataBlock owner, DustRuntimeToken token) {
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

	protected DustRuntimeDataCollection(DustRuntimeDataBlock owner, DustRuntimeToken token, ContainerType container) {
		this.owner = owner;
		this.token = token;
		this.container = container;
	}

	protected MiNDResultType notify(Object val, Iterable container) {
		MiNDResultType ret = MiNDResultType.REJECT;
		
		for (Object ob : container) {
			try {
				ret = DustRuntimeUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
			} catch (Exception e) {
				GiskardException.swallow(e);
			}
		}
		
		return ret;
	}

	public static class ValSet extends DustRuntimeDataCollection<Set> {
		public ValSet(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new HashSet());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;
			
			switch ( cmd ) {
			case Add:
					ret = container.add(val);
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				if ( GiskardUtils.isEqual(KEY_SIZE, key) ) {
					return container.size();
				}
				break;
			case Set:
				break;
			case Use:
				ret = notify(val, container);
				break;
			}
			
			return ret;
		}
	}

	public static class ValArr extends DustRuntimeDataCollection<ArrayList> {
		public ValArr(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new ArrayList());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = val;
			Integer idx = (Integer) key;

			switch ( cmd ) {
			case Add:
				container.add(val);
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				if ( GiskardUtils.isEqual(KEY_SIZE, key) ) {
					return container.size();
				} else if ( idx < container.size() ) {
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

	public static class ValMap extends DustRuntimeDataCollection<Map> {
		public ValMap(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new HashMap());
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class TagManager extends ValSet {
		public TagManager(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token);
		}

		@Override
		public Object access(MiNDAccessCommand cmd, Object val, Object key) {
			if ( null == val ) {
				GiskardException.wrap(null, "invalid access of", token, "missing value!");
			}
			
			DustRuntimeToken tok = (DustRuntimeToken) val;
			Object ret = val;
			DustRuntimeToken p = tok.getParent();

			switch ( cmd ) {
			case Add:
				container.add(tok);
				break;
			case Set:
				if ( !container.contains(tok) ) {
					if ( null != p ) {
						for (Iterator<DustRuntimeToken> it = container.iterator(); it.hasNext();) {
							DustRuntimeToken t = it.next();
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
					for (DustRuntimeToken t : (Iterable<DustRuntimeToken>) container) {
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
