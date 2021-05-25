package me.giskard.dust.db;

import me.giskard.GiskardConsts;
import me.giskard.tokens.DustTokensDB;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensGuard;
import me.giskard.tokens.DustTokensIO;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensText;

public interface DustDBConsts extends GiskardConsts, DustTokensMind, DustTokensGeneric, DustTokensText, DustTokensIO, DustTokensDB, DustTokensGuard {

	enum DbEntity {
		EntityId, Unit, PrimaryType, LastChange
	}
	
	enum DbData {
		DataId, Entity, LastValid, Token, 
		ValInteger, ValReal, ValLink,
		OptKey, OptIdx
	}

	enum DbEvent {
		EventId, Entity, LastValid, Type, Level, Start, End
	}

	enum DbText {
		TextId, Entity, LastValid, Language, Text
	}

	enum DbStream {
		StreamId, Entity, LastValid, Type, Content
	}

	enum DbTable {
		dust_entity(DbEntity.class), dust_data(DbData.class), dust_event(DbEvent.class), dust_text(DbText.class), dust_stream(DbStream.class);
		
		public final Class<?> fldEnum;

		private DbTable(Class<?> fldEnum) {
			this.fldEnum = fldEnum;
		}
	}

}
