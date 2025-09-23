package me.giskard.dust.narrative;

import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings("rawtypes")
public class DustNarrativeLogicGraphWalker implements DustConsts.MindLogic, DustNarrativeConsts, DustMachineConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		MindAction a = DustUtilsEnumTranslator.getEnum(action, null);
		MindHandle ret = null;
						
		switch ( a ) {
		case Init:
			MindHandle hItem = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_IDEA);
			MindHandle hAtt = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_ATT);

			Map val = Dust.access(MIND_TAG_ACCESS_PEEK, null, hItem, hAtt);
			Object[] keys = val.keySet().toArray();
			
			Dust.access(MIND_TAG_ACCESS_SET, keys, null, DUST_PARAM, MIND_VISIT_VALUE);
			Dust.access(MIND_TAG_ACCESS_SET, keys.length, null, DUST_PARAM, MISC_ATT_COUNT);
			
			Dust.log(null, a, "Initialising walk", keys);

			ret = MIND_TAG_RESULT_READ;
			break;
		case Begin:
			break;
		case Process:
			Integer c = Dust.access(MIND_TAG_ACCESS_PEEK, 0, null, DUST_PARAM, MISC_ATT_COUNT);
			Object[] k = Dust.access(MIND_TAG_ACCESS_PEEK, null, null, DUST_PARAM, MIND_VISIT_VALUE);
			ret = (--c > 0) ? MIND_TAG_RESULT_READ : MIND_TAG_RESULT_ACCEPT;
			Dust.access(MIND_TAG_ACCESS_SET, c, null, DUST_PARAM, MISC_ATT_COUNT);
			
			Object s = k[c];
			
			Dust.log(null, a, "Visiting", c, s);

			
			Dust.access(MIND_TAG_ACCESS_SET, "Process " + c, null, DUST_SELF, MISC_ATT_TARGET, MISC_ATT_COUNT);
			Dust.access(MIND_TAG_ACCESS_SET, s, null, DUST_SELF, MISC_ATT_TARGET, MIND_VISIT_VALUE);
			
			Dust.access(MIND_TAG_ACCESS_COMMIT, null, null, DUST_SELF, MISC_ATT_TARGET);
			break;
		case End:
			break;
		case Release:
			break;
		}
		
		return ret;
	}
}