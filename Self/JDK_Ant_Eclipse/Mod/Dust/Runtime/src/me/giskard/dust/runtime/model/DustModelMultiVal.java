package me.giskard.dust.runtime.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DustModelMultiVal<ContainerType> implements DustModelConsts {
	protected final ContainerType container;
	public abstract Object access(DustModelBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val, Object... valPath) throws Exception;
	
	public static DustModelMultiVal create(DustTokenMember token) {
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
	
	public DustModelMultiVal(ContainerType container) {
		this.container = container;
	}


	public static class ValSet extends DustModelMultiVal<Set> {
		public ValSet() {
			super(new HashSet());
		}

		@Override
		public Object access(DustModelBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val, Object... valPath) throws Exception {
			switch ( cmd ) {
			case Add:
				if ( val instanceof DustModelBlock ) {
					for ( Object ref : container ) {
						if ( ((DustModelRef) ref).to == val ) {
							return false;
						}
					}
					container.add(owner.ctx.setRef(owner, token, (DustModelBlock) val));
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
					DustModelUtils.notifyAgent((MiNDAgent) val, owner.ctx, ob);
				}
				break;
			}
			return null;
		}
	}
	
	public static class ValArr extends DustModelMultiVal<ArrayList> {
		public ValArr() {
			super(new ArrayList());
		}

		@Override
		public Object access(DustModelBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val, Object... valPath) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class ValMap extends DustModelMultiVal<Map> {
		public ValMap() {
			super(new HashMap());
		}

		@Override
		public Object access(DustModelBlock owner, DustTokenMember token, MiNDAccessCommand cmd, Object val, Object... valPath) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
