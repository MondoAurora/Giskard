package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDB extends GiskardConsts { 
	MiNDToken MTUNIT_DB = Giskard.defineToken(MiNDTokenType.Unit, "DB"); 
	MiNDToken MTTYPE_DBUSER = Giskard.defineToken(MiNDTokenType.Type, "DBUser", MTUNIT_DB);
	MiNDToken MTTYPE_CONNECTION = Giskard.defineToken(MiNDTokenType.Type, "Connection", MTUNIT_DB);
	MiNDToken MTTYPE_TABLE = Giskard.defineToken(MiNDTokenType.Type, "Table", MTUNIT_DB);
	MiNDToken MTTYPE_COLUMN = Giskard.defineToken(MiNDTokenType.Type, "Column", MTUNIT_DB);
	MiNDToken MTTYPE_ROW = Giskard.defineToken(MiNDTokenType.Type, "Row", MTUNIT_DB);
	MiNDToken MTAGENT_DBTEST01 = Giskard.defineToken(MiNDTokenType.Agent, "DBTest01", MTUNIT_DB);
	MiNDToken MTMEMBER_ROW_PRIMARYKEY = Giskard.defineToken(MiNDTokenType.Member, "PrimaryKey", MTTYPE_ROW, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_COLUMN_FULLNAME = Giskard.defineToken(MiNDTokenType.Member, "FullName", MTTYPE_COLUMN, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_TABLE_COLUMNS = Giskard.defineToken(MiNDTokenType.Member, "Columns", MTTYPE_TABLE, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_CONNECTION_DRIVER = Giskard.defineToken(MiNDTokenType.Member, "Driver", MTTYPE_CONNECTION, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_CONNECTION_OPTIONS = Giskard.defineToken(MiNDTokenType.Member, "Options", MTTYPE_CONNECTION, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_CONNECTION_TABLES = Giskard.defineToken(MiNDTokenType.Member, "Tables", MTTYPE_CONNECTION, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_DBUSER_DBCONN = Giskard.defineToken(MiNDTokenType.Member, "DBConn", MTTYPE_DBUSER, MiNDValType.Link, MiNDCollType.One);
}
