package me.giskard.dust.sandbox;

import me.giskard.dust.DustConsts;
import me.giskard.dust.forge.DustForgeConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;

public interface DustSandboxConsts extends DustConsts, DustUtilsConsts, DustForgeConsts, DustMachineConsts {
	String LP_GISKARD_ROOT = "giskard.root";
	
	String DUT_SIGNATURE = "DustUnitText";
	
	class DustSandboxHandle extends MindHandle {
		final String key;
		String hint;

		public DustSandboxHandle(String key) {
			super();
			this.key = key;
			hint = key;
		}
		
		@Override
		public String toString() {
			return hint;
		}
	}
}
