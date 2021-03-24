package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.coll.MindCollConsts;
import me.giskard.dust.runtime.DustRuntimeMeta.DustToken;
import me.giskard.dust.runtime.knowledge.DustKnowledgeContext;
import me.giskard.dust.runtime.machine.DustMachineAgora;

public class DustRuntimeGiskard extends Giskard
		implements MindCollConsts, DustRuntimeConsts, DustRuntimeConsts.DustGiskard {

	DustMachineAgora machine;

	DustRuntimeLog log;
	Map<String, DustToken> bootTokens = new TreeMap<>();

	public DustRuntimeGiskard() {
		log = new DustRuntimeLog();
		setImplementation(this);
		
		machine = new DustMachineAgora();
		
		DustKnowledgeContext ctx = machine.getContext();
		for ( Map.Entry<String, DustToken> e : bootTokens.entrySet() ) {
			ctx.registerToken(e.getKey(), e.getValue());
		}
	}

	@Override
	public void init(MiNDAgent agent) throws Exception {
		machine.init(agent);
	}

	@Override
	public void afterBoot() throws Exception {
		machine.optLoadNativeConn();
	}

	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, String name, Object... params) {
		if ( null != machine ) {
			return machine.getContext().defineToken(type, name, params);
		} else {
			String id = DustToken.buildId(type, name, params);
			DustToken ret = bootTokens.get(id);

			if ( null == ret ) {
				ret = DustToken.createToken(type, name, params);
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
