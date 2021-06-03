package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDB extends GiskardConsts { 
	MiNDToken MTUNI_DB = Giskard.defineToken(MiNDTokenType.Unit, "DB"); 
	MiNDToken MTTYP_DB_USER = Giskard.defineToken(MiNDTokenType.Type, "User", MTUNI_DB);
	MiNDToken MTMEM_DB_USER_CONN = Giskard.defineToken(MiNDTokenType.Member, "Conn", MTTYP_DB_USER, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_DB_CONNECTION = Giskard.defineToken(MiNDTokenType.Type, "Connection", MTUNI_DB);
	MiNDToken MTMEM_DB_CONNECTION_DRIVER = Giskard.defineToken(MiNDTokenType.Member, "Driver", MTTYP_DB_CONNECTION, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_DB_CONNECTION_OPTIONS = Giskard.defineToken(MiNDTokenType.Member, "Options", MTTYP_DB_CONNECTION, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_DB_CONNECTION_TABLES = Giskard.defineToken(MiNDTokenType.Member, "Tables", MTTYP_DB_CONNECTION, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTTYP_DB_TABLE = Giskard.defineToken(MiNDTokenType.Type, "Table", MTUNI_DB);
	MiNDToken MTMEM_DB_TABLE_COLUMNS = Giskard.defineToken(MiNDTokenType.Member, "Columns", MTTYP_DB_TABLE, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTTYP_DB_COLUMN = Giskard.defineToken(MiNDTokenType.Type, "Column", MTUNI_DB);
	MiNDToken MTMEM_DB_COLUMN_FULLNAME = Giskard.defineToken(MiNDTokenType.Member, "FullName", MTTYP_DB_COLUMN, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTTYP_DB_ROW = Giskard.defineToken(MiNDTokenType.Type, "Row", MTUNI_DB);
	MiNDToken MTMEM_DB_ROW_PRIMARYKEY = Giskard.defineToken(MiNDTokenType.Member, "PrimaryKey", MTTYP_DB_ROW, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTAGN_DB_TEST01 = Giskard.defineToken(MiNDTokenType.Agent, "Test01", MTUNI_DB);
}
