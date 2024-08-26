package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.event.DustEventHandles;

public class DustMachineBoot implements DustMachineConsts {
	
	public static DustMachineNarrative bootMachine() throws Exception {
		
		DustMachineHandle MIND_ATT_UNIT_HANDLES = new DustMachineHandle("giskard.me:mind_1.0:1");
		DustMachineHandle MIND_ATT_UNIT_CONTENT = new DustMachineHandle("giskard.me:mind_1.0:2");

		DustMachineKnowledgeItem kiMachine = new DustMachineKnowledgeItem(null);
		
		Map<String, DustMachineHandle> mh = new TreeMap<>();
		kiMachine.set(MIND_ATT_UNIT_HANDLES, mh);
		
		Map<DustMachineHandle, DustMachineKnowledgeItem> mc = new HashMap<>();
		kiMachine.set(MIND_ATT_UNIT_CONTENT, mc);
		
		DustMachineHandle hUnitMind = new DustMachineHandle("giskard.me:mind_1.0");
		DustMachineKnowledgeItem kiMind = new DustMachineKnowledgeItem(hUnitMind);

		mh.put(hUnitMind.getId(), hUnitMind);
		mc.put(hUnitMind, kiMind);
		
		Map<String, DustMachineHandle> mindh = new TreeMap<>();
		kiMind.set(MIND_ATT_UNIT_HANDLES, mindh);

		mindh.put(MIND_ATT_UNIT_HANDLES.getId(), MIND_ATT_UNIT_HANDLES);
		mindh.put(MIND_ATT_UNIT_CONTENT.getId(), MIND_ATT_UNIT_CONTENT);

		
		DustMachineNarrative m = new DustMachineNarrative(kiMachine);
		
		return m;
	}
	
	public static void loadBootModules() throws Exception {
		Dust.log(DustEventHandles.EVENT_TAG_TYPE_INFO, "would boot modules");
	}
	
}
