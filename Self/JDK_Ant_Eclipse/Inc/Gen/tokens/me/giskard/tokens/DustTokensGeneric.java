package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensGeneric extends MindConsts {
	MiNDToken MTUNIT_UTILS = Mind.defineToken(MiNDTokenType.UNIT, "Generic_Utils");
	
	MiNDToken MTTYPE_TAGGED = Mind.defineToken(MiNDTokenType.TYPE, "Tagged", MTUNIT_UTILS);
	MiNDToken MTMEMBER_TAGGED_TAGS = Mind.defineToken(MiNDTokenType.MEMBER, "Tags", MTTYPE_TAGGED, MiNDValType.Ref, MiNDCollType.Set);
	
	MiNDToken MTTYPE_VARIANT = Mind.defineToken(MiNDTokenType.TYPE, "Variant", MTUNIT_UTILS);
	MiNDToken MTMEMBER_VARIANT_TYPE = Mind.defineToken(MiNDTokenType.MEMBER, "Type", MTTYPE_VARIANT, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_VARIANT_VALUE = Mind.defineToken(MiNDTokenType.MEMBER, "Value", MTTYPE_VARIANT, MiNDValType.Raw, MiNDCollType.One);

	MiNDToken MTTYPE_CONN = Mind.defineToken(MiNDTokenType.TYPE, "Conn", MTUNIT_UTILS);
	MiNDToken MTMEMBER_CONN_OWNER = Mind.defineToken(MiNDTokenType.MEMBER, "Owner", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MTMEMBER_CONN_REQUIRES = Mind.defineToken(MiNDTokenType.MEMBER, "Requires", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_EXTENDS = Mind.defineToken(MiNDTokenType.MEMBER, "Extends", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
	MiNDToken MTMEMBER_CONN_PROVIDES = Mind.defineToken(MiNDTokenType.MEMBER, "Provides", MTTYPE_CONN, MiNDValType.Ref, MiNDCollType.Set);
}
