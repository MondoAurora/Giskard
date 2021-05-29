package me.giskard.dust.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts.MiNDCreator;
import me.giskard.coll.GisCollFactory;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustJdbcSerializer implements DustJdbcConsts {
	Object hDBConn;
	Connection conn;

	DustJdbcMetaInfo metaInfo;

	class DBDeltaEntity {
		Object entity;

		DBDeltaEntity unit;
		Integer pKey;

		ArrayList<DBDeltaData> data = new ArrayList<>();

		public DBDeltaEntity(Object entity_) {
			this.entity = entity_;
			pKey = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_ROW_PRIMARYKEY);
		}

		void addData() {
			data.add(new DBDeltaData());
		}

		boolean setStParams(PreparedStatement ps, Integer commitId) throws Exception {
			boolean ret = true;

			MiNDToken pt = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_ENTITY_PRIMARYTYPE);
			Integer i = (null == pt) ? -1
					: Giskard.access(MiNDAccessCommand.Get, -1, pt.getEntity(), MTMEMBER_ROW_PRIMARYKEY);
			ps.setInt(DbEntity.PrimaryType.ordinal(), i);

			if ( -1 == i ) {
				ret = false;
			}

			i = Giskard.access(MiNDAccessCommand.Get, -1, entity, MTMEMBER_ENTITY_STOREUNIT, MTMEMBER_ROW_PRIMARYKEY);

			ps.setInt(DbEntity.Unit.ordinal(), i);
			if ( -1 == i ) {
				ret = false;
			}

			i = commitId;
			ps.setInt(DbEntity.LastChange.ordinal(), i);
			if ( -1 == i ) {
				ret = false;
			}

			ps.setNull(DbEntity.LastValid.ordinal(), Types.INTEGER);

			return ret;
		}

		Integer loadDbId(ResultSet res) throws Exception {
			pKey = res.getInt(1);
			Giskard.access(MiNDAccessCommand.Set, pKey, entity, MTMEMBER_ROW_PRIMARYKEY);
			return pKey;
		}

		@Override
		public String toString() {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", true, "Entity:", entity, "\nData:");

			for (DBDeltaData dd : data) {
				sb = GiskardUtils.sbAppend(sb, "\n   ", true, dd);
			}
			return sb.toString();
		}
	}

	class DBDeltaData {
		Object t;
		DBDeltaEntity token;

		MiNDValType mvt;
		MiNDCollType mct;

		Object val;
		Object key;

		public DBDeltaData() {
			t = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_VISITINFO_TOKEN);

			token = getDeltaEntity(t);

			Object vt = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_VALUE_TYPE);
			Object ct = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_VALUE_COLLTYPE);

			mvt = (MiNDValType) GisToolsTokenTranslator.toEnum((MiNDToken) vt);
			Object valKey = null;
			switch ( mvt ) {
			case Int:
				valKey = MTMEMBER_VALUE_INT;
				break;
			case Link:
				valKey = MTMEMBER_VALUE_LINK;
				break;
			case Raw:
				valKey = MTMEMBER_VALUE_RAW;
				break;
			case Real:
				valKey = MTMEMBER_VALUE_REAL;
				break;
			}
			val = Giskard.access(MiNDAccessCommand.Get, null, hRoot, valKey);

			mct = (MiNDCollType) GisToolsTokenTranslator.toEnum((MiNDToken) ct);
			Object keyKey = null;
			switch ( mct ) {
			case Arr:
				keyKey = MTMEMBER_VISITINFO_KEYARR;
				break;
			case Map:
				keyKey = MTMEMBER_VISITINFO_KEYMAP;
				break;
			default:
				break;
			}

			key = (null == keyKey) ? null : Giskard.access(MiNDAccessCommand.Get, null, hRoot, keyKey);
			if ( key instanceof MiNDToken ) {
				key = ((MiNDToken) key).getEntity();
			}
		}

		@Override
		public String toString() {
			StringBuilder sb = GiskardUtils.sbAppend(null, " ", true, "Token:", t, ", ValType:", mvt, ", CollType:", mct,
					", Value: [", val, "]");

			if ( null != key ) {
				GiskardUtils.sbAppend(null, " ", true, ", key: ", key);
			}

			return sb.toString();
		}

		public void setStParams(PreparedStatement psData, PreparedStatement psText, DBDeltaEntity de, Integer commitId)
				throws Exception {
			PreparedStatement ps;

			if ( val instanceof String ) {
				ps = psText;
				ps.clearParameters();

				ps.setInt(DbText.Token.ordinal(), token.pKey);
				setInt(key, (mct == MiNDCollType.Map), ps, DbText.OptKey.ordinal());

				ps.setInt(DbText.Language.ordinal(), 0);
				ps.setString(DbText.Text.ordinal(), (String) val);

				ps.setInt(DbText.Entity.ordinal(), de.pKey);
				ps.setNull(DbText.LastValid.ordinal(), Types.INTEGER);
			} else {
				ps = psData;
				ps.clearParameters();

				ps.setNull(DbData.ValInteger.ordinal(), Types.INTEGER);
				ps.setNull(DbData.ValReal.ordinal(), Types.REAL);
				ps.setNull(DbData.ValLink.ordinal(), Types.INTEGER);

				switch ( mvt ) {
				case Int:
					setInt(val, false, ps, DbData.ValInteger.ordinal());
					break;
				case Link:
					setInt(val, true, ps, DbData.ValLink.ordinal());
					break;
				case Real:
					ps.setDouble(DbData.ValReal.ordinal(), (Double) val);
					break;
				case Raw:
					return;
				}

				ps.setInt(DbData.Token.ordinal(), token.pKey);
				setInt(key, (mct == MiNDCollType.Map), ps, DbData.OptKey.ordinal());

				ps.setInt(DbData.Entity.ordinal(), de.pKey);
				ps.setNull(DbData.LastValid.ordinal(), Types.INTEGER);
			}

			ps.addBatch();
		}

		public void setInt(Object src, boolean link, PreparedStatement ps, int col) throws SQLException {
			Integer value = optResolve(link, src);
			if ( null == value ) {
				ps.setNull(col, Types.INTEGER);
			} else {
				ps.setInt(col, value);
			}
		}

		public Integer optResolve(boolean link, Object val) {
			if ( !link || (null == val) ) {
				return (Integer) val;
			}

			DBDeltaEntity mk = factEntities.peek((Integer) val);
			return ((null == mk) || (null == mk.pKey)) ? null : mk.pKey;
		}
	}

	Object hRoot = MTMEMBER_ACTION_DIALOG;

	GisCollFactory<Object, DBDeltaEntity> factEntities = new GisCollFactory<Object, DBDeltaEntity>(true,
			new MiNDCreator<Object, DBDeltaEntity>() {
				@Override
				public DBDeltaEntity create(Object key) {
					return new DBDeltaEntity(key);
				}
			});

	GisCollFactory<Object, DBDeltaEntity> factUnits = new GisCollFactory<Object, DBDeltaEntity>(true,
			new MiNDCreator<Object, DBDeltaEntity>() {
				@Override
				public DBDeltaEntity create(Object key) {
					return factEntities.get(key);
				}
			});

	@Override
	public String toString() {
		StringBuilder sb = null;
		for (Object k : factEntities.keys()) {
			sb = GiskardUtils.sbAppend(sb, "\n---------\n", true, factEntities.peek(k));
		}
		return sb.toString();
	}

	public void step() throws Exception {
		Object entity = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_LINK_ONE);

		if ( null == entity ) {
			save();
		} else {
			DBDeltaEntity de = getDeltaEntity(entity);

			if ( null != de ) {
				de.addData();
			}
		}
	}

	/******** Collecting data ********/

	private DBDeltaEntity getDeltaEntity(Object entity) {
		DBDeltaEntity de = null;

		Object unit = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_ENTITY_STOREUNIT);

		if ( null != unit ) {
			DBDeltaEntity du = factUnits.get(unit);
			de = factEntities.get(entity);
			de.unit = du;
		}

		return de;
	}

	/******** Meta validation ********/

	private void validateDB(DustJdbcMetaInfo metaInfo) throws Exception {
		boolean error = false;

		for (DbTable tbl : DbTable.values()) {
			String tblName = tbl.name();
			if ( null == metaInfo.peekTable(tblName) ) {
				Giskard.log(MiNDEventLevel.Error, "Missing table", tblName);
				error = true;
			} else {
				error = checkColumns(metaInfo, error, tbl, tblName);
			}
		}

		for (DBView view : DBView.values()) {
			String viewName = view.name();

			if ( null == metaInfo.peekTable(viewName) ) {
				Giskard.log(MiNDEventLevel.Error, "Missing view", view);
				error = true;
			} else {
				if ( null == metaInfo.peekColumn(viewName, "Unit") ) {
					Giskard.log(MiNDEventLevel.Error, "Missing column Unit in", viewName);
					error = true;
				}
				error = checkColumns(metaInfo, error, view.tbl, viewName);
			}
		}

		if ( error ) {
			GiskardException.wrap(null, "Database verification failed!");
		} else {
			Giskard.log(MiNDEventLevel.Info, "Database verification successful.");
		}
	}

	private boolean checkColumns(DustJdbcMetaInfo metaInfo, boolean error, DbTable tbl, String tblName) {
		for (Object col : tbl.fldEnum.getEnumConstants()) {
			Object hCol = metaInfo.peekColumn(tblName, col.toString());
			if ( null == hCol ) {
				Giskard.log(MiNDEventLevel.Error, "Missing column", col, "in", tblName);
				error = true;
			}
		}
		return error;
	}

	/******** Loading ********/

	class DBContent {
		boolean empty = false;
		
		Set<Object> unitIds = new HashSet<>();

		public DBContent(Connection conn, DBView... views) throws Exception {
			StringBuilder sbKey = null;
			StringBuilder sbName = null;
			boolean keyOK = true;

			Statement stmt = conn.createStatement();
			String sql;
			ResultSet rs;

			Giskard.log(MiNDEventLevel.Trace, "---- Loading units ----");

			for (Object o : factUnits.keys()) {
				unitIds.add(o);
				if ( keyOK ) {
					Integer k = Giskard.access(MiNDAccessCommand.Get, null, o, MTMEMBER_ROW_PRIMARYKEY);
					if ( null == k ) {
						keyOK = false;
					} else {
						sbKey = GiskardUtils.sbAppend(sbKey, ", ", true, "'" + k + "'");
					}
				}
				String n = Giskard.access(MiNDAccessCommand.Get, null, o, MTMEMBER_PLAIN_STRING);
				sbName = GiskardUtils.sbAppend(sbName, ", ", true, "'" + n + "'");
//				Giskard.log(MiNDEventLevel.Trace, factEntities.peek(o));
			}

			if ( !keyOK ) {
				sql = "select distinct Entity from " + DbTable.dust_text + " WHERE Text in (" + sbName + ")";
				Giskard.log(MiNDEventLevel.Trace, "SQL:", sql);
				rs = stmt.executeQuery(sql);
				sbKey = null;

				if ( rs.first() ) {
					do {
						sbKey = GiskardUtils.sbAppend(sbKey, ", ", true, "'" + rs.getInt(1) + "'");
					} while (rs.next());
				}				
			}
			
			empty = (null == sbKey);

			for (DBView v : views) {
				sql = "SELECT * FROM " + v + " WHERE Unit in (" + sbKey + ")";
				Giskard.log(MiNDEventLevel.Trace, "SQL:", sql);
				rs = stmt.executeQuery(sql);
				
				DustJdbcUtils.dumpResultSet(rs);
			}
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
	}

	public void initConn() throws Exception {
		hDBConn = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_THIS, MTMEMBER_DBUSER_CONN);
		conn = DustJdbcUtils.optCreateConn(hDBConn, conn);

		if ( null == metaInfo ) {
			metaInfo = new DustJdbcMetaInfo(hDBConn, conn);
			validateDB(metaInfo);
		}
	}

	public void load() throws Exception {
	}

	/******** Saving ********/
	public void save() throws Exception {
		initConn();

		DBContent dbc = new DBContent(conn, DBView.dust_unit_entities, DBView.dust_unit_state, DBView.dust_unit_dates,
				DBView.dust_unit_res);

		boolean change = false;

		ArrayList<DBDeltaEntity> newEntities = new ArrayList<>();

		for (Object e : factEntities.keys()) {
			DBDeltaEntity de = factEntities.peek(e);

			if ( null == Giskard.access(MiNDAccessCommand.Get, null, e, MTMEMBER_ROW_PRIMARYKEY) ) {
				change = true;
				newEntities.add(de);
			}

			if ( !de.data.isEmpty() ) {
				change = true;
			}
		}

		change &= dbc.empty;

		if ( change ) {
			boolean ac = conn.getAutoCommit();

			try {
				conn.setAutoCommit(false);

				PreparedStatement psNewEntity = DbTable.dust_entity.getInsertStatement(conn);
				ResultSet res;

				Integer commitId = -1;
				Object chg = Giskard.access(MiNDAccessCommand.Get, MTTYPE_COMMIT);
				DBDeltaEntity deCommit = factEntities.get(chg);

				deCommit.setStParams(psNewEntity, commitId);
				psNewEntity.execute();
				res = psNewEntity.getGeneratedKeys();
				res.first();
				commitId = deCommit.loadDbId(res);

				ArrayList<DBDeltaEntity> entUpdate = new ArrayList<>();
				entUpdate.add(deCommit);

				if ( !newEntities.isEmpty() ) {
					for (DBDeltaEntity de : newEntities) {
						if ( !de.setStParams(psNewEntity, commitId) ) {
							entUpdate.add(de);
						}
						psNewEntity.addBatch();
					}

					psNewEntity.executeBatch();

					res = psNewEntity.getGeneratedKeys();
					res.first();
					for (DBDeltaEntity de : newEntities) {
						de.loadDbId(res);
						res.next();
					}
				}

				if ( !entUpdate.isEmpty() ) {
					PreparedStatement psEntUpdate = DbTable.dust_entity.getUpdateStatement(conn);

					for (DBDeltaEntity de : entUpdate) {
						Integer dbid = Giskard.access(MiNDAccessCommand.Get, -1, de.entity, MTMEMBER_ROW_PRIMARYKEY);
						if ( -1 != dbid ) {
							de.setStParams(psEntUpdate, commitId);
							psEntUpdate.setInt(5, dbid);
							psEntUpdate.addBatch();
						}
					}

					psEntUpdate.executeBatch();
				}

				PreparedStatement psData = DbTable.dust_data.getInsertStatement(conn);
				PreparedStatement psText = DbTable.dust_text.getInsertStatement(conn);

				for (Object e : factEntities.keys()) {
					DBDeltaEntity de = factEntities.peek(e);

					for (DBDeltaData dd : de.data) {
						dd.setStParams(psData, psText, de, commitId);
					}
				}

				psData.executeBatch();
				psText.executeBatch();

				conn.commit();
			} finally {
				conn.setAutoCommit(ac);
			}
		}
	}

}
