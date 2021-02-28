package me.giskard;

public class MontruTest01 implements MindConsts {

	public static void main(String[] args) throws Exception {
		MiNDMachine machine = new GiskardMachineModular("DustRuntime", "1.0", args);
		
		machine.init();
		
		machine.addModule("DustText", "1.0");
		machine.addModule("DustIO", "1.0");
		
		machine.launch();
	}

}
