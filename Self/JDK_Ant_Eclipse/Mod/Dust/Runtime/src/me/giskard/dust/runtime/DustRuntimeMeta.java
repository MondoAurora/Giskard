package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;

import me.giskard.coll.MindCollConsts;
import me.giskard.dust.runtime.knowledge.DustKnowledgeBlock;
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
		DustKnowledgeBlock entity;

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
		
		public void setEntity(DustKnowledgeBlock entity) {
			this.entity = entity;
		}
		
		public DustKnowledgeBlock getEntity() {
			return entity;
		}

		public DustToken getParent() {
			return parent;
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

		public MiNDValType getValType() {
			return valType;
		}
		
		public void setValType(MiNDValType valType) {
			this.valType = valType;
		}

		public MiNDCollType getCollType() {
			return collType;
		}
		
		public void setCollType(MiNDCollType collType) {
			this.collType = collType;
		}
	}
}
