package me.giskard.dust.machine;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class DustMachineKnowledgeItem implements DustMachineConsts, DustMachineConsts.KnowledgeItem {
	DustMachineHandle h;
	private final Map<MindHandle, Object> data = new TreeMap<>();
	
	public DustMachineKnowledgeItem(DustMachineHandle h) {
		this.h = h;
	}
	
	@Override
	public <RetType> RetType get(MindHandle hAtt) {
		return (RetType) data.get(hAtt);
	}
	
	@Override
	public void set(MindHandle hAtt, Object o) {
		data.put(hAtt, o);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
