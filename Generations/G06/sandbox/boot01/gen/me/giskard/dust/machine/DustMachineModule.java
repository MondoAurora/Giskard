package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.forge.DustForgeLogicJavaGen;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineModule implements DustMachineBootConsts, DustMachineConsts, DustUtilsConsts {

	private static DustHandle hMachine;
	private static DustHandle hModule;
	private static DustHandle hModuleUnit;

	public static Object dustMachineInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");

		Map initData = new HashMap();

		Map units = new TreeMap();
		Map dialogIdeas = new HashMap();
		Map vocabulary = new TreeMap();

		initData.put(MACHINE_UNITS, units);
		initData.put(DIALOG_IDEAS, dialogIdeas);
		initData.put(DIALOG_VOCABULARY, vocabulary);

		DustHandle.CURR_VOC = DustUtils.safeGet(vocabulary, LANG_ID, MAP_CREATOR);

		Map<MindHandle, Object> unitData;

		unitData = DustMachineUtils.storeHandle(MISC, units, dialogIdeas);
		unitData.put(UNIT_NEXT_ID, 7L);
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
		DustMachineUtils.storeHandle(MACHINE_UNITS, units, dialogIdeas);
		DustMachineUtils.storeHandle(MACHINE_MODULES, units, dialogIdeas);
		DustMachineUtils.storeHandle(LOGIC_BINARY, units, dialogIdeas);
		
		DustMachineUtils.storeHandle(AGENT_BINARY, units, dialogIdeas);
		DustMachineUtils.storeHandle(AGENT_SELF, units, dialogIdeas);
		DustMachineUtils.storeHandle(AGENT_PARAM, units, dialogIdeas);
		
		unitData = DustMachineUtils.storeHandle(DUSTJAVA, units, dialogIdeas);
		DustMachineUtils.storeHandle(LOGIC_CLASSNAME, units, dialogIdeas);
		DustMachineUtils.storeHandle(MODULE_CLASSLOADER, units, dialogIdeas);
		

		DustUtilsEnumTranslator.setEnum(ACCESS_SET, MindAccess.Set);
		DustUtilsEnumTranslator.setEnum(ACCESS_PEEK, MindAccess.Peek);
		
		
		
		
		hModuleUnit = new DustHandle("giskard.me/test01");
		DustMachineUtils.storeHandle(hModuleUnit, units, dialogIdeas);
		
		hMachine = new DustHandle(hModuleUnit, "?");
		hModule = new DustHandle(hModuleUnit, "?");

//		DustHandle hBinCreator = new DustHandle(hModuleUnit, "?");
//		DustHandle hBinCreatorLogic = new DustHandle(hModuleUnit, "?");
//		DustHandle hBinCreatorFactory = new DustHandle(hModuleUnit, "?");

		DustHandle hUnitLoader = new DustHandle(hModuleUnit, "?");
		DustHandle hUnitLoaderLogic = new DustHandle(hModuleUnit, "?");
		DustHandle hUnitLoaderFactory = new DustHandle(hModuleUnit, "?");


		Map<MindHandle, Object> data;
		Map<MindHandle, Object> machineData;
		Map m;
		ArrayList a;
		
		machineData = data = DustMachineUtils.storeHandle(hMachine, units, dialogIdeas);
		m = new HashMap();
		m.put(MACHINE_UNITS, hUnitLoaderFactory);
//		m.put(LOGIC_BINARY, hBinCreatorFactory);
		data.put(MEMBER_FACTORIES, m);
		
		MindDialog machine = new DustMachineLogic(initData, machineData);
		data.put(AGENT_BINARY, machine);


		data = DustMachineUtils.storeHandle(hModule, units, dialogIdeas);
		m = new HashMap();
//		m.put(hBinCreatorLogic, DustMachineLogicBinCreator.class.getName());
		m.put(hUnitLoaderLogic, DustMachineLogicFormatterJson.class.getName());
		data.put(LOGIC_CLASSNAME, m);
		

		data = DustMachineUtils.storeHandle(hUnitLoaderFactory, units, dialogIdeas);
		a = new ArrayList();
		a.add(hUnitLoader);
		data.put(IDEA_LISTENERS, a);
		
		data = DustMachineUtils.storeHandle(hUnitLoader, units, dialogIdeas);
		data.put(AGENT_BINARY, new DustMachineLogicFormatterJson());
		data.put(AGENT_LOGIC, hUnitLoaderLogic);
		
		
//		data = DustMachineUtils.storeHandle(hBinCreatorFactory, units, dialogIdeas);
//		a = new ArrayList();
//		a.add(hBinCreator);
//		data.put(IDEA_LISTENERS, a);
//
//		data = DustMachineUtils.storeHandle(hBinCreator, units, dialogIdeas);
//		data.put(AGENT_LOGIC, hBinCreatorLogic);
//		data.put(AGENT_BINARY, new DustMachineLogicBinCreator());
		

		return machine;
	}

	public static Object dustModuleInit() throws Exception {

		DustUtilsEnumTranslator.register(MindValType.class, MIND_TAG_VALTYPE_INT, MIND_TAG_VALTYPE_REAL, MIND_TAG_VALTYPE_HANDLE, MIND_TAG_VALTYPE_BIN);

		DustUtilsEnumTranslator.register(MindCollType.class, MIND_TAG_COLLTYPE_ONE, MIND_TAG_COLLTYPE_SET, MIND_TAG_COLLTYPE_ARR, MIND_TAG_COLLTYPE_MAP);

		DustUtilsEnumTranslator.register(MindAccess.class, MIND_TAG_ACCESS_CHECK, MIND_TAG_ACCESS_PEEK, MIND_TAG_ACCESS_GET, MIND_TAG_ACCESS_SET,
				MIND_TAG_ACCESS_INSERT, MIND_TAG_ACCESS_DELETE, MIND_TAG_ACCESS_RESET, MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACCESS_VISIT);

		DustUtilsEnumTranslator.register(MindAction.class, MIND_TAG_ACTION_INIT, MIND_TAG_ACTION_BEGIN, MIND_TAG_ACTION_PROCESS, MIND_TAG_ACTION_END,
				MIND_TAG_ACTION_RELEASE);

		DustUtilsEnumTranslator.register(MindResult.class, MIND_TAG_RESULT_REJECT, MIND_TAG_RESULT_PASS, MIND_TAG_RESULT_READ, MIND_TAG_RESULT_READACCEPT,
				MIND_TAG_RESULT_ACCEPT);
		

		Dust.lookup(null, "giskard.me/test01");
		Dust.lookup(null, "giskard.me/dust:0");

		
		Dust.access(MIND_TAG_ACCESS_SET, hModule, hMachine, MACHINE_MODULES, "");

		
		MindHandle hJavaGen = Dust.lookup(hModuleUnit, "?");
		MindHandle hJavaGenLogic = Dust.lookup(hModuleUnit, "?");
		MindHandle hJavaGenActivator = Dust.lookup(hModuleUnit, "?");

		Dust.access(MIND_TAG_ACCESS_INSERT, hJavaGen, hJavaGenActivator, IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenLogic, hJavaGen, AGENT_LOGIC);
		
		Dust.access(MIND_TAG_ACCESS_SET, DustForgeLogicJavaGen.class.getName(), hModule, LOGIC_CLASSNAME, hJavaGenLogic);

		Dust.access(MIND_TAG_ACCESS_COMMIT, null, hJavaGenActivator);


		return null;
	}
}
