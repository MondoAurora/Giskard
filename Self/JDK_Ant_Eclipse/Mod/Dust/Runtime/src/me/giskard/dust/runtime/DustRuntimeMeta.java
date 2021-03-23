package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;

import me.giskard.coll.MindCollConsts;
import me.giskard.tools.GisToolsTokenTranslator;

public interface DustRuntimeMeta extends MindCollConsts {

	abstract class DustToken implements MiNDToken {
		public static String buildId(String name, DustToken parent) {
			return (null == parent) ? name : parent.getId() + SEP_ID + name;
		}

		public static DustToken createToken(MiNDTokenType type, String name, Object... params) {
			DustToken ret;

			switch ( type ) {
			case UNIT:
			case LOCAL:
				ret = new DustTokenContainer(type, name, null);
				break;
			case MEMBER:
				ret = new DustTokenMember(name, (DustTokenContainer) params[0], (MiNDValType) params[1],
						(MiNDCollType) params[2]);
				break;
			default:
				ret = new DustTokenContainer(type, name, (DustTokenContainer) params[0]);
				if ( (type == MiNDTokenType.TAG) && (params.length > 1) ) {
					GisToolsTokenTranslator.register(ret, params[1]);
				}

				break;
			}

			return ret;
		}

		MiNDTokenType type;
		String name;
		DustToken parent;
		int entityHandle;

		public MiNDTokenType getType() {
			return type;
		}

		protected DustToken(MiNDTokenType type, String name, DustTokenContainer parent) {
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
		
		public DustToken getParent() {
			return parent;
		}
		
		public DustToken getRoot() {
			return (null == parent) ? this : parent.getRoot();
		}
		
		public String getName() {
			return name;
		}
		
		public String getId() {
			return buildId(name, parent);
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

	}

	class DustTokenContainer extends DustToken {
		Set<DustToken> children;

		public DustTokenContainer(MiNDTokenType type, String name, DustTokenContainer parent) {
			super(type, name, parent);
		}

		protected void addChild(DustToken child) {
			if ( null == children ) {
				children = new HashSet<>();
			}
			children.add(child);
		}
	}

	class DustTokenMember extends DustToken {
		MiNDValType valType;
		MiNDCollType collType;

		public DustTokenMember(String name, DustTokenContainer parent, MiNDValType valType, MiNDCollType collType) {
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
