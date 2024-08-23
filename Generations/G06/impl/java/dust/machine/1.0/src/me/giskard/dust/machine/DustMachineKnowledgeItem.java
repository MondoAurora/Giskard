package me.giskard.dust.machine;

import java.util.Map;
import java.util.TreeMap;

public class DustMachineKnowledgeItem implements DustMachineConsts {
	private final Map<MindHandle, Object> data = new TreeMap<>();

	
	@Override
	public String toString() {
		return data.toString();
	}

}
