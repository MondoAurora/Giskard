package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGeneric extends GiskardConsts {
	MiNDToken MTUNIT_UTILS = Giskard.defineToken(MiNDTokenType.UNIT, "Generic_Utils");
	
	MiNDToken MTTYPE_TAGGED = Giskard.defineToken(MiNDTokenType.TYPE, "Tagged", MTUNIT_UTILS);
	MiNDToken MTMEMBER_TAGGED_TAGS = Giskard.defineToken(MiNDTokenType.MEMBER, "Tags", MTTYPE_TAGGED, MiNDValType.Ref, MiNDCollType.Set);
	
	MiNDToken MTTYPE_VARIANT = Giskard.defineToken(MiNDTokenType.TYPE, "Variant", MTUNIT_UTILS);
	MiNDToken MTMEMBER_VARIANT_TYPE = Giskard.defineToken(MiNDTokenType.MEMBER, "Type", MTTYPE_VARIANT, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_VARIANT_VALUE = Giskard.defineToken(MiNDTokenType.MEMBER, "Value", MTTYPE_VARIANT, MiNDValType.Raw, MiNDCollType.One);

	MiNDToken MTTYPE_CONN = Giskard.defineToken(MiNDTokenType.TYPE, "Conn", MTUNIT_UTILS);
	MiNDToken MTMEMBER_CONN_OWNER = Giskard.defineToken(MiNDTokenType.MEMBER, "Owner", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_REQUIRES = Giskard.defineToken(MiNDTokenType.MEMBER, "Requires", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_EXTENDS = Giskard.defineToken(MiNDTokenType.MEMBER, "Extends", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_PROVIDES = Giskard.defineToken(MiNDTokenType.MEMBER, "Provides", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
}
