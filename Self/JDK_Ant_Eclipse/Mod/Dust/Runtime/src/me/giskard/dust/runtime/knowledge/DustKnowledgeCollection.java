package me.giskard.dust.runtime.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustKnowledgeCollection<ContainerType> implements DustKnowledgeConsts {
	protected final ContainerType container;
	public abstract Object access(DustKnowledgeBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val) throws Exception;
	
	public static DustKnowledgeCollection create(DustTokenMember token) {
		switch (token.getCollType() ) {
		case Arr:
			return new ValArr();
		case Map:
			return new ValMap();
		case Set:
			return new ValSet();
		default:
			return null;
		}
	}
	
	public DustKnowledgeCollection(ContainerType container) {
		this.container = container;
	}


	public static class ValSet extends DustKnowledgeCollection<Set> {
		public ValSet() {
			super(new HashSet());
		}

		@Override
		public Object access(DustKnowledgeBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val) throws Exception {
			switch ( cmd ) {
			case Add:
				if ( val instanceof DustKnowledgeBlock ) {
					for ( Object link : container ) {
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
				for ( Object ob : container ) {
					DustKnowledgeUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
				}
				break;
			}
			return null;
		}
	}
	
	public static class ValArr extends DustKnowledgeCollection<ArrayList> {
		public ValArr() {
			super(new ArrayList());
		}

		@Override
		public Object access(DustKnowledgeBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class ValMap extends DustKnowledgeCollection<Map> {
		public ValMap() {
			super(new HashMap());
		}

		@Override
		public Object access(DustKnowledgeBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
