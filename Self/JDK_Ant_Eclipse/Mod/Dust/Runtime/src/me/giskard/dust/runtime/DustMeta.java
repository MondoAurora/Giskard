package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;

public interface DustMeta extends DustConsts {

	abstract class DustToken implements MiNDToken {
		public static String buildId(String name, DustToken parent) {
			return (null == parent) ? name : parent.getId() + SEP_ID + name;
		}
		
		public static DustToken createToken(MiNDTokenType type, String name, Object... params) {
			switch ( type ) {
			case UNIT:
				return new DustTokenContainer(type, name, null);
			case MEMBER:
				return new DustTokenMember(name, (DustTokenContainer) params[0], (MiNDValType) params[1], (MiNDCollType) params[2]);
			default:
				return new DustTokenContainer(type, name, (DustTokenContainer) params[0]);
			}
		}
		
		MiNDTokenType type;
		String name;
		DustToken parent;

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
		
		public String getId() {
			return buildId(name, parent);
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
		
		public void setValType(MiNDValType valType) {
			this.valType = valType;
		}
		
		public void setCollType(MiNDCollType collType) {
			this.collType = collType;
		}
	}
}
