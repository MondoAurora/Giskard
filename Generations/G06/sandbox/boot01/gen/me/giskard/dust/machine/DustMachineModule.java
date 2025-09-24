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

	private static Map machineIdea;

//	private static DustHandle hMachine;
//	private static DustHandle hModule;
	private static DustHandle hModuleUnit;

	public static Object dustMachineInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");

		machineIdea = new DustIdea(RUNTIME_MACHINE);

		Map units = new TreeMap();
//		Map dialogIdeas = new DustIdea();
		Map vocabulary = new TreeMap();

		machineIdea.put(MACHINE_UNITS, units);
//		machineData.put(DIALOG_IDEAS, dialogIdeas);
		machineIdea.put(DIALOG_VOCABULARY, vocabulary);

		DustHandle.CURR_VOC = DustUtils.safeGet(vocabulary, LANG_ID, MAP_CREATOR);

		Map<MindHandle, Object> unitData;

		unitData = DustMachineUtils.storeHandle(MISC, machineIdea);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(MISC_TARGET, machineIdea);
		DustMachineUtils.storeHandle(MISC_PARENT, machineIdea);
		DustMachineUtils.storeHandle(MISC_CONN_REQUIRED, machineIdea);

		unitData = DustMachineUtils.storeHandle(MIND, machineIdea);
		unitData.put(UNIT_NEXT_ID, 100L);

		DustMachineUtils.storeHandle(IDEA_HANDLE, machineIdea);
		DustMachineUtils.storeHandle(IDEA_LISTENERS, machineIdea);
		DustMachineUtils.storeHandle(AGENT_LOGIC, machineIdea);
		DustMachineUtils.storeHandle(MEMBER_FACTORIES, machineIdea);

		DustMachineUtils.storeHandle(UNIT_HANDLES, machineIdea);
		DustMachineUtils.storeHandle(UNIT_NEXT_ID, machineIdea);
//		DustMachineUtils.storeHandle(DIALOG_IDEAS, machineIdea);
		DustMachineUtils.storeHandle(DIALOG_VOCABULARY, machineIdea);

		DustMachineUtils.storeHandle(ACTION_INIT, machineIdea);
		DustMachineUtils.storeHandle(ACCESS_PEEK, machineIdea);
		DustMachineUtils.storeHandle(ACCESS_SET, machineIdea);

		unitData = DustMachineUtils.storeHandle(DUST, machineIdea);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(PERS_ID, machineIdea);
		DustMachineUtils.storeHandle(MACHINE_THREADS, machineIdea);
		DustMachineUtils.storeHandle(MACHINE_DIALOGS, machineIdea);
		DustMachineUtils.storeHandle(THREAD_DIALOG, machineIdea);
		DustMachineUtils.storeHandle(MACHINE_UNITS, machineIdea);
		DustMachineUtils.storeHandle(MACHINE_MODULES, machineIdea);
		DustMachineUtils.storeHandle(LOGIC_BINARY, machineIdea);

		DustMachineUtils.storeHandle(AGENT_BINARY, machineIdea);
		DustMachineUtils.storeHandle(AGENT_SELF, machineIdea);
		DustMachineUtils.storeHandle(AGENT_PARAM, machineIdea);

		unitData = DustMachineUtils.storeHandle(DUSTJAVA, machineIdea);
		unitData.put(UNIT_NEXT_ID, 100L);
		DustMachineUtils.storeHandle(LOGIC_CLASSNAME, machineIdea);
		DustMachineUtils.storeHandle(MODULE_CLASSLOADER, machineIdea);

		DustUtilsEnumTranslator.setEnum(ACCESS_SET, MindAccess.Set);
		DustUtilsEnumTranslator.setEnum(ACCESS_PEEK, MindAccess.Peek);

		Map<MindHandle, Object> data;
		Map m;
		ArrayList a;

		hModuleUnit = new DustHandle("giskard.me/test01.0");
		
		DustHandle hUnitLoader = new DustHandle(hModuleUnit, null);
		DustHandle hUnitLoaderLogic = new DustHandle(hModuleUnit, null);
		DustHandle hUnitLoaderFactory = new DustHandle(hModuleUnit, null);
	
		m = new HashMap();
		m.put(MACHINE_UNITS, hUnitLoaderFactory);
		machineIdea.put(MEMBER_FACTORIES, m);

		MindDialog machine = new DustMachineLogic(machineIdea);
		machineIdea.put(AGENT_BINARY, machine);

		
		data = DustMachineUtils.storeHandle(hModuleUnit, machineIdea);
		Map unitRefs = m = new HashMap();

		m.put("giskard", "giskard.me/giskard.me.0");
		m.put("mind", "giskard.me/mind.0");
		m.put("misc", "giskard.me/misc.0");
		m.put("dust", "giskard.me/dust.0");
		m.put("dustjava", "giskard.me/dustjava.0");
		data.put(MISC_CONN_REQUIRED, m);

		m = new HashMap();
		m.put(hUnitLoaderLogic, DustMachineLogicFormatterJson.class.getName());
		data.put(LOGIC_CLASSNAME, m);

		data = DustMachineUtils.storeHandle(hUnitLoaderFactory, machineIdea);
		a = new ArrayList();
		a.add(hUnitLoader);
		data.put(IDEA_LISTENERS, a);

		data = DustMachineUtils.storeHandle(hUnitLoader, machineIdea);
		data.put(AGENT_BINARY, new DustMachineLogicFormatterJson());
		data.put(AGENT_LOGIC, hUnitLoaderLogic);

		DustMachineLogic.getData().put(AGENT_UNITREFS, unitRefs);

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


		
		Dust.access(MIND_TAG_ACCESS_SET, hModuleUnit, RUNTIME_MACHINE, MACHINE_MODULES, hModuleUnit.getId());

		Dust.access(MIND_TAG_ACCESS_SET, DustNarrativeLogicGraphWalker.class.getName(), hModuleUnit, LOGIC_CLASSNAME, MIND_LOGIC_GRAPHWALKER);

		MindHandle hJavaGenLogic = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, DustForgeLogicJavaGen.class.getName(), hModuleUnit, LOGIC_CLASSNAME, hJavaGenLogic);

		MindHandle hJavaGen = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenLogic, hJavaGen, AGENT_LOGIC);

		MindHandle hJavaGenParams = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_INSERT, hJavaGen, hJavaGenParams, IDEA_LISTENERS, KEY_ADD);

		MindHandle hUnitWalkerAgent = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, MIND_LOGIC_GRAPHWALKER, hUnitWalkerAgent, AGENT_LOGIC);

		MindHandle hUnitWalkerParams = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerAgent, hUnitWalkerParams, IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_SET, RUNTIME_MACHINE, hUnitWalkerParams, MIND_VISIT_HANDLE);
		Dust.access(MIND_TAG_ACCESS_SET, MACHINE_UNITS, hUnitWalkerParams, MIND_VISIT_ATT);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenParams, hUnitWalkerAgent, MISC_ATT_TARGET);
		Dust.access(MIND_TAG_ACCESS_SET, 10L, hUnitWalkerParams, MISC_ATT_COUNT);

//		Dust.access(MIND_TAG_ACCESS_COMMIT, null, hUnitWalkerParams);

		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerParams, DustMachineLogic.getData(), MIND_DIALOG_INIT, KEY_ADD);

		return null;
	}
}
