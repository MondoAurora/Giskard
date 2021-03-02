package me.giskard.dust.runtime;

import me.giskard.Mind;
import me.giskard.coll.MindCollConsts;
import me.giskard.utils.MindUtils;

public class DustMind extends Mind implements MindCollConsts, DustRuntimeConsts {
	
	DustLog log;
	MiNDContext ctxTemp;
	
	public DustMind() {
		log = new DustLog();
	}

	@Override
	public void initContext() {
		if ( null == ctxTemp ) {
			try {
				Class<?> ctxClass = Class.forName(CLASSNAME_CONTEXT);
				ctxTemp = (MiNDContext) ctxClass.newInstance();
				
				ctxClass.getMethod("boot").invoke(null);
			} catch (Throwable e) {
				MindUtils.wrapException(e);
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
