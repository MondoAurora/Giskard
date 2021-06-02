package me.giskard.dust.db.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import me.giskard.Giskard;
import me.giskard.coll.GisCollConsts.MiNDCreator;
import me.giskard.coll.GisCollFactory;
import me.giskard.dust.db.DustDBUtils;

public class DustJdbcMetaInfo implements DustJdbcConsts {
	Connection conn = null;
	DatabaseMetaData dbMetaData;
	String catalog;
	String schema;
	
	GisCollFactory<String, Object> factTables = new GisCollFactory<String, Object>(true, new MiNDCreator<String, Object>() {
		@Override
		public Object create(String key) {
			Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYP_DB_TABLE);
			Giskard.access(MiNDAccessCommand.Set, key, hRet, MTMEM_TEXT_PLAINTEXT_STRING);
			return hRet;
		}
	});

	GisCollFactory<String, Object> factColumns = new GisCollFactory<String, Object>(true, new MiNDCreator<String, Object>() {
		@Override
		public Object create(String key) {
			Object hRet = Giskard.access(MiNDAccessCommand.Get, MTTYP_DB_COLUMN);
			Giskard.access(MiNDAccessCommand.Set, key, hRet, MTMEM_DB_COLUMN_FULLNAME);
			return hRet;
		}
	});
	
	public DustJdbcMetaInfo(Object hDBConn, Connection conn) throws Exception {
		this.conn = conn;
		load(hDBConn);
	}

	public void load(Object hDBConn) throws Exception {
		conn = DustJdbcUtils.optCreateConn(hDBConn, conn);
		dbMetaData = conn.getMetaData();
		
		catalog = conn.getCatalog();
		schema = conn.getSchema();
		
		ResultSet rs;

		String dbName = catalog;

		rs = dbMetaData.getTables(dbName, null, null, new String[] { JDBC_TABLE, JDBC_VIEW });
		for (boolean ok = DustJdbcUtils.optFirst(rs); ok; ok = rs.next()) {
			String tblName = rs.getString(JDBC_TABLE_NAME);
			factTables.get(tblName);
		}

		rs = dbMetaData.getColumns(dbName, null, null, null);

		for (boolean ok = DustJdbcUtils.optFirst(rs); ok; ok = rs.next()) {
			String tblName = rs.getString(JDBC_TABLE_NAME);
			Object hTable = factTables.peek(tblName);
			if ( null != hTable ) {
				String colName = rs.getString(JDBC_COLUMN_NAME);
				Object hCol = factColumns.get(DustDBUtils.buildColId(tblName, colName));
				Giskard.access(MiNDAccessCommand.Set, colName, hCol, MTMEM_TEXT_PLAINTEXT_STRING);
				Giskard.access(MiNDAccessCommand.Add, hCol, hTable, MTMEM_DB_TABLE_COLUMNS);
			}
		}
	}
	
	Object peekTable(String name) {
		return factTables.peek(name);
	}
	
	Object peekColumn(String tblName, String colName) {
		return factColumns.peek(DustDBUtils.buildColId(tblName, colName));
	}
}
