package me.giskard.dust.runtime;

import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensText;

public interface DustRuntimeConsts extends DustRuntimeBootConsts, DustTokensMind, DustTokensMachine, DustTokensGeneric, DustTokensText {

	abstract class RuntimeAgent implements DustRuntimeConsts, MiNDAgent {
		private DustRuntimeMachine.Invocation invocation;
		
		protected DustRuntimeMachine.Invocation getInvocation() {
			return invocation;
		}
		
		public void setInvocation(DustRuntimeMachine.Invocation invocation_) {
			this.invocation = invocation_;
		}
	}
}
