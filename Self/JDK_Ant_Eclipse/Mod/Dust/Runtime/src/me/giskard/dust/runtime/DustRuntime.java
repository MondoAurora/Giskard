package me.giskard.dust.runtime;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.coll.MindCollConsts;

public class DustRuntime extends Giskard implements MindCollConsts, DustRuntimeConsts {
	
	DustLog log;
	MiNDContext ctxTemp;
	
	public DustRuntime() {
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
				GiskardException.wrap(e);
			}
		}
	}

	@Override
	protected MiNDContext getContext() {
		return ctxTemp;
	}
	
	@Override
	protected MiNDResultType invoke_(Object... agentPath) {
		return MiNDResultType.REJECT;
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
