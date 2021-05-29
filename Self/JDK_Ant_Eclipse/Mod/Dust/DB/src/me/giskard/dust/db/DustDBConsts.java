package me.giskard.dust.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGuard;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensTemp;
import me.giskard.tokens.DustTokensText;

public interface DustDBConsts extends GiskardConsts, DustTokensMind, DustTokensGeneric, DustTokensText, DustTokensIO, 
DustTokensDB, DustTokensGuard, DustTokensTemp {

	enum DbEntity {
		EntityId, Unit, PrimaryType, LastChange, LastValid
	}
	
	enum DbData {
		DataId, Entity, Token, OptKey, 
		ValInteger, ValReal, ValLink,
		LastValid
	}

	enum DbEvent {
		EventId, Entity, Type, Level, Start, End, LastValid
	}

	enum DbText {
		TextId, Entity,  Token, OptKey, Language, Text, LastValid
	}

	enum DbStream {
		StreamId, Entity, Type, Content, LastValid
	}

	enum DbTable {
		dust_entity(DbEntity.class), dust_data(DbData.class), dust_event(DbEvent.class), dust_text(DbText.class), dust_stream(DbStream.class);
		
		public final Class<?> fldEnum;
		public final int dataCount;
		public final String sqlInsert;
		public final String sqlUpdate;

		private DbTable(Class<?> fldEnum) {
			this.fldEnum = fldEnum;
			
			StringBuilder sbFld = null;
			StringBuilder sbPar = null;
			StringBuilder sbSet = null;
			
			Object[] ec = fldEnum.getEnumConstants();
			dataCount = ec.length - 1;
			
			for ( int i = 1; i <= dataCount; ++i ) {
				sbFld = GiskardUtils.sbAppend(sbFld, ", ", true, ec[i]);
				sbPar = GiskardUtils.sbAppend(sbPar, ", ", true, "?");
				sbSet = GiskardUtils.sbAppend(sbSet, ", ", true, ec[i] + " = ?");
			}
			
			sqlInsert = "INSERT INTO " + name() + " (" + sbFld + ") VALUES (" + sbPar + ")";
			sqlUpdate = "UPDATE " + name() + " SET " + sbSet + " WHERE " + ec[0] + " = ?";
		}
		
		public PreparedStatement getUpdateStatement(Connection conn) throws Exception {
			return conn.prepareStatement(sqlUpdate);
		}

		public PreparedStatement getInsertStatement(Connection conn) throws Exception {
			return conn.prepareStatement(sqlInsert, new String[] { fldEnum.getEnumConstants()[0].toString() });
		}
	}

	enum DBView {
		dust_unit_entities(DbTable.dust_entity), dust_unit_state(DbTable.dust_data), dust_unit_dates(DbTable.dust_event), 
		dust_unit_res(DbTable.dust_text), dust_unit_bin(DbTable.dust_stream);
		
		public final DbTable tbl;

		private DBView(DbTable tbl_) {
			this.tbl = tbl_;
		}

	}
}
