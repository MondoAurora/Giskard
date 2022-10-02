package me.giskard.dust.brain;

import me.giskard.Giskard;

public class DustBrainTest implements DustBrainConsts {
	public static void initHelloWorld() {

		Giskard.log("DustBrainTest.initHelloWorld()");
		
		MiNDHandle hMsg = Giskard.access(MiNDAccessCommand.Get, null, null, null, LANG_TYP_TEXT);
		Giskard.access(MiNDAccessCommand.Set, "Hello, world from MiND!", hMsg, LANG_MEM_TEXT_STRING);

		MiNDHandle hAgentOut = Giskard.access(MiNDAccessCommand.Get, null, null, null, DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hMsg, hAgentOut, DIALOG_MEM_IO_MESSAGE);
		
		Giskard.access(MiNDAccessCommand.Set, DustBrainAgent.DialogIO.class.getName(), DUST_BRAIN, DUST_MEM_BRAIN_IMPL, DIALOG_AGT_IO);
		Giskard.access(MiNDAccessCommand.Set, hAgentOut, null, NARRATIVE_MEM_JOURNEY_AGENT);
	}
}
