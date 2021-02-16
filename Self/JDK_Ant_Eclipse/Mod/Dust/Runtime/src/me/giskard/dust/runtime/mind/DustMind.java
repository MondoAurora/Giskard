package me.giskard.dust.runtime.mind;

import me.giskard.Mind;
import me.giskard.dust.runtime.DustCollections;
import me.giskard.dust.runtime.DustConsts;

public class DustMind extends Mind implements DustConsts, DustCollections {
	
	DustLog log;
	MiNDContext ctxTemp;
	
	public DustMind() {
		log = new DustLog();
	}

	@Override
	public void initContext() {
		if ( null == ctxTemp ) {
			try {
				ctxTemp = (MiNDContext) Class.forName("me.giskard.dust.runtime.data.DustDataContext").newInstance();
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
	
	@Override
	public String toString() {
		return ctxTemp.toString();
	}
}
