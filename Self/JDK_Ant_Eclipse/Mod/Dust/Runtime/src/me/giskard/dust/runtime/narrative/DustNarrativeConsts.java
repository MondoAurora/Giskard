package me.giskard.dust.runtime.narrative;

import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMind;

public interface DustNarrativeConsts extends GiskardConsts, DustTokensMind, DustTokensGeneric {
	public interface NarrativeBlock {
		void setRelay();
		MiNDResultType getLastResult();
	}
	
	public abstract class NarrativeAgent implements MiNDAgent {
		
	}
}
