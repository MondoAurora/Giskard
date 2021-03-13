package me.giskard.dust.runtime;

import me.giskard.Giskard;
import me.giskard.coll.MindCollConsts;

public class DustRuntimeGiskard extends Giskard implements MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustRuntime {
	
	private DustContext knowledge;
	DustMachine machine;
	DustRuntimeLog log;
	
	public DustRuntimeGiskard() {
		log = new DustRuntimeLog();
		setImplementation(this);
		knowledge = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_CONTEXT);
		machine = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_MACHINE);
	}

	@Override
	public void init(MiNDAgent agent) throws Exception {
		machine.init(knowledge, agent);
	}

	@Override
	public void afterBoot() throws Exception {
		machine.optLoadNativeConn();
	}

	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, String name, Object... params) {
		return machine.getContext().defineToken(type, name, params);
	}
	
	@Override
	protected boolean selectByPath_(MiNDToken target, Object... path) throws Exception {
		return machine.getContext().selectByPath(target, path);
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
		return machine.invoke();
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
