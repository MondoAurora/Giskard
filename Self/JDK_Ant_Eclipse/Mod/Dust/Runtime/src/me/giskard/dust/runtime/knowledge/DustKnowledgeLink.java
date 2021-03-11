package me.giskard.dust.runtime.knowledge;

public class DustKnowledgeLink implements DustKnowledgeConsts {
	DustKnowledgeBlock from;
	DustTokenMember def;
	DustKnowledgeBlock to;
	
	public DustKnowledgeLink(DustKnowledgeBlock from, DustTokenMember def, DustKnowledgeBlock to) {
		super();
		this.from = from;
		this.def = def;
		this.to = to;
	}

}
