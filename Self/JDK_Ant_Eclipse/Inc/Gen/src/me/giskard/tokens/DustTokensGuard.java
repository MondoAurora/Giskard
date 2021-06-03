package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGuard extends GiskardConsts { 
	MiNDToken MTUNI_GUARD = Giskard.defineToken(MiNDTokenType.Unit, "Guard"); 
	MiNDToken MTTYP_GUARD_ACCOUNT = Giskard.defineToken(MiNDTokenType.Type, "Account", MTUNI_GUARD);
	MiNDToken MTMEM_GUARD_ACCOUNT_ID = Giskard.defineToken(MiNDTokenType.Member, "Id", MTTYP_GUARD_ACCOUNT, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_GUARD_ACCOUNT_PASSWORD = Giskard.defineToken(MiNDTokenType.Member, "Password", MTTYP_GUARD_ACCOUNT, MiNDValType.Raw, MiNDCollType.One);
}
