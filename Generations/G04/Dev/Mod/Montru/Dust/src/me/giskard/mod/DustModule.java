package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.DustTokens;
import me.giskard.dust.boot.DustBootGiskard;
import me.giskard.dust.brain.DustBrainAgent;

public class DustModule implements DustTokens, GiskardConsts.MiNDAgent {
	
	@Override
	public MiNDResultType mindAgentStep() throws Exception {
		Giskard.log(null, this.getClass().getName(), "module initializing");
		
		new DustBootGiskard();
		
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.DialogIO.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.NarrativeFlow.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, NARRATIVE_AGT_FLOW);
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.NarrativeVisitor.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, NARRATIVE_AGT_VISITOR);

		DustBrainTest.initTest();		

		return MiNDResultType.Accept;
	}

}
