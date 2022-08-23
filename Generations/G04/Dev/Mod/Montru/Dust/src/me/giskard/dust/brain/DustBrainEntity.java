package me.giskard.dust.brain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustBrainEntity implements DustBrainConsts {

	abstract class MultiContBase<ContainerType> {
		final ContainerType container;

		protected MultiContBase(ContainerType container) {
			this.container = container;
		}

		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Broadcast:
				break;
			case Check:
				ret = ((Collection) container).contains(val);
				break;
			case Delete:
				ret = ((Collection) container).remove(val);
				break;
			case Get:
				break;
			case Insert:
				((Collection) container).add(val);
				break;
			case Peek:
				break;
			case Set:
				break;
			case Visit:
				break;
			}

			return (RetType) ret;
		}
	}

	class MultiContArr extends MultiContBase<ArrayList> {
		public MultiContArr(Object o1, Object o2) {
			super(new ArrayList());
			container.add(o1);
			container.add(o2);
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
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
				break;
			case Peek:
				ret = in ? container.get(k) : val;
				break;
			case Set:
				break;
			case Visit:
				break;
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	class MultiContQueue extends MultiContBase<LinkedList> {
		public MultiContQueue() {
			super(new LinkedList());
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Get:
				ret = container.pollLast();
				break;
			case Insert:
				container.addFirst(val);
				break;
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	class MultiContSet extends MultiContBase<Set> {
		public MultiContSet(Object o1, Object o2) {
			super(new HashSet());
			container.add(o1);
			container.add(o2);
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Broadcast:
				break;
			case Set:
				break;
			case Visit:
				break;
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	class MultiContPool extends MultiContBase<Set> {
		public MultiContPool() {
			super(new HashSet());
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Get:
				break;
			case Insert:
				break;
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	class MultiContStack extends MultiContBase<Stack> {
		public MultiContStack() {
			super(new Stack());
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
			Object ret = null;

			switch ( cmd ) {
			case Peek:
				ret = container.isEmpty() ? null : container.peek();
				break;
			case Get:
				ret = container.isEmpty() ? null : container.pop();
				break;
			case Insert:
				container.push(val);
				break;
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	class MultiContMap extends MultiContBase<Map> {
		public MultiContMap(Object o1, Object o2) {
			super(new HashMap());
			container.put(o1, o2);
		}

		@Override
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, Object key) {
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
			default:
				return super.access(cmd, val, key);
			}

			return (RetType) ret;
		}
	}
	
	MultiContBase forCt(CollType ct, Object o1, Object o2) {
		switch ( ct ) {
		case Arr:
			return new MultiContArr(o1, o2);
		case Map:
			return new MultiContMap(o1, o2);
		case Set:
			return new MultiContSet(o1, o2);
		case Pool:
			return new MultiContPool();
		case Queue:
			return new MultiContQueue();
		case Stack:
			return new MultiContStack();
		case One:
			break;
		}

		throw new RuntimeException("No container");
	}

	private final Map<MiNDHandle, Object> data = new HashMap<>();
	private Map<MiNDHandle, MiNDHandle> singleTags;

	public DustBrainEntity(MiNDHandle handle) {
		data.put(MODEL_MEM_ENTITY_HANDLE, handle);
	}

	boolean inToString = false;

	@Override
	public String toString() {
		try {
			if ( inToString ) {
				return GiskardUtils.toString(data.get(MODEL_MEM_ENTITY_HANDLE));
			} else {
				inToString = true;
				return data.toString();
			}
		} finally {
			inToString = false;
		}
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDHandle handle, CollType ct, Object key) {
		Object attVal = data.get(handle);
		Object ret = null;
		boolean changed = false;

		switch ( cmd ) {
		case Insert:
			if ( null == attVal ) {
				switch ( ct ) {
				case Arr:
					attVal = new ArrayList<>();
					break;
				case Map:
					attVal = new HashMap<>();
					break;
				case One:
				case Set:
					attVal = new HashSet<>();
					break;
				}
				data.put(handle, attVal);
			}

			switch ( ct ) {
			case Arr:
				attVal = new ArrayList<>();
				break;
			case Map:
				ret = ((Map) attVal).put(key, val);
				changed = !GiskardUtils.isEqual(ret, val);
				break;
			case One:
			case Set:
				ret = changed = ((Set) attVal).add(val);
				break;
			}

			break;
		case Broadcast:
			break;
		case Check:
			if ( null == attVal ) {
				ret = false;
			} else
				switch ( ct ) {
				case Arr:
				case Set:
					ret = ((Collection) attVal).contains(val);
					break;
				case Map:
					ret = ((Map) attVal).containsValue(val);
					break;
				case One:
					ret = GiskardUtils.isEqual(attVal, val);
					break;
				}

			break;
		case Delete:
			break;
		case Get:
			break;
		case Peek:
			if ( MODEL_MEM_ENTITY_TAGS == handle ) {
				ret = (null == singleTags) ? val : singleTags.getOrDefault(key, (MiNDHandle) val);
			} else if ( attVal instanceof Map ) {
				ret = ((Map) attVal).get(key);
			} else {
				ret = attVal;
			}
			break;
		case Set:
			ret = data.put(handle, val);
			changed = !GiskardUtils.isEqual(ret, val);

			break;
		case Visit:
			break;
		}

		if ( changed ) {

			// single tag management
			if ( MODEL_MEM_ENTITY_TAGS == handle ) {
				MiNDHandle tagClass = Giskard.access(MiNDAccessCommand.Peek, null, val, MODEL_MEM_ENTITY_OWNER);
				if ( null != tagClass ) {
					if ( null == singleTags ) {
						singleTags = new HashMap<>();
					} else {
						MiNDHandle prev = singleTags.get(tagClass);
						((Set) attVal).remove(prev);
					}
					singleTags.put(tagClass, (MiNDHandle) val);
				}
			}

			// listener notification
		}

		return (RetType) ret;
	}

}
