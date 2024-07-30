package me.giskard.dust.sandbox;

import java.util.Map;

import me.giskard.dust.DustConsts;
import me.giskard.dust.forge.DustForgeConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.utils.DustUtilsConsts;

public interface DustSandboxConsts extends DustConsts, DustUtilsConsts, DustForgeConsts, DustMachineConsts {
	String LP_GISKARD_ROOT = "giskard.root";
	
	String DUT_SIGNATURE = "DustUnitText";
	
	enum TextInfo {
		 owner, txtLang, txtType, text
	}

	
	class DustSandboxHandle extends MindHandle {
		final String id;
		static Map<DustSandboxHandle, String> TO_STRING;

		public DustSandboxHandle(String id) {
			super();
			this.id = id;
		}
		
		@Override
		public String getId() {
			return id;
		}
		
		@Override
		public String toString() {
			return ( null == TO_STRING ) ? id : TO_STRING.getOrDefault(this, id);
		}
	}
}
