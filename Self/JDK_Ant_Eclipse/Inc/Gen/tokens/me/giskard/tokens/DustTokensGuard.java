package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGuard extends DustTokensMind {
	MiNDToken MTUNIT_GUARD = Giskard.defineToken(MiNDTokenType.Unit, "Guard");
	
	MiNDToken MTTYPE_ACCOUNT = Giskard.defineToken(MiNDTokenType.Type, "Account", MTUNIT_GUARD);
	MiNDToken MTMEMBER_ACCOUNTID = Giskard.defineToken(MiNDTokenType.Member, "Id", MTTYPE_ACCOUNT, MiNDValType.Raw,
			MiNDCollType.One);
	MiNDToken MTMEMBER_PASSWORD = Giskard.defineToken(MiNDTokenType.Member, "Password", MTTYPE_ACCOUNT, MiNDValType.Raw,
			MiNDCollType.One);

}
