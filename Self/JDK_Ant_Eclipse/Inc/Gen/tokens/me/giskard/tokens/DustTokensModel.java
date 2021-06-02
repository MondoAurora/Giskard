package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensModel extends GiskardConsts { 
	MiNDToken MTUNI_MODEL = Giskard.defineToken(MiNDTokenType.Unit, "Model"); 
	MiNDToken MTTYP_MODEL_UNIT = Giskard.defineToken(MiNDTokenType.Type, "Unit", MTUNI_MODEL);
	MiNDToken MTMEM_MODEL_UNIT_SERVICES = Giskard.defineToken(MiNDTokenType.Member, "Services", MTTYP_MODEL_UNIT, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTTYP_MODEL_ENTITY = Giskard.defineToken(MiNDTokenType.Type, "Entity", MTUNI_MODEL);
	MiNDToken MTMEM_MODEL_ENTITY_TYPES = Giskard.defineToken(MiNDTokenType.Member, "Types", MTTYP_MODEL_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEM_MODEL_ENTITY_TAGS = Giskard.defineToken(MiNDTokenType.Member, "Tags", MTTYP_MODEL_ENTITY, MiNDValType.Raw, MiNDCollType.Set);
	MiNDToken MTMEM_MODEL_ENTITY_IDUNIT = Giskard.defineToken(MiNDTokenType.Member, "IdUnit", MTTYP_MODEL_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_MODEL_ENTITY_HANDLE = Giskard.defineToken(MiNDTokenType.Member, "Handle", MTTYP_MODEL_ENTITY, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEM_MODEL_ENTITY_UNIT = Giskard.defineToken(MiNDTokenType.Member, "Unit", MTTYP_MODEL_ENTITY, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_MODEL_ENTITY_IDGLOBAL = Giskard.defineToken(MiNDTokenType.Member, "IdGlobal", MTTYP_MODEL_ENTITY, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_MODEL_ENTITY_PRIMARYTYPE = Giskard.defineToken(MiNDTokenType.Member, "PrimaryType", MTTYP_MODEL_ENTITY, MiNDValType.Raw, MiNDCollType.One);
}
