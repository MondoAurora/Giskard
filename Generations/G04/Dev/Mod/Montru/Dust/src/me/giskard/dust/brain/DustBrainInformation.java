package me.giskard.dust.brain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

	public DustBrainInformation optExtend(MiNDAccessCommand cmd, Object val, Object key, CollType ct) {
		return null;
	}

	public final <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
		changed = false;
		return accessImpl(cmd, val, key);
	}

	protected abstract <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key);

	public static class Single extends DustBrainInformation {
		Object val;
		Object key;

		public Single(Object val, Object key) {
			set(val, key);
		}

		@Override
		public CollType getCollType() {
			return CollType.One;
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
						ret.access(MiNDAccessCommand.Set, val, key);
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
			case Broadcast:
				break;
			case Check:
				ret = GiskardUtils.isEqual(val, this.val) && GiskardUtils.isEqual(key, this.key);
				break;
			case Delete:
				break;
			case Get:
				break;
			case Insert:
				break;
			case Peek:
				ret = GiskardUtils.isEqual(key, this.key) ? this.val : val;				
				break;
			case Set:
				break;
			case Visit:
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
		protected final ContType container;

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
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;
			Integer k = (Integer) key;
			boolean in = (null != k) && (0 <= k) && (k < container.size());

			switch ( cmd ) {
			case Broadcast:
				break;
			case Get:
				ret = in ? container.get(k) : val;
				break;
			case Insert:
				container.add(val);
				break;
			case Peek:
				ret = in ? container.get(k) : val;
				break;
			case Set:
				break;
			case Visit:
				break;
			case Check:
				break;
			case Delete:
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
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Broadcast:
				break;
			case Get:
				break;
			case Insert:
				container.add(val);
				break;
			case Peek:
				break;
			case Set:
				break;
			case Visit:
				break;
			case Check:
				break;
			case Delete:
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
		protected <RetType> RetType accessImpl(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Check:
				ret = GiskardUtils.isEqual(val, container.get(key));
				break;
			case Delete:
				ret = ((Collection) container).remove(val);
				break;
			case Get:
				ret = container.get(key);
				break;
			case Set:
			case Insert:
				ret = container.put(key, val);
				break;
			case Peek:
				ret = container.getOrDefault(key, val);
				break;
			case Visit:
				break;
			case Broadcast:
				break;
			}

			return (RetType) ret;
		}
	}

}
