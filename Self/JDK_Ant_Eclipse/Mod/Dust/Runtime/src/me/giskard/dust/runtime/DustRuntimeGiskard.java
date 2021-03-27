package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeGiskard extends Giskard
		implements GisCollConsts, DustRuntimeBootConsts, DustRuntimeBootConsts.DustGiskard {

	DustRuntimeMachine machine;

	DustRuntimeLog log;
	Map<String, DustRuntimeToken> bootTokens = new TreeMap<>();

	public DustRuntimeGiskard() {
		log = new DustRuntimeLog();
		setImplementation(this);
		
		machine = new DustRuntimeMachine();
		
		DustRuntimeContext ctx = machine.getContext();
		for ( Map.Entry<String, DustRuntimeToken> e : bootTokens.entrySet() ) {
			ctx.registerToken(e.getKey(), e.getValue());
		}
	}

	@Override
	public void init(MiNDAgent agent) throws Exception {
		machine.init(agent);
	}

	@Override
	public void afterBoot() throws Exception {
		machine.getNativeConn().loadMappedClasses();
	}

	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, String name, Object... params) {
		if ( null != machine ) {
			return machine.getContext().defineToken(type, name, params);
		} else {
			String id = DustRuntimeToken.buildId(type, name, params);
			DustRuntimeToken ret = bootTokens.get(id);

			if ( null == ret ) {
				ret = DustRuntimeToken.createToken(type, name, params);
				bootTokens.put(id, ret);
			}

			return ret;
		}
	}

	@Override
	protected <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return machine.getContext().access(cmd, val, valPath);
	}

	@Override
	protected void addModule_(String modName, String ver) throws Exception {
		machine.getNativeConn().addModule(modName, ver);
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
