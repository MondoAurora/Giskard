package me.giskard.dust.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustJdbcAgentDBConn implements DustJdbcConsts, GiskardConsts.MiNDAgentBlock {
	Object hDBConn = MTMEMBER_ACTION_THIS;
	Connection conn = null;

	DustJdbcMetaInfo metaInfo;
	DustJdbcSerializer serializer;

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		if ( null == conn ) {
			hDBConn = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_DBUSER_CONN);
			conn = DustJdbcUtils.optCreateConn(hDBConn, conn);

			firstTest();
			getMetaInfo();

			serializer = new DustJdbcSerializer(metaInfo);
		}
		
		serializer.step(hDBConn, conn);

		return ret;
	}

	@Override
	public void mindAgentBegin() throws Exception {
	}

	@Override
	public void mindAgentEnd() throws Exception {
//		DustJdbcUtils.releaseConn(conn, null);
		
//		Giskard.log(MiNDEventLevel.Trace, " ---- Collected for serialisation ----", serializer);

	}

	public DustJdbcMetaInfo getMetaInfo() throws Exception {
		if ( null == metaInfo ) {
			metaInfo = new DustJdbcMetaInfo(hDBConn, conn);
		}
		return metaInfo;
	}

	public void firstTest() throws Exception {
		String query = "select * from dust_entity";

		Giskard.log(MiNDEventLevel.Trace, "Running SQL command", query);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		DustJdbcUtils.dumpResultSet(rs);
	}

}
