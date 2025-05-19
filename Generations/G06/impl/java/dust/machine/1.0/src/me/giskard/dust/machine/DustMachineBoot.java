package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;

public class DustMachineBoot implements DustMachineConsts {

	public static DustMachineLogicNode bootMachine() throws Exception {

		DustMachineLogicIdea lIdea = new DustMachineLogicIdea();

		Map<MindToken, Object> kiMachine = new TreeMap<>();
		DustMachineToken hUnitMind = new DustMachineToken(kiMachine, "giskard.me:mind_1.0");

		Map<MindToken, Object> kiMind = new TreeMap<>();

		DustMachineToken MIND_ATT_UNIT_HANDLES = new DustMachineToken(kiMind, "giskard.me:mind_1.0:1");
		DustMachineToken MIND_ATT_UNIT_CONTENT = new DustMachineToken(kiMind, "giskard.me:mind_1.0:2");

		Map<String, DustMachineToken> mh = new TreeMap<>();
		lIdea.set(kiMachine, MIND_ATT_UNIT_HANDLES, mh);
		mh.put(hUnitMind.getId(), hUnitMind);

		Map<DustMachineToken, Map<MindToken, Object>> mc = new HashMap<>();
		lIdea.set(kiMachine, MIND_ATT_UNIT_CONTENT, mc);
		mc.put(hUnitMind, kiMind);

		Map<String, DustMachineToken> mindh = new TreeMap<>();
		lIdea.set(kiMind, MIND_ATT_UNIT_HANDLES, mindh);

		mindh.put(MIND_ATT_UNIT_HANDLES.getId(), MIND_ATT_UNIT_HANDLES);
		mindh.put(MIND_ATT_UNIT_CONTENT.getId(), MIND_ATT_UNIT_CONTENT);

		DustMachineLogicNode m = new DustMachineLogicNode(lIdea, kiMachine, MIND_ATT_UNIT_HANDLES, MIND_ATT_UNIT_CONTENT);

		return m;
	}

	public static void loadBootModules() throws Exception {
		Dust.broadcast(null, "would boot modules");
	}

}
