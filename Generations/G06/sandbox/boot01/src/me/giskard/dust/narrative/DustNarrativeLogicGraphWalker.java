package me.giskard.dust.narrative;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

public class DustNarrativeLogicGraphWalker implements DustConsts.MindLogic, DustNarrativeConsts, DustMachineConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		MindHandle ret = null;
		
		
		MindHandle hItem = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_IDEA);
		MindHandle hAtt = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_ATT);
		
		Object val = Dust.access(MIND_TAG_ACCESS_PEEK, null, hItem, hAtt);
		
		Long c = Dust.access(MIND_TAG_ACCESS_PEEK, 0L, null, DUST_PARAM, MISC_ATT_COUNT);
		
		switch ( a ) {
		case Init:
			ret = MIND_TAG_RESULT_READ;
			break;
		case Begin:
			break;
		case Process:
			ret = (--c > 0) ? MIND_TAG_RESULT_READ : MIND_TAG_RESULT_ACCEPT;
			Dust.access(MIND_TAG_ACCESS_SET, c, null, DUST_PARAM, MISC_ATT_COUNT);
			
			Dust.access(MIND_TAG_ACCESS_SET, "Process " + c, null, DUST_SELF, MISC_ATT_TARGET, MISC_ATT_COUNT);
			
			Dust.access(MIND_TAG_ACCESS_COMMIT, null, null, DUST_SELF, MISC_ATT_TARGET);
			break;
		case End:
			break;
		case Release:
			break;
		}

		Dust.log(null, a, "Walking", c);
		
		return ret;
	}
}