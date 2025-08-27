package me.giskard.dust.forge;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;

public class DustForgeLogicJavaGen implements DustConsts.MindLogic, DustForgeConsts, DustMachineConsts, DustUtilsConsts {

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		Dust.log(null, "Now would generate sources");
		return null;
	}
}