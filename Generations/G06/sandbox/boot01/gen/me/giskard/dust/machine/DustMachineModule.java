package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineModule implements DustMachineBootConsts, DustMachineConsts {

	public static Object dustMachineInit() throws Exception {
		Dust.broadcast(null, "Hello world from Sandbox module");

		Map initData = new HashMap();

		Map units = new TreeMap();
		Map dialogIdeas = new HashMap();
		Map vocabulary = new TreeMap();

		initData.put(MACHINE_UNITS, units);
		initData.put(DIALOG_IDEAS, dialogIdeas);
		initData.put(DIALOG_VOCABULARY, vocabulary);
		
		Map<MindHandle, Object> unitData;

		unitData = DustMachineUtils.storeHandle(MISC, units, dialogIdeas, vocabulary, LANG_ID, "misc");
		unitData.put(UNIT_NEXT_ID, 7L);
		DustMachineUtils.storeHandle(MISC_TARGET, units, dialogIdeas, vocabulary, LANG_ID, "target");
		DustMachineUtils.storeHandle(MISC_PARENT, units, dialogIdeas, vocabulary, LANG_ID, "parent");
		DustMachineUtils.storeHandle(MISC_CONN_REQUIRED, units, dialogIdeas, vocabulary, LANG_ID, "connRequired");

		unitData = DustMachineUtils.storeHandle(MIND, units, dialogIdeas, vocabulary, LANG_ID, "mind");
		unitData.put(UNIT_NEXT_ID, 2L);

		DustMachineUtils.storeHandle(IDEA_HANDLE, units, dialogIdeas, vocabulary, LANG_ID, "handle");
		DustMachineUtils.storeHandle(UNIT_HANDLES, units, dialogIdeas, vocabulary, LANG_ID, "unitHandles");
		DustMachineUtils.storeHandle(UNIT_NEXT_ID, units, dialogIdeas, vocabulary, LANG_ID, "unitNextId");
		DustMachineUtils.storeHandle(DIALOG_IDEAS, units, dialogIdeas, vocabulary, LANG_ID, "dialogIdeas");
		DustMachineUtils.storeHandle(DIALOG_VOCABULARY, units, dialogIdeas, vocabulary, LANG_ID, "vocabulary");

		DustMachineUtils.storeHandle(ACTION_INIT, units, dialogIdeas, vocabulary, LANG_ID, "init");
		DustMachineUtils.storeHandle(ACCESS_PEEK, units, dialogIdeas, vocabulary, LANG_ID, "peek");
		DustMachineUtils.storeHandle(ACCESS_SET, units, dialogIdeas, vocabulary, LANG_ID, "set");

		unitData = DustMachineUtils.storeHandle(DUST, units, dialogIdeas, vocabulary, LANG_ID, "dust");
		DustMachineUtils.storeHandle(PERS_ID, units, dialogIdeas, vocabulary, LANG_ID, "persId");

		MindDialog machine = new DustMachineLogic(initData);

		return machine;
	}

	public static Object dustModuleInit() throws Exception {

		DustUtilsEnumTranslator.register(MindValType.class, MIND_TAG_VALTYPE_INT, MIND_TAG_VALTYPE_REAL, MIND_TAG_VALTYPE_HANDLE, MIND_TAG_VALTYPE_BIN);

		DustUtilsEnumTranslator.register(MindCollType.class, MIND_TAG_COLLTYPE_ONE, MIND_TAG_COLLTYPE_SET, MIND_TAG_COLLTYPE_ARR, MIND_TAG_COLLTYPE_MAP);

		DustUtilsEnumTranslator.register(MindAccess.class, MIND_TAG_ACCESS_CHECK, MIND_TAG_ACCESS_PEEK, MIND_TAG_ACCESS_GET, MIND_TAG_ACCESS_SET,
				MIND_TAG_ACCESS_INSERT, MIND_TAG_ACCESS_DELETE, MIND_TAG_ACCESS_RESET, MIND_TAG_ACCESS_COMMIT, MIND_TAG_ACCESS_BROADCAST, MIND_TAG_ACCESS_LOOKUP,
				MIND_TAG_ACCESS_VISIT);

		DustUtilsEnumTranslator.register(MindAction.class, MIND_TAG_ACTION_INIT, MIND_TAG_ACTION_BEGIN, MIND_TAG_ACTION_PROCESS, MIND_TAG_ACTION_END,
				MIND_TAG_ACTION_RELEASE);

		DustUtilsEnumTranslator.register(MindResult.class, MIND_TAG_RESULT_REJECT, MIND_TAG_RESULT_PASS, MIND_TAG_RESULT_READ, MIND_TAG_RESULT_READACCEPT,
				MIND_TAG_RESULT_ACCEPT);

		Dust.lookup(null, "giskard.me/test01");
		Dust.lookup(null, "giskard.me/dust:0");

		return null;
	}
}
