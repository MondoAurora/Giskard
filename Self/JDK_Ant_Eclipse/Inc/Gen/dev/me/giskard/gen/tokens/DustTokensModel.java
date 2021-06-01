package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensModel extends GiskardConsts { 
	MiNDToken MTUNIT_MODEL = Giskard.defineToken(MiNDTokenType.Unit, "Model"); 
	MiNDToken MTTYPE_ENTITY = Giskard.defineToken(MiNDTokenType.Type, "Entity", MTUNIT_MODEL);
	MiNDToken MTTYPE_UNIT = Giskard.defineToken(MiNDTokenType.Type, "Unit", MTUNIT_MODEL);
	MiNDToken MTMEMBER_UNIT_SERVICES = Giskard.defineToken(MiNDTokenType.Member, "Services", MTTYPE_UNIT, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTMEMBER_ENTITY_PRIMARYTYPE = Giskard.defineToken(MiNDTokenType.Member, "PrimaryType", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_IDGLOBAL = Giskard.defineToken(MiNDTokenType.Member, "IdGlobal", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_UNIT = Giskard.defineToken(MiNDTokenType.Member, "Unit", MTTYPE_ENTITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_TAGS = Giskard.defineToken(MiNDTokenType.Member, "Tags", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEMBER_ENTITY_HANDLE = Giskard.defineToken(MiNDTokenType.Member, "Handle", MTTYPE_ENTITY, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_IDUNIT = Giskard.defineToken(MiNDTokenType.Member, "IdUnit", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_ENTITY_TYPES = Giskard.defineToken(MiNDTokenType.Member, "Types", MTTYPE_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
}
