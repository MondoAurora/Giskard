package me.giskard.dust.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustBrainInformation implements DustBrainConsts {

	boolean changed;
	protected ValType valType;

	public abstract CollType getCollType();

	public ValType getValType() {
		return valType;
	}
	
	public void setValType(ValType valType) {
		this.valType = valType;
	}

	public DustBrainInformation optExtend(MiNDAccessCommand cmd, Object val, Object key, CollType ct) {
		return null;
	}

	public final <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
		changed = false;
		Object ret = null;

		if ( (cmd == MiNDAccessCommand.Peek) && (key instanceof MiNDSpecKey) ) {
			if ( KEY_SIZE == key ) {
				ret = getSize();
			} else if ( KEY_FORMAT_STRING == key ) {
				ret = toString();
			} else if ( KEY_ITERATOR == key ) {
				ret = getIterator();
			} 
//			switch ( key ) {
//			case KEY_FORMAT_STRING:
//				ret = toString();
//				break;
//			case KEY_SIZE:
//				ret = getSize();
//				break;
//			case KEY_ITERATOR:
//				ret = getIterator();
//				break;
//			}
		} else {
			if ( cmd.creator && (null != val) && ( valType != ValType.Any ) ) {
				ValType vt = DustBrainUtils.guessValType(val);

				if ( null == valType ) {
					valType = vt;
				} else if ( valType != vt ){
					throw new IllegalArgumentException();
				}
			}

			ret = accessImpl(cmd, val, key);
		}

		return (RetType) ret;
	}

	protected abstract int getSize();

	protected abstract Iterator getIterator();

	protected abstract <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key);


	public static class Single extends DustBrainInformation implements Map.Entry {
		static class SingleIterator implements Iterator {
			Single single;

			public SingleIterator(Single single) {
				this.single = single;
			}

			@Override
			public boolean hasNext() {
				return null != single;
			}

			@Override
			public Object next() {
				Object ret = single;
				single = null;
				return ret;
			}
		}

		private static final Iterator NO_VAL = Collections.emptyIterator();

		Object val;
		Object key;

		public Single(Object val, Object key) {
			set(val, key);
		}

		@Override
		public CollType getCollType() {
			return CollType.One;
		}

		@Override
		protected int getSize() {
			return (NO_VAL == val) ? 0 : 1;
		}

		@Override
		protected Iterator getIterator() {
			return (NO_VAL == val) ? NO_VAL : new SingleIterator(this);
		}

		@Override
		public Object getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return val;
		}

		@Override
		public Object setValue(Object value) {
			throw new IllegalAccessError();
		}

		public DustBrainInformation optExtend(MiNDAccessCommand cmd, Object val, Object key, CollType ct) {
			DustBrainInformation ret = null;

			if ( cmd.creator ) {
				if ( !GiskardUtils.isEqual(val, this.val) || !GiskardUtils.isEqual(key, this.key) ) {
					switch ( ct ) {
					case Arr:
						ret = new ContArr();
						break;
					case Map:
						ret = new ContMap();
						break;
					case One:
						break;
					case Set:
						ret = new ContSet();
						break;
					}

					if ( null != ret ) {
						ret.access(MiNDAccessCommand.Set, this.val, this.key);
					}
				}
			}

			return ret;
		}

		public void set(Object val, Object key) {
			this.val = val;
			this.key = key;
			valType = DustBrainUtils.guessValType(val);
		}

		@Override
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Check:
				ret = GiskardUtils.isEqual(val, this.val) && GiskardUtils.isEqual(key, this.key);
				break;
			case Delete:
				val = NO_VAL;
				break;
			case Get:
			case Peek:
				ret = ((NO_VAL == val) || !GiskardUtils.isEqual(key, this.key)) ? val : this.val;
				break;
			case Insert:
			case Set:
				set(val, key);
				break;
			}

			return (RetType) ret;
		}

		@Override
		public String toString() {
			return GiskardUtils.toString(val);
		}
	}

	public static abstract class Container<ContType> extends DustBrainInformation {
		final ContType container;

		protected Container(ContType container) {
			this.container = container;
		}

		@Override
		public String toString() {
			return container.toString();
		}

	}

	public static class ContArr extends Container<ArrayList> {

		public ContArr() {
			super(new ArrayList());
		}

		@Override
		public CollType getCollType() {
			return CollType.Arr;
		}

		@Override
		protected int getSize() {
			return container.size();
		}

		@Override
		protected Iterator getIterator() {
			return container.iterator();
		}

		private void append(int skipCount, Object val) {
			for (int i = 0; i < skipCount; ++i) {
				container.add(null);
			}
			container.add(val);
		}

		@Override
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;
			Integer k = (Integer) key;
			int missing = (null == k) ? 0 : k - container.size();
			boolean in = (null != k) && (0 <= k) && (missing < 0);

			switch ( cmd ) {
			case Get:
				if ( in ) {
					ret = container.get(k);
				} else {
					append(missing, val);
					ret = val;
				}
				break;
			case Insert:
				if ( in ) {
					container.add(k, val);
				} else {
					append(missing, val);
				}
				break;
			case Peek:
				ret = in ? container.get(k) : val;
				break;
			case Set:
				if ( in ) {
					container.set(k, val);
				} else {
					append(missing, val);
				}
				break;
			case Check:
				ret = in && GiskardUtils.isEqual(val, container.get(k));
				break;
			case Delete:
				if ( !container.isEmpty() ) {
					if ( null == key ) {
						changed = true;
						container.clear();
					} else if ( in ) {
						changed = true;
						ret = container.remove(k);
					}
				}
				break;
			}

			return (RetType) ret;
		}
	}

	public static class ContSet extends Container<Set> {

		public ContSet() {
			super(new HashSet());
		}

		@Override
		public CollType getCollType() {
			return CollType.Set;
		}

		@Override
		protected int getSize() {
			return container.size();
		}

		@Override
		protected Iterator getIterator() {
			return container.iterator();
		}

		@Override
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Get:
			case Set:
			case Insert:
				changed = container.add(val);
				ret = val;
				break;
			case Peek:
			case Check:
				ret = container.contains(val);
				break;
			case Delete:
				if ( !container.isEmpty() ) {
					if ( null == val ) {
						changed = true;
						container.clear();
					} else {
						ret = changed = container.remove(val);
					}
				}
				break;
			}

			return (RetType) ret;
		}
	}

	public static class ContMap extends Container<Map> {

		public ContMap() {
			super(new HashMap());
		}

		@Override
		public CollType getCollType() {
			return CollType.Map;
		}

		@Override
		protected int getSize() {
			return container.size();
		}

		@Override
		protected Iterator getIterator() {
			return container.entrySet().iterator();
		}

		@Override
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Check:
				ret = GiskardUtils.isEqual(val, container.get(key));
				break;
			case Delete:
				if ( !container.isEmpty() ) {
					if ( null == key ) {
						changed = true;
						container.clear();
					} else {
						ret = container.remove(key);
						changed = (null != ret);
					}
				}
				break;
			case Get:
				if ( container.containsKey(key) ) {
					ret = container.get(key);
				} else {
					container.put(key, val);
					ret = val;
					changed = true;
				}
				break;
			case Set:
			case Insert:
				ret = container.put(key, val);
				changed = !GiskardUtils.isEqual(val, ret);
				break;
			case Peek:
				ret = container.getOrDefault(key, val);
				break;
			}

			return (RetType) ret;
		}
	}
}
