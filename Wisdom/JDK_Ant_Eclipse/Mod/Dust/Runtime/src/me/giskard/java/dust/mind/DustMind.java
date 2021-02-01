package me.giskard.java.dust.mind;

import java.text.MessageFormat;

import me.giskard.java.Mind;
import me.giskard.java.dust.DustCollections;
import me.giskard.java.dust.DustConsts;

public class DustMind extends Mind implements DustConsts, DustCollections {
	
	class DustEntity implements MiNDEntity {
		public String token;

		public DustEntity(String token) {
			this.token = token;
		}
		
		// may be needed for refactor to keep the instance but change the key
		void setToken(String token) {
			this.token = token;
		}
		
		@Override
		public String toString() {
			return token;
		}
	}
	
	private final DustCreator<String, DustEntity> entityCreator = new DustCreator<String, DustEntity>() {
		@Override
		public DustEntity create(String key) {
			return new DustEntity(key);
		}
	};

	DustLog log;
	DustFactory<String, DustEntity> entities = new DustFactory<>(true, entityCreator);
	MiNDContext ctxTemp;
	
	public DustMind() {
		log = new DustLog();
		try {
			ctxTemp = (MiNDContext) Class.forName("me.giskard.java.dust.data.DustDataContext").newInstance();
		} catch (Exception e) {
			Mind.wrapException(e);
		}
	}

	@Override
	protected MiNDEntity getEntity_(Object unitName, Object entityId) {
		return entities.get(MessageFormat.format(FMT_ENTITY_ID, unitName, entityId));
	}

	@Override
	protected MiNDContext getContext() {
		return ctxTemp;
	}

	@Override
	protected void log_(MiNDEntity lvl, Object... obs) {
		log.log(lvl, obs);
	}
	
	@Override
	protected void init(String[] args) {
		LOGLEVEL_EXCEPTION = Mind.getEntity("Event", "EventLvlCritical");

		MiNDEntity t = LOGLEVEL_EXCEPTION;
		Mind.log(t, "Hello, world! ");
	}
}
