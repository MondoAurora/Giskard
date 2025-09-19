package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.forge.DustForgeLogicJavaGen;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.machine.DustMachineConstsInt.DustIdea;
import me.giskard.dust.narrative.DustNarrativeLogicGraphWalker;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineModule implements DustMachineBootConsts, DustMachineConsts, DustUtilsConsts {

	private static Map initData;

	private static DustHandle hMachine;
	private static DustHandle hModule;
	private static DustHandle hModuleUnit;

	public static Object dustMachineInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");

		initData = new DustIdea();

		Map units = new TreeMap();
		Map dialogIdeas = new DustIdea();
		Map vocabulary = new TreeMap();

		initData.put(MACHINE_UNITS, units);
		initData.put(DIALOG_IDEAS, dialogIdeas);
		initData.put(DIALOG_VOCABULARY, vocabulary);

		DustHandle.CURR_VOC = DustUtils.safeGet(vocabulary, LANG_ID, MAP_CREATOR);

		Map<MindHandle, Object> unitData;

		unitData = DustMachineUtils.storeHandle(MISC, units, dialogIdeas);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(MISC_TARGET, units, dialogIdeas);
		DustMachineUtils.storeHandle(MISC_PARENT, units, dialogIdeas);
		DustMachineUtils.storeHandle(MISC_CONN_REQUIRED, units, dialogIdeas);

		unitData = DustMachineUtils.storeHandle(MIND, units, dialogIdeas);
		unitData.put(UNIT_NEXT_ID, 100L);

		DustMachineUtils.storeHandle(IDEA_HANDLE, units, dialogIdeas);
		DustMachineUtils.storeHandle(IDEA_LISTENERS, units, dialogIdeas);
		DustMachineUtils.storeHandle(AGENT_LOGIC, units, dialogIdeas);
		DustMachineUtils.storeHandle(MEMBER_FACTORIES, units, dialogIdeas);

		DustMachineUtils.storeHandle(UNIT_HANDLES, units, dialogIdeas);
		DustMachineUtils.storeHandle(UNIT_NEXT_ID, units, dialogIdeas);
		DustMachineUtils.storeHandle(DIALOG_IDEAS, units, dialogIdeas);
		DustMachineUtils.storeHandle(DIALOG_VOCABULARY, units, dialogIdeas);

		DustMachineUtils.storeHandle(ACTION_INIT, units, dialogIdeas);
		DustMachineUtils.storeHandle(ACCESS_PEEK, units, dialogIdeas);
		DustMachineUtils.storeHandle(ACCESS_SET, units, dialogIdeas);

		unitData = DustMachineUtils.storeHandle(DUST, units, dialogIdeas);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(PERS_ID, units, dialogIdeas);
		DustMachineUtils.storeHandle(MACHINE_THREADS, units, dialogIdeas);
		DustMachineUtils.storeHandle(MACHINE_DIALOGS, units, dialogIdeas);
		DustMachineUtils.storeHandle(THREAD_DIALOG, units, dialogIdeas);
		DustMachineUtils.storeHandle(MACHINE_UNITS, units, dialogIdeas);
		DustMachineUtils.storeHandle(MACHINE_MODULES, units, dialogIdeas);
		DustMachineUtils.storeHandle(LOGIC_BINARY, units, dialogIdeas);

		DustMachineUtils.storeHandle(AGENT_BINARY, units, dialogIdeas);
		DustMachineUtils.storeHandle(AGENT_SELF, units, dialogIdeas);
		DustMachineUtils.storeHandle(AGENT_PARAM, units, dialogIdeas);

		unitData = DustMachineUtils.storeHandle(DUSTJAVA, units, dialogIdeas);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(LOGIC_CLASSNAME, units, dialogIdeas);
		DustMachineUtils.storeHandle(MODULE_CLASSLOADER, units, dialogIdeas);

		DustUtilsEnumTranslator.setEnum(ACCESS_SET, MindAccess.Set);
		DustUtilsEnumTranslator.setEnum(ACCESS_PEEK, MindAccess.Peek);

		hModuleUnit = new DustHandle("giskard.me/test01.0");
		DustMachineUtils.storeHandle(hModuleUnit, units, dialogIdeas);

		hMachine = new DustHandle(hModuleUnit, null);
		hModule = new DustHandle(hModuleUnit, null);

		DustHandle hUnitLoader = new DustHandle(hModuleUnit, null);
		DustHandle hUnitLoaderLogic = new DustHandle(hModuleUnit, null);
		DustHandle hUnitLoaderFactory = new DustHandle(hModuleUnit, null);

		Map<MindHandle, Object> data;
		Map<MindHandle, Object> machineData;
		Map m;
		ArrayList a;

		machineData = data = DustMachineUtils.storeHandle(hMachine, units, dialogIdeas);
		m = new HashMap();
		m.put(MACHINE_UNITS, hUnitLoaderFactory);
		data.put(MEMBER_FACTORIES, m);

		MindDialog machine = new DustMachineLogic(initData, machineData);
		data.put(AGENT_BINARY, machine);

		data = DustMachineUtils.storeHandle(hModule, units, dialogIdeas);
		m = new HashMap();
		m.put(hUnitLoaderLogic, DustMachineLogicFormatterJson.class.getName());
		data.put(LOGIC_CLASSNAME, m);

		data = DustMachineUtils.storeHandle(hUnitLoaderFactory, units, dialogIdeas);
		a = new ArrayList();
		a.add(hUnitLoader);
		data.put(IDEA_LISTENERS, a);

		data = DustMachineUtils.storeHandle(hUnitLoader, units, dialogIdeas);
		data.put(AGENT_BINARY, new DustMachineLogicFormatterJson());
		data.put(AGENT_LOGIC, hUnitLoaderLogic);

		return machine;
	}

	public static Object dustModuleInit() throws Exception {

		DustUtilsEnumTranslator.register(MindValType.class, MIND_TAG_VALTYPE_INT, MIND_TAG_VALTYPE_REAL, MIND_TAG_VALTYPE_HANDLE, MIND_TAG_VALTYPE_BIN);

		DustUtilsEnumTranslator.register(MindCollType.class, MIND_TAG_COLLTYPE_ONE, MIND_TAG_COLLTYPE_SET, MIND_TAG_COLLTYPE_ARR, MIND_TAG_COLLTYPE_MAP);

		DustUtilsEnumTranslator.register(MindAccess.class, MIND_TAG_ACCESS_CHECK, MIND_TAG_ACCESS_PEEK, MIND_TAG_ACCESS_GET, MIND_TAG_ACCESS_SET,
				MIND_TAG_ACCESS_INSERT, MIND_TAG_ACCESS_DELETE, MIND_TAG_ACCESS_RESET, MIND_TAG_ACCESS_COMMIT);

		DustUtilsEnumTranslator.register(MindAction.class, MIND_TAG_ACTION_INIT, MIND_TAG_ACTION_BEGIN, MIND_TAG_ACTION_PROCESS, MIND_TAG_ACTION_END,
				MIND_TAG_ACTION_RELEASE);

		DustUtilsEnumTranslator.register(MindResult.class, MIND_TAG_RESULT_REJECT, MIND_TAG_RESULT_PASS, MIND_TAG_RESULT_READ, MIND_TAG_RESULT_READACCEPT,
				MIND_TAG_RESULT_ACCEPT);

//		Dust.lookup(null, "giskard.me/test01");
//		Dust.lookup(null, "giskard.me/dust:0");

		Dust.access(MIND_TAG_ACCESS_SET, hModule, hMachine, MACHINE_MODULES, "");

		Dust.access(MIND_TAG_ACCESS_SET, DustNarrativeLogicGraphWalker.class.getName(), hModule, LOGIC_CLASSNAME, MIND_LOGIC_GRAPHWALKER);

		MindHandle hJavaGenLogic = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, DustForgeLogicJavaGen.class.getName(), hModule, LOGIC_CLASSNAME, hJavaGenLogic);

		MindHandle hJavaGen = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenLogic, hJavaGen, AGENT_LOGIC);

		MindHandle hJavaGenParams = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_INSERT, hJavaGen, hJavaGenParams, IDEA_LISTENERS, KEY_ADD);

		MindHandle hUnitWalkerAgent = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, MIND_LOGIC_GRAPHWALKER, hUnitWalkerAgent, AGENT_LOGIC);

		MindHandle hUnitWalkerParams = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerAgent, hUnitWalkerParams, IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_SET, hMachine, hUnitWalkerParams, MIND_VISIT_IDEA);
		Dust.access(MIND_TAG_ACCESS_SET, MACHINE_UNITS, hUnitWalkerParams, MIND_VISIT_ATT);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenParams, hUnitWalkerAgent, MISC_ATT_TARGET);
		Dust.access(MIND_TAG_ACCESS_SET, 10L, hUnitWalkerParams, MISC_ATT_COUNT);

//		Dust.access(MIND_TAG_ACCESS_COMMIT, null, hUnitWalkerParams);

		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerParams, initData, MIND_DIALOG_INIT, KEY_ADD);

		return null;
	}
}
