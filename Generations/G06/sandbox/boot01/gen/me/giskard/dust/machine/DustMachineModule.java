package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.forge.DustForgeConsts;
import me.giskard.dust.forge.DustForgeLogicCollectJson;
import me.giskard.dust.forge.DustForgeLogicGenJavaToken;
import me.giskard.dust.forge.DustForgeUtils;
import me.giskard.dust.forge.DustGenForgeConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.machine.DustMachineConstsInt.DustIdea;
import me.giskard.dust.narrative.DustNarrativeLogicGraphWalker;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineModule implements DustMachineBootConsts, DustMachineConsts, DustUtilsConsts, DustForgeConsts {

	private static Map machineIdea;

	private static DustHandle hModuleUnit;
	private static Map moduleData;

	public static Object dustMachineInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");

		machineIdea = new DustIdea(RUNTIME_MACHINE);

		Map units = new TreeMap();

		machineIdea.put(DUST_MACHINE_UNITS, units);

		DustMachineUtils.safeGetIdea(MISC, machineIdea);
		DustMachineUtils.safeGetIdea(MISC_GEN_TARGET, machineIdea);
		DustMachineUtils.safeGetIdea(MISC_GEN_PARENT, machineIdea);
		DustMachineUtils.safeGetIdea(MISC_CONN_REQUIRED, machineIdea);

		DustMachineUtils.safeGetIdea(MIND, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_IDEA_HANDLE, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_IDEA_LISTENERS, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_IDEA_PRIMARYASPECT, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_AGENT_LOGIC, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_MEMBER_FACTORIES, machineIdea);

		DustMachineUtils.safeGetIdea(MIND_UNIT_HANDLES, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_UNIT_NEXT_ID, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_UNIT_IDEAS, machineIdea);

		DustMachineUtils.safeGetIdea(MIND_TAG_ACTION_INIT, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_TAG_ACCESS_PEEK, machineIdea);
		DustMachineUtils.safeGetIdea(MIND_TAG_ACCESS_SET, machineIdea);

		DustMachineUtils.safeGetIdea(DUST, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_PERS_ID, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_MACHINE_THREADS, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_MACHINE_DIALOGS, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_THREAD_DIALOG, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_MACHINE_UNITS, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_MACHINE_MODULES, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_LOGIC_BINARY, machineIdea);

		DustMachineUtils.safeGetIdea(DUST_AGENT_BINARY, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_AGENT_SELF, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_AGENT_PARAM, machineIdea);
		DustMachineUtils.safeGetIdea(DUST_AGENT_UNITREFS, machineIdea);

		DustMachineUtils.safeGetIdea(JAVA, machineIdea);
		DustMachineUtils.safeGetIdea(JAVA_CLASSNAME, machineIdea);
		DustMachineUtils.safeGetIdea(JAVA_MODULE_CLASSLOADER, machineIdea);

		DustMachineUtils.safeGetIdea(TEXT, machineIdea);
		DustMachineUtils.safeGetIdea(TEXT_TAG_TOKEN, machineIdea);

		DustMachineUtils.safeGetIdea(FORGE, machineIdea);

		DustUtilsEnumTranslator.setEnum(MIND_TAG_ACCESS_SET, MindAccess.Set);
		DustUtilsEnumTranslator.setEnum(MIND_TAG_ACCESS_PEEK, MindAccess.Peek);

		Map<MindHandle, Object> data;
		Map m;
		ArrayList a;

		hModuleUnit = new DustHandle("giskard.me/test01.0");
		moduleData = DustMachineUtils.safeGetIdea(hModuleUnit, machineIdea);

		
		DustHandle hUnitLoader = new DustHandle(hModuleUnit, DustMachineUtils.nextId(moduleData));
		DustHandle hUnitLoaderLogic = new DustHandle(hModuleUnit, DustMachineUtils.nextId(moduleData));
		DustHandle hUnitLoaderFactory = new DustHandle(hModuleUnit, DustMachineUtils.nextId(moduleData));

		m = new HashMap();
		m.put(DUST_MACHINE_UNITS, hUnitLoaderFactory);
		machineIdea.put(MIND_MEMBER_FACTORIES, m);

		MindDialog machine = new DustMachineLogic(machineIdea);
		machineIdea.put(DUST_AGENT_BINARY, machine);

		data = DustMachineUtils.safeGetIdea(hModuleUnit, machineIdea);
		Map unitRefs = m = new HashMap();

		DustUtils.addBothRefs(m, "giskard", "giskard.me/giskard.me.0");
		DustUtils.addBothRefs(m, "mind", "giskard.me/mind.0");
		DustUtils.addBothRefs(m, "misc", "giskard.me/misc.0");
		DustUtils.addBothRefs(m, "dust", "giskard.me/dust.0");
		DustUtils.addBothRefs(m, "dustjava", "giskard.me/dustjava.0");
		DustUtils.addBothRefs(m, "text", "giskard.me/text.0");
		DustUtils.addBothRefs(m, "forge", "giskard.me/forge.0");
		data.put(MISC_CONN_REQUIRED, m);

		m = new HashMap();
		m.put(hUnitLoaderLogic, DustMachineLogicFormatterJson.class.getName());
		data.put(JAVA_CLASSNAME, m);

		data = DustMachineUtils.safeGetIdea(hUnitLoaderFactory, machineIdea);
		a = new ArrayList();
		a.add(hUnitLoader);
		data.put(MIND_IDEA_LISTENERS, a);

		data = DustMachineUtils.safeGetIdea(hUnitLoader, machineIdea);
		data.put(DUST_AGENT_BINARY, new DustMachineLogicFormatterJson());
		data.put(MIND_AGENT_LOGIC, hUnitLoaderLogic);

		DustMachineLogic.getData().put(DUST_AGENT_UNITREFS, unitRefs);

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

		DustForgeUtils.loadTokens(DustGenConsts.class, machineIdea);
		DustForgeUtils.loadTokens(DustGenForgeConsts.class, machineIdea);
		MindHandle bootTag = DustForgeUtils.loadTokens(DustGenBoot.class, machineIdea);

		Dust.access(MIND_TAG_ACCESS_SET, hModuleUnit, RUNTIME_MACHINE, DUST_MACHINE_MODULES, hModuleUnit.getId());

		Dust.access(MIND_TAG_ACCESS_SET, DustNarrativeLogicGraphWalker.class.getName(), hModuleUnit, JAVA_CLASSNAME, MIND_LOGIC_GRAPHWALKER);
		Dust.access(MIND_TAG_ACCESS_SET, DustForgeLogicGenJavaToken.class.getName(), hModuleUnit, JAVA_CLASSNAME, FORGE_LOGIC_GENJAVATOKEN);
		Dust.access(MIND_TAG_ACCESS_SET, DustForgeLogicCollectJson.class.getName(), hModuleUnit, JAVA_CLASSNAME, FORGE_LOGIC_DUMPJSON);

		MindHandle hJavaGen = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, FORGE_LOGIC_GENJAVATOKEN, hJavaGen, MIND_AGENT_LOGIC);

		MindHandle hCollect = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, FORGE_LOGIC_DUMPJSON, hCollect, MIND_AGENT_LOGIC);
		Dust.access(MIND_TAG_ACCESS_SET, "genUnits", hCollect, FORGE_GEN_PATH);
		Dust.access(MIND_TAG_ACCESS_SET, true, hCollect, MIND_IDEA_TAGS, MISC_TAG_VERBOSE);

		MindHandle hJavaGenParams = Dust.lookup(hModuleUnit, null);
