package me.giskard.dust.runtime;

import me.giskard.Giskard;
import me.giskard.coll.MindCollConsts;

public class DustRuntime extends Giskard implements MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustRuntime {
	
	DustRuntimeLog log;
	DustMachine machine;
	
	public DustRuntime() {
		log = new DustRuntimeLog();
		setImplementation(this);
		machine = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_MACHINE);
	}

	@Override
	public void init(String mindModule, MiNDAgent agent) throws Exception {
		machine.init(mindModule, agent);
	}

	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, String name, Object... params) {
		return machine.getContext().defineToken(type, name, params);
	}
	
	@Override
	protected void selectByPath_(MiNDToken target, Object... path) {
		machine.getContext().selectByPath(target, path);
	}
	
	@Override
	protected <RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		return machine.getContext().access(cmd, val, target, valPath);
	}
	
	@Override
	protected void addModule_(String modName, String ver) throws Exception {
		machine.addModule(modName, ver);
	}
	
	@Override
	protected MiNDResultType invoke_(Object... agentPath) throws Exception {
		return machine.invoke(agentPath);
	}

	@Override
	protected void log_(MiNDEventLevel lvl, Object... obs) {
		log.log(lvl, obs);
	}
	
	@Override
	public String toString() {
		return machine.getContext().toString();
	}
}
