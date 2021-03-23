package me.giskard.dust.db.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

public class DustJdbcAgent implements DustJdbcConsts, GiskardConsts.MiNDAgent {
	Connection conn = null;
	DatabaseMetaData dbMetaData;

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT_PASS;

		switch ( action ) {
		case Begin:
			break;
		case End:
			releaseConn(conn, null);
			break;
		case Init:
			break;
		case Process:
			optCreateConn();
			String query = "select * from dust_entity";

			Giskard.log(MiNDEventLevel.TRACE, "Running SQL command", query);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			DustJdbcUtils.dumpResultSet(rs);

			break;
		case Release:
			break;
		}

		return ret;
	}

	private void optCreateConn() throws Exception {
		if ( (null == conn) || conn.isClosed() ) {
			String dbName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
			String dbUrl = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_URL);
			if ( !GiskardUtils.isEmpty(dbName) && !dbUrl.endsWith(dbName) ) {
				dbUrl += "/" + dbName;
			}

			Giskard.log(MiNDEventLevel.TRACE, "Connecting to database...", dbUrl);

			try {
				String driver = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_DRIVER);
				Class.forName(driver);

				conn = DriverManager.getConnection(dbUrl, 
						Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_ACCOUNTID),
						Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_PASSWORD));

				DustJdbcUtils.addConn(conn);

				dbMetaData = conn.getMetaData();

				Giskard.log(MiNDEventLevel.TRACE, "Connection successful.");

			} catch (Throwable e) {
				releaseConn(conn, e);
				conn = null;
				dbMetaData = null;
				GiskardException.wrap(e);
			}
		}
	}

	public static void releaseConn(Connection conn, Throwable cause) throws Exception {
		if ( null != conn ) {
			if ( !conn.isClosed() ) {
				if ( !conn.getAutoCommit() ) {
					if ( null == cause ) {
						conn.commit();
					} else {
						conn.rollback();
					}
				}
				conn.close();
				Giskard.log(MiNDEventLevel.TRACE, "DB connection closed.");
			}
			conn = null;
		}
	}

}
