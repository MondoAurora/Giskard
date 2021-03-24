package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;

import me.giskard.GiskardConsts.MiNDToken;
import me.giskard.tools.GisToolsTokenTranslator;

abstract class DustRuntimeToken implements MiNDToken, DustRuntimeBootConsts {
	public static String buildId(MiNDTokenType type, String name, Object... params) {
		DustRuntimeToken parent = ((MiNDTokenType.UNIT == type) || (MiNDTokenType.LOCAL == type)) ? null
				: (DustRuntimeToken) params[0];
		return (null == parent) ? name : parent.getId() + SEP_ID + name;
	}

	public static DustRuntimeToken createToken(MiNDTokenType type, String name, Object... params) {
		DustRuntimeToken ret;

		switch ( type ) {
		case UNIT:
		case LOCAL:
			ret = new Container(type, name, null);
			break;
		case MEMBER:
			ret = new Member(name, (Container) params[0], (MiNDValType) params[1],
					(MiNDCollType) params[2]);
			break;
		default:
			ret = new Container(type, name, (Container) params[0]);
			if ( (type == MiNDTokenType.TAG) && (params.length > 1) ) {
				GisToolsTokenTranslator.register(ret, params[1]);
			}

			break;
		}

		return ret;
	}

	MiNDTokenType type;
	String name;
	DustRuntimeToken parent;
	int entityHandle = HANDLE_NULL;

	public MiNDTokenType getType() {
		return type;
	}

	protected DustRuntimeToken(MiNDTokenType type, String name, Container parent) {
		this.type = type;
		this.parent = parent;
		if ( null != parent ) {
			parent.addChild(this);
		}
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEntityHandle() {
		return entityHandle;
	}

	public void setEntityHandle(int entityHandle) {
		this.entityHandle = entityHandle;
	}

	public DustRuntimeToken getParent() {
		return parent;
	}

	public DustRuntimeToken getRoot() {
		return (null == parent) ? this : parent.getRoot();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return buildId(type, name, parent);
	}

	public MiNDCollType getCollType() {
		return MiNDCollType.One;
	}

	public MiNDValType getValType() {
		return MiNDValType.Link;
	}

	@Override
	public String toString() {
		return type + ":" + getId();
	}

	static class Container extends DustRuntimeToken {
		Set<DustRuntimeToken> children;

		public Container(MiNDTokenType type, String name, Container parent) {
			super(type, name, parent);
		}

		protected void addChild(DustRuntimeToken child) {
			if ( null == children ) {
				children = new HashSet<>();
			}
			children.add(child);
		}
	}

	static class Member extends DustRuntimeToken {
		MiNDValType valType;
		MiNDCollType collType;

		public Member(String name, Container parent, MiNDValType valType, MiNDCollType collType) {
			super(MiNDTokenType.MEMBER, name, parent);

			setValType(valType);
			setCollType(collType);
		}

		@Override
		public MiNDValType getValType() {
			return valType;
		}

		public void setValType(MiNDValType valType) {
			this.valType = valType;
		}

		@Override
		public MiNDCollType getCollType() {
			return collType;
		}

		public void setCollType(MiNDCollType collType) {
			this.collType = collType;
		}
	}

}