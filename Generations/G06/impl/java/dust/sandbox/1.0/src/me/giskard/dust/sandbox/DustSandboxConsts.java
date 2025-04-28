package me.giskard.dust.sandbox;

import java.util.Map;

import me.giskard.dust.DustMainConsts;
import me.giskard.dust.utils.DustUtilsConsts;

public interface DustSandboxConsts extends DustMainConsts, DustUtilsConsts {
	
	enum TextInfo {
		 owner, txtLang, txtType, text
	}

	
	class DustSandboxHandle extends MindToken {
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
