package me.giskard.dust.mind;

import java.text.MessageFormat;

import me.giskard.Mind;
import me.giskard.dust.DustCollections;
import me.giskard.dust.DustConsts;

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
			ctxTemp = (MiNDContext) Class.forName("me.giskard.dust.data.DustDataContext").newInstance();
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
	protected void log_(MiNDEventLevel lvl, Object... obs) {
		log.log(lvl, obs);
	}
	
	@Override
	protected void init(String[] args) {
		MiNDEntity t = Mind.getEntity("Test", "Test01");
		Mind.log(MiNDEventLevel.INFO, "Hello, world! ", t);
	}
}
