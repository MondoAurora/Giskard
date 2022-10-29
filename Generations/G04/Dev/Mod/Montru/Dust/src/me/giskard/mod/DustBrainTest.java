package me.giskard.mod;

import me.giskard.Giskard;
import me.giskard.dust.brain.DustBrainConsts;

public class DustBrainTest implements DustBrainConsts {
	
	public static void initTest() {
//		initHelloWorld();
//		initFlow();
		initVisit();
	}

	public static void initHelloWorld() {
		Giskard.log("DustBrainTest.initHelloWorld()");
		
		MiNDHandle hMsg = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND!", hMsg, LANG_MEM_TEXT_STRING, null);

		MiNDHandle hAgentOut = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg, hAgentOut, DIALOG_MEM_IO_MESSAGE, null);

		Giskard.access(MiNDAccessCommand.Set, hAgentOut, null, NARRATIVE_MEM_JOURNEY_AGENT, null);
	}
	
	public static void initFlow() {
		Giskard.log("DustBrainTest.initFlow()");

		MiNDHandle hMsg1 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 1111", hMsg1, LANG_MEM_TEXT_STRING, null);

		MiNDHandle hAgentOut1 = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg1, hAgentOut1, DIALOG_MEM_IO_MESSAGE, null);
		
		
		MiNDHandle hMsg2 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 2222", hMsg2, LANG_MEM_TEXT_STRING, null);

		MiNDHandle hAgentOut2 = Giskard.create(DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg2, hAgentOut2, DIALOG_MEM_IO_MESSAGE, null);
		
		
		MiNDHandle hAgentFlow = Giskard.create(NARRATIVE_AGT_FLOW);
		Giskard.access(MiNDAccessCommand.Set, hAgentOut1, hAgentFlow, NARRATIVE_MEM_FLOW_START, null);
		Giskard.access(MiNDAccessCommand.Insert, hAgentOut2, hAgentFlow, NARRATIVE_MEM_FLOW_END, null);
		Giskard.access(MiNDAccessCommand.Set, hAgentOut2, hAgentOut1, NARRATIVE_MEM_FLOW_NEXT, null);
		

		Giskard.access(MiNDAccessCommand.Set, hAgentFlow, null, NARRATIVE_MEM_JOURNEY_AGENT, null);
	}
	
	public static void initVisit() {
		Giskard.log("DustBrainTest.initVisit()");
		
		MiNDHandle hAgent = Giskard.create(NARRATIVE_AGT_VISITOR);
		
		MiNDHandle hMsg1 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 1111", hMsg1, LANG_MEM_TEXT_STRING, null);
		
		MiNDHandle hMsg2 = Giskard.create(LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND! 2222", hMsg2, LANG_MEM_TEXT_STRING, null);

		Giskard.access(MiNDAccessCommand.Insert, hMsg1, hAgent, NARRATIVE_MEM_VISITOR_TOVISIT, 0);
		Giskard.access(MiNDAccessCommand.Insert, hMsg2, hAgent, NARRATIVE_MEM_VISITOR_TOVISIT, 1);

		Giskard.access(MiNDAccessCommand.Set, hAgent, null, NARRATIVE_MEM_JOURNEY_AGENT, null);
	}
	
}
