package me.giskard.dust.runtime.model;

public class DustModelRef implements DustModelConsts {
	DustModelBlock from;
	DustTokenMember def;
	DustModelBlock to;
	
	public DustModelRef(DustModelBlock from, DustTokenMember def, DustModelBlock to) {
		super();
		this.from = from;
		this.def = def;
		this.to = to;
	}

}
