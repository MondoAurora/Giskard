package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensMind
		extends GiskardConsts, DustTokensGeneric, DustTokensModel, DustTokensIdea, DustTokensNarrative, DustTokensDialog, DustTokensMachine {
	MiNDToken MTLOC_MACHINE_THEMACHINE = Giskard.defineToken(MiNDTokenType.Local, "TheMachine", MTUNI_MACHINE);

}
