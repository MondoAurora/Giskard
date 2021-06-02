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
public abstract class DustRuntimeDataCollection<CollectorType> implements DustRuntimeConsts {
	protected final CollectorType collector;
	protected final DustRuntimeDataBlock owner;
	protected final DustRuntimeToken token;

	// TODO : access with standard hints like KEY_SIZE
	public abstract Object access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object key);

	abstract Iterator getIterator();

	public static DustRuntimeDataCollection create(DustRuntimeDataBlock owner, DustRuntimeToken token) {
		switch ( token.getCollType() ) {
		case Arr:
			return new ValArr(owner, token);
		case Map:
			return new ValMap(owner, token);
		case Set:
			return (MTMEM_MODEL_ENTITY_TAGS == token) ? new TagManager(owner, token) : new ValSet(owner, token);
		default:
			return null;
		}
	}

	protected DustRuntimeDataCollection(DustRuntimeDataBlock owner_, DustRuntimeToken token_, CollectorType collector_) {
		this.owner = owner_;
		this.token = token_;
		this.collector = collector_;
	}

	public static class ValSet extends DustRuntimeDataCollection<Set> {
		public ValSet(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new HashSet());
		}

		@Override
		public Object access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Add:
				if ( collector.add(val) ) {
					ret = true;
					notif.notify(cmd, owner.getHandle(), val, val, token, key);
				} else {
					ret = false;
				}
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				if ( GiskardUtils.isEqual(KEY_SIZE, key) ) {
					return collector.size();
				}
				break;
			case Set:
				break;
			}

			return ret;
		}

		@Override
		Iterator getIterator() {
			return collector.iterator();
		}
	}

	public static class ValArr extends DustRuntimeDataCollection<ArrayList> {
		public ValArr(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new ArrayList());
		}

		@Override
		public Object access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = val;
			Integer idx = (Integer) key;

			switch ( cmd ) {
			case Add:
				collector.add(val);
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				if ( GiskardUtils.isEqual(KEY_SIZE, key) ) {
					return collector.size();
				} else if ( idx < collector.size() ) {
					ret = collector.get(idx);
				}
				break;
			case Set:
				break;
			}
			return ret;
		}

		@Override
		Iterator getIterator() {
			return collector.iterator();
		}
	}

	public static class ValMap extends DustRuntimeDataCollection<Map> {
		public ValMap(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token, new HashMap());
		}

		@Override
		public Object access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = val;

			switch ( cmd ) {
			case Add:
				ret = collector.put(key, val);
				if ( !GiskardUtils.isEqual(ret, val) ) {
					notif.notify(cmd, owner.getHandle(), ret, val, token, key);
				}
				break;
			case Chk:
				break;
			case Del:
				break;
			case Get:
				ret = GiskardUtils.isEqual(KEY_SIZE, key) ? collector.size() : collector.get(key);
				break;
			case Set:
				collector.clear();
				collector.put(key, val);
				break;
			}
			return ret;
		}

		@Override
		Iterator getIterator() {
			return collector.entrySet().iterator();
		}
	}

	public static class TagManager extends ValSet {
		public TagManager(DustRuntimeDataBlock owner, DustRuntimeToken token) {
			super(owner, token);
		}

		@Override
		public Object access(DustNotifier notif, MiNDAccessCommand cmd, Object val, Object key) {
			if ( null == val ) {
				GiskardException.wrap(null, "invalid access of", token, "missing value!");
			}

			DustRuntimeToken tok = (DustRuntimeToken) val;
			Object ret = val;
			DustRuntimeToken p = tok.getParent();

			switch ( cmd ) {
			case Add:
				collector.add(tok);
				break;
			case Set:
				if ( !collector.contains(tok) ) {
					if ( null != p ) {
						for (Iterator<DustRuntimeToken> it = collector.iterator(); it.hasNext();) {
							DustRuntimeToken t = it.next();
							if ( p == t.getParent() ) {
								it.remove();
							}
						}
					}

					collector.add(tok);
				}
				break;
			case Chk:
				ret = collector.contains(tok);
				break;
			case Del:
				ret = collector.remove(tok);
				break;
			case Get:
				ret = tok;

				if ( null != p ) {
					for (DustRuntimeToken t : (Iterable<DustRuntimeToken>) collector) {
						if ( p == t.getParent() ) {
							ret = t;
							break;
						}
					}
				}
				break;
			}

			return ret;
		}
	}
}
