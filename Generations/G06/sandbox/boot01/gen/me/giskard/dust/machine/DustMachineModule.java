package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineModule implements DustMachineBootConsts {
	public static Object dustModuleInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");
		
		Map initData = new HashMap();

		Map units = new TreeMap();
		Map dialogIdeas = new HashMap();
		Map vocabulary = new TreeMap();

		initData.put(MACHINE_UNITS, units);
		initData.put(DIALOG_IDEAS, dialogIdeas);
		initData.put(DIALOG_VOCABULARY, vocabulary);

		DustMachineUtils.storeHandle(MISC, units, dialogIdeas, vocabulary, "iso:639-1:en", "misc");
		DustMachineUtils.storeHandle(MISC_TARGET, units, dialogIdeas, vocabulary, "iso:639-1:en", "MISC_TARGET");
		DustMachineUtils.storeHandle(MISC_PARENT, units, dialogIdeas, vocabulary, "iso:639-1:en", "MISC_PARENT");
		DustMachineUtils.storeHandle(MISC_CONN_REQUIRED, units, dialogIdeas, vocabulary, "iso:639-1:en", "connRequired");

		DustMachineUtils.storeHandle(MIND, units, dialogIdeas, vocabulary, "iso:639-1:en", "mind");
		DustMachineUtils.storeHandle(ACTION_INIT, units, dialogIdeas, vocabulary, "iso:639-1:en", "actionInit");

		DustMachineUtils.storeHandle(DUST, units, dialogIdeas, vocabulary, "iso:639-1:en", "dust");
		
		MindDialog machine = new DustMachineLogic(initData);
		
		return machine;
	}
}
