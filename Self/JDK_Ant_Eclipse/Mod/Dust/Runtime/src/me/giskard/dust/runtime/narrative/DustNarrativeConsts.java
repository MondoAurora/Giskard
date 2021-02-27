package me.giskard.dust.runtime.narrative;

import me.giskard.MindConsts;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMind;

public interface DustNarrativeConsts extends MindConsts, DustTokensMind, DustTokensGeneric {
	public interface NarrativeBlock {
		void setRelay();
		MiNDResultType getLastResult();
	}
	
	public abstract class NarrativeAgent implements MiNDAgent {
		
	}
}
