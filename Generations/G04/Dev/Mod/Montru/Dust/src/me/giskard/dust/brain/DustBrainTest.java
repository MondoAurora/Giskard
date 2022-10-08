package me.giskard.dust.brain;

import me.giskard.Giskard;

public class DustBrainTest implements DustBrainConsts {
	
	static {
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.DialogIO.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.NarrativeFlow.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, NARRATIVE_AGT_FLOW);
		Giskard.access(MiNDAccessCommand.Insert, DustBrainAgent.NarrativeVisitor.class.getName(), DUST_BRAIN, 
				DUST_MEM_BRAIN_IMPL, NARRATIVE_AGT_VISITOR);
	}

	public static void initHelloWorld() {
		Giskard.log("DustBrainTest.initHelloWorld()");
		
		MiNDHandle hMsg = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND!", hMsg, LANG_MEM_TEXT_STRING);

		MiNDHandle hAgentOut = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg, hAgentOut, DIALOG_MEM_IO_MESSAGE);

		Giskard.access(MiNDAccessCommand.Set, hAgentOut, null, NARRATIVE_MEM_JOURNEY_AGENT);
	}
	
	public static void initFlow() {
		Giskard.log("DustBrainTest.initFlow()");

		MiNDHandle hMsg1 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 1111", hMsg1, LANG_MEM_TEXT_STRING);

		MiNDHandle hAgentOut1 = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg1, hAgentOut1, DIALOG_MEM_IO_MESSAGE);
		
		
		MiNDHandle hMsg2 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 2222", hMsg2, LANG_MEM_TEXT_STRING);

		MiNDHandle hAgentOut2 = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg2, hAgentOut2, DIALOG_MEM_IO_MESSAGE);
		
		
		MiNDHandle hAgentFlow = Giskard.create(NARRATIVE_AGT_FLOW);
		Giskard.access(MiNDAccessCommand.Set, hAgentOut1, hAgentFlow, NARRATIVE_MEM_FLOW_START);
		Giskard.access(MiNDAccessCommand.Insert, hAgentOut2, hAgentFlow, NARRATIVE_MEM_FLOW_END);
		Giskard.access(MiNDAccessCommand.Set, hAgentOut2, hAgentOut1, NARRATIVE_MEM_FLOW_NEXT);
		

		Giskard.access(MiNDAccessCommand.Set, hAgentFlow, null, NARRATIVE_MEM_JOURNEY_AGENT);
	}
	
	public static void initVisit() {
		Giskard.log("DustBrainTest.initVisit()");
		
		MiNDHandle hAgent = Giskard.create(NARRATIVE_AGT_VISITOR);
		
		Giskard.access(MiNDAccessCommand.Set, IDEA_UNI, hAgent, NARRATIVE_MEM_VISITOR_TOVISIT);

		Giskard.access(MiNDAccessCommand.Set, hAgent, null, NARRATIVE_MEM_JOURNEY_AGENT);
	}
	
}
