package me.giskard.dust.mind;

import me.giskard.Mind;
import me.giskard.dust.DustCollections;
import me.giskard.dust.DustConsts;

public class DustMind extends Mind implements DustConsts, DustCollections {
	
	private final DustCreator<String, DustToken> entityCreator = new DustCreator<String, DustToken>() {
		@Override
		public DustToken create(String key) {
			return new DustToken(key);
		}
	};

	DustLog log;
	DustFactory<String, DustToken> entities = new DustFactory<>(true, entityCreator);
	MiNDContext ctxTemp;
	
	public DustMind() {
		log = new DustLog();
	}

	@Override
	public void initContext() {
		if ( null == ctxTemp ) {
			try {
				ctxTemp = (MiNDContext) Class.forName("me.giskard.dust.data.DustDataContext").newInstance();
			} catch (Throwable e) {
				Mind.wrapException(e);
			}
		}
	}

	@Override
	protected MiNDContext getContext() {
		return ctxTemp;
	}

	@Override
	protected void log_(MiNDEventLevel lvl, Object... obs) {
		log.log(lvl, obs);
	}
}
