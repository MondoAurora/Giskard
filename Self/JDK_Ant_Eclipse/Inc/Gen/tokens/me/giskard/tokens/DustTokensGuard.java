package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGuard extends DustTokensMind {
	MiNDToken MTUNIT_GUARD = Giskard.defineToken(MiNDTokenType.UNIT, "Guard");
	
	MiNDToken MTTYPE_ACCOUNT = Giskard.defineToken(MiNDTokenType.TYPE, "Account", MTUNIT_GUARD);
	MiNDToken MTMEMBER_ACCOUNTID = Giskard.defineToken(MiNDTokenType.MEMBER, "Id", MTTYPE_ACCOUNT, MiNDValType.Raw,
			MiNDCollType.One);
	MiNDToken MTMEMBER_PASSWORD = Giskard.defineToken(MiNDTokenType.MEMBER, "Password", MTTYPE_ACCOUNT, MiNDValType.Raw,
			MiNDCollType.One);

}
