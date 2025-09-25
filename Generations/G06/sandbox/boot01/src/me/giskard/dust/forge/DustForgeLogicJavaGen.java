package me.giskard.dust.forge;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

public class DustForgeLogicJavaGen implements DustConsts.MindLogic, DustForgeConsts, DustMachineConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		Integer count = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_PARAM, MISC_ATT_COUNT);
		boolean last = false;

		switch (a) {
		case Init:
		case Release:
			return MIND_TAG_RESULT_ACCEPT;
		case Begin:
			++count;
			break;
		case End:
			--count;
			last = null == Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE);
			break;
		case Process:
			break;
		}

		Dust.access(MIND_TAG_ACCESS_SET, count, null, DUST_PARAM, MISC_ATT_COUNT);

		Dust.log(null, a, "JavaGen processing", "local count: " + count, 
				"walk depth: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MISC_DEPTH),
				"handle: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_HANDLE),
				"att: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_ATT),
				"key: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_KEY),
				"value: " + Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE));

		if (last) {
			Dust.log(null, a, "JavaGen processing complete", count);
		}

		return MIND_TAG_RESULT_ACCEPT;
	}
}