//		Dust.access(MIND_TAG_ACCESS_INSERT, hJavaGen, hJavaGenParams, MIND_IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_INSERT, hCollect, hJavaGenParams, MIND_IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_SET, LANG_ID, hJavaGenParams, TEXT_ATT_LANG);
		Dust.access(MIND_TAG_ACCESS_SET, "gen", hJavaGenParams, FORGE_SRC_PATH);
//		Dust.access(MIND_TAG_ACCESS_SET, "gen2", hJavaGenParams, FORGE_SRC_PATH);
		Dust.access(MIND_TAG_ACCESS_SET, bootTag, hJavaGenParams, FORGE_BOOT_TAG);

		MindHandle hUnitWalkerAgent = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_SET, MIND_LOGIC_GRAPHWALKER, hUnitWalkerAgent, MIND_AGENT_LOGIC);

		MindHandle hUnitWalkerParams = Dust.lookup(hModuleUnit, null);
		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerAgent, hUnitWalkerParams, MIND_IDEA_LISTENERS, KEY_ADD);
		Dust.access(MIND_TAG_ACCESS_SET, RUNTIME_MACHINE, hUnitWalkerParams, MIND_VISIT_HANDLE);
		Dust.access(MIND_TAG_ACCESS_SET, DUST_MACHINE_UNITS, hUnitWalkerParams, MIND_VISIT_ATT);
		Dust.access(MIND_TAG_ACCESS_SET, hJavaGenParams, hUnitWalkerAgent, MISC_GEN_TARGET);
		Dust.access(MIND_TAG_ACCESS_SET, 10L, hUnitWalkerParams, MISC_PROC_COUNT);

		Dust.access(MIND_TAG_ACCESS_INSERT, hUnitWalkerParams, DustMachineLogic.getData(), MIND_DIALOG_INIT, KEY_ADD);

		return null;
	}

}
