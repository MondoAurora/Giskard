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

		Dust.log(null, a, "JavaGen processing", Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE));
		return MIND_TAG_RESULT_ACCEPT;
	}
}