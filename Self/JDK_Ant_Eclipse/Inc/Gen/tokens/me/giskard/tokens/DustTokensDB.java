package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensDB extends DustTokensMind {
	MiNDToken MTUNIT_DB = Giskard.defineToken(MiNDTokenType.Unit, "DB");
	
	MiNDToken MTTYPE_DBUSER = Giskard.defineToken(MiNDTokenType.Type, "DBUser", MTUNIT_DB);
	MiNDToken MTMEMBER_DBUSER_CONN = Giskard.defineToken(MiNDTokenType.Member, "DBConn", MTTYPE_DBUSER, MiNDValType.Link,
			MiNDCollType.One);
	
	MiNDToken MTTYPE_DBCONN = Giskard.defineToken(MiNDTokenType.Type, "Connection", MTUNIT_DB);
	MiNDToken MTMEMBER_DRIVER = Giskard.defineToken(MiNDTokenType.Member, "Driver", MTTYPE_DBCONN, MiNDValType.Raw,
			MiNDCollType.One);
	MiNDToken MTMEMBER_DBCONN_OPTIONS = Giskard.defineToken(MiNDTokenType.Member, "Options", MTTYPE_DBCONN, MiNDValType.Raw,
			MiNDCollType.One);
	MiNDToken MTMEMBER_DBCONN_TABLES = Giskard.defineToken(MiNDTokenType.Member, "Tables", MTTYPE_DBCONN, MiNDValType.Link,
			MiNDCollType.Arr);

	MiNDToken MTTYPE_DBTABLE = Giskard.defineToken(MiNDTokenType.Type, "Table", MTUNIT_DB);
	MiNDToken MTMEMBER_TABLE_COLUMNS = Giskard.defineToken(MiNDTokenType.Member, "Columns", MTTYPE_DBTABLE, MiNDValType.Link,
			MiNDCollType.Arr);

	MiNDToken MTTYPE_DBCOLUMN = Giskard.defineToken(MiNDTokenType.Type, "Column", MTUNIT_DB);
	MiNDToken MTMEMBER_COLUMN_FULLNAME = Giskard.defineToken(MiNDTokenType.Member, "FullName", MTTYPE_DBCOLUMN, MiNDValType.Raw,
			MiNDCollType.One);
	
	MiNDToken MTTYPE_DBROW = Giskard.defineToken(MiNDTokenType.Type, "Row", MTUNIT_DB);
	MiNDToken MTMEMBER_ROW_PRIMARYKEY = Giskard.defineToken(MiNDTokenType.Member, "PrimaryKey", MTTYPE_DBROW, MiNDValType.Int,
			MiNDCollType.One);

	
	MiNDToken MTAGENT_DBTEST01 = Giskard.defineToken(MiNDTokenType.Agent, "DBTest01", MTUNIT_DB);

}
