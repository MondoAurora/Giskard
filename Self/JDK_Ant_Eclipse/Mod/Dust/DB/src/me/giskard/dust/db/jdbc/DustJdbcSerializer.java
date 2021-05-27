package me.giskard.dust.db.jdbc;

import java.sql.Connection;
import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts.MiNDCreator;
import me.giskard.coll.GisCollFactory;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustJdbcSerializer implements DustJdbcConsts {

	class DBDeltaEntity {
		Object entity;

		DBDeltaEntity unit;
		String dbId;

		ArrayList<DBDeltaData> data = new ArrayList<>();

		public DBDeltaEntity(Object entity_) {
			this.entity = entity_;
			dbId = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_ENTITY_STOREID);
		}

		void addData() {
			data.add(new DBDeltaData());
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

	
	
	
	
	public DustJdbcSerializer(DustJdbcMetaInfo metaInfo) throws Exception {
		validateDB(metaInfo);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = null;
		for (Object k : factEntities.keys()) {
			sb = GiskardUtils.sbAppend(sb, "\n---------\n", true, factEntities.peek(k));
		}
		return sb.toString();
	}

	public void step(Object hDBConn, Connection conn) throws Exception {
		Object entity = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_LINK_ONE);

		if ( null == entity ) {
			save(hDBConn, conn);
		} else {
			DBDeltaEntity de = getDeltaEntity(entity);

			if ( null != de ) {
				de.addData();
			}
		}
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

	/******** Loading ********/
	
	public void load(Object hDBConn, Connection conn) throws Exception {
	}

	/******** Saving ********/
	public void save(Object hDBConn, Connection conn) throws Exception {
		Giskard.log(MiNDEventLevel.Trace, this);
	}

	
}
