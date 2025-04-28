package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;

public class DustMachineBoot implements DustMachineConsts {
	
	public static DustMachineNarrative bootMachine() throws Exception {
		
		DustMachineKnowledgeItem kiMachine = new DustMachineKnowledgeItem(null);
		DustMachineHandle hUnitMind = new DustMachineHandle(kiMachine, "giskard.me:mind_1.0");

		DustMachineKnowledgeItem kiMind = new DustMachineKnowledgeItem(hUnitMind);

		DustMachineHandle MIND_ATT_UNIT_HANDLES = new DustMachineHandle(kiMind, "giskard.me:mind_1.0:1");
		DustMachineHandle MIND_ATT_UNIT_CONTENT = new DustMachineHandle(kiMind, "giskard.me:mind_1.0:2");

		Map<String, DustMachineHandle> mh = new TreeMap<>();
		kiMachine.set(MIND_ATT_UNIT_HANDLES, mh);
		mh.put(hUnitMind.getId(), hUnitMind);
		
		Map<DustMachineHandle, DustMachineKnowledgeItem> mc = new HashMap<>();
		kiMachine.set(MIND_ATT_UNIT_CONTENT, mc);
		mc.put(hUnitMind, kiMind);
		
		Map<String, DustMachineHandle> mindh = new TreeMap<>();
		kiMind.set(MIND_ATT_UNIT_HANDLES, mindh);

		mindh.put(MIND_ATT_UNIT_HANDLES.getId(), MIND_ATT_UNIT_HANDLES);
		mindh.put(MIND_ATT_UNIT_CONTENT.getId(), MIND_ATT_UNIT_CONTENT);
		
		DustMachineNarrative m = new DustMachineNarrative(kiMachine, MIND_ATT_UNIT_HANDLES, MIND_ATT_UNIT_CONTENT);
		
		return m;
	}
	
	public static void loadBootModules() throws Exception {
		Dust.broadcast(null, "would boot modules");
	}
	
}
