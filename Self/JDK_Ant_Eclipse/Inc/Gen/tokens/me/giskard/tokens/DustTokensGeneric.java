package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensGeneric extends MindConsts {
	MiNDToken MT_UTILS_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "Generic_Utils");
	
	MiNDToken MT_UTILS_VARIANT = Mind.defineToken(MiNDTokenType.TYPE, "Variant", MT_UTILS_UNIT);
	MiNDToken MT_VARIANT_TYPE = Mind.defineToken(MiNDTokenType.MEMBER, "Type", MT_UTILS_VARIANT, MiNDValType.Ref, MiNDCollType.One);
	MiNDToken MT_VARIANT_VALUE = Mind.defineToken(MiNDTokenType.MEMBER, "Value", MT_UTILS_VARIANT, MiNDValType.Raw, MiNDCollType.One);

}
