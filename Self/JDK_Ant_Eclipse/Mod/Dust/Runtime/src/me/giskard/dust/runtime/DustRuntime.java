package me.giskard.dust.runtime;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.coll.MindCollConsts;

public class DustRuntime extends Giskard implements MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustRuntime {
	
	DustRuntimeLog log;
	DustContext ctxTemp;
	DustMachine machine;
	
	public DustRuntime() {
		log = new DustRuntimeLog();
		setImplementation(this);
	}

	@Override
	public void initContext() {
		if ( null == ctxTemp ) {
			try {
				Class<?> ctxClass = Class.forName(CLASSNAME_CONTEXT);
				ctxTemp = (DustContext) ctxClass.newInstance();
			} catch (Throwable e) {
				GiskardException.wrap(e);
			}
		}		
	}
	
	@Override
	public void setMachine(DustMachine machine) {
		this.machine = machine;
	}

	protected DustContext getContext() {
		return ctxTemp;
	}
	
	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, String name, Object... params) {
		return getContext().defineToken(type, name, params);
	}
	
	@Override
	protected void selectByPath_(MiNDToken target, Object... path) {
		getContext().selectByPath(target, path);
	}
	
	@Override
	protected <RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		return getContext().access(cmd, val, target, valPath);
	}
	
	@Override
	protected void addModule_(String modName, String ver) throws Exception {
		machine.addModule(modName, ver);
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
