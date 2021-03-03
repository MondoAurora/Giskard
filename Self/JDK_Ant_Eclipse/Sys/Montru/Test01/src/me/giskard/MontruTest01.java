package me.giskard;

import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensText;

public class MontruTest01 implements MindConsts, DustTokensMind, DustTokensText {

	public static void main(String[] args) throws Exception {
		MiNDMachine machine = new GiskardMachineModular("DustRuntime", "1.0", args);
		
		machine.init();
		
		machine.addModule("DustText", "1.0");
		machine.addModule("DustIO", "1.0");
		
		machine.launch();
		
		MiNDAgent a = machine.testCreateAgent(MTAGENT_MATCHCONST);
		a.process(MiNDAgentAction.Init);
	}

}
